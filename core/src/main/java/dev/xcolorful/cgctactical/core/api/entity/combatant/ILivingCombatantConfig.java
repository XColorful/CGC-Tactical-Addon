package dev.xcolorful.cgctactical.core.api.entity.combatant;

public interface ILivingCombatantConfig {

    default boolean cgctactical$enableDefaultLivingCombatantTick() {
        return false;
    }
}
