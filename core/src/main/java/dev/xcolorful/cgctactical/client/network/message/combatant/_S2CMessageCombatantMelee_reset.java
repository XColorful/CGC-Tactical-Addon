package dev.xcolorful.cgctactical.client.network.message.combatant;

import dev.xcolorful.cgctactical.client.api.entity.ILocalCombatant;
import dev.xcolorful.cgctactical.client.api.entity.combatant.ILocalCombatantGetter;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
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

        ILocalCombatant iLocalCombatant = ILocalCombatantGetter.cgctactical$fromLocalPlayer(localPlayer);
        ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(localPlayer);
        iLivingCombatant.cgctactical$getCombatantProperty().resetMeleeSync();
    }
}
