package dev.xcolorful.cgctactical.core.api.entity;

import dev.xcolorful.cgctactical.core.api.item.ITacticalItem;
import dev.xcolorful.cgctactical.core.api.item.tactical.ITacticalItemGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CombatantProperty {

    public final List<_ClientDelayedEvent> clientSchedule = new ArrayList<>();

    private @Deprecated ItemStack lastItem = ItemStack.EMPTY;
    private final LivingEntity entity;
    public int cooldownTick = 0;
    public int lastCooldownTick = 0;
    private @Deprecated int lastSelected = 0;
    public int drawingTick = 0;
    public boolean preparingAttack = false;
    /**
     * 当前攻击蓄力的连击数，用于校验连击数是否对的上
     */
    public int preparingAttackCombo = 0;

    private final Map<TacticalAction, Integer> combo = new EnumMap<>(TacticalAction.class);

    public CombatantProperty(LivingEntity entity) {
        this.entity = entity;
    }

    public int getLastCooldownTick() {
        return lastCooldownTick;
    }

    @Deprecated
    public boolean isDrawing() {
        return drawingTick > 0;
    }

    public int getCombo(TacticalAction tacticalAction) {
        return combo.getOrDefault(tacticalAction, 0);
    }
    public void setCombo(TacticalAction tacticalAction, int combo) {
        this.combo.put(tacticalAction, combo);
    }

    @Deprecated(forRemoval = true)
    public void tick() {
    }

    /**
     * Go to {@link ILivingCombatant#cgctactical$meleeCharge}
     */
    @Deprecated(forRemoval = true)
    public boolean preAttack(TacticalAction action, Vec3 origin, Vec3 direction) {
        return false;
    }

    /**
     * Go to {@link ILivingCombatant#cgctactical$meleeAttack}
     */
    @Deprecated(forRemoval = true)
    public void postAttack(TacticalAction action, int actionCount, List<Entity> entities) {
    }

    @Deprecated(forRemoval = true)
    private void forceResetMeleeSync(String reason) {
    }

    public void resetProperty(ITacticalItem tacticalItem, ItemStack last) {
        lastItem = entity.getMainHandItem().copy();
        int newCoolDown = tacticalItem.getDrawTime(entity.getMainHandItem());
        @Nullable ITacticalItem tacticalItem1 = ITacticalItemGetter.fromItemStack(last);
        if (tacticalItem1 != null) {
            newCoolDown += tacticalItem1.getPutAwayTime(last);
        }
        cooldownTick = newCoolDown;
        lastCooldownTick = newCoolDown;
        drawingTick = newCoolDown;
        preparingAttack = false;
        clientSchedule.clear();
        combo.clear();
        preparingAttackCombo = 0;
    }

    public void resetMeleeSync() {
        cooldownTick = 0;
        lastCooldownTick = 0;
        preparingAttack = false;
        combo.clear();
        clientSchedule.clear();
        preparingAttackCombo = 0;
    }

    /**
     * 同 {@code dev.xcolorful.customgun.client.api.event.ClientDelayedEvent}
     */
    @Deprecated
    public static class _ClientDelayedEvent {

        private final Consumer<LivingEntity> task;
        private int ticksLeft;

        /**
         * 创建一个延迟事件，不注册到事件总线
         * @param task      要执行的函数
         * @param delay     延迟的tick数
         */
        public _ClientDelayedEvent(Consumer<LivingEntity> task, int delay) {
            this.task = task;
            this.ticksLeft = delay;
        }

        /**
         * @return 是否已结束
         */
        public boolean onTick(LivingEntity livingEntity) {
            if (this.ticksLeft < 0) return true;

            if (--this.ticksLeft > 0) return false;

            run(livingEntity);
            return true;
        }

        private void run(LivingEntity livingEntity) {
            this.task.accept(livingEntity);
        }
    }

    // --------Deprecated--------

    @Deprecated public int getCoolDownTick() {
        return cooldownTick;
    }
    @Deprecated public void setCoolDownTick(int cooldownTick) {
        this.cooldownTick = cooldownTick;
    }
    @Deprecated
    public int getActionCount(TacticalAction tacticalAction) {
        return getCombo(tacticalAction);
    }
    @Deprecated
    public void setActionCount(TacticalAction tacticalAction, int actionCount) {
        setCombo(tacticalAction, actionCount);
    }
    /**
     * @deprecated 什么沙雕命名
     */
    @Deprecated(forRemoval = true)
    public int getLastMaxTick() {
        return lastCooldownTick;
    }
}
