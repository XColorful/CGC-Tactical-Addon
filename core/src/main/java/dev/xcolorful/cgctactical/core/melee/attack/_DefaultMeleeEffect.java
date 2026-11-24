package dev.xcolorful.cgctactical.core.melee.attack;

import net.minecraft.world.damagesource.DamageSource;
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

    public static float calculateEnchantedDamage(LivingEntity livingCombatant,
                                                 ItemStack meleeItem,
                                                 Entity meleeVictim,
                                                 DamageSource damageSource,
                                                 float damage) {
        MobType mobType = meleeVictim instanceof LivingEntity livingVictim ? livingVictim.getMobType() : MobType.UNDEFINED;
        return damage + EnchantmentHelper.getDamageBonus(meleeItem, mobType);
    }

    public static void doPostAttackEffects(LivingEntity livingCombatant,
                                           ItemStack meleeItem,
                                           Entity meleeVictim,
                                           DamageSource damageSource) {
        if (!(meleeVictim instanceof LivingEntity livingVictim)) return;

        // 火焰附加等命中后附魔效果
        int fireAspect = EnchantmentHelper.getFireAspect(livingCombatant);
        if (fireAspect > 0) {
            livingVictim.setSecondsOnFire(fireAspect * 4);
        }
        EnchantmentHelper.doPostHurtEffects(livingVictim, livingCombatant);
        EnchantmentHelper.doPostDamageEffects(livingCombatant, meleeVictim);
    }
}
