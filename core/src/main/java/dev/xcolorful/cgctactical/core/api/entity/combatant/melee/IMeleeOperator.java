package dev.xcolorful.cgctactical.core.api.entity.combatant.melee;

public interface IMeleeOperator {

    // --------近战操作--------

    /**
     * 执行{@link #cgctactical$meleeAttack}前的蓄力动作 (攻击前摇)
     * @return 是否成功开始蓄力
     */
    boolean cgctactical$meleeCharge();

    /**
     * 执行近战行为
     * <ul>
     *     <li>若没有先 {@link #cgctactical$meleeCharge} 进入蓄力状态，则本次操作可能会失败</li>
     * </ul>
     */
    void cgctactical$meleeAttack();
}
