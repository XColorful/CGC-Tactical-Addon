package dev.xcolorful.cgctactical.core.network.message.resource;

import dev.xcolorful.cgctactical.client.network.message.resource._S2CMessageSyncDataPack;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageSyncDataPack implements IMessage<S2CMessageSyncDataPack> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageSyncDataPack decode(FriendlyByteBuf buffer) {
        return new S2CMessageSyncDataPack();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageSyncDataPack.doSync(this)
                );
            });
        }
    }
}
