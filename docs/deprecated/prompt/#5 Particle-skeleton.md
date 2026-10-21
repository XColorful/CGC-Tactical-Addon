> #5 提示词

# 

```
对照TaCZ到CGC的粒子迁移，完成Lrtactical到CGC Tactical Addon的迁移
- 对于CGC里有封装的内容，则保持相同的版本区间写法
- 对于CGC已实现的内容/编排方式，优先用CGC的
- 完成服务端粒子、客户端粒子、粒子注册，部分类名会跟CGC保持一致，如ModParticles类名和包结构
- 完成后补充CGC Tactical Addon的./docs-lrtactical/Migration Mapping.md（参考TaCZ到CGC的./docs-tacz/TaCZ Migration Mapping.md）
```

# 

我先阅读参考文档和项目结构，理解 TaCZ→CGC 的粒子迁移模式。

粒子迁移完成，`./gradlew :forge-compat:jar` 通过（`BUILD SUCCESSFUL`），资源已确认打进 jar。

## 改动

**粒子注册（服务端）**
- [ModParticles.java](core/src/main/java/dev/xcolorful/cgctactical/core/init/registry/ModParticles.java) — 用 CGC 的 `CustomGun.getRegistrarFactory().createParticleTypes()` 注册 `cgctactical:smoke_cloud`（`SimpleParticleType(true)`，与 lrtactical 的 `overrideLimiter` 一致），类名/包结构与 CGC 对齐。
- [CgcTacticalForge.java:31](forge-compat/src/main/java/dev/xcolorful/cgctactical/forge/CgcTacticalForge.java:31) — `ModParticles.PARTICLE_TYPES.registerAll(modEventBus)`。

**客户端粒子**
- [SmokeCloudParticle.java](core/src/main/java/dev/xcolorful/cgctactical/client/particle/SmokeCloudParticle.java) — 保留原动画、尺寸、生命周期与邻块补光逻辑；打包光照改用 CGC 封装 `ClientRenderUtils.LightTexture_.pack`，删除了原类的空 `render`/`shouldCull` 覆写。
- [ClientModParticles.java](core/src/main/java/dev/xcolorful/cgctactical/client/init/ClientModParticles.java) — CGC 同名类/包结构，因本项目粒子用 `SpriteSet`，注册接口为 `SpriteParticleRegisterer`。
- [ForgeClientModParticles.java](forge-compat/src/main/java/dev/xcolorful/cgctactical/forgeclient/init/ForgeClientModParticles.java) — 对应 CGC 的 Forge 侧 `RegisterParticleProvidersEvent` 挂接。

**SSplashParticle**
- [S2CMessageSplashParticle.java](core/src/main/java/dev/xcolorful/cgctactical/core/network/message/world/S2CMessageSplashParticle.java) 补上 `blockPos` + `color` payload 与编解码。
- [_S2CMessageSplashParticle.java](core/src/main/java/dev/xcolorful/cgctactical/client/network/message/world/_S2CMessageSplashParticle.java) 实现客户端 100 个 `ParticleTypes.EFFECT` 的喷溅、染色与 `setPower`。

**资源**
- `assets/cgctactical/particles/smoke_cloud.json` 与 18 张 `textures/particle/smoke_*.png`，命名空间改为 `cgctactical`。

**文档**
- [Migration Mapping.md](docs-lrtactical/Migration%20Mapping.md) 新增 `Particle`、`Particle (Client)`、`Initialize (Client)` 三节（`ParticleUtil` 标为 `_Deprecated_`，按 CGC 直接调用 `ServerLevel.sendParticles`）。

## 需要你知道的

- 当前没有代码会发送 `S2CMessageSplashParticle`，也没有实体生成 `smoke_cloud` —— 烟雾弹实体尚未迁移，所以**未做游戏内渲染验证**；可用 `/particle cgctactical:smoke_cloud` 验证纹理与渲染。
- 新引入的平台 API：`LevelRenderer.getLightColor`（CGC 无封装），以及沿用原实现的已废弃 `Level#hasChunkAt`；请决定是否需要兼容封装。
- 未提交，等待你审阅。

# 

```
ModParticles需要补CustomParticleType
SmokeCloudParticle补PROVIDER
```

# 

我来补上 `CustomParticleType` 枚举与 `PROVIDER`，按 CGC 的包结构与写法。

已补完，`:forge-compat:compileJava` 通过。

## 改动

**CustomParticleType**
- [CustomParticleTypeTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/particle/CustomParticleTypeTag.java) — `SMOKE_CLOUD = "smoke_cloud"`。
- [CustomParticleType.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/particle/CustomParticleType.java) — 与 CGC 同包结构（`core.api.minecraft.particle`）、同实现（`ResourceTag.RegistryTag`、`typeName`/`registryName`/`registryLocation`、`fromString`），命名空间取 `CgcTactical.MOD_ID`。
- [ModParticles.java](core/src/main/java/dev/xcolorful/cgctactical/core/init/registry/ModParticles.java) — 注册名改用 `CustomParticleType.SMOKE_CLOUD.getTagName()`，与 CGC 的 `CustomParticleType.BULLET_HOLE.getTagName()` 写法一致。

**PROVIDER**
- [SmokeCloudParticle.java](core/src/main/java/dev/xcolorful/cgctactical/client/particle/SmokeCloudParticle.java) — 删掉 `provider(SpriteSet)` 与嵌套 Provider 类，改为 `// --------Client particles--------` 下的 `public static final ParticleEngine.SpriteParticleRegistration<SimpleParticleType> PROVIDER`，保留 SpriteSet 动画所需的小方块贴图。
- [ClientModParticles.java](core/src/main/java/dev/xcolorful/cgctactical/client/init/ClientModParticles.java) — 注册时直接引用 `SmokeCloudParticle.PROVIDER`。

## 需要你确认

`assets/cgctactical/particles/smoke_cloud.json` 现在是空 `textures` 数组，且 `textures/particle/` 下已无图片文件。这样加载时 `SpriteSet` 为空，粒子一旦生成会因取不到贴图抛异常。我按现状保留未动——如果是准备换成自己的贴图，补齐列表和文件即可。

# 
---

```
CGC Tactical AddonPR #5现在移植到1.21.1forge也正常，1.21.1neoforge会卡在游戏背景界面，可能有资源注册问题，对比CGC1.21.1的粒子注册看是否需要对称地补粒子注册API
```

# 

我先对比两个仓库的粒子注册实现。

两边的结构对称。我读一下粒子注册的具体实现。

我发现一个可疑点先核实：配置注册在 NeoForge 侧可能撞到 CGC 的 ModContainer。我先确认一下运行环境。

开发环境把 core 的未展开 jar 当成 mod 了，我先临时移开这个构建产物再跑。

已经复现了。真正的问题不在粒子，而在 NeoForge 侧的配置注册。我要确认修复 API 在 1.21.1 的 FML 版本里可用。

## 结论：不是粒子，是 NeoForge 侧的配置注册冲突

我用干净环境（NeoForge 1.21.1 + `cgctactical-0.0.0-neoforge-1.21.1.jar` + `customgun-26.10.28.2`）复现了启动失败，日志是：

```
Detected config file conflict on customgun-common.toml from customgun (already registered by customgun)
java.lang.RuntimeException ... at NeoModConfigSpecBuilder.buildAndRegister
```

addon 的构造函数里 `CgcTactical.init()` → `ModConfig.init()` 注册 COMMON/SERVER 配置时直接抛异常，后面的 `ModParticles.PARTICLE_TYPES.registerAll()` 和客户端 init 根本没执行。

### 根因（Forge 正常 / NeoForge 失败的原因）

- Forge：`ForgeModConfigSpecBuilder` 用 `ModLoadingContext.get().registerConfig(...)`，绑定的是**当前正在构造的 mod** → addon 写 `cgctactical-common.toml`。
- NeoForge：`NeoModConfigSpecBuilder` 写死 `CustomGunNeoforge.modContainer.registerConfig(...)` → addon 的配置落到 customgun 容器，文件名又是 `customgun-common.toml`，和 CGC 自己的配置撞名，`ConfigTracker` 直接抛冲突。

### 改动

[NeoModConfigSpecBuilder.java:53](app://localhost/Custom-Gun-Continued-1.21.1neoforge/neoforge-compat/src/main/java/dev/xcolorful/customgun/neoforge/config/NeoModConfigSpecBuilder.java:53)（CGC 仓库，1.21.1neoforge 分支）：

```java
ModLoadingContext.get().getActiveContainer().registerConfig(neoType, spec);
```

与 Forge 侧语义对齐，CGC 自身构造时 active container 就是 customgun，行为不变；addon 会正确落到自己的容器。这个缺陷影响**所有**用 `CustomGun.getModConfigSpecBuilder()` 的 addon，不只这个。

### 验证

- 用修好的 CGC 重新打 `jarJar`，同一套干净服务端启动到 `Done (3.518s)`，无配置冲突。
- 新 jar 已覆盖到 addon 仓库的 `libs/customgun-26.10.28.2-neoforge-1.21.1.jar`（gitignored 构建产物）。
- 客户端还需要你实际启动一次确认：服务端只验证了构造/注册链路；粒子 provider（`RegisterParticleProvidersEvent`）和 `smoke_cloud.json` 的 atlas 加载要在客户端看。

### 粒子注册对比：不需要补 API

addon 与 CGC 结构已对称，没有缺失：

||CGC|Addon|
|---|---|---|
|服务端注册|`ModParticles.PARTICLE_TYPES` + 构造函数 `registerAll(modEventBus)`|同左|
|客户端挂接|`ClientModParticles` + `@EventBusSubscriber`|同左|
|Provider 注册|`event::registerSpecial`（BulletHole 无 json）|`event::registerSpriteSet`（SmokeCloud 是 SpriteSet + particle json）|

`registerSpriteSet` 与 addon 的 `ParticleEngine.SpriteParticleRegistration` + `assets/cgctactical/particles/smoke_cloud.json` 是匹配的；`registerSpecial` 不能用于有 json 的粒子。**结论是不用改粒子代码，也不用给 CGC 补粒子注册 API。**

改动未提交，等你审阅。
