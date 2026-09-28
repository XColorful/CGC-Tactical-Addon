Reference mapping between [LesRaisins Tactical Equipements](https://github.com/LesRaisins-Studios/LesRaisins-Tactical-Equipements) 0.4.3 and the refactored [CGC Tactical Addon](https://github.com/XColorful/CGC-Tactical-Addon).

Use this document to:
- locate the corresponding implementation in the refactored project
- understand architectural changes introduced during the refactor
- assist source code navigation and migration

Use original LesRaisins Tactical Equipements package names or type names to find their corresponding implementation in Custom Gun Continued.

This document is intentionally maintained as a single file to simplify searching for both developers and AI agents.

Notation:
- `...` — Java type (`.java` file)
- plain text — package, namespace, field or method
- `*` — wildcard
- _Deprecated_ — already deprecated in LesRaisins Tactical Equipements or intentionally removed in the refactored implementation

## Common

### Network
> ```java
> package me.xjqsh.lrtactical.network;
> ```

|me.xjqsh.lrtactical.network|dev.xcolorful.cgctactical.core.network|
|---|---|
|message.`CCancelToggleConsumableUse`|message.combatant.`C2SMessageCombatantConsumable_cancel`|
|message.`CMeleeAttackRequest`|message.combatant.`C2SMessageCombatantMelee_attack`|
|message.`CPrepareMeleeAttack`|message.combatant.`C2SMessageCombatantMelee_prepare`|
|message.`SCustomCoolDownMessage`|message.combatant.`S2CMessageCombatantItemCooldown`|
|message.`SCustomSound`|message.resource.`S2CMessageResourceSound`|
|message.`SMeleeAnimationSync`|message.sync.`S2CMessageSyncMeleeAnimation`|
|message.`SPackSyncMessage`|message.resource.`S2CMessageSyncDataPack`|
|message.`SResetMeleeSyncMessage`|message.combatant.`S2CMessageCombatantMelee_reset`|
|message.`SShakeScreenMessage`|message.world.`S2CMessageNearbyExplosion`|
|message.`SShieldDisable`|message.combatant.`S2CMessageCombatantShield_disable`|
|message.`SShieldShake`|message.combatant.`S2CMessageCombatantShield_block`|
|message.`SSplashParticle`|message.world.`S2CMessageSplashParticle`|
|`NetworkHandler`|`NetworkHandler`|

|me.xjqsh.lrtactical.network|dev.xcolorful.cgctactical.core.resource|
|---|---|
|`DataType`|network.`SyncDataType`|
