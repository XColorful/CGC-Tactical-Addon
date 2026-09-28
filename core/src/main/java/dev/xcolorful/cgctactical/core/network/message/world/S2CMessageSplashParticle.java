package dev.xcolorful.cgctactical.core.network.message.world;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageSplashParticle implements IMessage<S2CMessageSplashParticle> {

    @Override
    public void encode(S2CMessageSplashParticle message, FriendlyByteBuf buffer) {
    }

    public static S2CMessageSplashParticle decode(FriendlyByteBuf buffer) {
        return new S2CMessageSplashParticle();
    }

    @Override
    public void handle(S2CMessageSplashParticle message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
