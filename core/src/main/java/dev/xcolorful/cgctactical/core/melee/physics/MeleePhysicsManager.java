package dev.xcolorful.cgctactical.core.melee.physics;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.melee.physics.IMeleePhysicsManager;

public class MeleePhysicsManager implements IMeleePhysicsManager {
    public static final MeleePhysicsManager INSTANCE = new MeleePhysicsManager();

    protected MeleePhysicsManager() {
    }
    public static final String _MANAGER_NAME = String.format("%s:%s", CgcTactical.MOD_ID, MeleePhysicsManager.class.getSimpleName());
    @Override public String getManagerName() {
        return _MANAGER_NAME;
    }
}
