package dev.xcolorful.cgctactical.neoforgeclient.init;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.init.ClientTooltipRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = CgcTactical.MOD_ID)
public class NeoClientTooltipRegistry {

    private static final ClientTooltipRegistry CLIENT_TOOLTIP_REGISTRY = ClientTooltipRegistry.get();

    @SubscribeEvent
    public static void onClientTooltipRegister(RegisterClientTooltipComponentFactoriesEvent event) {
        CLIENT_TOOLTIP_REGISTRY.registerTooltips(event::register);
    }
}
