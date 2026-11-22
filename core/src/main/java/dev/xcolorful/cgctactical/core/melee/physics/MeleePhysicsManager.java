package dev.xcolorful.cgctactical.core.melee.physics;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.cgctactical.core.api.melee.physics.HitVolumeType;
import dev.xcolorful.cgctactical.core.api.melee.physics.IMeleePhysicsManager;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttackData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._TargetFilterData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.combat._MeleeAttackInfo;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MeleePhysicsManager implements IMeleePhysicsManager {
    public static final MeleePhysicsManager INSTANCE = new MeleePhysicsManager();

    protected MeleePhysicsManager() {
    }
    public static final String _MANAGER_NAME = String.format("%s:%s", CgcTactical.MOD_ID, MeleePhysicsManager.class.getSimpleName());
    @Override public String getManagerName() {
        return _MANAGER_NAME;
    }

    // --------IMeleePhysicsRuntime--------

    @Override
    public List<Entity> prepareMeleeVictims(CombatantProperty combatantProperty,
                                            @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                                            ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                                            TacticalAction tacticalAction,
                                            Vec3 origin, Vec3 direction) {
        var meleeLocation = iMelee.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return new ArrayList<>();

        _TargetFilterData targetFilterData; {
            MeleeData meleeData = meleeIndexInstance.getMeleeData();
            _AttackData attackData = meleeData.getAttackData();
            @Nullable _MeleeAttackInfo attackInfo = TacticalAction.getMeleeAttackInfo(tacticalAction, attackData, 0);
            if (attackInfo == null) return new ArrayList<>();

            targetFilterData = attackInfo.getTargetFilterData();
        }

        HitVolumeType hitVolumeType = targetFilterData.getHitVolumeType();
        return hitVolumeType.getHitVolume()
                .collectTargets(livingCombatant.level(),
                        livingCombatant,
                        targetFilterData,
                        origin, direction);
    }
}
