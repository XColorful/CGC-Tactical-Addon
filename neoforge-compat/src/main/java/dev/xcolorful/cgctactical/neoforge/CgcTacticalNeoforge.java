package dev.xcolorful.cgctactical.neoforge;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.init.registry.ModParticles;
import dev.xcolorful.cgctactical.core.network.NetworkHandler;
import dev.xcolorful.cgctactical.neoforgeclient.CgcTacticalNeoforgeClient;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.INetworkAdapter;
import dev.xcolorful.customgun.neoforge.CustomGunNeoforge;
import dev.xcolorful.customgun.neoforge.network.NeoNetworkAdapter;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CgcTactical.MOD_ID)
public class CgcTacticalNeoforge {

    public static INetworkAdapter networkAdapter;

    public CgcTacticalNeoforge(IEventBus modEventBus) {
        CgcTacticalNeoforge.networkAdapter = new NeoNetworkAdapter(modEventBus, CustomGunNeoforge.mcRegistry, CgcTactical.MOD_ID, NetworkHandler.protocol_version);
        Dist dist = CustomGunNeoforge.getDist();
        McSide mcSide = dist.isClient() ? McSide.CLIENT : McSide.DEDICATED_SERVER;

        CgcTactical.init(mcSide,
                CgcTacticalNeoforge.networkAdapter);

        ModParticles.PARTICLE_TYPES.registerAll(modEventBus);

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
