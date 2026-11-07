package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import net.minecraft.world.entity.LivingEntity;

public abstract class LivingCombatantAspect {

    protected final LivingEntity livingCombatant;
    protected final CombatantProperty combatantProperty;

    public LivingCombatantAspect(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        this.livingCombatant = livingCombatant;
        this.combatantProperty = combatantProperty;
    }
}
