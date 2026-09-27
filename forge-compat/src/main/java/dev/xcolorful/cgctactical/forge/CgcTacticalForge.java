package dev.xcolorful.cgctactical.forge;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.forgeclient.CgcTacticalForgeClient;
import dev.xcolorful.customgun.core.api.common.McSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CgcTactical.MOD_ID)
public class CgcTacticalForge {

    public CgcTacticalForge() {
        Dist dist = FMLLoader.getDist();
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