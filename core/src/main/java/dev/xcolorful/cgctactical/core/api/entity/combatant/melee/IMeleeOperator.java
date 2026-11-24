package dev.xcolorful.cgctactical.core.api.entity.combatant.melee;

import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface IMeleeOperator {

    // --------近战操作--------

    /**
     * 执行{@link #cgctactical$meleeAttack}前的蓄力动作 (攻击前摇)
     * @return 是否成功开始蓄力
     */
    boolean cgctactical$meleeCharge(McLogicalSide logicalSide, TacticalAction tacticalAction);

    /**
     * 执行近战行为
     * <ul>
     *     <li>若没有先 {@link #cgctactical$meleeCharge} 进入蓄力状态，则本次操作可能会失败</li>
     * </ul>
     */
    void cgctactical$meleeAttack(TacticalAction tacticalAction, int combo, List<Entity> meleeVictims);

    // --------Deprecated--------

    @Deprecated(forRemoval = true)
    default boolean cgctactical$meleeCharge() {
        return cgctactical$meleeCharge(CustomGun.getSideExecutor().getLogicalSide(), TacticalAction.PRIMARY_ACTION);
    }
}
