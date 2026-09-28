package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public record C2SMessageCombatantConsumable_cancel() implements IMessage<C2SMessageCombatantConsumable_cancel> {

    @Override
    public void encode(C2SMessageCombatantConsumable_cancel message, FriendlyByteBuf buffer) {
    }

    public static C2SMessageCombatantConsumable_cancel decode(FriendlyByteBuf buffer) {
        return new C2SMessageCombatantConsumable_cancel();
    }

    @Override
    public void handle(C2SMessageCombatantConsumable_cancel message, Consumer<Runnable> handler, NetworkContext context) {
        if (CgcTactical.getSideExecutor().getLogicalSide().isServer()) {
            handler.accept(() -> {
                if (!(context.sender() instanceof ServerPlayer player)) {
                    return;
                }

                ItemStack useItem = player.getUseItem();
                // TODO
            });
        }
    }
}
