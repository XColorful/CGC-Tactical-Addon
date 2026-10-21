package dev.xcolorful.cgctactical.client.config;

import dev.xcolorful.cgctactical.core.api.config.ClientModConfigTag;
import dev.xcolorful.customgun.core.api.config.IModConfigSpec;
import dev.xcolorful.customgun.core.api.config.IModConfigSpecBuilder;

public class RenderConfig {
    public static IModConfigSpec<Boolean> BLACK_FLASH;
    public static IModConfigSpec<Double> EXPLODE_SCREEN_SHAKE_MULTIPLIER;

    public static void init(IModConfigSpecBuilder builder) {
        builder.startBuild(ClientModConfigTag.render_path);

        builder.addComment(ClientModConfigTag.blackFlash_comment);
        BLACK_FLASH = builder.addConfig(ClientModConfigTag.blackFlash_path, false);

        builder.addComment(ClientModConfigTag.explodeScreenShakeMultiplier_comment);
        EXPLODE_SCREEN_SHAKE_MULTIPLIER = builder.addConfig(ClientModConfigTag.explodeScreenShakeMultiplier_path, 1.0, 0.0, 128.0);

        builder.finishBuild();
    }
}
