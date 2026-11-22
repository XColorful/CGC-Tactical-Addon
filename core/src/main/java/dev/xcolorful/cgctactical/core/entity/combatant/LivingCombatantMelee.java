package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.client.entity.combatant.LocalCombatantMelee;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class LivingCombatantMelee extends LivingCombatantAspect {

    public LivingCombatantMelee(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        super(livingCombatant, combatantProperty);
    }

    public boolean meleeCharge(McLogicalSide logicalSide, TacticalAction tacticalAction) {
        if (logicalSide.isClient()) return LocalCombatantMelee.localMeleeCharge(this.livingCombatant, this.combatantProperty, tacticalAction);

        // 1. 手持近战检查
        ItemStack meleeItem = this.livingCombatant.getMainHandItem();
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return false; // 主手没近战物品

        if ( // 2.1 检查状态锁
                // 已经在近战蓄力
                this.combatantProperty.preparingAttack
        ) return false;

        if ( // 2.2 检查状态
                // 近战冷却
                this.combatantProperty.cooldownTick > 0
        ) return false;

        { // 3. IMeleeRuntime操作结果 -> Combatant状态
            // 可以把下面移到IMeleeAttackRuntime，补一个charge
        }

        _doCharge(this.combatantProperty, iMelee, meleeItem, livingCombatant, tacticalAction);

        return true;
    }
    public static void _doCharge(CombatantProperty combatantProperty,
                                 IMelee iMelee, ItemStack meleeItem,
                                 LivingEntity livingCombatant,
                                 TacticalAction tacticalAction) {
        int combo = combatantProperty.getCombo(tacticalAction);
        combatantProperty.setCombo(tacticalAction, combo + 1);
        combatantProperty.lastCooldownTick = combatantProperty.cooldownTick = iMelee.getMeleeCooldown(meleeItem, tacticalAction, combo);

        // --------服务端--------

        combatantProperty.preparingAttack = true;
        combatantProperty.preparingAttackCombo = combo;
        combatantProperty.cooldownTick = Math.max(0, combatantProperty.cooldownTick - 1);
    }

    public void meleeAttack() {
        // TODO
    }
}
