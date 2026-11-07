package dev.xcolorful.cgctactical.core.mixin.entity;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.entity.combatant.LivingCombatantDraw;
import dev.xcolorful.cgctactical.core.entity.combatant.LivingCombatantMelee;
import dev.xcolorful.cgctactical.core.entity.combatant.LivingCombatantCooldown;
import dev.xcolorful.cgctactical.core.entity.combatant.LivingCombatantSprint;import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingCombatant {
    private final LivingEntity cgctactical$livingCombatant = (LivingEntity) (Object) this;
    private final CombatantProperty cgctactical$combatantProperty = new CombatantProperty(cgctactical$livingCombatant);

    // 行为动作
    private final LivingCombatantSprint cgctactical$sprint = new LivingCombatantSprint(cgctactical$livingCombatant, cgctactical$combatantProperty);

    // 战术操作
    private final LivingCombatantDraw cgctactical$draw = new LivingCombatantDraw(cgctactical$livingCombatant, cgctactical$combatantProperty);
    private final LivingCombatantMelee cgctactical$melee = new LivingCombatantMelee(cgctactical$livingCombatant, cgctactical$combatantProperty);

    // 战术附加属性
    private final LivingCombatantCooldown cgctactical$cooldown = new LivingCombatantCooldown(cgctactical$livingCombatant, cgctactical$combatantProperty);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void cgctactical$onLivingTick(CallbackInfo ci) {
        if (this.cgctactical$enableDefaultLivingCombatantTick()) {
            this.cgctactical$defaultLivingCombatantTick();
        }
    }

    private void cgctactical$defaultLivingCombatantTick() {
        this.cgctactical$draw.tickDrawLegacy();
        this.cgctactical$cooldown.tickItemCooldown();
        this.cgctactical$sprint.tickSprint();
        this.cgctactical$draw.tickDraw();
    }

    // --------ILivingCombatant--------

    @Override
    public void cgctactical$initLivingCombatant() {
    }

    @Override
    public CombatantProperty cgctactical$getCombatantProperty() {
        return this.cgctactical$combatantProperty;
    }

    // --------ITacticalOperator--------

    // ----IMeleeOperator----
    @Override
    public boolean cgctactical$meleeCharge() {
        return this.cgctactical$melee.meleeCharge();
    }
    @Override
    public void cgctactical$meleeAttack() {
        this.cgctactical$melee.meleeAttack();
    }
}
