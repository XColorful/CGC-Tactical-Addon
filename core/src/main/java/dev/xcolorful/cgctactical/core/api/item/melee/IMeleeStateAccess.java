package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.customgun.core.api.gun.attack.IGunAttackRuntime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IMeleeStateAccess {

    // --------Tooltip--------

    boolean hasTooltipMask(ItemStack meleeItem);
    int getTooltipMask(ItemStack meleeItem);
    void setTooltipMask(ItemStack meleeItem, int tooltipMask);

    // --------Deprecated--------

    /**
     * 为什么把 {@link ILivingCombatant} 的东西放在物品 API 里？啊？
     * 回答我!
     * tell me, why?
     * 跟某个把本该是枪械的逻辑 {@link IGunAttackRuntime#shooterFire} 放在射手状态 {@code LivingEntityShoot} 的神人一样
     */
    @Deprecated(forRemoval = true) default boolean canAttack(Player livingCombatant, ItemStack meleeItem, TacticalAction tacticalAction) {
        // TODO ILivingCombatant
        return false;
    }
}
