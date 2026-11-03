```java
package dev.xcolorful.cgctactical.core.api.melee;

public interface IMeleeMainManager extends IMeleeSubManager {
    boolean registerRuntimeGroup(MeleeManagerGroup meleeManagerGroup);
    @NotNull MeleeManagerGroup getManagerGroup(String managerGroupTag);

    default @NotNull MeleeManagerGroup getManagerGroup(IMelee iMelee, ItemStack meleeItem) {
        return this.getManagerGroup(iMelee.getManagerGroupTag(meleeItem));
    }
    default IMeleeAttackManager getMeleeAttackManager(IMelee iMelee, ItemStack meleeItem) {
        return this.getManagerGroup(iMelee.getManagerGroupTag(meleeItem)).meleeAttackManager();
    }
    default IMeleePhysicsManager getMeleePhysicsManager(IMelee iMelee, ItemStack meleeItem) {
        return this.getManagerGroup(iMelee.getManagerGroupTag(meleeItem)).meleePhysicsManager();
    }
}
```
