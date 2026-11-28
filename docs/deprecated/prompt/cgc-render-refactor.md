# 

```
读`docs/architecture/client/render/PLAN.md`，现在CGC已经完成渲染重构，现在需要检查CGC Tactical Addon是否需要调整渲染写法来对齐新的性能优化的写法
```

# 

I'll read the render plan doc first, then inspect the Tactical Addon's render code.

渲染重构的核心是「静态烘焙 + 第一人称脏子树 + 光照解耦」。现在看 Tactical Addon 是如何接入渲染的。

Addon 是继承 CGC 的 `AnimatedModelObject` + `AnimateGeoItemRenderer` 的。关键要看 CGC 的烘焙发生在哪一层——addon 自己重写了 `renderByItem` 直接调 `modelObject.render`。查 CGC 侧实现。

# 

```
有需要调整的就直接改，不用给建议
```

# 

核对完成：**不需要改**，所以我没有修改任何文件。

原因：CGC 的烘焙入口在 `ModelObject.render` 内部（静态 B1-a / 第一人称 B1-b / 光照解耦 B1-c 都在这一层），而 Tactical Addon 的所有模型渲染本来就走这个入口，优化对它自动生效：

- **静态展示（展示框 / 地面 / 第三人称）**：三个 renderer 的 `renderByItem` 都是设好 arm collector 后直接调 `modelObject.render(...)`（[MeleeItemRenderer.java:185](../CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/MeleeItemRenderer.java:185)，Consumable/Throwable 相同），进入 [ModelObject.java:313](core/src/main/java/dev/xcolorful/customgun/client/model/ModelObject.java:313) 的 `renderBaked`（整模烘焙，无逐帧顶点生成与上传）。
- **第一人称**：Addon 只覆写 `renderFirstPerson` 设置 `effectVisible` 后转发给基类（[MeleeItemRenderer.java:122](D:/Github/CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/MeleeItemRenderer.java:122)），最终走 `renderBakedFirstPerson` 的脏子树分块；跳跃摆动只改根节点 offset（[TacticalRendererAddon.java:67](D:/Github/CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/melee/TacticalRendererAddon.java:67)），根节点是块根、其变换被提升为绘制矩阵，不会触发重烘焙。
- **烘焙对第三方的两条约定**（[b1a-static-bake.md:134](docs/architecture/client/render/refactor/b1a-static-bake.md:134)）：回调里不得直接发射几何、回调须幂等。Addon 的功能性回调只写 `visible` 并返回 null，手臂走 CGC 的 `HandRender` 委托，两条都满足；`visible` 进指纹，显隐切换能正确失效。也没有加载后增删节点、没有自建 `BedrockPart` 遍历或 `endBatch`。
- slot 贴图路径（GUI / 缺失材质）用的是 `SlotModel` + 直接缓冲写入，本来就不进烘焙，与 CGC 枪械 renderer 的写法一致。

验证：用 `customgun-26.11.36-forge-1.20.1.jar` 跑了 `core:compileJava`，通过。唯一未覆盖的是游戏内画面与帧时间实测（本机跑不了客户端）。

# 

```
看26.2是否要改
```

# 

我来检查 26.2 这对 worktree。先确认两边的分支状态和文档版本。

26.2 也不用改，未修改任何文件。逐项对了 26.2 的额外要求：

- **烘焙入口**：CGC 26.2 的 `ModelObject.render` 走 `drawOrBake26_2`（[ModelObject.java:289](../Custom-Gun-Continued-26.2neoforge/core/src/main/java/dev/xcolorful/customgun/client/model/ModelObject.java:289)），常驻显存失败时回放。Addon 的 `renderByItem` 和继承的 `renderFirstPerson` 都先把 `SubmitNodeCollector` 设进 `FirstPersonArmHelper` 再调 `modelObject.render`，`StaticBake.available()` 成立，整模烘焙与第一人称分块烘焙自动生效。
- **硬约束 1「包装 RenderType 必须缓存」**：Addon 只用 `ClientRenderUtils.RenderType_.entityCutout/entityTranslucent`，底层 `RenderTypes` 在 26.2 是 `Util.memoize`（已查 26.2 sources 确认），实例稳定；`bakePipelineState` 由 `ModelObject.render` 内部完成，Addon 不需要自己包。
- **硬约束 2「submit 阶段提交手臂」**：`_TacticalModelRender` 与 CGC 的 `_GunModelRender` 同构——`super_render` 之后调 `HandRender.renderFirstPersonArm`（[_TacticalModelRender.java:40](../CGC-Tactical-Addon-26.2neoforge/core/src/main/java/dev/xcolorful/cgctactical/client/model/_TacticalModelRender.java:40)）；functional 的 `HandRender` 委托在 prepare 阶段 collector 已清空，不会重复画手臂。
- **硬约束 3「effectVisible 要在 submit 设好且不能还原」**：`MeleeItemRenderer.renderFirstPerson` 设 true 且不在 finally 还原，`renderByItem` 设 false（[MeleeItemRenderer.java:120](../CGC-Tactical-Addon-26.2neoforge/core/src/main/java/dev/xcolorful/cgctactical/client/renderer/item/MeleeItemRenderer.java:120)）。
- 功能性回调仍只写 `visible`、幂等，没有直接发射几何，不会触发即时管线退回；slot 贴图走 `submitCustomGeometry`，与 `GunItemRenderer._renderSlotTexture` 一致且不进烘焙。代码里没有 `MultiBufferSource` 等旧 API 残留，注册路径（`IAnimateGeoItem` + `IItemBEWLR` mixin）与 `GunItemMixin` 一致。

验证：`:core:compileJava` 对 `customgun-26.11.39-neoforge-26.2.jar` 通过（exit 0）。两处与本次重构无关、1.20.1 也存在的既有缺口仅作记录：`entityRendering` 从未被置位（entity_hide 等投掷物实体渲染器），第三人称没有接 CGC 的 `visualUpdate` 渲染帧逻辑。

# 

```
1.20.1既有缺口从未被置位是什么意思？
```

# 

「从未被置位」的意思是：`TacticalModelObject` 里的 `entityRendering` 字段从来没有被设为过 `true`。

具体链条：

1. 字段默认 `false`（[TacticalModelObject.java:38](../CGC-Tactical-Addon-26.2neoforge/core/src/main/java/dev/xcolorful/cgctactical/client/model/TacticalModelObject.java:38)），setter `setEntityRendering(boolean)` 存在（[TacticalModelObject.java:118](../CGC-Tactical-Addon-26.2neoforge/core/src/main/java/dev/xcolorful/cgctactical/client/model/TacticalModelObject.java:118)）。
2. 全仓（1.20.1、26.2 两个分支，含 forge/neoforge-compat、mixin）grep `entityRendering`，**只有定义、getter、setter 三处，没有任何调用 `setEntityRendering(...)` 的地方**。
3. 唯一的读取点在 `_TacticalLoader` 给 `ENTITY_HIDE` / `entity_hide_*` 节点绑的回调里：`bedrockPart.visible = !_this.getEntityRendering();`（[_TacticalLoader.java:39](../CGC-Tactical-Addon-26.2neoforge/core/src/main/java/dev/xcolorful/cgctactical/client/model/_TacticalLoader.java:39)）。字段恒为 `false`，所以这个回调恒等于 `visible = true`。

结果就是：`entity_hide` 这套节点约定目前是**空转**的——模型无论拿在手里、放展示框还是画在实体上，这些节点都会正常显示，不会隐藏。

它本来是留给「模型被当作实体渲染时隐藏某些节点」的入口（比如投掷物飞在空中时的本体），但 addon 目前没有投掷物实体渲染器，也就没有地方调用它；`docs/deprecated/prompt/#12` 里也记着「`entity_hide` 要等投掷物实体渲染器落地才会碰到」。另外 26.2 下这个状态必须在 submit 阶段设好且不能随即还原（注释里已写明），所以将来接线时不能沿用 1.20.1 那种「设完立刻还原」的写法。

这与烘焙/性能重构无关：影响的是节点显隐，不影响是否走烘焙；1.20.1、26.2、26.3 三个分支都是同样情况。
