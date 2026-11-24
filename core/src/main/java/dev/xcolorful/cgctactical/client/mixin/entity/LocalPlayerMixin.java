package dev.xcolorful.cgctactical.client.mixin.entity;

import dev.xcolorful.cgctactical.client.api.entity.ILocalCombatant;
import dev.xcolorful.cgctactical.client.api.entity.LocalCombatantProperty;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin implements ILocalCombatant {
    private final LocalPlayer cgctactical$localCombatant = (LocalPlayer) (Object) this;
    private final LocalCombatantProperty cgctactical$localCombatantProperty = new LocalCombatantProperty(cgctactical$localCombatant);

    @Inject(method = "tick", at = @At("HEAD"))
    public void cgc$onLocalTick(CallbackInfo ci) {
        if (!cgctactical$localCombatant.level().isClientSide()) return;
    }

    @Inject(method = "respawn", at = @At("RETURN"))
    public void cgc$onRespawn(CallbackInfo ci) {
    }

    // --------ILocalCombatant--------

    @Override
    public LocalCombatantProperty cgctactical$getLocalCombatantProperty() {
        return this.cgctactical$localCombatantProperty;
    }
}
