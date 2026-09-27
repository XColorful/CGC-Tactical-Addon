package dev.xcolorful.cgctactical.neoforgeclient;

import dev.xcolorful.cgctactical.client.CgcTacticalClient;

public class CgcTacticalNeoforgeClient {

    protected static boolean initialized;

    public static void init() {
        if (initialized) return;

        CgcTacticalClient.init();
        initialized = true;
    }
}
