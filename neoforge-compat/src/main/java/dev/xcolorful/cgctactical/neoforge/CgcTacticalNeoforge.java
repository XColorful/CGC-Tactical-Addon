package dev.xcolorful.cgctactical.neoforge;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.neoforgeclient.CgcTacticalNeoforgeClient;
import dev.xcolorful.customgun.core.api.common.McSide;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(CgcTactical.MOD_ID)
public class CgcTacticalNeoforge {

    public CgcTacticalNeoforge(IEventBus modEventBus) {
        Dist dist = FMLLoader.getCurrent().getDist();
        McSide mcSide = dist.isClient() ? McSide.CLIENT : McSide.DEDICATED_SERVER;

        CgcTactical.init(mcSide);

        if (mcSide == McSide.CLIENT) {
            _CgcTacticalNeoforgeClient.init();
        }
    }

    private static class _CgcTacticalNeoforgeClient {
        public static void init() {
            CgcTacticalNeoforgeClient.init();
        }
    }
}
