package dev.xcolorful.cgctactical.neoforgeclient.init;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.init.ClientTooltipRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CgcTactical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeoClientTooltipRegistry {

    private static final ClientTooltipRegistry CLIENT_TOOLTIP_REGISTRY = ClientTooltipRegistry.get();

    @SubscribeEvent
    public static void onClientTooltipRegister(RegisterClientTooltipComponentFactoriesEvent event) {
        CLIENT_TOOLTIP_REGISTRY.registerTooltips(event::register);
    }
}
