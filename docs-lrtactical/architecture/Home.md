[English](#English)

# 架构总览

> 本文档作为项目架构的导航索引

## 项目结构

基于`me.xjqsh.lrtactical`顶层包的模块划分

### API
> _./api_

- [API索引](./api/api-index.md)：通过接口分类和职责介绍进行筛选

### 能力
> _./capability_

挂在玩家上的战斗状态数据：
- CombatProperties：战斗状态数据（冷却、预备攻击、动作计数）
- CombatPropertiesProvider：战斗状态能力提供者
- CustomItemCoolDowns：自定义物品冷却表
- CustomItemCoolDownsProvider：自定义冷却能力提供者
- TickHandler：玩家 Tick 处理器

### 客户端
> _./client_

- ClientEventsHandler：客户端事件处理器（相机震动、手部渲染、动画 Tick、致盲闪光）
- ClientSetupHandler：客户端初始化（注册渲染器、粒子、按键、HUD）

#### 音频
> _./client/audio_

- ICustomSoundSupplier：自定义音效供应接口
- SoundHandler：客户端声音管理器（致聋时的降音与耳鸣）
- StunRingingSound：眩晕耳鸣循环音效
- SmokeReleaseSound：烟雾释放音效

#### GUI
> _./client/gui_

- ClothConfigScreen：Cloth Config 缺失提示界面
- overlay：叠加层
	- InteractKeyTextOverlay：交互键提示叠加层

#### 按键
> _./client/input_

- AttackKeys：近战攻击按键映射
- ConsumableInputHandler：消耗品取消使用处理
- LrInteractKey：交互键触发处理

#### 叠加层
> _./client/overlay_

- UsingProgressOverlay：使用进度叠加层（使用进度、投掷火候、冷却）

#### 粒子
> _./client/particle_

- SmokeCloudParticle：烟雾云粒子

#### 渲染器
> _./client/renderer_

- CoolDownDecorations：物品冷却装饰
- JumpSwayUtil：跳跃模型晃动
- entity：投掷物实体渲染器
- item：物品渲染器（消耗品、闪光盾、近战武器、投掷物）
- model：自定义基岩版模型

#### 资源
> _./client/resource_

- LrClientAssetsManager：客户端资源管理器
- display：展示数据实例（消耗品、近战、投掷物）
- manager：展示数据管理器（消耗品、近战、投掷物）

#### 提示框
> _./client/tooltip_

- AbstractClientItemTooltip：客户端提示框基类
- ClientConsumableTooltip：消耗品提示框
- ClientMeleeTooltip：近战武器提示框
- ClientThrowableTooltip：投掷物提示框

### 模组联动
> _./compat_

- cloth：Cloth Config 兼容
- jei：JEI 兼容
- player_animator：Player Animator 兼容

### 配置
> _./config_

- ClientConfig：客户端配置（闪光遮罩、震屏倍率）
- CommonConfig：通用配置（手雷破坏方块、近战耐久）
- ServerConfig：服务端配置（闪光盾耐久与冷却、投掷初速）

### 状态效果
> _./effect_

- BurnedEffect：可燃效果
- HarmfulEffect：通用负面效果

### 附魔
> _./enchantment_

- BackstabEnchantment：背刺附魔

### 实体
> _./entity_

- ThrowableItemEntity：投掷物实体基类（弹跳物理与生命周期）
- GrenadeEntity：爆炸手雷实体
- StickyGrenadeEntity：粘性手雷实体
- SmokeGrenadeEntity：烟雾手雷实体
- StunGrenadeEntity：闪光震撼弹实体
- EffectCloudGrenadeEntity：效果投掷物实体
- sp：范围效果云实体

### 事件处理
> _./handler_

- CriticalHitEventHandler：背刺暴击处理
- EntityHurtEventHandler：可燃增伤处理
- ShieldBlockEventHandler：闪光盾格挡处理

### 初始化
> _./init_

- ModItems：物品与创造模式标签页
- ModEntities：模组实体
- ModEffects：模组状态效果
- ModEnchantment：模组附魔
- ModParticleTypes：模组粒子
- ModSounds：模组声音
- ModRegistries：自定义注册表
- ModCustomTypes：投掷物与近战类型注册
- CommonSetupHandler：玩家能力附加
- CompatRegistry：前置模组兼容注册

### 背包
> _./inventory_

- tooltip：提示框数据（消耗品、近战、投掷物）

### 物品
> _./item_

- ConsumableItem：消耗品物品
- DetonatorItem：远程引爆器物品
- FlashShieldItem：闪光盾物品
- MeleeItem：近战武器物品
- ThrowableItem：投掷物物品
- consumable：消耗品属性配置
- index：物品索引
- melee：近战武器数据
- throwable：投掷物数据
	- area：区域云投掷物
	- explode：爆炸投掷物
	- flash：闪光震撼投掷物
	- smoke：烟雾投掷物

### Mixin
> _./mixin_

- common：双端 Mixin
- client：客户端 Mixin

### 网络
> _./network_

- NetworkHandler：网络处理器（注册网络消息、发送消息）
- DataType：资源同步数据类别
- message：网络消息
	- 客户端 → 服务端消息
	- 服务端 → 客户端消息

### 资源
> _./resource_

- CommonAssetsManager：服务端资源管理器（索引加载与数据包同步）
- CommonNetworkCache：客户端索引缓存
- ICommonResourceProvider：资源提供接口
- manager：索引管理器
- serializer：数据反序列化器

### 工具
> _./util_

- CustomExplosion：自定义爆炸
- DefaultAttrUUIDUtil：属性修饰符 UUID 缓存
- IPAAssetManager：Player Animator 动画资源读取接口
- ParticleUtil：粒子工具
- PotionTooltipUtil：药水效果提示框工具
- SightTraceUtil：视线遮挡射线追踪
- TooltipLine：提示框数值行
- TooltipUtil：提示框格式化工具
- VectorUtil：向量工具

# English

> This document serves as a navigation index for the project architecture

## Project Structure

Module division based on the `me.xjqsh.lrtactical` top-level package

### API
> _./api_

- [API Index](./api/api-index.md#English): Filter by interface classification and responsibility introduction

### Capability
> _./capability_

Combat state data attached to the player:
- CombatProperties: Combat state data (cooldown, prepared attack, action counts)
- CombatPropertiesProvider: Combat state capability provider
- CustomItemCoolDowns: Custom item cooldown map
- CustomItemCoolDownsProvider: Custom cooldown capability provider
- TickHandler: Player tick handler

### Client
> _./client_

- ClientEventsHandler: Client event handler (camera shake, hand rendering, animation tick, blind flash)
- ClientSetupHandler: Client initialization (register renderers, particles, keys, HUD)

#### Audio
> _./client/audio_

- ICustomSoundSupplier: Custom sound supplier interface
- SoundHandler: Client sound manager (volume reduction and ringing under deafness)
- StunRingingSound: Stun ringing looping sound
- SmokeReleaseSound: Smoke release sound

#### GUI
> _./client/gui_

- ClothConfigScreen: Cloth Config missing notice screen
- overlay: Overlays
	- InteractKeyTextOverlay: Interact key hint overlay

#### Input
> _./client/input_

- AttackKeys: Melee attack key mappings
- ConsumableInputHandler: Consumable use cancellation handling
- LrInteractKey: Interact key trigger handling

#### Overlay
> _./client/overlay_

- UsingProgressOverlay: Using progress overlay (use progress, throw cook, cooldown)

#### Particle
> _./client/particle_

- SmokeCloudParticle: Smoke cloud particle

#### Renderer
> _./client/renderer_

- CoolDownDecorations: Item cooldown decorations
- JumpSwayUtil: Jump model sway
- entity: Throwable entity renderer
- item: Item renderers (consumable, flash shield, melee weapon, throwable)
- model: Custom bedrock model

#### Resource
> _./client/resource_

- LrClientAssetsManager: Client assets manager
- display: Display data instances (consumable, melee, throwable)
- manager: Display data managers (consumable, melee, throwable)

#### Tooltip
> _./client/tooltip_

- AbstractClientItemTooltip: Client tooltip base class
- ClientConsumableTooltip: Consumable tooltip
- ClientMeleeTooltip: Melee weapon tooltip
- ClientThrowableTooltip: Throwable tooltip

### Mod Compat
> _./compat_

- cloth: Cloth Config compat
- jei: JEI compat
- player_animator: Player Animator compat

### Config
> _./config_

- ClientConfig: Client config (flash overlay, screen shake multiplier)
- CommonConfig: Common config (grenade block damage, melee durability)
- ServerConfig: Server config (flash shield durability and cooldown, throw initial speed)

### Effect
> _./effect_

- BurnedEffect: Flammable effect
- HarmfulEffect: Generic harmful effect

### Enchantment
> _./enchantment_

- BackstabEnchantment: Backstab enchantment

### Entity
> _./entity_

- ThrowableItemEntity: Throwable entity base class (bounce physics and lifetime)
- GrenadeEntity: Explosive grenade entity
- StickyGrenadeEntity: Sticky grenade entity
- SmokeGrenadeEntity: Smoke grenade entity
- StunGrenadeEntity: Flashbang entity
- EffectCloudGrenadeEntity: Effect throwable entity
- sp: Area effect cloud entity

### Handler
> _./handler_

- CriticalHitEventHandler: Backstab critical hit handling
- EntityHurtEventHandler: Flammable damage bonus handling
- ShieldBlockEventHandler: Flash shield blocking handling

### Initialization
> _./init_

- ModItems: Mod items and creative mode tabs
- ModEntities: Mod entities
- ModEffects: Mod mob effects
- ModEnchantment: Mod enchantments
- ModParticleTypes: Mod particles
- ModSounds: Mod sounds
- ModRegistries: Custom registries
- ModCustomTypes: Throwable and melee type registration
- CommonSetupHandler: Player capability attachment
- CompatRegistry: Prerequisite mod compat registration

### Inventory
> _./inventory_

- tooltip: Tooltip data (consumable, melee, throwable)

### Item
> _./item_

- ConsumableItem: Consumable item
- DetonatorItem: Remote detonator item
- FlashShieldItem: Flash shield item
- MeleeItem: Melee weapon item
- ThrowableItem: Throwable item
- consumable: Consumable property config
- index: Item indices
- melee: Melee weapon data
- throwable: Throwable data
	- area: Area cloud throwable
	- explode: Explosive throwable
	- flash: Flashbang throwable
	- smoke: Smoke throwable

### Mixin
> _./mixin_

- common: Dual-side mixins
- client: Client mixins

### Network
> _./network_

- NetworkHandler: Network handler (register messages, send messages)
- DataType: Resource sync data category
- message: Network messages
	- Client → Server messages
	- Server → Client messages

### Resource
> _./resource_

- CommonAssetsManager: Server asset manager (index loading and datapack sync)
- CommonNetworkCache: Client index cache
- ICommonResourceProvider: Resource provider interface
- manager: Index managers
- serializer: Data deserializers

### Utility
> _./util_

- CustomExplosion: Custom explosion
- DefaultAttrUUIDUtil: Attribute modifier UUID cache
- IPAAssetManager: Player Animator animation asset reading interface
- ParticleUtil: Particle utilities
- PotionTooltipUtil: Potion effect tooltip utilities
- SightTraceUtil: Line-of-sight occlusion ray trace
- TooltipLine: Tooltip value line
- TooltipUtil: Tooltip formatting utilities
- VectorUtil: Vector utilities
