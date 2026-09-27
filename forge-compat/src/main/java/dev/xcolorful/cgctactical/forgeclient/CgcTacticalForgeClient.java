package dev.xcolorful.cgctactical.forgeclient;

import dev.xcolorful.cgctactical.client.CgcTacticalClient;

public class CgcTacticalForgeClient {

    protected static boolean initialized;

    public static void init() {
        if (initialized) return;

        CgcTacticalClient.init();
        initialized = true;
    }
}
