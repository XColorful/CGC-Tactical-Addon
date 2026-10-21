package dev.xcolorful.cgctactical.neoforge.init;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.init.CommonSetup;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = CgcTactical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeoCommonSetup {

    private static final CommonSetup COMMON_SETUP = CommonSetup.get();

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(COMMON_SETUP::onCommonSetup);
    }
}
