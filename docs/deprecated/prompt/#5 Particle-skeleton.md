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
