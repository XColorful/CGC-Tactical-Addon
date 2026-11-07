package dev.xcolorful.cgctactical.core.api.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.melee.IMeleeOperator;

/**
 * <ul>
 *     <li>{@code 实体} ({@link ILivingCombatant}) 执行这些{@code 物品使用行为} ({@link #cgctactical$meleeCharge}/{@link #cgctactical$meleeAttack})</li>
 * </ul>
 * @see ILivingCombatant
 */
public interface ITacticalOperator extends IMeleeOperator {
}
