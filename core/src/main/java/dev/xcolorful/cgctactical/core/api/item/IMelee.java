package dev.xcolorful.cgctactical.core.api.item;

import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeDataAccess;
import dev.xcolorful.cgctactical.core.api.melee.IMeleeRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IMelee extends IMeleeRuntime,
        IMeleeDataAccess,
        ITacticalItem {

    // --------IPojoItem--------

    @Override
    default @NotNull ResourceLocation getPojoLocation(ItemStack meleeItem) {
        return this.getMeleeLocation(meleeItem);
    }
    @Override
    default void setPojoLocation(ItemStack gunItem, ResourceLocation meleeLocation) {
        this.setMeleeLocation(gunItem, meleeLocation);
    }

    @Override
    default @NotNull ResourceLocation getPojoDisplayLocation(ItemStack meleeItem) {
        return this.getMeleeDisplayLocation(meleeItem);
    }
    @Override
    default void setPojoDisplayLocation(ItemStack gunItem, ResourceLocation meleeDisplayLocation) {
        this.setMeleeDisplayLocation(gunItem, meleeDisplayLocation);
    }
}
