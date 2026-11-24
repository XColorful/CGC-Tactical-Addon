package dev.xcolorful.cgctactical.core.item.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.MeleeDataAccessor;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.gui.tooltip.melee.MeleeTooltip;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MeleeItem extends Item implements IMelee, MeleeDataAccessor {

    protected MeleeItem(Properties properties) {
        super(properties);
    }
    public MeleeItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.MELEE.getRegistryLocation()));
    }

    // --------
    // 可用于Forge getAttributeModifiers
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack meleeItem, EquipmentSlot equipmentSlot) {
        if (equipmentSlot != EquipmentSlot.MAINHAND) return ImmutableMultimap.of();

        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return ImmutableMultimap.of();

        return meleeIndexInstance.getAttributeModifierCache();
    }

    // --------Item--------

    @Override
    public @NotNull String getDescriptionId(@NotNull ItemStack meleeItem) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return super.getDescriptionId();

        MeleeIndex meleeIndex = meleeIndexInstance.getPojo();
        return meleeIndex.getNameLang();
    }

    /**
     * 获取供客户端使用的 Tooltip 信息
     */
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack meleeItem) {
        return Optional.ofNullable(MeleeTooltip.fromItem(meleeItem));
    }

    // --------Client--------

    /**
     * 复用 {@link #getDescriptionId}
     */
    @Override
    public @NotNull Component getName(@NotNull ItemStack meleeItem) {
        return super.getName(meleeItem);
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
    @Override public List<Entity> prepareMeleeVictims(CombatantProperty combatantProperty, @NotNull IMelee iMelee, @NotNull ItemStack meleeItem, ILivingCombatant iLivingCombatant, LivingEntity livingCombatant, TacticalAction tacticalAction, Vec3 origin, Vec3 direction) {
        return CgcTactical.getMeleeManager().getManagerGroup(this.getManagerGroupTag(meleeItem))
                .meleePhysicsManager()
                .prepareMeleeVictims(combatantProperty, iMelee, meleeItem, iLivingCombatant, livingCombatant, tacticalAction, origin, direction);
    }
}
