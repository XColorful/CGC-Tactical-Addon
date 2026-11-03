package dev.xcolorful.cgctactical.core.api.item;

import dev.xcolorful.cgctactical.core.api.item.throwable.IThrowableDataAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IThrowable extends IThrowableDataAccess,
        ITacticalItem {

    // --------IPojoItem--------

    @Override
    default @NotNull ResourceLocation getPojoLocation(ItemStack throwableItem) {
        return this.getThrowableLocation(throwableItem);
    }
    @Override
    default void setPojoLocation(ItemStack gunItem, ResourceLocation throwableLocation) {
        this.setThrowableLocation(gunItem, throwableLocation);
    }

    @Override
    default @NotNull ResourceLocation getPojoDisplayLocation(ItemStack throwableItem) {
        return this.getThrowableDisplayLocation(throwableItem);
    }
    @Override
    default void setPojoDisplayLocation(ItemStack gunItem, ResourceLocation throwableDisplayLocation) {
        this.setThrowableDisplayLocation(gunItem, throwableDisplayLocation);
    }
}
