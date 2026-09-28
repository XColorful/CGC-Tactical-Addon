package dev.xcolorful.cgctactical;

import com.mojang.logging.LogUtils;
import dev.xcolorful.customgun.core.api.common.ISideExecutor;
import dev.xcolorful.customgun.core.api.common.McSide;
import org.slf4j.Logger;

public class CgcTactical {
    public static final String MOD_ID = "cgctactical";
    public static final String MOD_ID_OLD1 = "lrtactical";
    public static final Logger LOGGER = LogUtils.getLogger();

    protected static boolean initialized;
    protected static McSide mcSide = McSide.CLIENT;
    protected static ISideExecutor sideExecutor;

    public static void init(McSide mcSide, ISideExecutor sideExecutor) {
        if (initialized) return;

        CgcTactical.mcSide = mcSide;
        CgcTactical.sideExecutor = sideExecutor;

        initialized = true;
    }

    public static McSide getMcSide() {
        return mcSide;
    }
    public static ISideExecutor getSideExecutor() {
        return sideExecutor;
    }
}
