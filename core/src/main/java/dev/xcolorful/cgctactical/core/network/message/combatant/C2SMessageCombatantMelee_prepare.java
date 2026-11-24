package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.entity.combatant.LivingCombatantMelee;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public record C2SMessageCombatantMelee_prepare(TacticalAction tacticalAction,
                                               Vec3 origin,
                                               Vec3 direction)
        implements IMessage<C2SMessageCombatantMelee_prepare> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
        var message = this;
        buffer.writeEnum(message.tacticalAction);
        buffer.writeDouble(message.origin.x);
        buffer.writeDouble(message.origin.y);
        buffer.writeDouble(message.origin.z);
        buffer.writeDouble(message.direction.x);
        buffer.writeDouble(message.direction.y);
        buffer.writeDouble(message.direction.z);
    }

    public static C2SMessageCombatantMelee_prepare decode(FriendlyByteBuf buffer) {
        TacticalAction tacticalAction = buffer.readEnum(TacticalAction.class);
        Vec3 origin = new Vec3(buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble());
        Vec3 direction = new Vec3(buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble());
        return new C2SMessageCombatantMelee_prepare(tacticalAction, origin, direction);
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        var message = this;
        if (context.direction() == MessageDirection.CLIENT_TO_SERVER) {
            handler.accept(() -> {
                if (!(context.sender() instanceof ServerPlayer player)) {
                    return;
                }

                ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(player);
                if (iLivingCombatant.cgctactical$meleeCharge(McLogicalSide.SERVER, message.tacticalAction)) {
                    /**
                     * 已经在 {@link LivingCombatantMelee#_doCharge} 处理好了
                     */
                }
            });
        }
    }
}
