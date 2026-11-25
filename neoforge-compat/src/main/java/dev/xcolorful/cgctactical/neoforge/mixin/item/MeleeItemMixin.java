package dev.xcolorful.cgctactical.neoforge.mixin.item;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.item.melee.MeleeItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(MeleeItem.class)
public abstract class MeleeItemMixin extends Item {

    protected MeleeItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack meleeItem) {
        MeleeItem _this = (MeleeItem) (Object) this;
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (Map.Entry<Holder<Attribute>, AttributeModifier> entry :
                _this.getAttributeModifiers(meleeItem, EquipmentSlot.MAINHAND).entries()) {
            builder.add(entry.getKey(), entry.getValue(), EquipmentSlotGroup.MAINHAND);
        }
        return builder.build();
    }

    @Override
    public int getMaxDamage(ItemStack meleeItem) {
        MeleeItem _this = (MeleeItem) (Object) this;
        return _this.getMaxDurability(meleeItem);
    }

    @Override
    public boolean canPerformAction(ItemStack meleeItem, ItemAbility itemAbility) {
        return itemAbility == ItemAbilities.SWORD_SWEEP;
    }

    @Deprecated(since = "1.21.4")
//    @Override
    public int getEnchantmentValue(ItemStack meleeItem) {
        MeleeItem _this = (MeleeItem) (Object) this;
        return _this.getEnchantmentPotential(meleeItem);
    }

    /**
     * 以原版剑能否附魔该附魔作为武器附魔判定
     */
    @Override
    public boolean isPrimaryItemFor(ItemStack meleeItem, Holder<Enchantment> enchantment) {
//        return enchantment.category.canEnchant(Items.IRON_SWORD); // [1.20.1, 1.21.1)
        return enchantment.value().getSupportedItems().contains(Items.IRON_SWORD.builtInRegistryHolder());
    }

    @Override
    public boolean isDamageable(ItemStack meleeItem) {
        return this.getMaxDamage(meleeItem) > 0;
    }

    // --------IMeleeRuntime--------

    // ----IMeleeAttackRuntime----
    @Inject(
            method = "meleeAttack",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void meleeAttack(CombatantProperty combatantProperty,
                             IMelee iMelee, ItemStack meleeItem,
                             ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                             float damage, float knockback,
                             Entity meleeVictim,
                             CallbackInfoReturnable<Integer> cir) {
        if (livingCombatant instanceof Player player && !CommonHooks.onPlayerAttackTarget(player, meleeVictim)) {
            cir.setReturnValue(0);
        }
    }
}
