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
