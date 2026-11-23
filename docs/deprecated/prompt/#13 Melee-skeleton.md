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

# 
---

```
读git工作区，可以从CGC Tactical Addon和CGC已有的ResourcePojo写法寻找参照，完成_AttributeData、_AttributeInfo、MeleeItem#getAttributeModifiers、MeleeIndexInstance
其中xxxTag已经固定，MeleeIndexInstance使用BuiltInRegistries.ATTRIBUTE而不要引入ForgeRegistries
还需要检查以上两个ResourcePojo是否跟me.xjqsh.lrtactical.item.melee.AttributeData反序列化的一致 (这个Deserializer写法对人类不友好)
工作区wiki也要补上
```

# 

我先读取工作区现状和相关文件，理解现有约定。

现在实现。先写 `_AttributeInfo`。

已完成，`core` 与 `forge-compat` 均编译通过（`:core:compileJava`、`:forge-compat:compileJava`）。

**实现内容**

- [_AttributeInfo.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/melee/attribute/_AttributeInfo.java)：读 `attribute_location`/`id`、`amount`、`modifier_operation`/`operation`；值为裸数字时按 `amount` + `SHARED_BASE_ADD` 解析；带 `toJson`、`validatePojo`（缺 location 或未知 operation 判为无效）、深拷贝与 back-compat（operation 缺省补 `SHARED_BASE_ADD`）。
- [_AttributeData.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/melee/_AttributeData.java)：`attribute_info`/`attributes` 接受列表或 id 为键的映射；同时把 `attribute_data` 对象下的非包装键当作 lrtactical 旧式属性映射解析（数值或 `{amount, operation}`），无效 id 与非数值/对象值均跳过。
- [MeleeIndexInstance.java:53](core/src/main/java/dev/xcolorful/cgctactical/core/resource/instance/data/MeleeIndexInstance.java:53)：用 `BuiltInRegistries.ATTRIBUTE` 构建 `ImmutableMultimap`；未注册属性记 debug 并跳过；UUID 按 `ResourceLocation#toString` 经 `nameUUIDFromBytes` 生成并缓存，与 lrtactical 的 `DefaultAttrUUIDUtil`“每属性一个 UUID”语义一致（但为确定性 UUID）。
- [MeleeItem.java:45](core/src/main/java/dev/xcolorful/cgctactical/core/item/melee/MeleeItem.java:45)：仅主手返回实例缓存的修饰符，实例/缓存缺失时返回空 map。
- [Melee data.md](docs/wiki/resource/data/cgc_data/Melee%20data.md)：中英文都补了 `attribute_data → attribute_info → _AttributeInfo` 结构与 JSON 示例，并注明 lrtactical 的映射写法兼容。

**与 lrtactical `AttributeData.Deserializer` 的核对**

一致：id 作键、数值值等价 `amount` + ADDITION、对象值 `amount` 缺省 0、`addition`/`percent`/`multiply` 恰好对应 `AttributeModifierOperation` 的 `typeNameOld`、无效 id 跳过。

一处刻意不一致：lrtactical 对未知 operation 字符串会静默回退为 ADDITION；本项目按既有 Pojo 规范（同 `_TargetFilterData` 的枚举处理）会返回 null 并让整份数据校验失败，属于 fail-loud。如果你希望完全对齐它，我可以改成回退。

**未验证**：没有运行时的 JSON 解析测试（项目无测试源集），也没有进游戏实测属性是否生效；按项目规则未提交。

# 

```
看工作区对wiki的改动，现在MeleeData的attributeData改成JsonUtils RL2ObjectMap的方式，把数字和beginObject的区分放在_AttributeInfo的read里，避免出现额外专门处理读取的函数
```

# 

我先确认兼容开关的默认值，再按 RL2ObjectMap 方式改写。

用户的 IDE 已把 `MeleeData.attributeData` 改成 `Map` 字段。我先读完 MeleeData 的其余部分再统一改。

已按你的方向改完，`:forge-compat:compileJava`（含 core）通过。

**改动**

- [MeleeData.java:41](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/MeleeData.java:41)：`attribute_data`/`attributes` 直接 `JsonUtils.readRl2ObjectMap(reader, _AttributeInfo::fromJson)`，写出用 `writeRl2ObjectMap`；字段、getter/setter、`validatePojo`、`copyTo`、`applyBackCompatibility` 都改为 `Map<ResourceLocation, _AttributeInfo>` 语义。
- [_AttributeInfo.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/melee/attribute/_AttributeInfo.java)：只保留 `amount`、`modifier_operation`/`operation`；`fromJsonReader` 里用 `peek() != BEGIN_OBJECT` 区分裸数字与对象，未再读 `attribute_location`。
- [MeleeIndexInstance.java:58](core/src/main/java/dev/xcolorful/cgctactical/core/resource/instance/data/MeleeIndexInstance.java:58)：按 `entrySet()` 取键作属性 id 构建修饰符。
- 删除了 [_AttributeData.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/melee/_AttributeData.java)（已无引用，两个专门读取函数随之消失）。
- [Melee data.md](docs/wiki/resource/data/cgc_data/Melee%20data.md)：中英文都改为 `Map<ResourceLocation, \_AttributeInfo>`，JSON 示例统一成你改过的键为属性 id 的写法。

**两点说明**

- 现在 `attribute_data`/`attributes` 的旧键直接对应 lrtactical 的 `data.attributes` 映射，形状完全一致，仍依赖 `addition`/`percent`/`multiply` 这组 `typeNameOld` 对齐 operation。
- `_AttributeDataTag` 和 `_AttributeInfoTag.ATTRIBUTE_LOCATION`/`_OLD1` 已不再被引用；因为你说过 Tag 已固定，我没有动它们。需要清理的话告诉我。

改动未提交，未做运行时反序列化验证。

# 

```
格式要以CGC已有的为标注，如从GunData里有参考，包括copyTo等
```

# 

我先读 CGC 的 GunData 作为格式基准。

用户已把 `_AttributeInfo` 移到 `melee` 包。现在按 GunData 的写法统一格式。

已按 GunData 的模板统一格式，`core`/`forge-compat` 编译通过。

**MeleeData**
- 字段改为 `private @Nullable Map<ResourceLocation, _AttributeInfo> attributeData;`，getter 同步加 `@Nullable`。
- `fromJsonReader` 的 `default` 改为 `this.readCustomKey(reader, key)`，并补上 `readCustomKey` 覆写。
- `toJson` 末尾调用 `writeCustomValue(writer)`，并补上对应覆写。
- `validatePojo` 保持 GunData 的 `n1` 空检查分组、先校验标量再单独循环 map 值。
- `copyTo` 改用 GunData 的可空复制写法：

```java
@Nullable Map<ResourceLocation, _AttributeInfo> attributeData = null; {
    if (this.attributeData != null) {
        attributeData = new HashMap<>();
        for (var entry : this.attributeData.entrySet()) {
            attributeData.put(entry.getKey(), entry.getValue().copy());
        }
    }
} pojo.setAttributeData(attributeData);
```

- `applyBackCompatibility` 改为 GunData 的 `if (null) ... else ...forEach(...)` 形式。

**_AttributeInfo**（按你移动后的 `melee` 包）
- 同样补了 `readCustomKey` / `writeCustomValue` 与 `default` 分支。
- `validatePojo` 改为 `boolean n1 = (...); if (n1)` 的 CGC 写法。

`MeleeIndexInstance` 不是 ResourcePojo，未套用 GunData 模板。改动未提交。

# 
---

```
目前CGC Tactical Addon的MeleeItem对空气按住左键会一直处于第一段攻击，不会触发第二段（日志./logs/debug.log里resetting）
检查一下跟Lrtactical的区别，哪里漏了
```

# 

I'll investigate the melee combo logic in both projects.

Now let me look at the attack runtime result handling, which differs from LrTactical's unconditional clear.

Found the core divergence. Let me verify the combo indexing and the reset packet handling before making the fix.

Root cause confirmed. Let me apply the fix.

The fix compiles clean per IDE inspection. Let me run a targeted build to confirm.

## 根因

`LivingCombatantMelee.meleeAttack` 只有在 `combatantAttack` 返回 `SUCCESS` 时才清除 `preparingAttack`：

```java
if (result.isSuccess()) {
    this.combatantProperty.preparingAttack = false;
}
```

而 `_DefaultMeleeAttack.combatantAttack` 在空目标（打空气）时，循环不执行、`resultBitmap == 0`，于是返回 `ERROR`（[_DefaultMeleeAttack.java:99](core/src/main/java/dev/xcolorful/cgctactical/core/melee/attack/_DefaultMeleeAttack.java:99)）。

结果服务端的 `preparingAttack` 一直为 `true`：

1. 下一次按住左键时，`meleeCharge` 在状态锁 `preparingAttack` 处直接拒绝（[LivingCombatantMelee.java:40](core/src/main/java/dev/xcolorful/cgctactical/core/entity/combatant/LivingCombatantMelee.java:40)），服务端不再推进 combo；
2. 冷却走完后触发"蓄力超时"强制重置（[LivingCombatantCooldown.java:34](core/src/main/java/dev/xcolorful/cgctactical/core/entity/combatant/LivingCombatantCooldown.java:34)），`resetMeleeSync()` 把 combo 清空；
3. 下次点击又从 combo 0 开始 —— 所以永远停在第一段，日志里反复出现 `exceed attack preparation time, resetting`。

## 与 Lrtactical 的差异

Lrtactical 的 `CombatProperties.postAttack` 在校验通过后是**无条件**收尾的：

```java
weapon.attack(entity, stack, action, entities, preparingAttackCnt);
preparingAttack = false;   // 与命中与否无关
```

CGC 把这一步改成了依赖攻击结果，而空目标被当作 `ERROR`，于是漏掉了收尾。

## 修改

[LivingCombatantMelee.java:105](core/src/main/java/dev/xcolorful/cgctactical/core/entity/combatant/LivingCombatantMelee.java:105)：收到合法攻击包后无条件清除 `preparingAttack`（顺带移除不再使用的 `@NotNull` 导入）。打完空气后冷却正常结束，不触发超时重置，combo 得以保留并进入第二段。

IDE 构建已通过；未进游戏实测。

另外同方法里还有一处与 Lrtactical 不一致：连击数不匹配时 Lrtactical 会 `forceResetMeleeSync` 后 `return`，而 CGC 重置后仍继续用这个不匹配的 combo 执行攻击。这不影响打空气的连段，但属于同步校验上的漏洞，已放到下一步确认框里。

# 

```
也修
```

# 

已按 Lrtactical 对齐连击数不匹配分支（[LivingCombatantMelee.java:93](core/src/main/java/dev/xcolorful/cgctactical/core/entity/combatant/LivingCombatantMelee.java:93)）：

- 重置并发 reset 包后直接 `return`，不再继续用不匹配的 combo 执行攻击。
- 顺手修了同一处的日志：原来 `resetMeleeSync()` 先把 `preparingAttackCombo` 清零，日志里的 `expect {}` 永远打印 0；现在先取出期望值再重置，日志才有诊断意义。
- 清掉了磁盘上残留的未使用 `result` 变量、空的 `if (result.isSuccess()) {}` 以及随之失效的 `@NotNull` 导入。

IDE 构建通过，未进游戏实测。改动都在工作区，未提交。
