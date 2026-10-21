package dev.xcolorful.cgctactical.client.init;

import dev.xcolorful.cgctactical.client.config.RenderConfig;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.config.IModConfigSpecBuilder;
import dev.xcolorful.customgun.core.api.config.ModConfigType;

public class ClientModConfig {

    public static void init() {
        ClientConfig.init();
    }

    private static class ClientConfig {
        public static void init() {
            IModConfigSpecBuilder builder = CustomGun.getModConfigSpecBuilder();
            RenderConfig.init(builder);
            builder.buildAndRegister(ModConfigType.CLIENT);
        }
    }
}
