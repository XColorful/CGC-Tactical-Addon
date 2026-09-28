package dev.xcolorful.cgctactical.core.network;

import dev.xcolorful.customgun.core.api.network.INetworkAdapter;
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
    }
}
