> #8 提示词

# 

```
浏览当前git工作区，把melee, throwable, consumable都补上跟CGC相同目录结构、代码编排的tooltip
- 补上tooltip的MeleeProperty IMeleeStateAccess MeleeDataAccessor相关内容
- 补上tooltip注册
- CGC Tactical Addon目前tooltip的渲染都先留空，tooltipmaks名称跟CGC一样（工作区已有)
```

# 

我会先浏览当前工作区和文档，理解 CGC 与本项目的结构对应关系。

现在查看工作区中已有的 tooltip 骨架文件。

现在研究 CGC 的 tooltip 目录结构。

工作区已有部分骨架，但引用了不存在的 part 类。继续对比 CGC 的 gun part 与本项目的 melee API。

结构已明确。现在开始补齐属性/状态/访问器中的 tooltip mask 支持。

现在创建 throwable/consumable 的 Tooltip 记录与物品挂载。

现在补齐客户端 mask 与 part 接口。

现在编写三个 client tooltip 骨架（渲染部分留空）。

现在创建 melee 的 part 接口与六个 part 骨架。

创建 throwable 的 part 接口与六个 part 骨架。

创建 consumable 的 part 接口与六个 part 骨架。

最后补上 tooltip 注册（core 注册表 + forge 事件订阅）。

开始编译验证。

编译通过。检查变更汇总。

编译与打包均通过。任务完成，未提交，等待审阅。

已完成三种物品的 tooltip 骨架，结构与 CGC 的 `gun/ammo/ammobox/attachment` 一一对应：

**属性与访问器（tooltip mask）**
- [MeleePropertyTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/MeleePropertyTag.java)、[IMeleeStateAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/IMeleeStateAccess.java)、[MeleeDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/MeleeDataAccessor.java)、[MeleeProperty.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/MeleeProperty.java)：新增 `TOOLTIP_MASK` 属性与 `hasTooltipMask/getTooltipMask/setTooltipMask`，NBT key 为 `tooltip_mask`，与 CGC gun/ammo 一致。
- throwable、consumable 同样补齐（这两个类型没有独立的 `I*StateAccess`，方法直接声明在 `IThrowableDataAccess`/`IConsumableDataAccess` 上）。

**客户端 tooltip**
- Mask：[MeleeTooltipMask.java](core/src/main/java/dev/xcolorful/cgctactical/client/api/item/melee/MeleeTooltipMask.java)（补全 fromBitmap/toBitmap）、[ThrowableTooltipMask.java](core/src/main/java/dev/xcolorful/cgctactical/client/api/item/throwable/ThrowableTooltipMask.java)、[ConsumableTooltipMask.java](core/src/main/java/dev/xcolorful/cgctactical/client/api/item/consumable/ConsumableTooltipMask.java)，位序与 CGC 相同。
- Part：每类 6 个（DESCRIPTION / STATE_INFO / BASE_INFO / ENCHANTMENT_INFO / GUIDE_TIP / DETAIL_INFO）+ 各自的 `*TooltipPart` 接口；`build/renderText/renderImage` 全部留空，`measureHeight` 按 CGC 骨架写法先返回 0。
- Client：`ClientMeleeTooltip` 补全 View/Context（visibleParts、index/display 实例），新增 `ClientThrowableTooltip`、`ClientConsumableTooltip`，含跨版本适配层。

**装配与注册**
- core：`MeleeTooltip` 沿用工作区版本，新增 [ThrowableTooltip.java](core/src/main/java/dev/xcolorful/cgctactical/core/gui/tooltip/throwable/ThrowableTooltip.java)、[ConsumableTooltip.java](core/src/main/java/dev/xcolorful/cgctactical/core/gui/tooltip/consumable/ConsumableTooltip.java)；`ThrowableItem`/`ConsumableItem` 补 `getTooltipImage`。
- 注册：[ClientTooltipRegistry.java](core/src/main/java/dev/xcolorful/cgctactical/client/init/ClientTooltipRegistry.java) 与 Forge 的 [ForgeClientTooltipRegistry.java](forge-compat/src/main/java/dev/xcolorful/cgctactical/forgeclient/init/ForgeClientTooltipRegistry.java)（`RegisterClientTooltipComponentFactoriesEvent`），与 CGC 的接线方式一致。

验证：`:core:compileJava` 与 `:forge-compat:jar` 均构建成功。按要求未提交，等待你审阅。

一处需要你决定：投掷物/消耗品我把 mask 方法直接放在 DataAccess 接口上；CGC 的枪/弹药/配件则会单独拆一个 `I*StateAccess`。是否也要为这两个类型拆出独立接口，请按上面的按钮选择。

# 

```
拆出接口
```

# 

按你的选择，为 throwable/consumable 拆出独立的 StateAccess 接口。先看 CGC 的同类接口写法。

已按你的选择拆出独立接口：

- 新增 [IThrowableStateAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/IThrowableStateAccess.java) 和 [IConsumableStateAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/consumable/IConsumableStateAccess.java)，只包含 `hasTooltipMask/getTooltipMask/setTooltipMask`，注释沿用 CGC 的「服务端处理数据，客户端读取」。
- [IThrowableDataAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/IThrowableDataAccess.java) 与 [IConsumableDataAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/consumable/IConsumableDataAccess.java) 改为继承对应的 StateAccess，并移除原先直接声明的三个方法；`ThrowableDataAccessor`/`ConsumableDataAccessor` 的实现保持不变，仅把分节标题改为对应的 StateAccess 名称。
- 你手动补充的 `MeleePropertyTag`/`IMeleeStateAccess` 分节与 `TOOLTIP_MASK` 属性条目保持原样。

`./gradlew :core:compileJava :forge-compat:compileJava` 构建成功。未提交，等待审阅。

# 

```
检查工作区新增文件里，CGC的docs/architecture/core/Home.md和client/Home.md里如果有涉及的就在Tactical Addon里补上，如果没有就不用补
```

# 

先查看 CGC 的两份 Home 文档是否覆盖 tooltip 相关包。

CGC 文档确有对应条目，读取其 GUI 段落的准确写法。

仅需补 GUI/Tooltip 与 ClientTooltipRegistry 两类条目（CGC 的 Home 未涉及 Mask/StateAccess）。开始更新两份文档。

文件在磁盘上有其他改动，通读确认排版与内容。

发现 `ClientTooltipRegistry` 条目重复（磁盘上已有一条），去重并核对 core 文档。

已按 CGC 的文档覆盖范围补齐两份 Home：

**[core/Home.md](docs/architecture/core/Home.md)**
- 在「配置」与「初始化」之间新增 `### GUI > ./core/gui`，列出 `ConsumableTooltip`、`MeleeTooltip`、`ThrowableTooltip`（中英双段）。

**[client/Home.md](docs/architecture/client/Home.md)**
- 新增 `### GUI > ./client/gui`，按类型列出 `ClientConsumableTooltip` / `ClientMeleeTooltip` / `ClientThrowableTooltip`，并在各自下以 `>` 子列表列出 6 个 Part（与 CGC 对 Gun 组件的写法一致）。
- 在「初始化」中补充 `ClientTooltipRegistry：客户端提示框注册`。

未补的部分：CGC 的两份 Home 均未涉及 `TooltipMask` 与 `StateAccess`/`Property` 类（API 段只有索引链接），按你的要求保持不补。期间发现初始化段出现重复的 `ClientTooltipRegistry` 条目，已去重。改动未提交。

# 

```
对比CGC tooltip对TaCZ的迁移映射TaCZ Migration Mapping.md，如果有的则也补上
```

# 

我先找到并阅读这份迁移映射文档，再对照两个源码库。

阅读 LRTactical 的三种 tooltip 与 TaCZ 的 gun tooltip，确认结构与可迁移内容。

LRTactical 的三种 tooltip 是「描述 + 数值行」结构。再看本项目 docs-lrtactical 的迁移映射里 tooltip 相关条目。

映射文档的写法已确认。CGC 文档有 Inventory/Tooltip(Client)/初始化中的注册映射；本项目的 `docs-lrtactical/Migration Mapping.md` 目前完全没有 tooltip 条目，按同样结构补上。

再核对映射中由 `appendHoverText` 迁移为 Part 的条目，确认 LRTactical 侧是否需要对应处理。

已对照 CGC 的 `docs-tacz/TaCZ Migration Mapping.md` 与两个源码库核对完成：

**CGC 文档中的 tooltip 映射条目**
- `inventory`.tooltip.`*Tooltip` → `core.gui.tooltip.*.Tooltip`
- `Client*Tooltip` → `client.gui.tooltip.*.Client*Tooltip`
- `getHeight/getWidth/getText/renderText/renderImage` → `BaseTooltipContext`/`TooltipPart`
- `shouldShow` → `*TooltipMask.fromBitmap`；`GunTooltipPart` → `*TooltipMask`
- `onClientSetup(RegisterClientTooltipComponentFactoriesEvent)` → `ClientTooltipRegistry.registerTooltips`

**在 Tactical Addon 中补上的内容**（[Migration Mapping.md](docs-lrtactical/Migration%20Mapping.md)）
- 新增 `### Inventory`：`me.xjqsh.lrtactical.inventory.tooltip` 的三种 Tooltip 映射到 `core.gui.tooltip.*`。
- 新增 `### Tooltip (Client)`：`Client*Tooltip`、`AbstractClientItemTooltip` 以及 `getHeight/getWidth/build/renderText/renderImage` 到 CGC 基础 API 的对应关系。
- 在 `### Initialize (Client)` 两个表中补充 `ClientSetupHandler.onRegisterTooltipComponentFactories` → `ClientTooltipRegistry.registerTooltips` / `ForgeClientTooltipRegistry.onClientTooltipRegister`。

**未补的部分及原因**
- `shouldShow`/`GunTooltipPart` 这类行来自 TaCZ，LRTactical 侧没有对应实现，因此不在 LRTactical 映射中加入；而本项目的 `*TooltipMask.fromBitmap` 与 Part 机制已在代码中就位。
- LRTactical 的物品没有 `appendHoverText`（CGC 文档里那两行来自 TaCZ 的 `AmmoItem`），故没有对应条目。
- `TooltipEvent` 在 LRTactical 中不存在，未添加。

本次仅改动文档，未改代码，未提交。
