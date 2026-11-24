package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantMelee_reset;
import dev.xcolorful.cgctactical.core.util.SendUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class LivingCombatantCooldown extends LivingCombatantAspect {

    public LivingCombatantCooldown(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        super(livingCombatant, combatantProperty);
    }

    /**
     * 物品冷却
     */
    public void tickItemCooldown() {
        // TODO
    }

    /**
     * 近战冷却
     */
    public void tickCooldown() {
        if (this.combatantProperty.cooldownTick <= 0) return;

        if (--this.combatantProperty.cooldownTick <= 0) {
            if (this.combatantProperty.preparingAttack
                    && !this.livingCombatant.level().isClientSide()) { // 因为合并了双端逻辑，需要单独过滤
                // 蓄力超时未收包，强制重置
                this.combatantProperty.resetMeleeSync();
                if (this.livingCombatant instanceof ServerPlayer serverPlayer) {
                    CgcTactical.LOGGER.info("{} exceed attack preparation time, resetting", serverPlayer.getName().getString());
                    SendUtils.sendMessageToPlayer(serverPlayer, new S2CMessageCombatantMelee_reset());
                }
            }
            this.combatantProperty.preparingAttack = false;
        }
    }
}
