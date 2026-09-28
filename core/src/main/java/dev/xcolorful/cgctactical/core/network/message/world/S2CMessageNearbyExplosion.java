package dev.xcolorful.cgctactical.core.network.message.world;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageNearbyExplosion implements IMessage<S2CMessageNearbyExplosion> {

    @Override
    public void encode(S2CMessageNearbyExplosion message, FriendlyByteBuf buffer) {
    }

    public static S2CMessageNearbyExplosion decode(FriendlyByteBuf buffer) {
        return new S2CMessageNearbyExplosion();
    }

    @Override
    public void handle(S2CMessageNearbyExplosion message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
