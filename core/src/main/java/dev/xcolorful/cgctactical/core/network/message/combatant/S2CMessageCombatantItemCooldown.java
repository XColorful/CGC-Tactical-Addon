package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.cgctactical.client.network.message.combatant._S2CMessageCombatantItemCooldown;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public class S2CMessageCombatantItemCooldown implements IMessage<S2CMessageCombatantItemCooldown> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageCombatantItemCooldown decode(FriendlyByteBuf buffer) {
        return new S2CMessageCombatantItemCooldown();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageCombatantItemCooldown.handle(this)
                );
            });
        }
    }
}
