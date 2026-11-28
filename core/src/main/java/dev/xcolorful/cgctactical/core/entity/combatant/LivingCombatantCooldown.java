package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.item.ITacticalItem;
import dev.xcolorful.cgctactical.core.api.item.tactical.ITacticalItemGetter;
import dev.xcolorful.cgctactical.core.developer.PlannedRefactor;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantItemCooldown;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantMelee_reset;
import dev.xcolorful.cgctactical.core.util.SendUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class LivingCombatantCooldown extends LivingCombatantAspect {

    private Map<ResourceLocation, _ItemCooldown> itemCooldown = new HashMap<>();

    public LivingCombatantCooldown(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        super(livingCombatant, combatantProperty);
    }

    public int getItemCooldown(ItemStack tacticalItem) {
        @Nullable ITacticalItem iTacticalItem = ITacticalItemGetter.fromItemStack(tacticalItem);
        if (iTacticalItem == null) return 0;

        @Nullable var cooldownLocation = iTacticalItem.getCooldownLocation(tacticalItem);
        if (cooldownLocation == null) return 0;

        @Nullable var itemCooldown = this.itemCooldown.get(cooldownLocation);
        if (itemCooldown == null) return 0;

        return itemCooldown.getCooldownRemain();
    }
    public float getItemCooldownPercent(ItemStack tacticalItem, float partialTick) {
        @Nullable ITacticalItem iTacticalItem = ITacticalItemGetter.fromItemStack(tacticalItem);
        if (iTacticalItem == null) return 0;

        @Nullable var cooldownLocation = iTacticalItem.getCooldownLocation(tacticalItem);
        if (cooldownLocation == null) return 0;

        @Nullable var itemCooldown = this.itemCooldown.get(cooldownLocation);
        if (itemCooldown == null) return 0;

        return itemCooldown.getCooldownRemainPercent(partialTick);
    }

    public void setItemCooldown(ResourceLocation cooldownLocation, int cooldown) {
        if (cooldown == 0) {
            this.itemCooldown.remove(cooldownLocation);
            if (PlannedRefactor.ITEM_COOLDOWN_FINISH_EVENT) {}
        }

        if (PlannedRefactor.ITEM_COOLDOWN_EVENT) {}
        this.itemCooldown.put(cooldownLocation, new _ItemCooldown(cooldown));
        if (this.livingCombatant instanceof ServerPlayer serverPlayer) {
            // 服务端
            SendUtils.sendMessageToPlayer(serverPlayer, new S2CMessageCombatantItemCooldown(cooldownLocation, cooldown));
        }
    }

    /**
     * 物品冷却
     */
    public void tickItemCooldown() {
        if (this.itemCooldown.isEmpty()) return;
        this.itemCooldown.entrySet().removeIf(entry -> {
                    if (entry.getValue().tick_isFinished()) {
                        if (PlannedRefactor.ITEM_COOLDOWN_FINISH_EVENT) {}
                        return true;
                    }
                    return false;
                }
        );
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

    /**
     * 随时可替换
     */
    @ApiStatus.Internal
    public static class _ItemCooldown {
        private int tickCount;
        private final int cooldownTick;
        public _ItemCooldown(int cooldownTick) {
            this.tickCount = 0;
            this.cooldownTick = cooldownTick;
        }

        public int getTickCount() {
            return this.tickCount;
        }
        public int getCooldownTick() {
            return this.cooldownTick;
        }

        /**
         * @return 冷却是否已结束
         */
        public boolean tick_isFinished() {
            return ++this.tickCount >= this.cooldownTick;
        }

        public int getCooldownRemain() {
            return this.cooldownTick - this.tickCount;
        }
        public float getCooldownRemainPercent(float partialTick) {
            return Math.min(this.cooldownTick - (this.tickCount + partialTick), 1.0f);
        }
    }
}
