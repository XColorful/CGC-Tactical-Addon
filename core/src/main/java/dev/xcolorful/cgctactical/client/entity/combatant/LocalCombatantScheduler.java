package dev.xcolorful.cgctactical.client.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import net.minecraft.world.entity.LivingEntity;

public class LocalCombatantScheduler {

    public static void tickScheduler(LivingEntity livingCombatant,
                                     CombatantProperty combatantProperty) {
        if (combatantProperty.clientSchedule.isEmpty()) return;

        combatantProperty.clientSchedule.removeIf(event -> event.onTick(livingCombatant));
    }
}
