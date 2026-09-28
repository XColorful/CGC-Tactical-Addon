package dev.xcolorful.cgctactical.core.network.message.resource;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageSyncDataPack implements IMessage<S2CMessageSyncDataPack> {

    @Override
    public void encode(S2CMessageSyncDataPack message, FriendlyByteBuf buffer) {
    }

    public static S2CMessageSyncDataPack decode(FriendlyByteBuf buffer) {
        return new S2CMessageSyncDataPack();
    }

    @Override
    public void handle(S2CMessageSyncDataPack message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
