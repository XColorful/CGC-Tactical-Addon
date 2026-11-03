package dev.xcolorful.cgctactical.core.api.item;

import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeDataAccess;
import dev.xcolorful.cgctactical.core.api.melee.IMeleeRuntime;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IMelee extends IMeleeRuntime,
        IMeleeDataAccess,
        ITacticalItem {

    // --------ITacticalItem--------

    @Override
    default boolean replaceVanillaAttack() {
        return true;
    }
    @Override
    default boolean replaceVanillaUseItem() {
        return true;
    }

    // --------IPojoItem--------

    @Override
    default @NotNull Identifier getPojoLocation(ItemStack meleeItem) {
        return this.getMeleeLocation(meleeItem);
    }
    @Override
    default void setPojoLocation(ItemStack gunItem, Identifier meleeLocation) {
        this.setMeleeLocation(gunItem, meleeLocation);
    }

    @Override
    default @NotNull Identifier getPojoDisplayLocation(ItemStack meleeItem) {
        return this.getMeleeDisplayLocation(meleeItem);
    }
    @Override
    default void setPojoDisplayLocation(ItemStack gunItem, Identifier meleeDisplayLocation) {
        this.setMeleeDisplayLocation(gunItem, meleeDisplayLocation);
    }
}
