package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class C2SMessageCombatantMelee_attack implements IMessage<C2SMessageCombatantMelee_attack> {

    @Override
    public void encode(C2SMessageCombatantMelee_attack message, FriendlyByteBuf buffer) {
    }

    public static C2SMessageCombatantMelee_attack decode(FriendlyByteBuf buffer) {
        return new C2SMessageCombatantMelee_attack();
    }

    @Override
    public void handle(C2SMessageCombatantMelee_attack message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
