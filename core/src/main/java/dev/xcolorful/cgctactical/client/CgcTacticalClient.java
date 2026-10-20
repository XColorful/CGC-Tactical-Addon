package dev.xcolorful.cgctactical.client;

import dev.xcolorful.cgctactical.client.event.custom.ClientEventHandlers;
import dev.xcolorful.customgun.CustomGun;

public class CgcTacticalClient {

    protected static boolean initialized;

    public static void init() {
        if (initialized) return;

        ClientEventHandlers.registerAll(CustomGun.getEventRegister());

        initialized = true;
    }
}
