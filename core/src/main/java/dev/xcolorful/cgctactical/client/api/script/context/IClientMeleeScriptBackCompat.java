package dev.xcolorful.cgctactical.client.api.script.context;

import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.script.context.IMeleeScriptBackCompat;
import dev.xcolorful.customgun.client.util.ClientInputUtils;
import dev.xcolorful.customgun.core.util.EntityUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public interface IClientMeleeScriptBackCompat extends IClientMeleeScriptContextAccess, IMeleeScriptBackCompat {

    // --------IClientMeleeScriptContextAccess--------

    @Deprecated(forRemoval = true)
    default <T> Optional<T> processCameraEntity(Function<Entity, T> processor) {
        @Nullable Entity cameraCombatant = this.getCameraCombatant();
        if (cameraCombatant != null) {
            return Optional.ofNullable(processor.apply(cameraCombatant));
        } else {
            return Optional.empty();
        }
    }

    // --------IClientMeleeScriptBackCompat--------
    
    default long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    default boolean isInputUp() {
        @Nullable LocalPlayer localShooter = this.getLocalCombatant();
        return ClientInputUtils.Key.forward(localShooter);
    }
    default boolean isInputDown() {
        @Nullable LocalPlayer localShooter = this.getLocalCombatant();
        return ClientInputUtils.Key.backward(localShooter);
    }
    default boolean isInputLeft() {
        @Nullable LocalPlayer localShooter = this.getLocalCombatant();
        return ClientInputUtils.Key.left(localShooter);
    }
    default boolean isInputRight() {
        @Nullable LocalPlayer localShooter = this.getLocalCombatant();
        return ClientInputUtils.Key.right(localShooter);
    }
    default boolean isInputJumping() {
        @Nullable LocalPlayer localShooter = this.getLocalCombatant();
        return ClientInputUtils.Key.jump(localShooter);
    }

    default boolean isOnGround() {
        @Nullable Entity entity = this.getCameraCombatant();
        return entity != null && entity.onGround();
    }
    default boolean isCrouching() {
        @Nullable Entity entity = this.getCameraCombatant();
        return entity != null && entity.isCrouching();
    }

    default void anchorWalkDist() {
        @Nullable Entity cameraShooter = this.getCameraCombatant();
        if (cameraShooter == null) return;
        float moveDist; {
            float horizontalMoveDist = EntityUtils.getHorizontalMoveDist(cameraShooter);
            float horizontalMoveDistOld = EntityUtils.getHorizontalMoveDistOld(cameraShooter);
            moveDist = horizontalMoveDist + (horizontalMoveDist - horizontalMoveDistOld) * this.getPartialTicks();
        }
        this.setWalkDistAnchor(moveDist);
    }
    default float getWalkDist() {
        @Nullable Entity cameraShooter = this.getCameraCombatant();
        if (cameraShooter == null) return 0;
        float moveDist; {
            float horizontalMoveDist = EntityUtils.getHorizontalMoveDist(cameraShooter);
            float horizontalMoveDistOld = EntityUtils.getHorizontalMoveDistOld(cameraShooter);
            moveDist = horizontalMoveDist + (horizontalMoveDist - horizontalMoveDistOld) * this.getPartialTicks();
        }
        return moveDist - this.getWalkDistAnchor();
    }

    default int getActionCount(String action) {
        @Nullable TacticalAction tacticalAction = TacticalAction.fromString(action);
        if (tacticalAction == null) return 0;

        @Nullable Entity cameraShooter = this.getCameraCombatant();
        if (cameraShooter == null) return 0;

        @Nullable ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromEntity(cameraShooter);
        if (iLivingCombatant == null) return 0;

        return iLivingCombatant.cgctactical$getCombatantProperty().getActionCount(tacticalAction);
    }
}
