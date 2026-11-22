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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
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
}
