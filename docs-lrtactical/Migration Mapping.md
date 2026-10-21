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

### API
> ```java
> package me.xjqsh.lrtactical.api;
> ```

|me.xjqsh.lrtactical.api.collision|dev.xcolorful.cgctactical.core.resource|
|---|---|
|`ITargetFilter`|data.data.melee|
|`*`|data.data.melee|

### Capability
> ```java
> package me.xjqsh.lrtactical.capability;
> ```

| |dev.xcolorful.cgctactical.core.mixin|
|---|---|
|net.minecraftforge.common.capabilities.`AutoRegisterCapability`|entity.`PlayerMixin`|

### Config
> ```java
> package me.xjqsh.lrtactical.config;
> ```

|me.xjqsh.lrtactical.config|dev.xcolorful.cgctactical.client.config|
|---|---|
|`ClientConfig`|`RenderConfig`|

|me.xjqsh.lrtactical.config|dev.xcolorful.cgctactical.core.config|
|---|---|
|`CommonConfig`|`GrenadeConfig`|
|`CommonConfig`|`MeleeConfig`|
|`ServerConfig`|`SyncConfig`|

### Item
> ```java
> package me.xjqsh.lrtactical.item;
> ```

|me.xjqsh.lrtactical.item|dev.xcolorful.cgctactical.core.resource|
|---|---|
|consumable.`ConsumableData`|data.data.`ConsumableData`|
|index.`*Index`|data.index.`*Index`|
|melee.`MeleeWeaponData`|data.data.`MeleeData`|
|melee.`CombatData`.`MeleeAttackInfo`|data.data.melee.combat|
|melee.`CombatData`.`MeleeMovement`|data.data.melee.combat|
|melee.`*`|data.data.melee|
|throwable.`ThrowableData`|data.data.`ThrowableData`|
|throwable.`area.EffectCloudThrowableData`.`EffectData`|data.data.throwable.cloud|
|throwable.`flash.StunThrowableData`.`BlindData`|data.data.throwable.stun|
|throwable.`flash.StunThrowableData`.`DeafenedData`|data.data.throwable.stun|
|throwable.`*`|data.data.throwable|

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

### Particle
> ```java
> package me.xjqsh.lrtactical.init;
> ```

|me.xjqsh.lrtactical.init|dev.xcolorful.cgctactical.core.init.registry|
|---|---|
|`ModParticleTypes`|`ModParticles`|
|`ModParticleTypes`.`SMOKE_CLOUD`|`ModParticles`.`SMOKE_CLOUD`|

|me.xjqsh.lrtactical.util|dev.xcolorful.cgctactical.core.util|
|---|---|
|`ParticleUtil`|_Deprecated_|

### Resource
> ```java
> package me.xjqsh.lrtactical.resource;
> ```

|me.xjqsh.lrtactical.resource|dev.xcolorful.cgctactical.core.resource|
|---|---|
|manager.`ConsumableIndexManager`|data.`IndexManager`.`ConsumableIndexManager`|
|manager.`MeleeIndexManager`|data.`IndexManager`.`MeleeIndexManager`|
|manager.`ThrowableIndexManager`|data.`IndexManager`.`ThrowableIndexManager`|
|serializer.`ParticleOptionsDeserializer`|_Deprecated_|
|serializer.`Vector3fDeserializer`|_Deprecated_|
|`CommonAssetsManager`|`_AllDataManager`|
|`CommonNetworkCache`|client.resource.network.`SyncDataCache`|

|me.xjqsh.lrtactical.resource|dev.xcolorful.cgctactical.core.api.resource|
|---|---|
|`ICommonResourceProvider`|`ResourceApi`|

## Client

### Resource (Client)
> ```java
> package me.xjqsh.lrtactical.client.resource;
> ```

|me.xjqsh.lrtactical.client.resource|dev.xcolorful.cgctactical.client.resource|
|---|---|
|display.`*DisplayInstance`|instance.assets.`*DisplayInstance`|
|display.`ConsumableDisplayInstance`.`ConsumableDisplay`|assets.display.`ConsumableDisplay`|
|display.`MeleeDisplayInstance`.`MeleeDisplay`|assets.display.`MeleeDisplay`|
|display.`ThrowableDisplayInstance`.`ThrowableDisplay`|assets.display.`ThrowableDisplay`|
|manager.`*DisplayManager`|assets.`DisplayManager`.`*DisplayManager`|
|`LrClientAssetsManager`|`_AllAssetsManager`|

|me.xjqsh.lrtactical.client.resource|dev.xcolorful.cgctactical.client.api.resource|
|---|---|
|`LrClientAssetsManager`|`ClientResourceApi`|

### Particle (Client)
> ```java
> package me.xjqsh.lrtactical.client.particle;
> ```

|me.xjqsh.lrtactical.client.particle|dev.xcolorful.cgctactical.client.particle|
|---|---|
|`SmokeCloudParticle`|`SmokeCloudParticle`|

### Initialize (Client)
> ```java
> package me.xjqsh.lrtactical.client;
> ```

|me.xjqsh.lrtactical.client|dev.xcolorful.cgctactical.client.init|
|---|---|
|`ClientSetupHandler`.registerParticles|`ClientModParticles`.onRegisterParticleProviders|

|me.xjqsh.lrtactical.client|dev.xcolorful.cgctactical.forgeclient.init|
|---|---|
|`ClientSetupHandler`.registerParticles|`ForgeClientModParticles`.onRegisterParticleFactory|
