package dev.xcolorful.cgctactical.core.melee.attack;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackManager;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackRuntime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
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
    public @NotNull IMeleeAttackRuntime.CombatantAttackResult combatantAttack(CombatantProperty combatantProperty,
                                                                              @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                                                                              ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                                                                              TacticalAction tacticalAction, int combo,
                                                                              List<Entity> meleeVictims) {
        return _DefaultMeleeAttack.combatantAttack(combatantProperty,
                iMelee, meleeItem,
                iLivingCombatant, livingCombatant,
                tacticalAction, combo,
                meleeVictims);
    }

    @Override
    public int meleeAttack(CombatantProperty combatantProperty,
                           @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                           ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                           float damage, float knockback,
                           Entity meleeVictim) {
        EnumSet<MeleeAttackResult> results = _DefaultMeleeAttack.meleeAttack(combatantProperty,
                iMelee, meleeItem,
                iLivingCombatant, livingCombatant,
                damage, knockback,
                meleeVictim);
        return MeleeAttackResult.toBitmap(results);
    }

    @ApiStatus.Internal
    public static class CriticalHitContext {
        public final LivingEntity livingCombatant;
        public final Entity meleeVictim;
        public boolean vanillaCritical;
        public float damageModifier;

        public CriticalHitContext(LivingEntity livingCombatant, Entity meleeVictim, boolean vanillaCritical, float damageModifier) {
            this.livingCombatant = livingCombatant;
            this.meleeVictim = meleeVictim;
            this.vanillaCritical = vanillaCritical;
            this.damageModifier = damageModifier;
        }
    }
    @ApiStatus.Internal
    public static @Nullable CriticalHitContext onCriticalHit(CriticalHitContext criticalHitContext) {
        // mixin注入点
        return criticalHitContext;
    }
}
