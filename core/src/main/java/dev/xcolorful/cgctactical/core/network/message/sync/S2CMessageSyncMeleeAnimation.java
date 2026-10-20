package dev.xcolorful.cgctactical.core.network.message.sync;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageSyncMeleeAnimation implements IMessage<S2CMessageSyncMeleeAnimation> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageSyncMeleeAnimation decode(FriendlyByteBuf buffer) {
        return new S2CMessageSyncMeleeAnimation();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
    }
}
