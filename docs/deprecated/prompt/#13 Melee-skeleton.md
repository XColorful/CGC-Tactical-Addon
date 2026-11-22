> #13 提示词

# 

```
查Cone、Ray、Obb标准的检测定义，完成dev.xcolorful.cgctactical.core.api.melee.physics.volume包下的类
对于标准检测部分，效仿dev.xcolorful.customgun.core.projectile.physics.ProjectilePhysicsManager调用dev.xcolorful.customgun.core.util.RayTractUtils，提取到dev.xcolorful.cgctactical.core.util.HitVolumeUtils
collectTargets的语义见me.xjqsh.lrtactical.api.collision.ITargetFilter
```

# 

I'll start by exploring the relevant packages and reference implementations.

Now let me read the lrtactical standard detectors, which are the spec.

Let me check what changed in the volume classes since the skeleton commit, and confirm `ClipContext` API availability.

I have the full picture: the lrtactical `ConeFilter`/`RayFilter`/`OBBFilter` define the reference semantics, and `ProjectilePhysicsManager` shows the `RayTraceUtils` usage pattern. Now implementing `HitVolumeUtils` with the standard detection primitives.

Now compiling the addon's core module with JDK 17.

The source was edited concurrently in the IDE (AbstractVolume now keeps its own `TARGET_ENTITY_FILTER`). I'll leave that as-is. The remaining build noise is a GBK-vs-UTF-8 environment issue, so I'll compile with UTF-8 forced.

Build succeeded. The files are being edited in the IDE concurrently, so let me verify the current state.

The implementation compiles. One semantic gap remains versus `ITargetFilter`: lrtactical's `RayFilter` also counts an entity when the ray *starts inside* its hitbox (vanilla `AABB.clip` returns nothing then). I'll add that fallback in the shared utility.

All three volumes and the shared utility compile. Marking the work complete.

已完成。核心实现如下：

**`HitVolumeUtils`（标准检测）** — [HitVolumeUtils.java](core/src/main/java/dev/xcolorful/cgctactical/core/util/HitVolumeUtils.java)
- `rayTraceEntities`：仿照 `ProjectilePhysicsManager` 调用 `RayTraceUtils.EntityTrace.rayTraceEntities`，并补上 `ITargetFilter` 的“起点已在包围盒内也算命中”分支，结果按距离升序。
- `clip`：仿照其 `RayTraceUtils.BlockTrace.rayTraceBlocksWithFilter` 调用完成方块遮挡检测（沿用 64 格上限）。
- `hasLineOfSight`：眼部直连 + 8 个包围盒顶点，语义同 `ITargetFilter.hasLineOfSight`。
- `distanceVector` / `angleBetween` / `isInAngle`：对应 `VectorUtil`。
- `obbIntersectsAABB`：对应 `OBB.isColliding`，使用 JOML `Intersectionf.testObOb`。

**三个判定体积**
- [ConeVolume.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/volume/ConeVolume.java)：扫描盒 `inflate(range*2, range, range*2)`，`maxAngle/2` 半锥角 + 视线，`excludeSelf`。
- [RayVolume.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/volume/RayVolume.java)：`origin + dir*range` 射线、排除自身/载具/旁观者、视线过滤、按距离取前 `penetration + 1`。
- [ObbVolume.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/volume/ObbVolume.java)：以施击者 yaw/pitch/roll 构造旋转，中心 `origin + dir*range/2`，半长 `(halfWidth, halfHeight, range/2)`。
- [AbstractVolume.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/volume/AbstractVolume.java) 保留为共享的候选收集流程（你在 IDE 中把它留下的 `TARGET_ENTITY_FILTER` 保持不变，未覆盖）。

**额外修复**：`_TargetFilterData` 误导入 `javax.swing.text.html.parser.Entity`，导致 `collectTargets` 返回类型不匹配，已改为 `net.minecraft.world.entity.Entity`——不修则无法编译。

验证情况：`:core:compileJava` 通过。因项目未配置源码编码，命令行需 `JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8` 才能编译（IDE 默认 UTF-8 不受影响）。未进行游戏内实测，也未提交，按项目规则留给你审查。

# 

```
ray能用CGC的RayTraceUtils的则优先使用，不能用再在HitVolumeUtils里重写一遍
MAX_CLIP_DISTANCE移到AbstractVolume里，Utils里默认都要传这个参数
补IHitVolume#collectTargets的javadoc说明
```

# 

I'll apply the three changes: use `RayTraceUtils`' own overloads for the ray, move `MAX_CLIP_DISTANCE` to `AbstractVolume` and thread it through the utils, and document `collectTargets`.

三处调整已完成，`:core:compileJava` 通过。

1. **Ray 优先复用 CGC 的 `RayTraceUtils`** — `HitVolumeUtils.rayTraceEntities` 现在直接调用 `RayTraceUtils.EntityTrace.rayTraceEntities(contextEntity, owner, start, end, inflateRadius, ...)`（与 `ProjectilePhysicsManager` 相同的重载），扫描盒也用 `RayTraceUtils.EntityTrace.createTraceBox`。`HitVolumeUtils` 里只保留 `RayTraceUtils` 确实做不到的部分：起点已在目标包围盒内时补命中、视线过滤和按距离排序。

2. **`MAX_CLIP_DISTANCE` 归位** — 已移到 [AbstractVolume.java:22](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/volume/AbstractVolume.java:22)，`HitVolumeUtils` 不再持有该常量；`hasLineOfSight` / `clip` 都改为显式接收 `maxClipDistance`，并删除了不带该参数的旧 `clip` 重载。`AbstractVolume.collectInArea` 与 `RayVolume` 的视线过滤都传入 `AbstractVolume.MAX_CLIP_DISTANCE`。

3. **`IHitVolume#collectTargets` 文档** — 已在 [IHitVolume.java:14](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/IHitVolume.java:14) 补充说明：语义参照 `ITargetFilter`、统一应用 `exclude_self` / marker 盔甲架 / 视线判定，射线额外按距离应用 `penetration` 上限，并列出各参数含义。

另外修正了 `AbstractVolume` 中并发编辑产生的重复 `MAX_CLIP_DISTANCE` 声明。改动未提交，留待你审查。

# 

```
效仿CGC文档，补一下git工作区里HitVolumeUtils的描述
```

# 

我先看一下 CGC 文档里工具类的描述风格，再补 `HitVolumeUtils`。

已按 CGC 文档的条目风格补齐 [Home.md](docs/architecture/core/Home.md):

- 中文：`HitVolumeUtils：近战判定体积标准检测工具`，子项注明统一实现 cone/ray/obb 的候选收集、视线遮挡与射线命中检测，并复用 `RayTraceUtils`。
- English：对应的 `HitVolumeUtils: Standard detection utilities for melee hit volumes` 及子项说明。

仅文档改动，未提交。

# 
---

```
效仿dev.xcolorful.cgctactical.client.input.combatant.PrimaryActionKey，完成SecondaryActionKey
SecondaryActionKey不需要持续按压检测
其余部分应该完全同构
```

# 

[SecondaryActionKey.java](core/src/main/java/dev/xcolorful/cgctactical/client/input/combatant/SecondaryActionKey.java) 已完成，结构与 `PrimaryActionKey` 一致：

- 实现 `IEventHandler`，注册 `PREPARE_CLIENT_TICK_EVENT` / `CLIENT_TICK_EVENT` 并做事件派发。
- `onSecondaryActionKeyInput` 与主键相同：仅在游戏焦点内、`PRESS` 时写入 `inputQueue`。
- 蓄力/动画部分同构，仅把 `TacticalAction.PRIMARY_ACTION` 换成 `SECONDARY_ACTION`（含 `typeNameOld`），同样在蓄力成功后调用 `ensureHasSentCarriedItem` 并摆臂。
- 按你的要求去掉了持续按压检测：`actionPressed` 只取本 tick 的 `hasPressedKey`，不读 `keyMapping.isDown()`，也不检查 `KeyConfig.CONTINUOUS_ACTION_INPUT`。

`:core:compileJava` 编译通过；改动未提交。
