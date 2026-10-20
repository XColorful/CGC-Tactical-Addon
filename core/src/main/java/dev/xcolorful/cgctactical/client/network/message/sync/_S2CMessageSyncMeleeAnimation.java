package dev.xcolorful.cgctactical.client.network.message.sync;

import dev.xcolorful.cgctactical.core.api.event.combatant.MeleeAttackEvent;
import dev.xcolorful.cgctactical.core.network.message.sync.S2CMessageSyncMeleeAnimation;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class _S2CMessageSyncMeleeAnimation {

    public static void handle(S2CMessageSyncMeleeAnimation message) {
        CustomGun.getEventPoster().postCustomEvent(new MeleeAttackEvent(McLogicalSide.CLIENT));
    }
}
