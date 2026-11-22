package dev.xcolorful.cgctactical.core.api.melee.physics.volume;

import dev.xcolorful.cgctactical.core.api.melee.physics.IHitVolume;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._TargetFilterData;
import dev.xcolorful.cgctactical.core.util.HitVolumeUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class ObbVolume extends AbstractVolume implements IHitVolume {
    public static final ObbVolume INSTANCE = new ObbVolume();

    public ObbVolume() {
        super();
    }

    // --------IHitVolume--------

    @Override
    public @NotNull List<Entity> collectTargets(Level level,
                                                LivingEntity livingCombatant,
                                                _TargetFilterData targetFilterData,
                                                Vec3 origin, Vec3 direction) {
        float maxRange = targetFilterData.getMaxRange();

        // 旋转跟随施击者朝向，direction 仅用于确定向前延伸的盒中心
        Quaternionf rotation = new Quaternionf().rotationYXZ(
                -livingCombatant.getYRot() * Mth.DEG_TO_RAD,
                livingCombatant.getXRot() * Mth.DEG_TO_RAD,
                targetFilterData.getRoll() * Mth.DEG_TO_RAD
        );
        Vector3f extents = new Vec3(targetFilterData.getHalfWidth(), targetFilterData.getHalfHeight(), maxRange / 2.0).toVector3f();
        Vector3f center = origin.add(direction.scale(maxRange / 2.0)).toVector3f();

        AABB area = livingCombatant.getBoundingBox().inflate(maxRange * 2, maxRange, maxRange * 2);

        return collectInArea(level, livingCombatant, area, targetFilterData.isExcludeSelf(),
                entity -> HitVolumeUtils.obbIntersectsAABB(center, extents, rotation, entity.getBoundingBox()));
    }
}
