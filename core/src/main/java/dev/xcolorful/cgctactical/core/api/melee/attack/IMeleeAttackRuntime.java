package dev.xcolorful.cgctactical.core.api.melee.attack;

import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public interface IMeleeAttackRuntime {

    /**
     * 战斗生物攻击时触发
     */
    @NotNull CombatantAttackResult combatantAttack(Object combatantProperty, // TODO CombatantProperty
                                                   @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                                                   ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                                                   TacticalAction tacticalAction, int combo,
                                                   List<Entity> meleeVictims);
    enum CombatantAttackResult {
        SUCCESS(0),
        ERROR(0),
        // ----以下按判定优先级排序----
        ;

        public final int priority;
        CombatantAttackResult(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        public boolean isSuccess() {
            return this == SUCCESS;
        }
    }
    /**
     * 近战攻击时触发，针对单个目标
     * @return result掩码，{@code 0}表示无结果 (事件取消等)，非 {@code 0} 时再使用 {@link MeleeAttackResult#fromBitmap(int)} 读取
     */
    int meleeAttack(Object combatantProperty, // TODO CombatantProperty
                    @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                    ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                    float damage, float knockback,
                    Entity meleeVictim);
    /**
     * 设计同 {@link dev.xcolorful.customgun.client.api.item.gun.GunTooltipMask}
     * bitmap 为 0 时为空
     */
    enum MeleeAttackResult implements ResourceTag.MaskTag {
        MISS(0),
        HIT(1),
        CRIT(2),
        KILL(3),
        ;

        public final String maskName;
        private final int mask;
        MeleeAttackResult(int ordinal) {
            this.maskName = this.name().toLowerCase();
            this.mask = 1 << ordinal;
        }
        @Override
        public String getTagName() {
            return this.maskName;
        }
        @Override
        public int getMask() {
            return this.mask;
        }

        private static final MeleeAttackResult[] VALUES = values();

        public static EnumSet<MeleeAttackResult> fromBitmap(int bitmap) {
            EnumSet<MeleeAttackResult> set = EnumSet.noneOf(MeleeAttackResult.class);
            for (MeleeAttackResult value : VALUES) {
                if ((bitmap & value.mask) != 0) {
                    set.add(value);
                }
            }
            return set;
        }

        public static int toBitmap(EnumSet<MeleeAttackResult> set) {
            int bitmap = 0;
            for (MeleeAttackResult value : VALUES) {
                if (set.contains(value)) {
                    bitmap |= value.mask;
                }
            }
            return bitmap;
        }
    }

    // --------Deprecated--------

    @Deprecated default void attack(Player livingCombatant,
                                    ItemStack meleeItem,
                                    TacticalAction tacticalAction, List<Entity> meleeVictims, int combo) {
        ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(livingCombatant);
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return;
        this.combatantAttack(null, iMelee, meleeItem, iLivingCombatant, livingCombatant, tacticalAction, combo, meleeVictims);
    }
    @Deprecated default EnumSet<MeleeAttackResult> performAttack(Player livingCombatant,
                                                                 Entity meleeVictim,
                                                                 ItemStack meleeItem,
                                                                 float damage, float knockback) {
        ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(livingCombatant);
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return EnumSet.noneOf(MeleeAttackResult.class);
        return MeleeAttackResult.fromBitmap(this.meleeAttack(null, iMelee, meleeItem, iLivingCombatant, livingCombatant, damage, knockback, meleeVictim));
    }
    // TODO 改到 data (ResourcePojo) 或 config 里
    @Deprecated default boolean canSprintingAttack() {
        return true;
    }
}
