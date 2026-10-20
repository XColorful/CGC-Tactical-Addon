package dev.xcolorful.cgctactical.client.network.message.resource;

import dev.xcolorful.cgctactical.core.network.message.resource.S2CMessageResourceSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class _S2CMessageResourceSound {

    public static void handle(S2CMessageResourceSound message) {
        Minecraft mc = Minecraft.getInstance();
        @Nullable LocalPlayer localPlayer = mc.player;
        @Nullable ClientLevel level = mc.level;
        if (localPlayer == null || level == null) return;

        // TODO
    }
}
