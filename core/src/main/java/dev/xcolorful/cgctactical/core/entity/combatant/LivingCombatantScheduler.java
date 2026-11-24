package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.client.entity.combatant.LocalCombatantScheduler;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import net.minecraft.world.entity.LivingEntity;

public class LivingCombatantScheduler {

    public static void tickScheduler(LivingEntity livingCombatant,
                                     CombatantProperty combatantProperty) {
        McLogicalSide logicalSide = livingCombatant.level().isClientSide() ? McLogicalSide.CLIENT : McLogicalSide.SERVER;
        if (logicalSide.isClient()) LocalCombatantScheduler.tickScheduler(livingCombatant, combatantProperty);
    }
}
