package dev.xcolorful.cgctactical.core.mixin.entity;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingCombatant {
    private final LivingEntity livingCombatant = (LivingEntity) (Object) this;
    private final CombatantProperty combatantProperty = new CombatantProperty(livingCombatant);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void cgctactical$onLivingTick(CallbackInfo ci) {
        if (this.cgctactical$enableDefaultLivingCombatantTick()) {
            this.cgctactical$defaultLivingCombatantTick();
        }
    }

    private void cgctactical$defaultLivingCombatantTick() {
        if (level().isClientSide()) {
            return;
        }
    }

    // --------ILivingCombatant--------

    @Override
    public void cgctactical$initLivingCombatant() {
    }

    @Override
    public CombatantProperty cgctactical$getCombatantProperty() {
        return this.combatantProperty;
    }
}
