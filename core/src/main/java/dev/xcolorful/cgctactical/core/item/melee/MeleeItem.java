package dev.xcolorful.cgctactical.core.item.melee;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.item.melee._MeleeItem;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.item.melee.MeleeDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.gui.tooltip.melee.MeleeTooltip;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class MeleeItem extends Item implements IMelee, MeleeDataAccessor {

    protected MeleeItem(Properties properties) {
        super(properties);
    }
    public MeleeItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.MELEE.getRegistryLocation()));
    }

    // --------Item--------

    /**
     * 获取供客户端使用的 Tooltip 信息
     */
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack meleeItem) {
        return Optional.ofNullable(MeleeTooltip.fromItem(meleeItem));
    }

    // --------Client--------

    @Override
    public @NotNull Component getName(@NotNull ItemStack meleeItem) {
        var name = _MeleeItem.getName(this, meleeItem);
        return name != null ? name : super.getName(meleeItem);
    }

    // --------IMeleeRuntime--------

    // ----IMeleeAttackRuntime----
    @Override public @NotNull CombatantAttackResult combatantAttack(CombatantProperty combatantProperty, @NotNull IMelee iMelee, @NotNull ItemStack meleeItem, ILivingCombatant iLivingCombatant, LivingEntity livingCombatant, TacticalAction tacticalAction, int combo, List<Entity> meleeVictims) {
        return CgcTactical.getMeleeManager().getManagerGroup(this.getManagerGroupTag(meleeItem))
                .meleeAttackManager()
                .combatantAttack(combatantProperty, iMelee, meleeItem, iLivingCombatant, livingCombatant, tacticalAction, combo, meleeVictims);
    }
    @Override public int meleeAttack(CombatantProperty combatantProperty, @NotNull IMelee iMelee, @NotNull ItemStack meleeItem, ILivingCombatant iLivingCombatant, LivingEntity livingCombatant, float damage, float knockback, Entity meleeVictim) {
        return CgcTactical.getMeleeManager().getManagerGroup(this.getManagerGroupTag(meleeItem))
                .meleeAttackManager()
                .meleeAttack(combatantProperty, iMelee, meleeItem, iLivingCombatant, livingCombatant, damage, knockback, meleeVictim);
    }
    // ----IMeleePhysicsRuntime----
}
