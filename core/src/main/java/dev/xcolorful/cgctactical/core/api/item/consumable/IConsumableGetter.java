package dev.xcolorful.cgctactical.core.api.item.consumable;

import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IConsumableGetter {

    static @Nullable IConsumable fromItemStack(@Nullable ItemStack consumableItem) {
        if (consumableItem == null) return null;
        return consumableItem.getItem() instanceof IConsumable iConsumable ? iConsumable : null;
    }
    static @Nullable IConsumable fromMainHand(@Nullable LivingEntity livingEntity) {
        if (livingEntity == null) return null;
        return livingEntity.getMainHandItem().getItem() instanceof IConsumable iConsumable ? iConsumable : null;
    }

    // --------Deprecated--------

    @Deprecated static @Nullable IConsumable of(@Nullable ItemStack consumableItem) {
        return fromItemStack(consumableItem);
    }
}
