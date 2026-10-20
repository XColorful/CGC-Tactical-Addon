package dev.xcolorful.cgctactical.neoforge;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.network.NetworkHandler;
import dev.xcolorful.cgctactical.neoforgeclient.CgcTacticalNeoforgeClient;
import dev.xcolorful.customgun.core.api.common.ISideExecutor;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.minecraft.IMcRegistry;
import dev.xcolorful.customgun.core.api.network.INetworkAdapter;
import dev.xcolorful.customgun.neoforge.common.NeoSideExecutor;
import dev.xcolorful.customgun.neoforge.minecraft.NeoRegistry;
import dev.xcolorful.customgun.neoforge.network.NeoNetworkAdapter;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(CgcTactical.MOD_ID)
public class CgcTacticalNeoforge {

    public static ISideExecutor sideExecutor;
    public static IMcRegistry mcRegistry;
    public static INetworkAdapter networkAdapter;

    public CgcTacticalNeoforge(IEventBus modEventBus) {
        CgcTacticalNeoforge.sideExecutor = new NeoSideExecutor();
        CgcTacticalNeoforge.mcRegistry = new NeoRegistry();
        CgcTacticalNeoforge.networkAdapter = new NeoNetworkAdapter(modEventBus, CgcTacticalNeoforge.mcRegistry, CgcTactical.MOD_ID, NetworkHandler.protocol_version);
        Dist dist = FMLLoader.getDist();
        McSide mcSide = dist.isClient() ? McSide.CLIENT : McSide.DEDICATED_SERVER;

        CgcTactical.init(mcSide, CgcTacticalNeoforge.sideExecutor);

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
