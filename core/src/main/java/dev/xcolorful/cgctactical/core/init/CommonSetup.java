package dev.xcolorful.cgctactical.core.init;

import dev.xcolorful.cgctactical.core.network.NetworkHandler;

public class CommonSetup {

    private static final CommonSetup INSTANCE = new CommonSetup();
    public static CommonSetup get() {
        return INSTANCE;
    }
    private CommonSetup() {}

    public void onCommonSetup() {
        NetworkHandler.get().registerMessages();
    }
}
