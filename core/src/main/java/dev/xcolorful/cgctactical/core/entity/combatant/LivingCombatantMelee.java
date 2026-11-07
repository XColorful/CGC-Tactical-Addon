package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import net.minecraft.world.entity.LivingEntity;

public final class LivingCombatantMelee extends LivingCombatantAspect {

    public LivingCombatantMelee(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        super(livingCombatant, combatantProperty);
    }

    public boolean meleeCharge() {
        // TODO
        return false;
    }

    public void meleeAttack() {
        // TODO
    }
}
