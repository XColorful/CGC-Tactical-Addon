package dev.xcolorful.cgctactical.core.api.item;

import dev.xcolorful.cgctactical.core.api.item.consumable.IConsumableDataAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IConsumable extends IConsumableDataAccess,
        ITacticalItem {

    // --------ITacticalItem--------

    @Override
    default boolean silentBreak() {
        return false;
    }

    // --------IPojoItem--------

    @Override
    default @NotNull ResourceLocation getPojoLocation(ItemStack consumableItem) {
        return this.getConsumableLocation(consumableItem);
    }
    @Override
    default void setPojoLocation(ItemStack gunItem, ResourceLocation consumableLocation) {
        this.setConsumableLocation(gunItem, consumableLocation);
    }

    @Override
    default @NotNull ResourceLocation getPojoDisplayLocation(ItemStack consumableItem) {
        return this.getConsumableDisplayLocation(consumableItem);
    }
    @Override
    default void setPojoDisplayLocation(ItemStack gunItem, ResourceLocation consumableDisplayLocation) {
        this.setConsumableDisplayLocation(gunItem, consumableDisplayLocation);
    }
}
