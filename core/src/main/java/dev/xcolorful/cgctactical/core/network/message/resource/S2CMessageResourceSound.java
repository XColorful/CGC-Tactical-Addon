package dev.xcolorful.cgctactical.core.network.message.resource;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageResourceSound implements IMessage<S2CMessageResourceSound> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageResourceSound decode(FriendlyByteBuf buffer) {
        return new S2CMessageResourceSound();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
    }
}
