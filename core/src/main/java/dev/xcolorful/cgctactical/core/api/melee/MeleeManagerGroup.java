package dev.xcolorful.cgctactical.core.api.melee;

import dev.xcolorful.cgctactical.core.api.melee.attack.IMeleeAttackManager;
import dev.xcolorful.cgctactical.core.api.melee.physics.IMeleePhysicsManager;
import org.jetbrains.annotations.NotNull;

/*
文档译名: 近战管理器组 (XiaoColorful译)
 */
public record MeleeManagerGroup(String managerGroupTag,
                                @NotNull IMeleeAttackManager meleeAttackManager,
                                @NotNull IMeleePhysicsManager meleePhysicsManager) {
}
