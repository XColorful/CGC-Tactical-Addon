package dev.xcolorful.cgctactical.client.config;

import dev.xcolorful.cgctactical.core.api.config.ClientModConfigTag;
import dev.xcolorful.customgun.core.api.config.IModConfigSpec;
import dev.xcolorful.customgun.core.api.config.IModConfigSpecBuilder;

public class KeyConfig {
    public static IModConfigSpec<Boolean> CONTINUOUS_ACTION_INPUT;

    public static void init(IModConfigSpecBuilder builder) {
        builder.startBuild(ClientModConfigTag.key_path);

        builder.addComment(ClientModConfigTag.continuousActionInput_comment);
        CONTINUOUS_ACTION_INPUT = builder.addConfig(ClientModConfigTag.continuousActionInput_path, true);

        builder.finishBuild();
    }
}
