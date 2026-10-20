package dev.xcolorful.cgctactical.core.network.message.world;

import dev.xcolorful.cgctactical.client.network.message.world._S2CMessageNearbyExplosion;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageNearbyExplosion implements IMessage<S2CMessageNearbyExplosion> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageNearbyExplosion decode(FriendlyByteBuf buffer) {
        return new S2CMessageNearbyExplosion();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageNearbyExplosion.handle(this)
                );
            });
        }
    }
}
