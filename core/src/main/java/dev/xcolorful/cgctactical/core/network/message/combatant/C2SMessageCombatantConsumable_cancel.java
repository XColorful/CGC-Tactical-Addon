package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public record C2SMessageCombatantConsumable_cancel() implements IMessage<C2SMessageCombatantConsumable_cancel> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static C2SMessageCombatantConsumable_cancel decode(FriendlyByteBuf buffer) {
        return new C2SMessageCombatantConsumable_cancel();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.CLIENT_TO_SERVER) {
            handler.accept(() -> {
                if (!(context.sender() instanceof ServerPlayer player)) {
                    return;
                }

                ItemStack useItem = player.getUseItem();
                // TODO IConsumable
            });
        }
    }
}
