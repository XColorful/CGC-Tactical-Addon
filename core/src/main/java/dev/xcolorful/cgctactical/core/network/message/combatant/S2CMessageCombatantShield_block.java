package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageCombatantShield_block implements IMessage<S2CMessageCombatantShield_block> {

    @Override
    public void encode(S2CMessageCombatantShield_block message, FriendlyByteBuf buffer) {
    }

    public static S2CMessageCombatantShield_block decode(FriendlyByteBuf buffer) {
        return new S2CMessageCombatantShield_block();
    }

    @Override
    public void handle(S2CMessageCombatantShield_block message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
