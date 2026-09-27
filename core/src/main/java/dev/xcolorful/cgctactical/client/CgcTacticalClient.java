package dev.xcolorful.cgctactical.client;

public class CgcTacticalClient {

    protected static boolean initialized;

    public static void init() {
        if (initialized) return;

        initialized = true;
    }
}
