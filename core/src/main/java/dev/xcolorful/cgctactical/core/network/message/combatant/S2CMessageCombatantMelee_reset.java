package dev.xcolorful.cgctactical.core.network.message.combatant;

import dev.xcolorful.customgun.core.api.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

/**
 * 客户端本地计算结果非法时通知其重置状态，跟{@link dev.xcolorful.customgun.core.network.message.shooter.S2CMessageShooterBaseTimestamp}有相似性质
 */
public class S2CMessageCombatantMelee_reset implements IMessage<S2CMessageCombatantMelee_reset> {

    @Override
    public void encode(S2CMessageCombatantMelee_reset message, FriendlyByteBuf buffer) {
    }

    public static S2CMessageCombatantMelee_reset decode(FriendlyByteBuf buffer) {
        return new S2CMessageCombatantMelee_reset();
    }

    @Override
    public void handle(S2CMessageCombatantMelee_reset message, Consumer<Runnable> handler, NetworkContext context) {
    }
}
