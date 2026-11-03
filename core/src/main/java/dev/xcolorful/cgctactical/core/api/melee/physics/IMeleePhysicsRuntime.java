package dev.xcolorful.cgctactical.core.api.melee.physics;

import dev.xcolorful.cgctactical.core.api.item.melee.MeleeAction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface IMeleePhysicsRuntime {

    // --------Deprecated--------

    /**
     * // TODO 改个名
     * 客户端
     */
    @Deprecated default List<Entity> collectTargets(Player livingCombatant,
                                                    ItemStack meleeItem,
                                                    MeleeAction meleeAction,
                                                    Vec3 origin, Vec3 direction) {
        return null;
    }
}
