package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class LivingCombatantDraw extends LivingCombatantAspect {

    private ItemStack lastItem = ItemStack.EMPTY;
    private int lastSelected = 0;

    public LivingCombatantDraw(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        super(livingCombatant, combatantProperty);
    }

    public void tickDrawLegacy() {
        /**
         * @deprecated 原模组压根没使用过 {@link #lastItem} 和 {@link #lastSelected}
         */
    }

    public void tickDraw() {
        if (this.combatantProperty.drawingTick > 0) this.combatantProperty.drawingTick--;
    }
}
