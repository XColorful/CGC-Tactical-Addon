package dev.xcolorful.cgctactical.client.network.message.world;

import dev.xcolorful.cgctactical.core.network.message.world.S2CMessageSplashParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class _S2CMessageSplashParticle {

    public static void handle(S2CMessageSplashParticle message) {
        Minecraft mc = Minecraft.getInstance();
        @Nullable LocalPlayer localPlayer = mc.player;
        @Nullable ClientLevel level = mc.level;
        if (localPlayer == null || level == null) return;

        // TODO
    }
}
