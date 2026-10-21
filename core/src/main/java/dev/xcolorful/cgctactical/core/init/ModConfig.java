package dev.xcolorful.cgctactical.core.init;

import dev.xcolorful.cgctactical.core.config.GrenadeConfig;
import dev.xcolorful.cgctactical.core.config.MeleeConfig;
import dev.xcolorful.cgctactical.core.config.SyncConfig;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.config.IModConfigSpecBuilder;
import dev.xcolorful.customgun.core.api.config.ModConfigType;

public class ModConfig {

    public static void init() {
        CommonConfig.init();
        ServerConfig.init();
    }

    private static class CommonConfig {
        public static void init() {
            IModConfigSpecBuilder builder = CustomGun.getModConfigSpecBuilder();
            GrenadeConfig.init(builder);
            MeleeConfig.init(builder);
            builder.buildAndRegister(ModConfigType.COMMON);
        }
    }

    private static class ServerConfig {
        public static void init() {
            IModConfigSpecBuilder builder = CustomGun.getModConfigSpecBuilder();
            SyncConfig.init(builder);
            builder.buildAndRegister(ModConfigType.SERVER);
        }
    }
}
