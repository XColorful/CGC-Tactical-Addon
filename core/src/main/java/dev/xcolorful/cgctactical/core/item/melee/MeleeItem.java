package dev.xcolorful.cgctactical.core.item.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeDataAccess;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
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

    private static final int ENCHANTMENT_POTENTIAL = 5;

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
    // 可用于Forge getEnchantmentValue(ItemStack)
    /**
     * 获取附魔潜力值，同原版 {@code Item#getEnchantmentValue}
     */
    public int getEnchantmentPotential(ItemStack meleeItem) {
        return ENCHANTMENT_POTENTIAL;
    }
    // 可用于Forge getMaxDamage(ItemStack)
    /**
     * <ul>
     *     <li>不放在 {@link IMeleeDataAccess} 是因为 {@link IMelee} 本身不应该保证一个<b>vanilla</b>的耐久度机制</li>
     *     <li>从而没必要把该方法提到 {@link IMelee}</li>
     *     <li>并且对于非 {@code instanceof MeleeItem} 的 {@link IMelee}，都不一定是 {@link Item} ({@link IMeleeGetter} 使其跟 {@link Item} 解耦，可 Mixin 做中间人转发)，</li>
     *     <li>进而自己处理耐久度读取和应用</li>
     * </ul>
     */
    public int getMaxDurability(ItemStack meleeItem) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return 0;

        MeleeData meleeData = meleeIndexInstance.getMeleeData();
        return meleeData.getMaxDurability();
    }

    // --------Item--------

    @Override
    public @NotNull Component getName(@NotNull ItemStack meleeItem) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return super.getName(meleeItem);

        MeleeIndex meleeIndex = meleeIndexInstance.getPojo();
        return Component.translatable(meleeIndex.getNameLang());
    }

    /**
     * 获取供客户端使用的 Tooltip 信息
     */
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack meleeItem) {
        return Optional.ofNullable(MeleeTooltip.fromItem(meleeItem));
    }

    @Deprecated(since = "1.21.4")
    @Override
    public boolean isEnchantable(@NotNull ItemStack meleeItem) {
        return true;
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
    @Override public List<Entity> prepareMeleeVictims(CombatantProperty combatantProperty, @NotNull IMelee iMelee, @NotNull ItemStack meleeItem, ILivingCombatant iLivingCombatant, LivingEntity livingCombatant, TacticalAction tacticalAction, int combo, Vec3 origin, Vec3 direction) {
        return CgcTactical.getMeleeManager().getManagerGroup(this.getManagerGroupTag(meleeItem))
                .meleePhysicsManager()
                .prepareMeleeVictims(combatantProperty, iMelee, meleeItem, iLivingCombatant, livingCombatant, tacticalAction, combo, origin, direction);
    }
}
