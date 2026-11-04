package dev.xcolorful.cgctactical.core.melee.attack;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackManager;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackRuntime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MeleeAttackManager implements IMeleeAttackManager {
    public static final MeleeAttackManager INSTANCE = new MeleeAttackManager();

    protected MeleeAttackManager() {
    }
    public static final String _MANAGER_NAME = String.format("%s:%s", CgcTactical.MOD_ID, MeleeAttackManager.class.getSimpleName());
    @Override public String getManagerName() {
        return _MANAGER_NAME;
    }

    // --------IMeleeAttackRuntime--------

    @Override
    public @NotNull IMeleeAttackRuntime.CombatantAttackResult combatantAttack(Object combatantProperty,
                                                                              @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                                                                              ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                                                                              TacticalAction tacticalAction, int combo,
                                                                              List<Entity> meleeVictims) {
        // TODO 近战攻击逻辑移植
        return CombatantAttackResult.ERROR;
    }

    @Override
    public int meleeAttack(Object combatantProperty,
                           @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                           ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                           float damage, float knockback,
                           Entity meleeVictim) {
        // TODO 近战攻击逻辑移植
        return 0;
    }
}
