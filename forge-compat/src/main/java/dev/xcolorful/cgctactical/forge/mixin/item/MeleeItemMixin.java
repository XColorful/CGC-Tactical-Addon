package dev.xcolorful.cgctactical.forge.mixin.item;

import com.google.common.collect.Multimap;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.item.melee.MeleeItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MeleeItem.class)
public abstract class MeleeItemMixin extends Item {

    protected MeleeItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack meleeItem) {
        MeleeItem _this = (MeleeItem) (Object) this;
        return _this.getAttributeModifiers(meleeItem, equipmentSlot);
    }

    @Deprecated(since = "1.21.1forge")
//    @Override
    public int getMaxDamage(ItemStack meleeItem) {
        MeleeItem _this = (MeleeItem) (Object) this;
        return _this.getMaxDurability(meleeItem);
    }

    @Override
    public boolean canPerformAction(ItemStack meleeItem, ToolAction toolAction) {
        return toolAction == ToolActions.SWORD_SWEEP;
    }

    @Override
    public int getEnchantmentValue(ItemStack meleeItem) {
        MeleeItem _this = (MeleeItem) (Object) this;
        return _this.getEnchantmentPotential(meleeItem);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack meleeItem, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON;
    }

    @Deprecated(since = "1.21.1forge")
//    @Override
    public boolean isDamageable(ItemStack meleeItem) {
        MeleeItem _this = (MeleeItem) (Object) this;
        return _this.getMaxDurability(meleeItem) > 0;
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
        if (livingCombatant instanceof Player player && !ForgeHooks.onPlayerAttackTarget(player, meleeVictim)) {
            cir.setReturnValue(0);
        }
    }
}
