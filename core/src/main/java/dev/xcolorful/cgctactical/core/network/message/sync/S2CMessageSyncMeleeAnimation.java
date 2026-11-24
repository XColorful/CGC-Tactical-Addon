package dev.xcolorful.cgctactical.core.network.message.sync;

import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.cgctactical.client.network.message.sync._S2CMessageSyncMeleeAnimation;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import dev.xcolorful.customgun.core.util.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public record S2CMessageSyncMeleeAnimation(int livingCombatantId,
                                           ResourceLocation animationLocation,
                                           TacticalAction tacticalAction,
                                           int combo)
        implements IMessage<S2CMessageSyncMeleeAnimation> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
        var message = this;
        buffer.writeInt(message.livingCombatantId);
        NetworkUtils.writeResourceLocation(buffer, message.animationLocation);
        buffer.writeEnum(message.tacticalAction);
        buffer.writeVarInt(message.combo);
    }

    public static S2CMessageSyncMeleeAnimation decode(FriendlyByteBuf buffer) {
        int livingCombatantId = buffer.readInt();
        var animationLocation = NetworkUtils.readResourceLocation(buffer);
        TacticalAction tacticalAction = buffer.readEnum(TacticalAction.class);
        int combo = buffer.readVarInt();
        return new S2CMessageSyncMeleeAnimation(livingCombatantId, animationLocation, tacticalAction, combo);
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
