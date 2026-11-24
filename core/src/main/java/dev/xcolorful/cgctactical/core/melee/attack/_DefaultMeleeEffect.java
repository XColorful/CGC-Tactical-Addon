package dev.xcolorful.cgctactical.core.melee.attack;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * 一次性移植，暂不考虑通用跨版本封装
 */
@Deprecated(forRemoval = false)
public class _DefaultMeleeEffect {

    public static float calculateBonusDamageModifier(ItemStack meleeItem, MobType mobType) {
        return EnchantmentHelper.getDamageBonus(meleeItem, mobType); // TODO 待移植，高版本不兼容
    }

    public static void calculateFireAspect(ItemStack meleeItem,
                                           LivingEntity livingCombatant,
                                           Entity meleeVictim) {
        if (!(meleeVictim instanceof LivingEntity livingEntity)) return;

        int enchantmentLevel = EnchantmentHelper.getFireAspect(livingCombatant); // TODO 待移植，高版本不兼容
        if (enchantmentLevel > 0) {
            livingEntity.setSecondsOnFire(enchantmentLevel * 4);
        }
    }

    public static void postHurtEffect(LivingEntity livingCombatant, LivingEntity meleeVictim) {
        EnchantmentHelper.doPostHurtEffects(meleeVictim, livingCombatant); // TODO 待移植，高版本不兼容
    }
}
