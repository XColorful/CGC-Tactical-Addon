package dev.xcolorful.cgctactical.core.network.message.resource;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageResourceSound implements IMessage<S2CMessageResourceSound> {

    @Override
    public void encode(S2CMessageResourceSound message, FriendlyByteBuf buffer) {
    }

    public static S2CMessageResourceSound decode(FriendlyByteBuf buffer) {
        return new S2CMessageResourceSound();
    }

    @Override
    public void handle(S2CMessageResourceSound message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
