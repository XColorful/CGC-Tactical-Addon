package dev.xcolorful.cgctactical.client.init;

import dev.xcolorful.cgctactical.client.particle.SmokeCloudParticle;
import dev.xcolorful.cgctactical.core.init.registry.ModParticles;
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class ClientModParticles {

    private static final ClientModParticles INSTANCE = new ClientModParticles();

    public static ClientModParticles get() {
        return INSTANCE;
    }

    @FunctionalInterface
    public interface SpriteParticleRegisterer {
        <T extends ParticleOptions> void register(ParticleType<T> type, ParticleResources.SpriteParticleRegistration<T> registration);
    }

    public void onRegisterParticleProviders(SpriteParticleRegisterer registerer) {
        registerer.register(ModParticles.SMOKE_CLOUD.get(), SmokeCloudParticle.PROVIDER);
    }
}
