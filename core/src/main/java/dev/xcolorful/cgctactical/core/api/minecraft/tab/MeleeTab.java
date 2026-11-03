package dev.xcolorful.cgctactical.core.api.minecraft.tab;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.item.builder.MeleeBuilder;
import dev.xcolorful.cgctactical.core.api.item.melee.MeleeProperty;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
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

public class MeleeTab {

    public static Comparator<Map.Entry<ResourceLocation, MeleeIndexInstance>> indexSort() {
        return Comparator.comparingInt(entry -> entry.getValue().getPojo().getSlotSort());
    }

    public static List<ItemStack> buildMeleeItems() {
        List<ItemStack> meleeItems = new ArrayList<>();
        ResourceApi.getAllMeleeIndexInstance().stream().sorted(indexSort()).forEach(entry -> {
            @Nullable MeleeIndexInstance meleeIndexInstance = entry.getValue();

            MeleeIndex meleeIndex = meleeIndexInstance.getPojo();
            if (meleeIndex.getHideInGame()) {
                return;
            }

            {
                ItemStack meleeItem = buildMeleeItem(entry.getKey());
                meleeItems.add(meleeItem);
            }
        });
        return meleeItems;
    }

    public static ItemStack buildMeleeItem(ResourceLocation meleeLocation) {
        return MeleeBuilder.create(ModItems.MELEE.get())
                // 近战ResourceLocation
                .setProperty(MeleeProperty.MELEE_LOCATION,
                        ResourceLocation.class,
                        meleeLocation)
                .build();
    }

    public static @NotNull ResourceLocation MELEE_ICON_LOCATION;
    static {
        IMcRegistry mcRegistry = CustomGun.getMcRegistry();
        MELEE_ICON_LOCATION = mcRegistry.createResourceLocation(String.format("%s:%s", CgcTactical.MOD_ID, "melee_icon"));
    }

    public static ItemStack buildMeleeTabItem() {
        if (true) return ItemStack.EMPTY;
        // mixin注入点
        return buildMeleeItem(MELEE_ICON_LOCATION);
    }
}
