package dev.xcolorful.cgctactical.core.api.minecraft.tab;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.item.builder.ThrowableBuilder;
import dev.xcolorful.cgctactical.core.api.item.throwable.ThrowableProperty;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.minecraft.IMcRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ThrowableTab {

    public static Comparator<Map.Entry<Identifier, ThrowableIndexInstance>> indexSort() {
        return Comparator.comparingInt(entry -> entry.getValue().getPojo().getSlotSort());
    }

    public static List<ItemStack> buildThrowableItems() {
        List<ItemStack> throwableItems = new ArrayList<>();
        ResourceApi.getAllThrowableIndexInstance().stream().sorted(indexSort()).forEach(entry -> {
            @Nullable ThrowableIndexInstance throwableIndexInstance = entry.getValue();

            ThrowableIndex throwableIndex = throwableIndexInstance.getPojo();
            if (throwableIndex.getHideInGame()) {
                return;
            }

            {
                ItemStack throwableItem = buildThrowableItem(entry.getKey());
                throwableItems.add(throwableItem);
            }
        });
        return throwableItems;
    }

    public static ItemStack buildThrowableItem(Identifier throwableLocation) {
        return ThrowableBuilder.create(ModItems.THROWABLE.get())
                // 投掷物ResourceLocation
                .setProperty(ThrowableProperty.THROWABLE_LOCATION,
                        Identifier.class,
                        throwableLocation)
                .build();
    }

    public static @NotNull Identifier THROWABLE_ICON_LOCATION;
    static {
        IMcRegistry mcRegistry = CustomGun.getMcRegistry();
        THROWABLE_ICON_LOCATION = mcRegistry.createResourceLocation(String.format("%s:%s", CgcTactical.MOD_ID, "throwable_icon"));
    }

    public static ItemStack buildThrowableTabItem() {
        if (true) return ItemStack.EMPTY;
        // mixin注入点
        return buildThrowableItem(THROWABLE_ICON_LOCATION);
    }
}
