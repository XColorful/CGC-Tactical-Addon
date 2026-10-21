package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.cgctactical.client.network.message.combatant._S2CMessageCombatantShield_block;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageCombatantShield_block implements IMessage<S2CMessageCombatantShield_block> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageCombatantShield_block decode(FriendlyByteBuf buffer) {
        return new S2CMessageCombatantShield_block();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageCombatantShield_block.handle(this)
                );
            });
        }
    }
}
