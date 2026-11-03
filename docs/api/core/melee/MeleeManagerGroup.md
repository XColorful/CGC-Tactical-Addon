```java
package dev.xcolorful.cgctactical.core.api.melee;

public record MeleeManagerGroup(String managerGroupTag,
                                @NotNull IMeleeAttackManager meleeAttackManager,
                                @NotNull IMeleePhysicsManager meleePhysicsManager) {
}
```
