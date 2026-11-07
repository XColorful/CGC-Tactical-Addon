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

|me.xjqsh.lrtactical.api|dev.xcolorful.cgctactical.core.api.resource|
|---|---|
|`LrTacticalAPI`|`ResourceApi`|

|me.xjqsh.lrtactical.api|dev.xcolorful.cgctactical.client.api.resource|
|---|---|
|`LrTacticalAPI`|`ClientResourceApi`|

#### Animation API
> ```java
> package me.xjqsh.lrtactical.api.animation;
> ```

|me.xjqsh.lrtactical.api.animation|dev.xcolorful.cgctactical.client.api.script|
|---|---|
|`BaseAnimationStateContext`|context.`IClientMeleeScriptBackCompat`|

|me.xjqsh.lrtactical.api.animation|dev.xcolorful.cgctactical.client.animation|
|---|---|
|`ConsumableAnimationStateContext`|statemachine.`ConsumableAnimStateContext`|
|`FlashShieldAnimationStateContext`||
|`MeleeAnimationStateContext`|statemachine.`MeleeAnimStateContext`|
|`ThrowableAnimationStateContext`|statemachine.`ThrowableAnimStateContext`|

#### Collision API
> ```java
> package me.xjqsh.lrtactical.api.collision;
> ```

|me.xjqsh.lrtactical.api.collision|dev.xcolorful.cgctactical.core.resource|
|---|---|
|`ITargetFilter`|data.data.melee|
|`*`|data.data.melee|

#### Event API
> ```java
> package me.xjqsh.lrtactical.api.event;
> ```

|me.xjqsh.lrtactical.api.event|dev.xcolorful.cgctactical.core.api.event|
|---|---|
|`ConsumableUseEvent`||
|`MeleePreAttackEvent`|combatant.`MeleeAttackEvent`|

#### Index API
> ```java
> package me.xjqsh.lrtactical.api.index;
> ```

|me.xjqsh.lrtactical.api.index|dev.xcolorful.cgctactical.core.resource|
|---|---|
|`ICustomItemIndex`|data.data.`*`|

#### Item API
> ```java
> package me.xjqsh.lrtactical.api.item;
> ```

|me.xjqsh.lrtactical.api.item|dev.xcolorful.cgctactical.core.api.item|
|---|---|
|`IConsumable`|`IConsumable`|
|`ICustomItem`|`ITacticalItem`|
|`IMeleeWeapon`|`IMelee`|
|`IThrowable`|`IThrowable`|

#### Melee API
> ```java
> package me.xjqsh.lrtactical.api.melee;
> ```

|me.xjqsh.lrtactical.api.melee|dev.xcolorful.cgctactical.core.api.melee|
|---|---|
|`AttackResult`|attack.`IMeleeAttackRuntime`.`MeleeAttackResult`|

|me.xjqsh.lrtactical.api.melee|dev.xcolorful.cgctactical.core.api.item|
|---|---|
|`MeleeAction`|tactical.`TacticalAction`|

### Capability
> ```java
> package me.xjqsh.lrtactical.capability;
> ```

|me.xjqsh.lrtactical.capability|dev.xcolorful.cgctactical.core.api.entity|
|---|---|
|`CombatProperties`|`CombatantProperty`|
|`CombatProperties`.preAttack|combatant.melee.`IMeleeOperator`.cgctactical$meleeCharge|
|`CombatProperties`.postAttack|combatant.melee.`IMeleeOperator`.cgctactical$meleeAttack|
|`CombatPropertiesProvider`|_Deprecated_|
|`CustomItemCoolDownsProvider`|_Deprecated_|

|me.xjqsh.lrtactical.capability|dev.xcolorful.cgctactical.core.entity|
|---|---|
|`CombatProperties`.tick|combatant.`LivingCombatantSpring`.tickSprint|
|`CombatProperties`.tick|combatant.`LivingCombatantDraw`.tickDraw|
|`CustomItemCoolDowns`|combatant.`LivingCombatantCooldown`|

|me.xjqsh.lrtactical.capability|dev.xcolorful.cgctactical.core.mixin|
|---|---|
|`TickHandler`.onPlayerTick|entity.`LivingEntityMixin`.cgctactical$onLivingTick|

| |dev.xcolorful.cgctactical.core.mixin|
|---|---|
|net.minecraftforge.common.capabilities.`AutoRegisterCapability`|entity.`LivingEntityMixin`|

### Compat
> ```java
> package me.xjqsh.lrtactical.compat;
> ```

|me.xjqsh.lrtactical.compat|dev.xcolorful.cgctactical.client.compat|
|---|---|
|cloth||
|jei||
|player_animator||

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

### Effect
> ```java
> package me.xjqsh.lrtactical.effect;
> ```

|me.xjqsh.lrtactical.effect||
|---|---|
|`BurnedEffect`|_Deprecated_|
|`HarmfulEffect`||

### Enchantment
> ```java
> package me.xjqsh.lrtactical.enchantment;
> ```

|me.xjqsh.lrtactical.enchantment||
|---|---|
|`BackstabEnchantment`|_Deprecated_|

### Entity
> ```java
> package me.xjqsh.lrtactical.entity;
> ```

|me.xjqsh.lrtactical.entity|dev.xcolorful.cgctactical.core.entity|
|---|---|
|sp.`SpEffectCloudEntity`||
|`EffectCloudGrenadeEntity`||
|`GrenadeEntity`||
|`SmokeGrenadeEntity`||
|`StickyGrenadeEntity`||
|`StunGrenadeEntity`||
|`ThrowableItemEntity`||

### Handler
> ```java
> package me.xjqsh.lrtactical.handler;
> ```

|me.xjqsh.lrtactical.handler||
|---|---|
|`CriticalHitEventHandler`|_Deprecated_|
|`EntityHurtEventHandler`|_Deprecated_|
|`ShieldBlockEventHandler`||

### Initialize
> ```java
> package me.xjqsh.lrtactical.init;
> ```

|me.xjqsh.lrtactical.init|dev.xcolorful.cgctactical.core.mixin|
|---|---|
|`CommonSetupHandler`|entity.`LivingEntityMixin`|

|me.xjqsh.lrtactical.init|dev.xcolorful.cgctactical.core.init|
|---|---|
|`CompatRegistry`|_Deprecated_|
|`ModCustomTypes`||
|`ModEffects`||
|`ModEnchantment`||
|`ModEntities`||
|`ModItems`.TABS|registry.`ModCreativeTabs`.TABS|
|`ModItems`|registry.`ModItems`|
|`ModParticleTypes`|registry.`ModParticles`|
|`ModParticleTypes`.`SMOKE_CLOUD`|registry.`ModParticles`.`SMOKE_CLOUD`|
|`ModRegistries`||
|`ModSounds`||

|me.xjqsh.lrtactical.init|dev.xcolorful.cgctactical.core.api.minecraft|
|---|---|
|`ModItems`.\*|tab.`*Tab`.\*|

### Inventory
> ```java
> package me.xjqsh.lrtactical.inventory;
> ```

|me.xjqsh.lrtactical.inventory|dev.xcolorful.cgctactical.core.gui|
|---|---|
|tooltip.`ConsumableTooltip`|tooltip.consumable.`ConsumableTooltip`|
|tooltip.`MeleeTooltip`|tooltip.melee.`MeleeTooltip`|
|tooltip.`ThrowableTooltip`|tooltip.throwable.`ThrowableTooltip`|

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

|me.xjqsh.lrtactical.item|dev.xcolorful.cgctactical.core.item|
|---|---|
|`ConsumableItem`|consumable.`ConsumableItem`|
|`DetonatorItem`||
|`FlashShieldItem`||
|`MeleeItem`|melee.`MeleeItem`|
|`ThrowableItem`|throwable.`ThrowableItem`|

### Mixin
> ```java
> package me.xjqsh.lrtactical.mixin;
> ```

|me.xjqsh.lrtactical.mixin|dev.xcolorful.cgctactical.core.mixin|
|---|---|
|client.`GameRendererMixin`||
|client.`GunSmithTableScreenMixin`|_Deprecated_|
|client.`MinecraftMixin`||
|client.`PlayerAnimatorAssetManagerMixin`|_Deprecated_|
|client.`PlayerModelMixin`|_Deprecated_|
|common.`AnvilMenuMixin`||
|common.`LivingEntityMixin`||
|common.`PlayerMixin`||

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

### Util

|me.xjqsh.lrtactical.util|dev.xcolorful.cgctactical.core.util|
|---|---|
|`CustomExplosion`||
|`DefaultAttrUUIDUtil`||
|`IPAAssetManager`||
|`ParticleUtil`|_Deprecated_|
|`PotionTooltipUtil`|_Deprecated_|
|`SightTraceUtil`|_Deprecated_|
|`TooltipLine`|_Deprecated_|
|`TooltipUtil`|_Deprecated_|
|`VectorUtil`||

## Client

|me.xjqsh.lrtactical.client||
|---|---|
|`ClientEventsHandler`.computeCameraAngles||
|`ClientEventsHandler`.onRenderHand|_Deprecated_|
|`ClientEventsHandler`.tickAnimation||
|`ClientEventsHandler`.tickMove||
|`ClientEventsHandler`.afterLevel||
|`ClientEventsHandler`.onClickInput|_Deprecated_|
|`ClientEventsHandler`.renderAttackHitBox|_Deprecated_|
|`ClientEventsHandler`.renderOBB||

|me.xjqsh.lrtactical.client|dev.xcolorful.cgctactical.client.init|
|---|---|
|`ClientSetupHandler`.registerParticles|`ClientModParticles`.onRegisterParticleProviders|
|`ClientSetupHandler`.onRegisterTooltipComponentFactories|`ClientTooltipRegistry`.registerTooltips|Providers|
|`ClientSetupHandler`.onClientSetup(RegisterKeyMappingsEvent)|`ClientKeyMappingRegistry`.registerKeyMappings|

### Audio (Client)
> ```java
> package me.xjqsh.lrtactical.client.audio;
> ```

|me.xjqsh.lrtactical.client.audio||
|---|---|
|`ICustomSoundSupplier`||
|`SmokeReleaseSound`|_Deprecated_|
|`SoundHandler`||
|`StunRingingSound`||

### GUI (Client)
> ```java
> package me.xjqsh.lrtactical.client.gui;
> ```

|me.xjqsh.lrtactical.client.gui||
|---|---|
|overlay.`InteractKeyTextOverlay`|_Deprecated_|
|`ClothConfigScreen`||

### Input (Client)
> ```java
> package me.xjqsh.lrtactical.client.input;
> ```

|me.xjqsh.lrtactical.client.input|dev.xcolorful.cgctactical.client.input|
|---|---|
|`AttackKeys`.NORMAL_ATTACK|combatant.`PrimaryActionKey`|
|`AttackKeys`.SPECIAL_ATTACK|combatant.`SecondaryActionKey`|
|`ConsumableInputHandler`||
|`LrInteractKey`|_Deprecated_|

### Overlay (Client)
> ```java
> package me.xjqsh.lrtactical.client.overlay;
> ```

|me.xjqsh.lrtactical.client.overlay|dev.xcolorful.cgctactical.client.overlay|
|---|---|
|`UsingProgressOverlay`|_Deprecated_|

### Particle (Client)
> ```java
> package me.xjqsh.lrtactical.client.particle;
> ```

|me.xjqsh.lrtactical.client.particle|dev.xcolorful.cgctactical.client.particle|
|---|---|
|`SmokeCloudParticle`|`SmokeCloudParticle`|

### Renderer (Client)
> ```java
> package me.xjqsh.lrtactical.client.renderer;
> ```

|me.xjqsh.lrtactical.client.renderer||
|---|---|
|entity.`ThrowableEntityRenderer`||
|item.`ConsumableItemRenderer`||
|item.`FlashShieldItemRenderer`||
|item.`MeleeItemRenderer`||
|item.`ThrowableItemRendererWrapper`||
|model.`CustomBedrockModel`||
|`CoolDownDecorations`|_Deprecated_|
|`JumpSwayUtil`||

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

### Tooltip (Client)
> ```java
> package me.xjqsh.lrtactical.client.tooltip;
> ```

|me.xjqsh.lrtactical.client.tooltip|dev.xcolorful.cgctactical.client.gui.tooltip|
|---|---|
|`Client*Tooltip`|*.`Client*Tooltip`|
|`AbstractClientItemTooltip`|*.`Client*Tooltip`.Context|

|me.xjqsh.lrtactical.client.tooltip|dev.xcolorful.customgun.client.api.gui|
|---|---|
|`Client*Tooltip`.getHeight|tooltip.`BaseTooltipContext`.calculateHeight|
|`Client*Tooltip`.getWidth|tooltip.`BaseTooltipContext`.getMaxWidth|
|`AbstractClientItemTooltip`.build|tooltip.`BaseTooltipContext`.buildView|
|`Client*Tooltip`.renderText|tooltip.`TooltipPart`.renderText|
|`Client*Tooltip`.renderImage|tooltip.`TooltipPart`.renderImage|
