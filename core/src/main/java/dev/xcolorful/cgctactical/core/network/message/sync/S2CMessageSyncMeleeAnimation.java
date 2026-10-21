package dev.xcolorful.cgctactical.core.network.message.sync;

import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.cgctactical.client.network.message.sync._S2CMessageSyncMeleeAnimation;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
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
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageSyncMeleeAnimation.handle(this)
                );
            });
        }
    }
}
