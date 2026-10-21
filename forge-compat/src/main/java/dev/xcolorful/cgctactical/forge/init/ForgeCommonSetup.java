package dev.xcolorful.cgctactical.forge.init;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.init.CommonSetup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = CgcTactical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeCommonSetup {

    private static final CommonSetup COMMON_SETUP = CommonSetup.get();

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(COMMON_SETUP::onCommonSetup);
    }
}
