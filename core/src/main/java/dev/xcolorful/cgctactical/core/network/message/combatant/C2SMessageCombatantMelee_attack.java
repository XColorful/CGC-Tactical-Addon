package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.config.SyncConfig;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public record C2SMessageCombatantMelee_attack(TacticalAction tacticalAction,
                                              int combo,
                                              int[] victims)
        implements IMessage<C2SMessageCombatantMelee_attack> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
        var message = this;
        buffer.writeEnum(message.tacticalAction);
        buffer.writeVarInt(message.combo);
        buffer.writeVarIntArray(message.victims);
    }

    public static C2SMessageCombatantMelee_attack decode(FriendlyByteBuf buffer) {
        TacticalAction tacticalAction = buffer.readEnum(TacticalAction.class);
        int combo = buffer.readVarInt();
        int[] victims = buffer.readVarIntArray();
        return new C2SMessageCombatantMelee_attack(tacticalAction, combo, victims);
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        var message = this;
        if (context.direction() == MessageDirection.CLIENT_TO_SERVER) {
            handler.accept(() -> {
                if (!(context.sender() instanceof ServerPlayer player)) {
                    return;
                }

                int victimLimit = SyncConfig.MELEE_MAX_TARGET_PER_PACKET.get();
                if (message.victims.length > victimLimit) {
                    CgcTactical.LOGGER.info("Received invalid C2SMessageCombatantMelee_attack from {} (tried to attack {} victims, limit: {})", player.getName().getString(), message.victims.length, victimLimit);
                    return;
                }

                // TODO ILivingCombatant
            });
        }
    }
}
