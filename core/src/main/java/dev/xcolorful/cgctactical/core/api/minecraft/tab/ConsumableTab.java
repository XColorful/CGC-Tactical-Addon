package dev.xcolorful.cgctactical.core.api.minecraft.tab;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.item.builder.ConsumableBuilder;
import dev.xcolorful.cgctactical.core.api.item.consumable.ConsumableProperty;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.minecraft.IMcRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ConsumableTab {

    public static Comparator<Map.Entry<ResourceLocation, ConsumableIndexInstance>> indexSort() {
        return Comparator.comparingInt(entry -> entry.getValue().getPojo().getSlotSort());
    }

    public static List<ItemStack> buildConsumableItems() {
        List<ItemStack> consumableItems = new ArrayList<>();
        ResourceApi.getAllConsumableIndexInstance().stream().sorted(indexSort()).forEach(entry -> {
            @Nullable ConsumableIndexInstance consumableIndexInstance = entry.getValue();

            ConsumableIndex consumableIndex = consumableIndexInstance.getPojo();
            if (consumableIndex.getHideInGame()) {
                return;
            }

            {
                ItemStack consumableItem = buildConsumableItem(entry.getKey());
                consumableItems.add(consumableItem);
            }
        });
        return consumableItems;
    }

    public static ItemStack buildConsumableItem(ResourceLocation consumableLocation) {
        return ConsumableBuilder.create(ModItems.CONSUMABLE.get())
                // 消耗品ResourceLocation
                .setProperty(ConsumableProperty.CONSUMABLE_LOCATION,
                        ResourceLocation.class,
                        consumableLocation)
                .build();
    }

    public static @NotNull ResourceLocation CONSUMABLE_ICON_LOCATION;
    static {
        IMcRegistry mcRegistry = CustomGun.getMcRegistry();
        CONSUMABLE_ICON_LOCATION = mcRegistry.createResourceLocation(String.format("%s:%s", CgcTactical.MOD_ID, "consumable_icon"));
    }

    public static ItemStack buildConsumableTabItem() {
        if (true) return ItemStack.EMPTY;
        // mixin注入点
        return buildConsumableItem(CONSUMABLE_ICON_LOCATION);
    }
}
