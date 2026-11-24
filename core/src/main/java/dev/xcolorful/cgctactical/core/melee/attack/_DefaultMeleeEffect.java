package dev.xcolorful.cgctactical.core.melee.attack;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
        if (!(livingCombatant.level() instanceof ServerLevel serverLevel)) return damage;

        // 附魔伤害加成（锋利/亡灵杀手/节肢杀手/穿刺）
        return EnchantmentHelper.modifyDamage(serverLevel, meleeItem, meleeVictim, damageSource, damage);
    }

    public static void doPostAttackEffects(LivingEntity livingCombatant,
                                           ItemStack meleeItem,
                                           Entity meleeVictim,
                                           DamageSource damageSource) {
        if (!(livingCombatant.level() instanceof ServerLevel serverLevel)) return;

        // 火焰附加等命中后附魔效果
        EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, meleeVictim, damageSource, meleeItem);
    }
}
