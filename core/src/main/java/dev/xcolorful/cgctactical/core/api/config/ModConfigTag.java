package dev.xcolorful.cgctactical.core.api.config;

public class ModConfigTag {

    // GrenadeConfig
    public static final String grenade_path = "grenade";

    public static final String grenadeExplosionBlockDamage_path = "GrenadeExplosionBlockDamage";
    public static final String grenadeExplosionBlockDamage_comment = "Whether grenade explosions can damage blocks (if the grenade can)";

    // MeleeConfig
    public static final String melee_path = "melee";

    public static final String meleeItemConsumeDurability_path = "MeleeItemConsumeDurability";
    public static final String meleeItemConsumeDurability_comment = "Whether melee items consume durability on attack";

    public static final String meleeIgnoreInvulnerableTickThreshold_path = "MeleeIgnoreInvulnerableTickThreshold";
    public static final String meleeIgnoreInvulnerableTickThreshold_comment = "When the target's invulnerable tick is less than this value, melee attacks will ignore it";

    // SyncConfig
    public static final String flashShield_path = "flash_shield";

    public static final String flashShieldMaxDurability_path = "FlashShieldMaxDurability";
    public static final String flashShieldMaxDurability_comment = "Max durability of the flash shield";

    public static final String flashShieldCooldown_path = "FlashShieldCooldown";
    public static final String flashShieldCooldown_comment = "Cooldown of the flash shield, in ticks";

    public static final String throwable_path = "throwable";

    public static final String crouchingInitSpeedPercent_path = "CrouchingInitSpeedPercent";
    public static final String crouchingInitSpeedPercent_comment = "Modifier of the initial speed when crouching, 1.0 means no change";

    public static final String meleeMaxTargetPerPacket_path = "MeleeMaxTargetPerPacket";
    public static final String meleeMaxTargetPerPacket_comment = "The maximum number of targets that a melee attack request packet can contain";

    private ModConfigTag() {}
}
