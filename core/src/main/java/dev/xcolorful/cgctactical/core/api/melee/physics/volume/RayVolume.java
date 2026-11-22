package dev.xcolorful.cgctactical.core.api.melee.physics.volume;

import dev.xcolorful.cgctactical.core.api.melee.physics.IHitVolume;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._TargetFilterData;
import dev.xcolorful.cgctactical.core.util.HitVolumeUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RayVolume extends AbstractVolume implements IHitVolume {
    public static final RayVolume INSTANCE = new RayVolume();

    public RayVolume() {
        super();
    }

    // --------IHitVolume--------

    @Override
    public @NotNull List<Entity> collectTargets(Level level,
                                                LivingEntity livingCombatant,
                                                _TargetFilterData targetFilterData,
                                                Vec3 origin, Vec3 direction) {
        double maxRange = targetFilterData.getMaxRange();
        Vec3 end = origin.add(direction.normalize().scale(maxRange));

        List<EntityHitResult> hitResults = HitVolumeUtils.rayTraceEntities(
                livingCombatant,
                origin,
                end,
                1.0,
                entity -> !(targetFilterData.isExcludeSelf() && entity == livingCombatant)
                        && entity != livingCombatant.getVehicle()
                        && entity.isAlive()
                        && !entity.isSpectator(),
                entity -> HitVolumeUtils.hasLineOfSight(livingCombatant, entity, MAX_CLIP_DISTANCE)
        );
        // 已按命中距离升序排列，穿透数量上限即截取前 pierce + 1 个
        return hitResults.stream()
                .limit(targetFilterData.getPierce() + 1L)
                .map(EntityHitResult::getEntity)
                .toList();
    }
}
