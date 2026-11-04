package dev.xcolorful.cgctactical.neoforgeclient.init;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.init.ClientKeyMappingRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CgcTactical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeoClientKeyMappingRegistry {

    private static final ClientKeyMappingRegistry CLIENT_KEY_MAPPING_REGISTRY = ClientKeyMappingRegistry.get();

    @SubscribeEvent
    public static void onClientKeyMappingRegister(RegisterKeyMappingsEvent event) {
        CLIENT_KEY_MAPPING_REGISTRY.registerInputCategories((category) -> {});
        CLIENT_KEY_MAPPING_REGISTRY.registerKeyMappings(event::register);
    }
}
