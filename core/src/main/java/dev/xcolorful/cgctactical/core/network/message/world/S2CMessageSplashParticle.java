package dev.xcolorful.cgctactical.core.network.message.world;

import dev.xcolorful.cgctactical.client.network.message.world._S2CMessageSplashParticle;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public record S2CMessageSplashParticle(BlockPos blockPos, int color)
        implements IMessage<S2CMessageSplashParticle> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockPos);
        buffer.writeInt(this.color);
    }

    public static S2CMessageSplashParticle decode(FriendlyByteBuf buffer) {
        BlockPos blockPos = buffer.readBlockPos();
        int color = buffer.readInt();
        return new S2CMessageSplashParticle(blockPos, color);
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageSplashParticle.handle(this)
                );
            });
        }
    }
}
