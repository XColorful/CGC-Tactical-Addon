package dev.xcolorful.cgctactical.core.config;

import dev.xcolorful.cgctactical.core.api.config.ModConfigTag;
import dev.xcolorful.customgun.core.api.config.IModConfigSpec;
import dev.xcolorful.customgun.core.api.config.IModConfigSpecBuilder;

public class MeleeConfig {
    public static IModConfigSpec<Boolean> MELEE_ITEM_CONSUME_DURABILITY;
    public static IModConfigSpec<Integer> MELEE_IGNORE_INVULNERABLE_TICK_THRESHOLD;

    public static void init(IModConfigSpecBuilder builder) {
        builder.startBuild(ModConfigTag.melee_path);

        builder.addComment(ModConfigTag.meleeItemConsumeDurability_comment);
        MELEE_ITEM_CONSUME_DURABILITY = builder.addConfig(ModConfigTag.meleeItemConsumeDurability_path, true);

        builder.addComment(ModConfigTag.meleeIgnoreInvulnerableTickThreshold_comment);
        MELEE_IGNORE_INVULNERABLE_TICK_THRESHOLD = builder.addConfig(ModConfigTag.meleeIgnoreInvulnerableTickThreshold_path, 20, 0, 100);

        builder.finishBuild();
    }
}
