package dev.xcolorful.cgctactical.neoforgeclient.init;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.init.ClientModParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CgcTactical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeoClientModParticles {

    public static ClientModParticles CLIENT_MOD_PARTICLES = ClientModParticles.get();

    @SubscribeEvent
    public static void onRegisterParticleFactory(RegisterParticleProvidersEvent event) {
        CLIENT_MOD_PARTICLES.onRegisterParticleProviders(event::registerSpriteSet);
    }
}
