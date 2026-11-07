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
