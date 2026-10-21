package dev.xcolorful.cgctactical.forge;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.network.NetworkHandler;
import dev.xcolorful.cgctactical.forgeclient.CgcTacticalForgeClient;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.INetworkAdapter;
import dev.xcolorful.customgun.forge.CustomGunForge;
import dev.xcolorful.customgun.forge.network.ForgeNetworkAdapter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CgcTactical.MOD_ID)
public class CgcTacticalForge {

    public static INetworkAdapter networkAdapter;

    public CgcTacticalForge() {
        CgcTacticalForge.networkAdapter = new ForgeNetworkAdapter(CustomGunForge.mcRegistry, CgcTactical.MOD_ID, NetworkHandler.protocol_version);
        Dist dist = CustomGunForge.getDist();
        McSide mcSide = dist.isClient() ? McSide.CLIENT : McSide.DEDICATED_SERVER;

        CgcTactical.init(mcSide);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        if (mcSide == McSide.CLIENT) {
            _CgcTacticalForgeClient.init();
        }
    }

    private static class _CgcTacticalForgeClient {
        public static void init() {
            CgcTacticalForgeClient.init();
        }
    }
}