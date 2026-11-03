package dev.xcolorful.cgctactical.core.api.item.throwable;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IThrowableGetter {

    static @Nullable IThrowable fromItemStack(@Nullable ItemStack throwableItem) {
        if (throwableItem == null) return null;
        return throwableItem.getItem() instanceof IThrowable iThrowable ? iThrowable : null;
    }
    static @Nullable IThrowable fromMainHand(@Nullable LivingEntity livingEntity) {
        if (livingEntity == null) return null;
        return livingEntity.getMainHandItem().getItem() instanceof IThrowable iThrowable ? iThrowable : null;
    }

    // --------Deprecated--------

    @Deprecated static @Nullable IThrowable of(@Nullable ItemStack throwableItem) {
        return fromItemStack(throwableItem);
    }
}
