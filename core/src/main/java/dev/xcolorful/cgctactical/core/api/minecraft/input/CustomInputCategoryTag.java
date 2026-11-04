package dev.xcolorful.cgctactical.core.api.minecraft.input;

import dev.xcolorful.cgctactical.CgcTactical;
import org.jetbrains.annotations.ApiStatus;

public class CustomInputCategoryTag {

    // 1.21.10+会强行加上"key.category"前缀，并跟上RL的namespace
    public static final String PREFIX = "key.category." + CgcTactical.MOD_ID + "."; @ApiStatus.AvailableSince("1.21.10") public static final String PREFIX_OLD1 = "key.category." + CgcTactical.MOD_ID + ".";

    public static final String COMBATANT = "combatant";

    private CustomInputCategoryTag() {}
}
