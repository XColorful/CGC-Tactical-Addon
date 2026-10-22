package dev.xcolorful.cgctactical.client.particle;

import dev.xcolorful.customgun.client.util.ClientRenderUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.LightCoordsUtil;
import org.jetbrains.annotations.NotNull;

public class SmokeCloudParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;

    private SmokeCloudParticle(ClientLevel level, double x, double y, double z,
                               double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet.first());
        this.spriteSet = spriteSet;
        this.quadSize *= 5.5f;
        this.lifetime = 20;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    public @NotNull SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Override
    public int getLightCoords(float partialTick) {
        BlockPos pos = BlockPos.containing(this.x, this.y, this.z);
        if (!this.level.hasChunkAt(pos)) {
            return 0;
        }
        int light = LightCoordsUtil.getLightCoords(this.level, pos);
        int sky = Math.max(light >> 20 & 15, 2);
        int block = Math.max(light >> 4 & 15, 2);
        // 粒子中心陷入实心方块时该格光照为 0，会直接渲染成黑色。
        // 如果低于下限，尝试采样周围方块取最大值
        if (sky <= 2 && block <= 2) {
            for (Direction direction : Direction.values()) {
                int neighbor = LightCoordsUtil.getLightCoords(this.level, pos.relative(direction));
                sky = Math.max(sky, neighbor >> 20 & 15);
                block = Math.max(block, neighbor >> 4 & 15);
            }
        }
        return ClientRenderUtils.LightTexture_.pack(block, sky);
    }

    // --------Client particles--------

    public static final ParticleResources.SpriteParticleRegistration<SimpleParticleType> PROVIDER = spriteSet ->
            (option, world,
             x, y, z,
             xSpeed, ySpeed, zSpeed, random) -> new SmokeCloudParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
}
