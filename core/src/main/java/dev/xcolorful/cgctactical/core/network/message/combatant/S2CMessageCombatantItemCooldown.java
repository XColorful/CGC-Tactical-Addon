package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageCombatantItemCooldown implements IMessage<S2CMessageCombatantItemCooldown> {

    @Override
    public void encode(S2CMessageCombatantItemCooldown message, FriendlyByteBuf buffer) {
    }

    public static S2CMessageCombatantItemCooldown decode(FriendlyByteBuf buffer) {
        return new S2CMessageCombatantItemCooldown();
    }

    @Override
    public void handle(S2CMessageCombatantItemCooldown message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
