package dev.xcolorful.cgctactical.core.api.item;

import dev.xcolorful.cgctactical.core.api.item.consumable.IConsumableDataAccess;
import net.minecraft.resources.Identifier;
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
    default @NotNull Identifier getPojoLocation(ItemStack consumableItem) {
        return this.getConsumableLocation(consumableItem);
    }
    @Override
    default void setPojoLocation(ItemStack gunItem, Identifier consumableLocation) {
        this.setConsumableLocation(gunItem, consumableLocation);
    }

    @Override
    default @NotNull Identifier getPojoDisplayLocation(ItemStack consumableItem) {
        return this.getConsumableDisplayLocation(consumableItem);
    }
    @Override
    default void setPojoDisplayLocation(ItemStack gunItem, Identifier consumableDisplayLocation) {
        this.setConsumableDisplayLocation(gunItem, consumableDisplayLocation);
    }
}
