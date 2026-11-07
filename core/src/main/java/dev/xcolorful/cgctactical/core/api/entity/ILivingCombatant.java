package dev.xcolorful.cgctactical.core.api.entity;

import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantConfig;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ITacticalOperator;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackRuntime;
import dev.xcolorful.customgun.core.api.entity.ILivingShooter;
import dev.xcolorful.customgun.core.api.gun.attack.IGunAttackRuntime;
import dev.xcolorful.customgun.core.api.item.gun.FireModeType;

/*
文档译名：战斗生物 (XiaoColorful译); 否决译名：战斗者, 战术生物
 */
/**
 * 主模组的开火流程设计:
 * <ol>
 *     <li>射手{@code 生物}使用枪械{@code 物品} {@link ILivingShooter#cgc$shoot}</li>
 *     <li>枪械{@code 物品}扣动一次扳机 {@link IGunAttackRuntime#shooterFire}</li>
 *     <li>{@code 物品}可能进行多次击发 {@link IGunAttackRuntime#gunFire} (如{@link FireModeType#BURST})</li>
 * </ol>
 * 本接口与主模组同构:
 * <ol>
 *     <li>战斗{@code 生物}使用战术{@code 物品} {@link ITacticalOperator#cgctactical$meleeCharge}/{@link ITacticalOperator#cgctactical$meleeAttack}</li>
 *     <li>战术{@code 物品}进行一次攻击 {@link IMeleeAttackRuntime#combatantAttack}</li>
 *     <li>{@code 物品}对每个实体出伤 {@link IMeleeAttackRuntime#meleeAttack}</li>
 * </ol>
 */
public interface ILivingCombatant extends ILivingCombatantConfig,
        ITacticalOperator {

    /**
     * 初始化战斗操作的各个数据
     */
    void cgctactical$initLivingCombatant();

    CombatantProperty cgctactical$getCombatantProperty();
}
