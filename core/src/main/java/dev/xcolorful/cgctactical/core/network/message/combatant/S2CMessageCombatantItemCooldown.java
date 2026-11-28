package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.cgctactical.client.network.message.combatant._S2CMessageCombatantItemCooldown;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import dev.xcolorful.customgun.core.util.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public record S2CMessageCombatantItemCooldown(ResourceLocation cooldownLocation,
                                              int cooldown)
        implements IMessage<S2CMessageCombatantItemCooldown> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
        var message = this;
        NetworkUtils.writeResourceLocation(buffer, message.cooldownLocation);
        buffer.writeInt(message.cooldown);
    }

    public static S2CMessageCombatantItemCooldown decode(FriendlyByteBuf buffer) {
        var cooldownLocation = NetworkUtils.readResourceLocation(buffer);
        int cooldown = buffer.readInt();
        return new S2CMessageCombatantItemCooldown(cooldownLocation, cooldown);
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        var message = this;
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageCombatantItemCooldown.handle(message)
                );
            });
        }
    }
}
