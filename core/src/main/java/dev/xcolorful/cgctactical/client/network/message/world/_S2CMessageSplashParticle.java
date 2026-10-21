package dev.xcolorful.cgctactical.client.network.message.world;

import dev.xcolorful.cgctactical.core.network.message.world.S2CMessageSplashParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class _S2CMessageSplashParticle {

    public static void handle(S2CMessageSplashParticle message) {
        Minecraft mc = Minecraft.getInstance();
        @Nullable LocalPlayer localPlayer = mc.player;
        @Nullable ClientLevel level = mc.level;
        if (localPlayer == null || level == null) return;

        int color = message.color();
        RandomSource random = level.random;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        Vec3 pos = Vec3.atBottomCenterOf(message.blockPos());

        ParticleOptions particleOptions = ParticleTypes.EFFECT;

        for (int i = 0; i < 100; ++i) {
            double distance = random.nextDouble() * 4.0D;
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double xSpeed = Math.cos(angle) * distance;
            double ySpeed = 0.01D + random.nextDouble() * 0.5D;
            double zSpeed = Math.sin(angle) * distance;
            @Nullable Particle particle = mc.particleEngine.createParticle(particleOptions,
                    pos.x + xSpeed * 0.1D, pos.y + 0.3D, pos.z + zSpeed * 0.1D,
                    xSpeed, ySpeed, zSpeed);
            if (particle != null) {
                float factor = 0.75F + random.nextFloat() * 0.25F;
                particle.setColor(r * factor, g * factor, b * factor);
                particle.setPower((float) distance);
            }
        }
    }
}
