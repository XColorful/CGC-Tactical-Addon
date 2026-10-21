package dev.xcolorful.cgctactical.core.init.registry;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.minecraft.particle.CustomParticleType;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.init.registry.IRegistrar;
import dev.xcolorful.customgun.core.api.init.registry.IRegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticles {

    public static final IRegistrar<ParticleType<?>> PARTICLE_TYPES = CustomGun.getRegistrarFactory().createParticleTypes(CgcTactical.MOD_ID);

    public static final SimpleParticleType SMOKE_CLOUD_PARTICLE_TYPE = new SimpleParticleType(true);
    public static final IRegistryObject<SimpleParticleType> SMOKE_CLOUD = PARTICLE_TYPES.register(CustomParticleType.SMOKE_CLOUD.getTagName(), () ->
            SMOKE_CLOUD_PARTICLE_TYPE);
}
