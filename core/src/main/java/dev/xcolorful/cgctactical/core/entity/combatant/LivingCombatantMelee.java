package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.entity.combatant.LocalCombatantMelee;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackRuntime;
import dev.xcolorful.cgctactical.core.melee.attack.MeleeAttackManager;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantMelee_reset;
import dev.xcolorful.cgctactical.core.network.message.sync.S2CMessageSyncMeleeAnimation;
import dev.xcolorful.cgctactical.core.util.SendUtils;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

        var animationLocation = iMelee.getMeleeLocation(meleeItem);
        SendUtils.sendMessageToNearbyPlayersAndSelf(
                livingCombatant,
                new S2CMessageSyncMeleeAnimation(livingCombatant.getId(),
                        animationLocation,
                        tacticalAction,
                        combatantProperty.getCombo(tacticalAction))
        );
    }

    public void meleeAttack(TacticalAction tacticalAction, int combo, List<Entity> meleeVictims) {
        // 1. 手持近战检查
        ItemStack meleeItem = this.livingCombatant.getMainHandItem();
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return; // 主手没近战物品

        if ( // 2.1 检查状态锁
                // 不在近战蓄力状态
                !this.combatantProperty.preparingAttack
        ) return;

        if ( // 2.2 检查状态
                // 连击数对不上
                this.combatantProperty.preparingAttackCombo != combo
        ) {
            int expectCombo = this.combatantProperty.preparingAttackCombo;
            // 强制重置
            this.combatantProperty.resetMeleeSync();
            if (this.livingCombatant instanceof ServerPlayer serverPlayer) {
                CgcTactical.LOGGER.info("{} attack combo {} mismatched (expect {}), resetting", serverPlayer.getName().getString(), combo, expectCombo);
                SendUtils.sendMessageToPlayer(serverPlayer, new S2CMessageCombatantMelee_reset());
            }
            return;
        }

        { // 3. IMeleeRuntime操作结果 -> Combatant状态
            ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(this.livingCombatant);
            /**
             * {@link IMeleeAttackRuntime#combatantAttack}的默认实现为{@link MeleeAttackManager#combatantAttack}
             */
            @NotNull IMeleeAttackRuntime.CombatantAttackResult result = iMelee.combatantAttack(this.combatantProperty, iMelee, meleeItem, iLivingCombatant, this.livingCombatant, tacticalAction, combo, meleeVictims);
            // 收到合法攻击包即结束蓄力（即使没命中/打空）；否则服务端冷却超时会强制重置，连击数被清零
            this.combatantProperty.preparingAttack = false;
            if (result.isSuccess()) {
            }
        }
    }
}
