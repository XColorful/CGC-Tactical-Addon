/*
 * Go to BattleRoyale GameManager 的设计
 */

package dev.xcolorful.cgctactical.core.melee;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.melee.IMeleeManager;
import dev.xcolorful.cgctactical.core.api.melee.MeleeManagerGroup;
import dev.xcolorful.cgctactical.core.melee.attack.MeleeAttackManager;
import dev.xcolorful.cgctactical.core.melee.physics.MeleePhysicsManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MeleeManager implements IMeleeManager {
    public static final MeleeManager INSTANCE = new MeleeManager();

    private final MeleeManagerGroup defaultGroup = new MeleeManagerGroup("default",
            MeleeAttackManager.INSTANCE,
            MeleePhysicsManager.INSTANCE);
    private final Map<String, MeleeManagerGroup> managerGroups;

    protected MeleeManager() {
        this.managerGroups = new HashMap<>();
        this.registerRuntimeGroup(defaultGroup);
    }

    public static final String _MANAGER_NAME = String.format("%s:%s", CgcTactical.MOD_ID, MeleeManager.class.getSimpleName());
    @Override public String getManagerName() {
        return _MANAGER_NAME;
    }

    // --------IMeleeMainManager--------

    @Override
    public boolean registerRuntimeGroup(MeleeManagerGroup meleeManagerGroup) {
        return this.managerGroups.putIfAbsent(meleeManagerGroup.managerGroupTag(), meleeManagerGroup) == null;
    }
    @Override
    public @NotNull MeleeManagerGroup getManagerGroup(String managerGroupTag) {
        MeleeManagerGroup group = this.managerGroups.get(managerGroupTag);
        return group != null ? group : this.defaultGroup;
    }
}
