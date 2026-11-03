package dev.xcolorful.cgctactical.core.mixin.entity;

import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerMixin implements ILivingCombatantConfig {

    // --------ILivingCombatantConfig--------

    @Override
    public boolean cgctactical$enableDefaultLivingCombatantTick() {
        return true;
    }
}
