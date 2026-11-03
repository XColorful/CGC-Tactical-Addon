package dev.xcolorful.cgctactical.core.api.melee;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackManager;
import dev.xcolorful.cgctactical.core.api.melee.physics.IMeleePhysicsManager;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IMeleeMainManager extends IMeleeSubManager {

    boolean registerRuntimeGroup(MeleeManagerGroup meleeManagerGroup);

    @NotNull MeleeManagerGroup getManagerGroup(String managerGroupTag);

    // --------便利方法--------

    default @NotNull MeleeManagerGroup getManagerGroup(IMelee iMelee, ItemStack meleeItem) {
        return this.getManagerGroup(iMelee.getManagerGroupTag(meleeItem));
    }

    default IMeleeAttackManager getMeleeAttackManager(IMelee iMelee, ItemStack meleeItem) {
        return this.getManagerGroup(iMelee.getManagerGroupTag(meleeItem)).meleeAttackManager();
    }
    default IMeleePhysicsManager getMeleePhysicsManager(IMelee iMelee, ItemStack meleeItem) {
        return this.getManagerGroup(iMelee.getManagerGroupTag(meleeItem)).meleePhysicsManager();
    }
}
