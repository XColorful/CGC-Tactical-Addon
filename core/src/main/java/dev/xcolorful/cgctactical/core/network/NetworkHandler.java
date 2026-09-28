package dev.xcolorful.cgctactical.core.network;

import dev.xcolorful.cgctactical.core.network.message.combatant.C2SMessageCombatantConsumable_cancel;
import dev.xcolorful.cgctactical.core.network.message.combatant.C2SMessageCombatantMelee_attack;
import dev.xcolorful.cgctactical.core.network.message.combatant.C2SMessageCombatantMelee_prepare;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantItemCooldown;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantMelee_reset;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantShield_block;
import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantShield_disable;
import dev.xcolorful.cgctactical.core.network.message.resource.S2CMessageResourceSound;
import dev.xcolorful.cgctactical.core.network.message.resource.S2CMessageSyncDataPack;
import dev.xcolorful.cgctactical.core.network.message.sync.S2CMessageSyncMeleeAnimation;
import dev.xcolorful.cgctactical.core.network.message.world.S2CMessageNearbyExplosion;
import dev.xcolorful.cgctactical.core.network.message.world.S2CMessageSplashParticle;
import dev.xcolorful.customgun.core.api.network.INetworkAdapter;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import org.jetbrains.annotations.ApiStatus;

public final class NetworkHandler extends dev.xcolorful.customgun.core.network.NetworkHandler {
    private static NetworkHandler INSTANCE;

    public static final int protocol_version = 0;

    private NetworkHandler(INetworkAdapter adapter) {
        super(adapter);
    }

    @ApiStatus.Internal
    public static void initialize(INetworkAdapter adapter) {
        INSTANCE = new NetworkHandler(adapter);
    }

    @ApiStatus.Internal
    public static NetworkHandler get() {
        return INSTANCE;
    }

    public void registerMessages() {
        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageSyncDataPack.class, S2CMessageSyncDataPack::decode, MessageDirection.SERVER_TO_CLIENT);
        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageCombatantItemCooldown.class, S2CMessageCombatantItemCooldown::decode, MessageDirection.SERVER_TO_CLIENT);
        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageResourceSound.class, S2CMessageResourceSound::decode, MessageDirection.SERVER_TO_CLIENT);

        adapter.registerMessage(ID_COUNT.getAndIncrement(), C2SMessageCombatantMelee_attack.class, C2SMessageCombatantMelee_attack::decode, MessageDirection.CLIENT_TO_SERVER);
        adapter.registerMessage(ID_COUNT.getAndIncrement(), C2SMessageCombatantMelee_prepare.class, C2SMessageCombatantMelee_prepare::decode, MessageDirection.CLIENT_TO_SERVER);

        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageCombatantShield_block.class, S2CMessageCombatantShield_block::decode, MessageDirection.SERVER_TO_CLIENT);
        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageCombatantShield_disable.class, S2CMessageCombatantShield_disable::decode, MessageDirection.SERVER_TO_CLIENT);
        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageNearbyExplosion.class, S2CMessageNearbyExplosion::decode, MessageDirection.SERVER_TO_CLIENT);

        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageSplashParticle.class, S2CMessageSplashParticle::decode, MessageDirection.SERVER_TO_CLIENT);

        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageSyncMeleeAnimation.class, S2CMessageSyncMeleeAnimation::decode, MessageDirection.SERVER_TO_CLIENT);
        adapter.registerMessage(ID_COUNT.getAndIncrement(), S2CMessageCombatantMelee_reset.class, S2CMessageCombatantMelee_reset::decode, MessageDirection.SERVER_TO_CLIENT);
        adapter.registerMessage(ID_COUNT.getAndIncrement(), C2SMessageCombatantConsumable_cancel.class, C2SMessageCombatantConsumable_cancel::decode, MessageDirection.CLIENT_TO_SERVER);
    }
}
