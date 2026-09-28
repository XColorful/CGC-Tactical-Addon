package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class C2SMessageCombatantMelee_prepare implements IMessage<C2SMessageCombatantMelee_prepare> {

    @Override
    public void encode(C2SMessageCombatantMelee_prepare message, FriendlyByteBuf buffer) {
    }

    public static C2SMessageCombatantMelee_prepare decode(FriendlyByteBuf buffer) {
        return new C2SMessageCombatantMelee_prepare();
    }

    @Override
    public void handle(C2SMessageCombatantMelee_prepare message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
