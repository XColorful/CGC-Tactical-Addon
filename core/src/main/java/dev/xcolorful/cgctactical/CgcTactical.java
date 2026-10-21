package dev.xcolorful.cgctactical;

import com.mojang.logging.LogUtils;
import dev.xcolorful.cgctactical.core.event.custom.CoreEventHandlers;
import dev.xcolorful.cgctactical.core.init.ModConfig;
import dev.xcolorful.cgctactical.core.network.NetworkHandler;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.INetworkAdapter;
import org.slf4j.Logger;

public class CgcTactical {
    public static final String MOD_ID = "cgctactical";
    public static final String MOD_ID_OLD1 = "lrtactical";
    public static final Logger LOGGER = LogUtils.getLogger();

    protected static boolean initialized;
    protected static McSide mcSide = McSide.CLIENT;

    public static void init(McSide mcSide,
                            INetworkAdapter networkAdapter) {
        if (initialized) return;

        CgcTactical.mcSide = mcSide;

        NetworkHandler.initialize(networkAdapter);

        ModConfig.init();

        CoreEventHandlers.registerAll(CustomGun.getEventRegister());

        initialized = true;
    }

    public static McSide getMcSide() {
        return mcSide;
    }
}
