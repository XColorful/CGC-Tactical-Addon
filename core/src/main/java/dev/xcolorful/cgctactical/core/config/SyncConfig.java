package dev.xcolorful.cgctactical.core.config;

import dev.xcolorful.cgctactical.core.api.config.ModConfigTag;
import dev.xcolorful.customgun.core.api.config.IModConfigSpec;
import dev.xcolorful.customgun.core.api.config.IModConfigSpecBuilder;

public class SyncConfig {

    public static void init(IModConfigSpecBuilder builder) {
        flashShield(builder);
        throwable(builder);
        melee(builder);
    }

    public static IModConfigSpec<Integer> FLASH_SHIELD_MAX_DURABILITY;
    public static IModConfigSpec<Integer> FLASH_SHIELD_COOLDOWN;

    private static void flashShield(IModConfigSpecBuilder builder) {
        builder.startBuild(ModConfigTag.flashShield_path);

        builder.addComment(ModConfigTag.flashShieldMaxDurability_comment);
        FLASH_SHIELD_MAX_DURABILITY = builder.addConfig(ModConfigTag.flashShieldMaxDurability_path, 450, 1, 32767);

        builder.addComment(ModConfigTag.flashShieldCooldown_comment);
        FLASH_SHIELD_COOLDOWN = builder.addConfig(ModConfigTag.flashShieldCooldown_path, 200, 20, 32767);

        builder.finishBuild();
    }

    public static IModConfigSpec<Double> CROUCHING_INIT_SPEED_PERCENT;

    private static void throwable(IModConfigSpecBuilder builder) {
        builder.startBuild(ModConfigTag.throwable_path);

        builder.addComment(ModConfigTag.crouchingInitSpeedPercent_comment);
        CROUCHING_INIT_SPEED_PERCENT = builder.addConfig(ModConfigTag.crouchingInitSpeedPercent_path, 0.5, 0.01, 2.0);

        builder.finishBuild();
    }

    public static IModConfigSpec<Integer> MELEE_MAX_TARGET_PER_PACKET;

    private static void melee(IModConfigSpecBuilder builder) {
        builder.startBuild(ModConfigTag.melee_path);

        builder.addComment(ModConfigTag.meleeMaxTargetPerPacket_comment);
        MELEE_MAX_TARGET_PER_PACKET = builder.addConfig(ModConfigTag.meleeMaxTargetPerPacket_path, 32, 1, 512);

        builder.finishBuild();
    }
}
