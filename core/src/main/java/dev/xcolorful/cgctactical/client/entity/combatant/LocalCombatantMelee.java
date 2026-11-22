package dev.xcolorful.cgctactical.client.entity.combatant;

import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.assets.display.MeleeDisplay;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.config.SyncConfig;
import dev.xcolorful.cgctactical.core.entity.combatant.LivingCombatantMelee;
import dev.xcolorful.cgctactical.core.network.message.combatant.C2SMessageCombatantMelee_attack;
import dev.xcolorful.cgctactical.core.network.message.combatant.C2SMessageCombatantMelee_prepare;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.attack.info._MeleeMovement;
import dev.xcolorful.cgctactical.core.util.SendUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class LocalCombatantMelee {

    public static boolean localMeleeCharge(LivingEntity livingCombatant,
                                           CombatantProperty combatantProperty,
                                           TacticalAction tacticalAction) {
        if (!(livingCombatant instanceof LocalPlayer localPlayer)) return false;
        return meleeCharge(localPlayer, combatantProperty, tacticalAction);
    }
    public static boolean meleeCharge(LocalPlayer localCombatant,
                                      CombatantProperty combatantProperty,
                                      TacticalAction tacticalAction) {
        // 1. 手持近战检查
        ItemStack meleeItem = localCombatant.getMainHandItem();
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return false; // 主手没近战物品

        if ( // 2.2 检查状态
                // 近战冷却
                combatantProperty.cooldownTick > 0
        ) return false;

        { // 3. IMeleeRuntime操作结果 -> Combatant状态
            // 可以把下面移到IMeleeAttackRuntime，补一个charge
        }

        _doCharge(combatantProperty, iMelee, meleeItem, localCombatant, tacticalAction);

        return true;
    }
    /**
     * 对应 {@link LivingCombatantMelee#_doCharge}
     */
    private static void _doCharge(CombatantProperty combatantProperty,
                                  IMelee iMelee, ItemStack meleeItem,
                                  LocalPlayer localCombatant,
                                  TacticalAction tacticalAction) {
        int combo = combatantProperty.getCombo(tacticalAction);
        combatantProperty.setCombo(tacticalAction, combo + 1);
        combatantProperty.lastCooldownTick = combatantProperty.cooldownTick = iMelee.getMeleeCooldown(meleeItem, tacticalAction, combo);

        // --------客户端--------

        Vec3 origin = localCombatant.getEyePosition();
        Vec3 direction = localCombatant.getLookAngle();
        SendUtils.sendMessageToServer(new C2SMessageCombatantMelee_prepare(tacticalAction, origin, direction));

        chargeAttack(combatantProperty, iMelee, meleeItem, localCombatant, tacticalAction, origin, direction, combo);
        chargeMove(combatantProperty, iMelee, meleeItem, localCombatant, tacticalAction);
        chargeSound(iMelee, meleeItem, localCombatant, tacticalAction, origin);
    }

    public static void chargeAttack(CombatantProperty combatantProperty,
                                    IMelee iMelee, ItemStack meleeItem,
                                    LocalPlayer localCombatant,
                                    TacticalAction tacticalAction,
                                    Vec3 origin, Vec3 direction, int combo) {
        Consumer<LivingEntity> task = (livingCombatant) -> {
            ItemStack _meleeItem = livingCombatant.getMainHandItem();
            @Nullable IMelee _iMelee = IMeleeGetter.fromItemStack(_meleeItem);
            if (_iMelee == null) return;

            if (_iMelee.switchItemNeedReset(meleeItem, _meleeItem)) return;

            ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(localCombatant);
            List<Entity> meleeVictims = _iMelee.prepareMeleeVictims(iLivingCombatant.cgctactical$getCombatantProperty(), _iMelee, _meleeItem, iLivingCombatant, localCombatant,tacticalAction, origin, direction);
            SendUtils.sendMessageToServer(new C2SMessageCombatantMelee_attack(tacticalAction, combo, _filterMeleeVictims(meleeVictims)));
        };

        int meleeDelay = iMelee.getMeleeDelay(meleeItem, tacticalAction, combo);
        if (meleeDelay == 0) {
            task.accept(localCombatant);
        } else {
            combatantProperty.clientSchedule.add(new CombatantProperty._ClientDelayedEvent(task, meleeDelay));
        }
    }
    /**
     * 只过滤数量，不重新排序
     */
    @ApiStatus.Internal
    public static int[] _filterMeleeVictims(@NotNull List<Entity> victims) {
        int maxVictims = Mth.clamp(victims.size(), 0, SyncConfig.MELEE_MAX_TARGET_PER_PACKET.get());
        int[] result = new int[maxVictims];
        for (int i = 0; i < maxVictims; i++) {
            result[i] = victims.get(i).getId();
        }
        return result;
    }

    public static void chargeMove(CombatantProperty combatantProperty,
                                  IMelee iMelee, ItemStack meleeItem,
                                  LocalPlayer localCombatant,
                                  TacticalAction tacticalAction) {
        @Nullable _MeleeMovement meleeMovement = iMelee.getMeleeMovement(meleeItem, tacticalAction);
        if (meleeMovement == null) return;

        float speed = meleeMovement.getSpeed();
        Consumer<LivingEntity> task = (livingCombatant) -> {
            ItemStack _meleeItem = livingCombatant.getMainHandItem();
            @Nullable IMelee _iMelee = IMeleeGetter.fromItemStack(_meleeItem);
            if (_iMelee == null) return;

            if (_iMelee.switchItemNeedReset(meleeItem, _meleeItem)) return;

            double factor = livingCombatant.onGround() ? 1.0 : 0.5; // TODO 何意味？加个config？
            Vec3 _direction = livingCombatant.getLookAngle().multiply(1, 0, 1).normalize();
            livingCombatant.addDeltaMovement(_direction.scale(factor * speed));
        };

        int moveDelay = meleeMovement.getDelay();
        if (moveDelay == 0) {
            task.accept(localCombatant);
        } else {
            combatantProperty.clientSchedule.add(new CombatantProperty._ClientDelayedEvent(task, moveDelay));
        }
    }

    public static void chargeSound(IMelee iMelee, ItemStack meleeItem,
                                   LocalPlayer localCombatant,
                                   TacticalAction tacticalAction,
                                   Vec3 origin) {
        var meleeDisplayLocation = iMelee.getMeleeDisplayLocation(meleeItem);
        @Nullable MeleeDisplayInstance meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(meleeDisplayLocation);
        if (meleeDisplayInstance == null) return;

        MeleeDisplay meleeDisplay = meleeDisplayInstance.getPojo();
        var meleeSounds = meleeDisplay.getSounds();
        @Nullable var soundLocation = meleeSounds.get(tacticalAction.getCategoryName()); {
            if (soundLocation == null) soundLocation = meleeSounds.get(tacticalAction.typeNameOld);
        }
        if (soundLocation == null) return;

        localCombatant.level().playLocalSound(
                origin.x, origin.y, origin.z,
                SoundEvent.createVariableRangeEvent(soundLocation),
                SoundSource.PLAYERS, 1.0f, 1.0f, false
        );
    }
}
