package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class C2SMessageCombatantMelee_prepare implements IMessage<C2SMessageCombatantMelee_prepare> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static C2SMessageCombatantMelee_prepare decode(FriendlyByteBuf buffer) {
        return new C2SMessageCombatantMelee_prepare();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.CLIENT_TO_SERVER) {
            handler.accept(() -> {
                if (!(context.sender() instanceof ServerPlayer player)) {
                    return;
                }

                // TODO ILivingCombatant, S2CMessageSyncMeleeAnimation
            });
        }
    }
}
