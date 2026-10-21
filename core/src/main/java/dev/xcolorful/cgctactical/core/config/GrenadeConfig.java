package dev.xcolorful.cgctactical.core.config;

import dev.xcolorful.cgctactical.core.api.config.ModConfigTag;
import dev.xcolorful.customgun.core.api.config.IModConfigSpec;
import dev.xcolorful.customgun.core.api.config.IModConfigSpecBuilder;

public class GrenadeConfig {
    public static IModConfigSpec<Boolean> GRENADE_EXPLOSION_BLOCK_DAMAGE;

    public static void init(IModConfigSpecBuilder builder) {
        builder.startBuild(ModConfigTag.grenade_path);

        builder.addComment(ModConfigTag.grenadeExplosionBlockDamage_comment);
        GRENADE_EXPLOSION_BLOCK_DAMAGE = builder.addConfig(ModConfigTag.grenadeExplosionBlockDamage_path, true);

        builder.finishBuild();
    }
}
