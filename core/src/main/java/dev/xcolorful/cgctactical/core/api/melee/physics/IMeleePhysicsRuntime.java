package dev.xcolorful.cgctactical.core.api.melee.physics;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface IMeleePhysicsRuntime {

    /**
     * 可放客户端执行
     * @param combo 当前连击数
     * @param origin 近战攻击的起始点
     * @param direction 近战攻击的朝向
     * @return 受击实体列表
     */
    List<Entity> prepareMeleeVictims(CombatantProperty combatantProperty,
                                     @NotNull IMelee iMelee, @NotNull ItemStack meleeItem,
                                     ILivingCombatant iLivingCombatant, LivingEntity livingCombatant,
                                     TacticalAction tacticalAction, int combo,
                                     Vec3 origin, Vec3 direction);

    // --------Deprecated--------

    @Deprecated default List<Entity> collectTargets(LivingEntity livingCombatant,
                                                    ItemStack meleeItem,
                                                    TacticalAction tacticalAction,
                                                    Vec3 origin, Vec3 direction) {
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return new ArrayList<>();

        ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(livingCombatant);
        return prepareMeleeVictims(iLivingCombatant.cgctactical$getCombatantProperty(),
                iMelee, meleeItem,
                iLivingCombatant, livingCombatant,
                tacticalAction, 0,
                origin, direction);
    }
}
