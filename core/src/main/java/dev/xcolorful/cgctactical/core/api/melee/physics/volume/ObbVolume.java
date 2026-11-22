package dev.xcolorful.cgctactical.core.api.melee.physics.volume;

import dev.xcolorful.cgctactical.core.api.melee.physics.IHitVolume;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._TargetFilterData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ObbVolume implements IHitVolume {
    public static final ObbVolume INSTANCE = new ObbVolume();

    // --------IHitVolume--------

    @Override
    public @NotNull List<Entity> collectTargets(Level level,
                                                LivingEntity livingCombatant,
                                                _TargetFilterData targetFilterData,
                                                Vec3 origin, Vec3 direction) {
        return List.of();
    }
}
