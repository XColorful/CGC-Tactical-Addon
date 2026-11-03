package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.cgctactical.core.api.item.tactical.ITacticalDataAccess;
import net.minecraft.world.item.ItemStack;

public interface IMeleeDataAccess extends ITacticalDataAccess {

    int getMeleeCooldown(ItemStack meleeItem, MeleeAction meleeAction, int index);

    // --------Deprecated--------

    @SuppressWarnings("deprecation")
    @Override
    @Deprecated default int getAttackCoolDown(ItemStack meleeItem, MeleeAction meleeAction, int index) {
        return getMeleeCooldown(meleeItem, meleeAction, index);
    }
}
