> #12 提示词

# 

```
浏览git当前改动文件，
- TaCZ到CGC的映射见./docs-tacz/TaCZ Migration Mapping.md
- 参照CGC GunItemRenderer, AttachmentItemRenderer相同的重构方式，格式、排版等保持一致
- 继续完成CGC Tactical Addon对lrtactical的MeleeItemRenderer, TacticalModelObject及引用的相关辅助类/枚举的实现
- CGC在同目录下有参考实现（如枚举、_AttachmentLoader等）
- Throwable和Consumable先不动
```

# 

我先浏览当前改动和相关参考实现。

我已梳理清楚现有骨架与 CGC 参考实现的关系。现在开始实现，并同步展示计划。

核心实现已完成，现在编译验证。

编译通过。现在复核完整改动。

已按 CGC `GunItemRenderer` / `_GunLoader` / `NodeName` 的结构完成近战渲染链路。

**实现内容**

- [NodeName.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/resource/assets/model/bedrock/geometry/NodeName.java) / [_BoneTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/resource/assets/model/bedrock/geometry/_BoneTag.java)：按 CGC 枚举样式补齐 `FIRST_PERSON_EFFECT`、`ENTITY_HIDE` 及其 `Prefix`（支持 `1p_effect_2`、`entity_hide_2` 这类编号变体）。
- [TacticalModelObject.java](core/src/main/java/dev/xcolorful/cgctactical/client/model/TacticalModelObject.java)：对应 `CustomBedrockModel`，新增 `effectVisible` / `entityRendering` 状态与读写方法，`resetCache` 中挂载功能渲染。
- [_TacticalLoader.java](core/src/main/java/dev/xcolorful/cgctactical/client/model/_TacticalLoader.java)：对应 `_GunLoader`，注册左右手 `HandRender`，遍历模型节点绑定 `1p_effect` / `entity_hide` 的可见性。
- [MeleeDisplayInstance.java](core/src/main/java/dev/xcolorful/cgctactical/client/resource/instance/assets/MeleeDisplayInstance.java)：按 CGC `GunDisplayInstance` 的方式缓存 `TacticalModelObject` 与 `LuaAnimStateMachine`（`resetCache` 中构建，脚本无效时保持空）。
- [MeleeItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/MeleeItemRenderer.java)：补齐 context、模型/状态机/材质获取、切出时长（ticks × 50）、第一人称特效开关、摄像机动画清理、`renderByItem`（GUI 槽位贴图、模型分支、缺失材质回退）以及 CGC 风格的 `_renderSlotTexture`。
- [JumpSwayUtil.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/JumpSwayUtil.java)：从 lrtactical 移植的跳跃摆动辅助类，由 `doExtraTransforms` 调用。
- [ClientResourceApi.java](core/src/main/java/dev/xcolorful/cgctactical/client/api/resource/ClientResourceApi.java)：新增 `getMeleeDisplayInstance(ItemStack)` 便捷重载，对应 CGC 的同名方法。

**验证**

`./gradlew :forge-compat:compileJava` 通过（JDK 17 + `-Dfile.encoding=UTF-8`），仅有既有的 `FMLJavaModLoadingContext.get()` 弃用警告。未进入游戏实测。

**需要你知道的判断**

1. `GUI` 时仅当 `slot_texture_location` 不是 `NULL_LOCATION` 才走槽位贴图，否则回退 3D 模型，以保持 lrtactical 原行为；CGC 的枪械渲染器是无条件走槽位贴图的。
2. 新增的平台 API：`Mth.lerp`、`LocalPlayer.yOld` / `onGround()`、`ItemDisplayContext.firstPerson()`；前者在 CGC 的 `GunRendererAddon` 已有先例。
3. Throwable / Consumable 的渲染器与显示实例未改动。改动未提交。

# 

[fork1](#fork1)
[fork2](#fork2)

# fork1

```
继续throwable和consumable，以已提交的写法为确定的风格
```

# 

我先确认已提交的近战实现风格与当前工作区状态。

我已知悉已提交风格（`TacticalRendererAddon`、显示实例缓存模型/状态机、`ClientResourceApi` 便捷重载）。现在核对投掷物/消耗品的上下文与数据语义。

编译通过。复核改动与格式。

已按已提交的近战写法完成投掷物与消耗品。

**显示实例**

- [ConsumableDisplayInstance.java](core/src/main/java/dev/xcolorful/cgctactical/client/resource/instance/assets/ConsumableDisplayInstance.java) / [ThrowableDisplayInstance.java](core/src/main/java/dev/xcolorful/cgctactical/client/resource/instance/assets/ThrowableDisplayInstance.java)：与 `MeleeDisplayInstance` 相同，在 `resetCache` 中构建 `TacticalModelObject` 与对应上下文的 `LuaAnimStateMachine`，并暴露 `getModelObject()` / `getStateMachine()`。
- [ClientResourceApi.java](core/src/main/java/dev/xcolorful/cgctactical/client/api/resource/ClientResourceApi.java)：补齐 `getConsumableDisplayInstance(ItemStack)`、`getThrowableDisplayInstance(ItemStack)`。

**渲染器**

- [ConsumableItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/ConsumableItemRenderer.java)：`updateContext` 同步 `currentItem/using/usingTick/partialTicks`，切出时长保持 ticks × 50，其余结构（`doExtraTransforms`、摄像机动画清理、`renderByItem`、`_renderSlotTexture`）与近战一致。
- [ThrowableItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/ThrowableItemRenderer.java)：同上；投掷物的 `put_away_time` 在原数据中是 long 毫秒，保持原行为直接返回、不乘 50。
- [ConsumableItemMixin.java](forge-compat/src/main/java/dev/xcolorful/cgctactical/forgeclient/mixin/item/ConsumableItemMixin.java) / [ThrowableItemMixin.java](forge-compat/src/main/java/dev/xcolorful/cgctactical/forgeclient/mixin/item/ThrowableItemMixin.java)：照 `MeleeItemMixin` 接入 `IAnimateGeoItem` / `IItemBEWLR`，让渲染器真正生效。

**数据补全**

- [ThrowableDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/ThrowableDataAccessor.java)：原先未覆盖 `getPutAwayTime`（落到默认 0），按近战/消耗品的同款写法补上。

**判断与验证**

- 跳跃摆动复用已提交的 `MeleeRendererAddon.applyJumpingSway`：原版三类渲染器都用同一个 `JumpSwayUtil`，而提交里它的实现已归到该类。若你希望改成各分类独立的 `*RendererAddon`，告诉我即可。
- 消耗品/投掷品不设置 `effectVisible`，与原版一致（仅近战开启 `1p_effect`）。
- `:forge-compat:compileJava` 通过，仅既有弃用警告；未进入游戏实测，未提交。

# fork2

```
现在slot材质，第三人称，第一人称都有渲染模型
当从枪械(IGun)或近战(IMelee)切换到近战(IMelee)时有播放掏刀动画，但是从近战切到原版物品再切回近战时没有掏刀动画
从近战切到别的物品也没有播放put away动画
刀包在游戏目录下./tacz/
需要排查问题
- 部分功能已经在CGC里能够复用，不一定需要像lrtactical一样重写一遍
- 如果遇到IGun无害放宽为IAnimationItem这类重构，则可以修改CGC
```

# 

我先查看磁盘上的最新实现，以及 CGC 中 `tryInit`/`tryExit` 的调用链。

我定位到两处原因：CGC 的 `LocalShooterDraw` 用 `IGun` 判断切出/切入，近战不属于 `IGun`；另外近战渲染器没有触发 `put_away`。先确认 CGC 仓库状态。

CGC 仓库干净（`core1.20.1`）。现在做两处修改：CGC 的切换判定放宽到 `IAnimationItem`，近战渲染器补上 `tryExit` 的 put-away 触发。

修复代码已通过编译。现在重新构建 CGC 与 Addon 的 jar，便于你在游戏目录里直接验证。

已定位并完成修复，jar 也已重建部署。先记录本次修复清单。

已定位到两个原因并修复。

**根因**

1. CGC 的 [LocalShooterDraw.java](D:/Github/Custom-Gun-Continued/core/src/main/java/dev/xcolorful/customgun/client/entity/shooter/LocalShooterDraw.java) 在 `draw()` 里用 `IGun` 判断切出物品，近战不属于 `IGun`，所以：
    - 近战切走时 `_doPutAway` 从不调用 → 没有 put-away；
    - `tryExit` 不执行 → 状态机一直保持 initialized → 切回近战时 `needReInit` 为 false → 没有 draw。
      这就是“切回没有掏刀动画”和“切走没有收刀动画”是同一个根因。
2. CGC 基础类 `AnimateGeoItemRenderer.tryExit` 只设置时长并退出，不会像 `GunItemRenderer.tryExit` 那样先触发 `put_away` 动画输入；近战渲染器没有覆写它。

**改动**

- `LocalShooterDraw`：把切出/切入判定从 `IGun` 放宽为 `IAnimationItem`（`IGun extends IAnimationItem`，对枪械行为无变化）；`tickDraw` 的 NBT 变化判定也同步改为 `IAnimationItem.switchItemNeedReset`，避免近战耐久变化被误判成切枪而反复播放动画。
- [MeleeItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/MeleeItemRenderer.java)：新增 `tryExit` 覆写，按 `GunItemRenderer` 的方式设置 `putAwayTime`、触发 `INPUT_PUT_AWAY`、`KeepingItemRenderer.cgc$keep`、再 `exit`。
- Throwable/Consumable 未涉及。

**验证**

- CGC 与 Addon 的 `:core:compileJava`、`:forge-compat:compileJava` 均通过（仅既有弃用警告）。
- 已重建并覆盖部署：
    - `customgun-26.11.35-forge-1.20.1.jar`（CGC `reobfJarJar`）
    - `cgctactical-0.0.0-forge-1.20.1.jar`（Addon `reobfJar`）
    - 目标：`E:\Software\Microsoft...` → 实际为 `E:\Software\Minecraft\1.20.1test\mods\`
- 已用 `javap` 确认部署后的 jar 中 `MeleeItemRenderer.tryExit` 与 `LocalShooterDraw` 的 `IAnimationItemGetter` 均已生效。
- 刀包的旧内联 `data` 会由 `MeleeIndex._reloadLegacyData` 迁移，`put_away_time`（刀 6 tick、karambit 5 tick）应能正常生效。

请在游戏里按这两个路径复测：近战 → 原版/其他物品（应收刀），原版 → 近战（应掏刀）。两边仓库都还是未提交状态。

# 

```
consumable和throwable也补一下
```

# 

继续完成 Consumable 与 Throwable。先读取它们的现状与 lrtactical 参考实现。

三个物品接口都已继承 `IAnimationItem`，CGC 的切出判定已覆盖它们；现在给 Consumable / Throwable 补上与 Melee 一致的 `tryExit`。

发现 Throwable 还缺一处：`prepareTime` 从未从数据写入（脚本靠它控制投掷前摇）。确认接口后补上。

补完后再编译并更新测试目录的 jar。

Consumable 与 Throwable 已按 Melee 的方式补齐。

**本次改动**

- [ConsumableItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/ConsumableItemRenderer.java) / [ThrowableItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/ThrowableItemRenderer.java)：新增 `tryExit`，流程与 Melee 一致——写入 `putAwayTime`、触发 `INPUT_PUT_AWAY`、`KeepingItemRenderer.cgc$keep`、退出状态机并设置 `exitingTime`。CGC 的切换判定已放宽到 `IAnimationItem`，两者都满足，所以切出会收刀、切回会重新 `tryInit` 播放 draw。
- [ThrowableAnimStateContext.java](core/src/main/java/dev/xcolorful/cgctactical/client/animation/statemachine/ThrowableAnimStateContext.java)：`setCurrentItem` 现在通过 `IThrowableGetter` 取物品并写入 `prepareTime`（`getUseDurationLimit`）。此前该字段恒为 0，而手雷脚本会用 `context:getPrepareTime()` 判断前摇是否结束。
- 顺手把 Consumable 内部对 display 的调用从废弃别名 `getStateMachine()` 换成 `getAnimStateMachine()`，与 Throwable/Melee 一致。

**未改动的部分（按原版行为）**

- Consumable/Throwable 没有覆写 `renderFirstPerson` 去开关 `1p_effect`；lrtactical 原本也只有 Melee 这么做，且当前刀包模型没有 `1p_effect` 节点。
- 投掷物的实体渲染器（`ThrowableEntityRenderer`）尚未实现，`TacticalModelObject.setEntityRendering` 已为其预留。
- 三个类别的 `INPUT_INSPECT` 还没有输入源（CGC 的 inspect 仍绑定 `IGun`）。

**验证**

- `:forge-compat:compileJava` 通过（仅既有弃用警告）。
- 已执行 `:forge-compat:reobfJar` 并覆盖部署到 `E:\Software\Minecraft\1.20.1test\mods\cgctactical-0.0.0-forge-1.20.1.jar`。
- 部署后的 jar 已用 `javap` 确认：Consumable/Throwable 渲染器都含 `tryExit`，Throwable 状态机含 `getUseDurationLimit` 调用。
- CGC 侧上一轮的改动仍是 `core1.20.1` 上的已提交状态；Addon 这些改动未提交。

# 
---

```
现在要把CGC Tactical Addon 26.2分支对齐CGC 26.2，写法都跟CGC保持一致
git有些修改，继续完成并通过编译
```

# 

我先检查两个仓库的当前状态。

CGC 最近有 "unify style" 提交。我先看 Tactical Addon 的未提交改动和 CGC 的 unify style 具体做了什么。

改动是把 `setFirstPersonArmCollector_` 改名为 `setFirstPersonArmCollector`。我确认 CGC 侧现在的实际 API，并看 unify style 的具体内容。

CGC 同时保留了 `setFirstPersonArmCollector(SubmitNodeCollector)` 和跨版本占位重载 `setFirstPersonArmCollector_(Object)`。我需要确认 Tactical Addon 里 `bufferSource` 的实际类型，才能判断改名后是否能编译。

`bufferSource` 在 Tactical Addon 里是 `MultiBufferSource` 类型。我需要看 CGC 对应渲染器里传入的到底是什么类型。

CGC 26.2 的 `renderByItem` 已经改成 `SubmitNodeCollector`，但 Tactical Addon 里 `_renderSlotTexture` 仍接收 `MultiBufferSource`。我先完整看 Tactical Addon 这三个文件的签名与导入。

Tactical Addon 的 `renderByItem` 仍是 `MultiBufferSource`，而 CGC 26.2 已改为 `SubmitNodeCollector` —— 这不是正确的 override。我先直接编译看实际错误范围。

编译是针对本地 jar `customgun-26.11.35-neoforge-26.2.jar`。我先检查该 jar 的 API 签名，再决定怎么改。

确认了：jar 里 `setFirstPersonArmCollector` 只接受 `SubmitNodeCollector`，且 `renderByItem` 也是 `SubmitNodeCollector`。Tactical Addon 仍是旧签名。先跑一次编译拿到完整错误清单。

等待编译时，我先读 CGC 侧同类渲染器作为目标写法模板。

CGC 的模板已确认。看编译结果。

现在按 CGC 的写法改三个文件。

core 编译通过。现在跑完整构建确认 compat 模块也没问题。

等待构建时，我检查其他写法对齐点，比如 CGC 的 `unify style` 把注释分区头改成了接口名。

完整构建通过。接下来统一剩余的分区注释头（CGC 用的是 `IAnimateGeoItemRendererOperator`）。

构建通过。看最终改动。

已完成对齐并通过编译。

**改动内容**（三个物品渲染器：[ConsumableItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/ConsumableItemRenderer.java)、[MeleeItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/MeleeItemRenderer.java)、[ThrowableItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/ThrowableItemRenderer.java)）：

- 26.2 已移除 `MultiBufferSource`，`renderByItem` 的参数改为 `SubmitNodeCollector`（与 CGC `GunItemRenderer` 一致）。
- `_renderSlotTexture` 的参数改为 `SubmitNodeCollector collector`，贴图渲染从 `getBuffer(...)` 改为 `submitCustomGeometry(...)` + 拷贝 PoseStack，完全照 CGC 的写法。
- 你原来未提交的改名保留：`setFirstPersonArmCollector_` → `setFirstPersonArmCollector`，因为现在传入的确实就是 `SubmitNodeCollector`。
- 顺带把分区注释头 `AnimateGeoItemRendererOperator` 统一成 CGC 用的 `IAnimateGeoItemRendererOperator`。
- 清掉不再使用的 `VertexConsumer`、`MultiBufferSource` 导入。

**验证**：`./gradlew build` 两个模块（core、neoforge-compat）全部 BUILD SUCCESSFUL。

按项目约定没有提交，改动留在工作区等你审阅。

# 

```
现在CGC Tactical Addon 26.2：
- 第一人称没渲染手
- 第三人称和物品槽GUI渲染正常
- CGC原本的渲染正常

需要排查是否还有其他要跟CGC26.2写法同步的地方
- 部分功能已经在CGC里能够复用，不一定需要像lrtactical一样重写一遍
- 如果遇到IGun无害放宽为IAnimationItem这类重构，则可以修改CGC
```

# 

我先看三个渲染器的当前状态，以及 CGC 第一人称手部渲染的入口链路。

最近有一个 "Port item renderer to 26.2 #12" 提交，先看它改了什么。

对比 addon 26.2 与 26.3 分支的渲染器差异，看 26.3 是否已有第一人称的修法。

我用 IDEA MCP 直接看 26.2 原版的 `ItemInHandRenderer` 与 `ItemStackRenderState`，确认第一人称到底走哪条路径。

我已经定位到根因。先确认 CGC 侧 26.2 的写法差异，再看两边的构建产物路径。

根因确认：26.2 延迟渲染下，`HandRender` 的委托在 flush 阶段才执行，此时 `FirstPersonArmHelper` 的收集器已被清空，所以手臂提交不出去。CGC 的枪械已经在 submit 阶段显式出手臂（`_GunModelRender._renderHand`），而 Addon 仍走旧的委托路径。

我先把 CGC 里的这段手臂渲染提炼成可复用的公共方法。

现在改 Addon 的模型类，让它在 submit 阶段复用 CGC 的手臂渲染。

CGC 侧改完了。重建 CGC 的 jarJar 产物并同步到 Addon 的 libs。

等待构建时，继续排查 Addon 里其他还没跟 26.2 对齐的渲染写法。

CGC 构建成功。同步 jar 到 Addon 并重新编译。

两边都构建成功。汇总最终改动。

已定位并修复第一人称不渲染手的问题。

**根因**

26.2 起模型几何走 `SubmitNodeCollector` 延迟渲染。`ModelObject.render` 里 `delegateRenderers`（手臂就挂在这）是在 `submitCustomGeometry` 的 **flush 回调** 里执行的，而此时 `FirstPersonArmHelper` 的 ThreadLocal 收集器早已在 `renderFirstPerson` 的 `finally` 中清空 —— `renderFirstPersonArm` 直接 `return`，手臂提交不出去。

CGC 的枪械已经在 26.2 里绕开了这条路：`_GunModelRender._renderHand` 在 **submit 阶段**沿手部定位组路径显式渲染手臂。Addon 仍在走旧的 `_TacticalLoader` → `HandRender` 委托路径，所以模型能画、手臂不能画。第三人称/GUI 不经过这条路径，因此正常。

**改动**

CGC（2 个文件）：
- [HandRender.java](D:/Github/Custom-Gun-Continued-26.2neoforge/core/src/main/java/dev/xcolorful/customgun/client/renderer/model/HandRender.java)：新增公共 `renderFirstPersonArm(poseStack, handPosPath, hand, light)`，把「沿定位组路径 → 转 180° → 提交手臂」这段提成可复用 API。
- [_GunModelRender.java](D:/Github/Custom-Gun-Continued-26.2neoforge/core/src/main/java/dev/xcolorful/customgun/client/model/_GunModelRender.java)：`_renderHandInternal` 改为调用它，行为不变、少一份重复。

Addon（1 个文件）：
- [TacticalModelObject.java](D:/Github/CGC-Tactical-Addon-26.2neoforge/core/src/main/java/dev/xcolorful/cgctactical/client/model/TacticalModelObject.java)：`resetCache` 缓存左右手定位组路径；覆写 `render(...)`，在第一人称且 `getRenderHand()` 时于 submit 阶段调用上面那个 CGC 公共方法。三个物品（近战/消耗品/投掷物）共用，不需要各自改渲染器。

**验证**：CGC `:neoforge-compat:jarJar` 通过，jar 已覆盖到 `libs/customgun-26.11.35-neoforge-26.2.jar`；Addon `./gradlew build` 通过。**未进游戏实测，两边均未提交。**

**排查出的另一处同类隐患**（尚未改）：`_TacticalLoader` 里 `1p_effect` / `entity_hide` 的可见性是在 flush 阶段的 functionalRenderer 里读字段，而 `MeleeItemRenderer.renderFirstPerson` 用 `setEffectVisible(true) … finally false` 把字段包在渲染调用前后 —— flush 时读到的一定是 `false`，所以第一人称特效节点在 26.2 下不显示。当前刀包模型没有 `1p_effect` 节点，暂不影响观感；`entity_hide` 要等投掷物实体渲染器落地才会碰到。

# 

```
目前26.2的修改已经能渲染手，且刚才CGC的修改已经1.20.1-26.3跨版本统一，
- CGC Tactical Addon的左右手字段也要像CGC一样在core1.20.1就存在+标记available since 26.2
- Addon的渲染方式跟CGC风格保持一致，如有必要可以用版本区间、if (true) return等方式隔离26.2前后的渲染路径
- 并且，把你刚才提到的隐患也在core1.20.1应用修改
- 修改完core1.20.1之后，等我手动merge到高版本，再处理冲突
```

# 

我先确认两个主检出（core1.20.1）的当前状态与相关代码。

CGC 主检出在 `1.20.1` 分支，且已有提交 “Provide 26.2 hand render for addon mods”。我先看清各 worktree 与已有改动。

主检出情况已清楚：CGC 的 `core1.20.1` 改动你已经提交并合到 `1.20.1`；Addon 的 `core1.20.1` 上你只加了字段。我继续完成 Addon 部分。

现在完成 Addon `core1.20.1` 的改动。

需要先用最新的 CGC `core1.20.1` 重建 1.20.1 的 jar。

core1.20.1 上的改动已完成，两边都编译通过。

**CGC（`D:\Github\Custom-Gun-Continued`，`1.20.1`）**

你之前的提交 `d699abe2e Provide 26.2 hand render for addon mods` 已包含所需内容：`HandRender.renderFirstPersonArm(...)`（带 `if (true) return;` 让 1.20.x 走空实现并保留引用）+ `_GunModelRender` 复用。我没有再改源码，只重建了 `:forge-compat:reobfJarJar`，产物已覆盖到 Addon 的 `libs/customgun-26.11.35-forge-1.20.1.jar`。

**Addon（`D:\Github\CGC-Tactical-Addon`，`core1.20.1`）**

[TacticalModelObject.java](core/src/main/java/dev/xcolorful/cgctactical/client/model/TacticalModelObject.java)：
- 保留你加的 `leftHandPosPath` / `rightHandPosPath` 字段（`@ApiStatus.AvailableSince("26.2")`，包内可见），并在 `resetCache` 里像 `_GunLoader.constructOtherPath` 一样缓存路径。
- 补了 `getLeftHandPosPath()` / `getRightHandPosPath()`，同样是 `available since 26.2`，与 CGC `GunModelObject` 一致。
- 新增 `render(...)` 覆写，按 CGC 的隔离写法：

```java
// [1.20.1, 26.2)
if (true) return; // 让IDE保留下面的引用关系
// [26.2, )
if (!transformType.firstPerson() || !this.getRenderHand()) return;
HandRender.renderFirstPersonArm(poseStack, this.leftHandPosPath, HumanoidArm.LEFT, light);
...
```

[MeleeItemRenderer.java](core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/MeleeItemRenderer.java)（隐患修复）：
- `renderFirstPerson` 不再用 `try / finally` 立刻还原 `effectVisible`，改为在 submit 阶段置 `true` 后保留。
- `renderByItem` 在非第一人称分支置 `false`。

这样 flush 阶段的 functionalRenderer 读到的就是本次 submit 的值，`1p_effect` 可见性在 26.2 下能生效。
另外给 `setEntityRendering` 补了说明：26.2 起该状态须在 submit 阶段设好、不能随即还原 —— 等投掷物实体渲染器落地时按此处理。

**验证**：CGC `:forge-compat:reobfJarJar` BUILD SUCCESSFUL（48s）；Addon `./gradlew build` BUILD SUCCESSFUL（28s）。**未提交**。

两点提醒：
1. `docs/deprecated/prompt/#12 Item-renderer-skeleton.md` 在我操作期间被外部追加了本轮对话记录，我没有动它。
2. 26.2 worktree 里还留着同样改动的未提交版本（CGC 的 `HandRender`/`_GunModelRender`、Addon 的 `TacticalModelObject`）。等你 merge core1.20.1 上去后，冲突按「保留 core1.20.1 的字段+getter 结构，去掉 `if (true) return;` 启用 26.2 段」即可，我来处理。
