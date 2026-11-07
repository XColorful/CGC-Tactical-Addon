package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import net.minecraft.world.entity.LivingEntity;

public final class LivingCombatantCooldown extends LivingCombatantAspect {

    public LivingCombatantCooldown(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        super(livingCombatant, combatantProperty);
    }

    public void tickItemCooldown() {
        // TODO
    }
}
