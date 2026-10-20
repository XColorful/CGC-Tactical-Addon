package dev.xcolorful.cgctactical.core.network.message.world;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.network.message.world._S2CMessageSplashParticle;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageSplashParticle implements IMessage<S2CMessageSplashParticle> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageSplashParticle decode(FriendlyByteBuf buffer) {
        return new S2CMessageSplashParticle();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CgcTactical.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageSplashParticle.handle(this)
                );
            });
        }
    }
}
