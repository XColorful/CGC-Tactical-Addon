package dev.xcolorful.cgctactical.client.network.message.combatant;

import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantMelee_reset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class _S2CMessageCombatantMelee_reset {

    public static void handle(S2CMessageCombatantMelee_reset message) {
        @Nullable LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;

        // TODO ILivingCombatant
    }
}
