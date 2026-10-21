package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.cgctactical.client.network.message.combatant._S2CMessageCombatantMelee_reset;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

/**
 * 客户端本地计算结果非法时通知其重置状态，跟{@link dev.xcolorful.customgun.core.network.message.shooter.S2CMessageShooterBaseTimestamp}有相似性质
 */
public class S2CMessageCombatantMelee_reset implements IMessage<S2CMessageCombatantMelee_reset> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
    }

    public static S2CMessageCombatantMelee_reset decode(FriendlyByteBuf buffer) {
        return new S2CMessageCombatantMelee_reset();
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageCombatantMelee_reset.handle(this)
                );
            });
        }
    }
}
