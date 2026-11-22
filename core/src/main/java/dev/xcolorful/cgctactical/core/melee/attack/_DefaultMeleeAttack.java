package dev.xcolorful.cgctactical.core.melee.attack;

import dev.xcolorful.cgctactical.client.network.message.resource._S2CMessageResourceSound;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalCategory;
import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackRuntime;
import dev.xcolorful.cgctactical.core.api.melee.physics.volume.AbstractVolume;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.api.resource.assets.display.SoundName;
import dev.xcolorful.cgctactical.core.api.resource.assets.display.SoundNameTag;
import dev.xcolorful.cgctactical.core.config.MeleeConfig;
import dev.xcolorful.cgctactical.core.network.message.resource.S2CMessageResourceSound;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttackData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._TargetFilterData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.combat._MeleeAttackInfo;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import dev.xcolorful.cgctactical.core.util.SendUtils;
import dev.xcolorful.customgun.core.util.EntityUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class _DefaultMeleeAttack {

    public static @NotNull IMeleeAttackRuntime.CombatantAttackResult combatantAttack(CombatantProperty combatantProperty,
                                                                                     @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                                                                                     ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                                                                                     TacticalAction tacticalAction, int combo,
                                                                                     List<Entity> meleeVictims) {
        var meleeLocation = iMelee.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return IMeleeAttackRuntime.CombatantAttackResult.ERROR;

        @Nullable _MeleeAttackInfo meleeAttackInfo; {
            MeleeData meleeData = meleeIndexInstance.getMeleeData();
            _AttackData attackData = meleeData.getAttackData();

            meleeAttackInfo = TacticalAction.getMeleeAttackInfo(tacticalAction, attackData, combo);
            if (meleeAttackInfo == null) return IMeleeAttackRuntime.CombatantAttackResult.ERROR;
        }

        // 放音效
        playMeleeSoundToNearbyPlayers(livingCombatant, meleeLocation, tacticalAction.getCategoryName());

        float damage; {
            float baseDamage = (float) livingCombatant.getAttributeValue(Attributes.ATTACK_DAMAGE);
            damage = baseDamage * meleeAttackInfo.getFactor();
        }
        if (damage <= 0) return IMeleeAttackRuntime.CombatantAttackResult.SUCCESS; // 没伤害，整个跳过

        _TargetFilterData targetFilterData = meleeAttackInfo.getTargetFilterData();
        float maxRange = targetFilterData.getMaxRange();
        float knockback = meleeAttackInfo.getKnockback();

        int resultBitmap = 0;
        for (int i = 0; i < meleeVictims.size(); i++) { // 遍历每个victim
            Entity meleeVictim = meleeVictims.get(i);
            if (false) { // 服务端不再检查，保留下面IDE引用
                if ( // 要查也得拿实际HitVolume类型的filter
                        !AbstractVolume.TARGET_ENTITY_FILTER.test(meleeVictim)
                        || livingCombatant.equals(meleeVictim)
                ) continue;
            }

            if (livingCombatant.distanceToSqr(meleeVictim) > maxRange * maxRange) {
                // 超出有效距离
                continue;
            }

            // 单次近战
            resultBitmap |= iMelee.meleeAttack(combatantProperty,
                    iMelee, meleeItem,
                    iLivingCombatant, livingCombatant,
                    damage, knockback,
                    meleeVictim);
        }

        // ----返回结果----
        if (resultBitmap == 0) return IMeleeAttackRuntime.CombatantAttackResult.ERROR;
        else {
            EnumSet<IMeleeAttackRuntime.MeleeAttackResult> results = IMeleeAttackRuntime.MeleeAttackResult.fromBitmap(resultBitmap);
            if (results.contains(IMeleeAttackRuntime.MeleeAttackResult.HIT)) {
                if (MeleeConfig.MELEE_ITEM_CONSUME_DURABILITY.get()) {
                    // 消耗耐久
                    meleeItem.hurtAndBreak(meleeAttackInfo.getDurabilityDamage(), livingCombatant, (livingEntity) -> {
                        livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    });
                }

                boolean blankHit = false; // 是否仅命中，无击杀/暴击等
                if (results.contains(IMeleeAttackRuntime.MeleeAttackResult.KILL)) {
                    // 击杀
                    playMeleeSoundToNearbyPlayersAndSelf(livingCombatant, meleeLocation, SoundName.KILL.getConstantName());
                    blankHit = true;
                }
                if (results.contains(IMeleeAttackRuntime.MeleeAttackResult.CRIT)) {
                    // 暴击
                    playMeleeSoundToNearbyPlayersAndSelf(livingCombatant, meleeLocation, SoundName.CRIT.getConstantName());
                    blankHit = true;
                }
                if (blankHit) {
                    // 裸命中
                    playMeleeSoundToNearbyPlayersAndSelf(livingCombatant, meleeLocation, tacticalAction.getCategoryName() + "_" + SoundNameTag.Suffix.HIT);
                }
            }

            return IMeleeAttackRuntime.CombatantAttackResult.SUCCESS;
        }
    }

    @ApiStatus.Internal
    private static void playMeleeSoundToNearbyPlayersAndSelf(LivingEntity livingCombatant,
                                                             ResourceLocation meleeLocation,
                                                             String sound) {
        if (!(livingCombatant instanceof ServerPlayer serverPlayer)) return;

        Vec3 soundPos = serverPlayer.position();
        float volume = 2;
        float pitch = 1;
        // TODO 加距离限制
        SendUtils.sendMessageToNearbyPlayers(serverPlayer, new S2CMessageResourceSound(TacticalCategory.MELEE, meleeLocation, sound, soundPos, volume, pitch));
        SendUtils.sendMessageToPlayer(serverPlayer, new S2CMessageResourceSound(TacticalCategory.MELEE, meleeLocation, sound + _S2CMessageResourceSound.FEEDBACK_SUFFIX, soundPos, volume, pitch));
    }

    @ApiStatus.Internal
    private static void playMeleeSoundToNearbyPlayers(LivingEntity livingCombatant,
                                                      ResourceLocation meleeLocation,
                                                      String sound) {
        if (!(livingCombatant instanceof ServerPlayer serverPlayer)) return;

        Vec3 soundPos = serverPlayer.position();
        float volume = 2;
        float pitch = 1;
        // TODO 加距离限制
        SendUtils.sendMessageToNearbyPlayers(serverPlayer, new S2CMessageResourceSound(TacticalCategory.MELEE, meleeLocation, sound, soundPos, volume, pitch));
    }

    public static EnumSet<IMeleeAttackRuntime.MeleeAttackResult> meleeAttack(CombatantProperty combatantProperty,
                                                                             @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                                                                             ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                                                                             float damage, float knockback,
                                                                             Entity meleeVictim) {
        if (
                // 不可攻击
                !meleeVictim.isAttackable()
                // 跳过攻击交互
                || meleeVictim.skipAttackInteraction(livingCombatant)
        ) return EnumSet.of(IMeleeAttackRuntime.MeleeAttackResult.MISS);

        MobType mobType = meleeVictim instanceof LivingEntity ? livingCombatant.getMobType() : MobType.UNDEFINED;
        float bonusDamage = _DefaultMeleeEffect.calculateBonusDamageModifier(meleeItem, mobType);

        boolean isCriticalHit;
        float damageModifier;
        { // 原版暴击判定
            boolean vanillaCriticalHit = isVanillaCriticalHit(livingCombatant, meleeVictim);
            damageModifier = vanillaCriticalHit ? 1.5f : 1f;
            @Nullable MeleeAttackManager.CriticalHitContext criticalHitContext = new MeleeAttackManager.CriticalHitContext(livingCombatant, meleeVictim, vanillaCriticalHit, damageModifier);
            criticalHitContext = MeleeAttackManager.onCriticalHit(criticalHitContext);
            isCriticalHit = criticalHitContext != null && criticalHitContext.vanillaCritical;
            damageModifier = criticalHitContext != null ? criticalHitContext.damageModifier : 1f;
        }

        // 火焰附加
        _DefaultMeleeEffect.calculateFireAspect(meleeItem, livingCombatant, meleeVictim);

        if (EntityUtils.getInvulnerableTime(meleeVictim) < MeleeConfig.MELEE_IGNORE_INVULNERABLE_TICK_THRESHOLD.get()) {
            // 无敌时间低于阈值则本次攻击无视其无敌
            EntityUtils.setInvulnerableTime(meleeVictim, 0);
        }

        DamageSource damageSource = livingCombatant instanceof Player player ? livingCombatant.damageSources().playerAttack(player) : livingCombatant.damageSources().generic();
        if (!meleeVictim.hurt(damageSource, damage + bonusDamage)) {
            // 免疫伤害/没造成伤害，不算命中
            return EnumSet.of(IMeleeAttackRuntime.MeleeAttackResult.MISS);
        }
        EnumSet<IMeleeAttackRuntime.MeleeAttackResult> results = EnumSet.of(IMeleeAttackRuntime.MeleeAttackResult.HIT);

        if (meleeVictim instanceof LivingEntity livingMeleeVictim) {
            livingMeleeVictim.knockback(knockback,
                    Mth.sin(livingCombatant.getYRot() * ((float) Math.PI / 180F)),
                    -Mth.cos(livingCombatant.getYRot() * ((float) Math.PI / 180F)));
            _DefaultMeleeEffect.postHurtEffect(livingCombatant, livingMeleeVictim);
        }

        if (isCriticalHit) {
            results.add(IMeleeAttackRuntime.MeleeAttackResult.CRIT);
            if (livingCombatant instanceof Player player) {
                player.crit(meleeVictim);
            }
        }

        if (isKilled(meleeVictim)) {
            results.add(IMeleeAttackRuntime.MeleeAttackResult.KILL);
        }

        return results;
    }

    public static boolean isVanillaCriticalHit(LivingEntity livingCombatant, Entity meleeVictim) {
        if (
                // 只对生物暴击
                !(meleeVictim instanceof LivingEntity)
                // 未处于下落状态
                || livingCombatant.fallDistance <= 0 || livingCombatant.onGround()
                // 在攀爬
                || livingCombatant.onClimbable()
                // 在水里
                || livingCombatant.isInWater()
                // 失明
                || livingCombatant.hasEffect(MobEffects.BLINDNESS)
                // 骑乘载具
                || livingCombatant.isPassenger()
        ) return false;

        return true;
    }

    /**
     * 这个并不能检测不死图腾之类的
     */
    public static boolean isKilled(Entity meleeVictim) {
        // mixin注入点
        return meleeVictim instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying();
    }
}
