package dev.xcolorful.cgctactical.core.api.melee.physics.volume;

import dev.xcolorful.cgctactical.core.api.melee.physics.IHitVolume;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.attack.info._TargetFilterData;
import dev.xcolorful.cgctactical.core.util.HitVolumeUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConeVolume extends AbstractVolume implements IHitVolume {
    public static final ConeVolume INSTANCE = new ConeVolume();

    public ConeVolume() {
        super();
    }

    // --------IHitVolume--------

    @Override
    public @NotNull List<Entity> collectTargets(Level level,
                                                LivingEntity livingCombatant,
                                                _TargetFilterData targetFilterData,
                                                Vec3 origin, Vec3 direction) {
        double maxRange = targetFilterData.getMaxRange();
        AABB area = livingCombatant.getBoundingBox().inflate(maxRange * 2, maxRange, maxRange * 2);
        double halfAngle = targetFilterData.getMaxAngle() / 2.0;

        return collectInArea(level, livingCombatant, area, targetFilterData.isExcludeSelf(),
                entity -> HitVolumeUtils.isInAngle(origin, direction, entity, halfAngle, maxRange));
    }
}
