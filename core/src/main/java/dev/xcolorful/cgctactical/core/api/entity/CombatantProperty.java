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

public class CombatantProperty {

    private final List<DelayTask> delayedActions = new ArrayList<>();

    private ItemStack lastItem = ItemStack.EMPTY;
    private final LivingEntity entity;
    private int coolDownTick = 0;
    private int lastMaxTick = 0; // TODO 啥玩意?
    private int lastSelected = 0;
    private int drawingTick = 0;
    private boolean preparingAttack = false;
    private int preparingAttackCnt = 0;

    private final Map<TacticalAction, Integer> actionCounts = new EnumMap<>(TacticalAction.class);

    public CombatantProperty(LivingEntity entity) {
        this.entity = entity;
    }

    public int getCoolDownTick() {
        return coolDownTick;
    }
    public int getLastMaxTick() {
        return lastMaxTick;
    }
    public void setCoolDownTick(int coolDownTick) {
        this.coolDownTick = coolDownTick;
    }

    public boolean isDrawing() {
        return drawingTick > 0;
    }

    public int getActionCount(TacticalAction meleeAction) {
        return actionCounts.getOrDefault(meleeAction, 0);
    }

    @Deprecated(forRemoval = true)
    public void tick() {
    }

    @Deprecated(forRemoval = true)
    public boolean preAttack(TacticalAction action, Vec3 origin, Vec3 direction) {
        return false;
    }

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
        coolDownTick = newCoolDown;
        lastMaxTick = newCoolDown;
        drawingTick = newCoolDown;
        preparingAttack = false;
        delayedActions.clear();
        actionCounts.clear();
        preparingAttackCnt = 0;
    }

    public void resetMeleeSync() {
        coolDownTick = 0;
        lastMaxTick = 0;
        preparingAttack = false;
        actionCounts.clear();
        delayedActions.clear();
        preparingAttackCnt = 0;
    }

    /**
     * TODO 改用提前准备task函数(含异常终止状态检查)并调用 {@link dev.xcolorful.customgun.core.api.event.CycledEvent}
     */
    @Deprecated(forRemoval = true)
    public abstract static class DelayTask {
    }
}
