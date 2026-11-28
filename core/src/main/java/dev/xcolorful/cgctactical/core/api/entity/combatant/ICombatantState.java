package dev.xcolorful.cgctactical.core.api.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.ITacticalItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * <ul>
 *     <li>目前这个位置的接口具体做多细是待定的</li>
 *     <li>{@code ITacticalState}/{@code IThrowableState}命名倾向于挂在{@link ITacticalItem}上，但这个接口的功能应在{@link ILivingCombatant}（实体）上</li>
 *     <li>无论如何，外部都应使用 {@link ILivingCombatant} 这个全能易用门面，而无需了解内部分形细节</li>
 *     <li>从而该设计问题收敛到内部复杂度，对外部透明</li>
 *     <li>如果要限制读取权限而收紧接口，则调用方应感知本接口的变动</li>
 * </ul>
 */
public interface ICombatantState {

    /**
     * 若不为 {@link ITacticalItem}，返回 {@code 0}
     * @return 战术物品冷却时间，单位 tick
     */
    int cgctactical$getItemCooldown(ItemStack tacticalItem);
    /**
     * 若不为 {@link ITacticalItem}，返回 {@code 0}
     * @return 战术物品冷却时间百分比，范围 {@code 0} ~ {@code 1}
     */
    float cgctactical$getItemCooldownPercent(ItemStack tacticalItem, float partialTick);
    default float cgctactical$getItemCooldownPercent(LivingEntity livingCombatant, ItemStack tacticalItem) {
        return this.cgctactical$getItemCooldownPercent(tacticalItem, 0);
    }

    /**
     * 设置物品冷却
     */
    void cgctactical$setItemCooldown(ResourceLocation cooldownLocation, int cooldown);
}
