package dev.xcolorful.cgctactical.forgeclient.init;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.init.ClientModParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CgcTactical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeClientModParticles {

    public static ClientModParticles CLIENT_MOD_PARTICLES = ClientModParticles.get();

    @SubscribeEvent
    public static void onRegisterParticleFactory(RegisterParticleProvidersEvent event) {
        CLIENT_MOD_PARTICLES.onRegisterParticleProviders(event::registerSpriteSet);
    }
}
