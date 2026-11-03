> #7 提示词

# 

```
按照dev.xcolorful.customgun.core.api.item.gun.GunDataAccessor相同的模式，完成git工作区里4个类别的accessor、XxxProperty、XxxPropertyTag、_IXxxPropertyAccess
```

# 

I'll start by reading the reference pattern and the current state of the four categories.

Now I'll implement the tactical base layer.

All four categories are implemented. Now I'll verify compilation through the IDEA MCP.

The IDE build failed on dependency resolution rather than code. I'll try the project's Gradle build.

Compilation passes with only pre-existing warnings. Let me verify the changed file set.

已按 `GunDataAccessor` 的模式完成四个类别，`./gradlew compileJava --offline` 通过（仅有原有的弃用警告）。

**Tactical**（基础层）
- [TacticalDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/tactical/TacticalDataAccessor.java)：给出默认实现（无数据时 null / 0），实际数据由三个子类别覆盖
- [TacticalProperty.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/tactical/TacticalProperty.java)、[TacticalPropertyTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/tactical/TacticalPropertyTag.java)、[_ITacticalPropertyAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/tactical/_ITacticalPropertyAccess.java)

**Consumable**
- [ConsumableDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/consumable/ConsumableDataAccessor.java)：NBT 读写 + `ConsumableIndexInstance` 回退；tactical 方法从 `ConsumableData` 取（use_duration / cooldown / draw_time / put_away_time / cooldown_category）
- [ConsumableProperty.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/consumable/ConsumableProperty.java)、[ConsumablePropertyTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/consumable/ConsumablePropertyTag.java)、[_IConsumablePropertyAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/consumable/_IConsumablePropertyAccess.java)

**Melee**
- [MeleeDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/MeleeDataAccessor.java)：manager group、melee_ rl、display 回退；cooldown/delay/movement 从 `_CombatData` 的左/右攻击列表按 combo 索引取（越界返回 0/null）
- [MeleeProperty.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/MeleeProperty.java)、[MeleePropertyTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/MeleePropertyTag.java)、[_IMeleePropertyAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/_IMeleePropertyAccess.java)

**Throwable**
- [ThrowableDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/ThrowableDataAccessor.java)：资源位置/显示回退；tactical 方法取 prepare_time / cooldown / put_away_time
- [ThrowableProperty.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/ThrowableProperty.java)、[ThrowablePropertyTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/ThrowablePropertyTag.java)、[_IThrowablePropertyAccess.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/_IThrowablePropertyAccess.java)

**接线方式**：四个 `IXxxDataAccess` 各自继承对应的 `_IXxxPropertyAccess`（与 `IGunDataAccess extends _IGunPropertyAccess` 一致）。由于 consumable/melee/throwable 同时经 `ITacticalItem → ITacticalDataAccess` 继承了 `_ITacticalPropertyAccess`，三个子接口让 `_IXxxPropertyAccess extends _ITacticalPropertyAccess` 并覆盖 `(ItemStack, String)` 便捷方法：先查自身枚举，未命中再回退到 tactical 枚举，避免默认方法冲突。

**需要你确认的判断**：
1. `getUseDurationLimit` 对投掷物取 `prepare_time`；近战没有对应数据，沿用基础默认 0。
2. `TacticalProperty` 的 setter 全部为 `null`（`ITacticalDataAccess` 本身没有 setter，属性均为数据驱动只读）。
3. `MeleeProperty` 未包含 `getMeleeCooldown/Delay/Movement`——它们需要 `MeleeAction`/`combo` 参数，`Property` 的 `BiFunction<Access, ItemStack, T>` 形态无法承载，与 `GunProperty` 的限制一致。

未提交，等你审阅。另外 IDEA MCP 构建因无法解析 `net.minecraft:client:1.20.1`（环境依赖问题）失败，改用 Gradle 验证。

# 

```
不得在接口里建_getConsumableIndexInstance等方式，必须展开
```

# 

The on-disk change shows the desired inline style. I'll remove all `_get…` static helpers and expand the resolution in every method.

已按你的要求展开，接口里不再有任何辅助静态方法：

- [ConsumableDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/consumable/ConsumableDataAccessor.java)：`getConsumableDisplayLocation` 及 4 个 tactical 方法全部展开为 `getConsumableLocation` → `ResourceApi.getConsumableIndexInstance` 的内联写法（与你磁盘上改的 `getCooldownLocation` 一致）。
- [ThrowableDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/throwable/ThrowableDataAccessor.java)：同样展开 5 处。
- [MeleeDataAccessor.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/MeleeDataAccessor.java)：`getMeleeDisplayLocation`、`getDrawTime`、`getPutAwayTime` 展开；`getMeleeCooldown` / `getMeleeDelay` / `getMeleeMovement` 各自完整展开「取索引实例 → 取 attackData → 按 MeleeAction 选左右列表 → combo 越界判断」。
- 移除了对应的 `ApiStatus` 导入。

`./gradlew compileJava --offline` 通过，仅剩原有的弃用警告。一个说明：`getMeleeMovement` 没有 combo 参数，展开后取该动作下第 0 个攻击的 movement。未提交。

# 

```
有些接口不需要重载，对比Migration Mapping.md重新检查
```

# 

对比原始 LesRaisins 实现后，我发现两处多余的覆盖：原版 `IConsumable`/`IThrowable` 都没有覆盖 `getAttackCoolDown`（默认 20），而 `getUseCooldown` 对应它的新名字；Throwable 也没有覆盖 `getPutAwayTime`（你已删除）。我只保留原版确有覆盖的方法。

已对照原版 LesRaisins 逐方法核查（`ICustomItem` 的默认值与各 `*Item` 的实际覆盖），只保留原版确实覆盖的方法：

| 方法 | 原版来源 | 现在 |
|---|---|---|
| Consumable `getCooldownLocation` | `IConsumable.getCoolDownId` | 保留 |
| Consumable `getUseDurationLimit` | `IConsumable.getMaxUsingTick` | 保留 |
| Consumable `getDrawTime` / `getPutAwayTime` | `ConsumableItem` 覆盖 | 保留 |
| Consumable `getUseCooldown` | 未覆盖 `getAttackCoolDown`，默认 20 | **已删除覆盖**，回落到接口默认 20 |
| Melee `getDrawTime` / `getPutAwayTime` | `MeleeItem` 覆盖 | 保留 |
| Melee `getMeleeCooldown/Delay/Movement` | `getAttackCoolDown/AttackDelay/AttackMovement` | 保留 |
| Melee `getCooldownLocation` / `getUseDurationLimit` | 未覆盖 | 未覆盖（Tactical 默认 null/0） |
| Throwable `getCooldownLocation` | `IThrowable.getCoolDownId` | 保留 |
| Throwable `getUseDurationLimit` | `IThrowable.getMaxUsingTick`（prepare_time） | 保留 |
| Throwable `getUseCooldown` | 未覆盖，默认 20 | **已删除覆盖** |
| Throwable `getPutAwayTime` / `getDrawTime` | `ThrowableItem` 未覆盖，仅 renderer 直接读数据 | 不覆盖（保持你删除后的状态） |

`./gradlew compileJava --offline` 通过，仅剩原有弃用警告。`TacticalDataAccessor` 仍保留 null/0 的中性默认值，作为未被具体类别覆盖时的兜底。未提交。

# 

```
继续物品注册
```

# 

`MeleeItem` 与 `ModItems.MELEE` 已存在，Consumable/Throwable 仍是空类。我按同样的模式补齐这两个类并注册。

物品注册已补齐：

- [ConsumableItem.java](core/src/main/java/dev/xcolorful/cgctactical/core/item/consumable/ConsumableItem.java)：`extends Item implements IConsumable, ConsumableDataAccessor`，构造方式与 `MeleeItem` 一致（`ModItems.CUSTOM_ITEM_PROPERTY` + `ItemType.CONSUMABLE`）
- [ThrowableItem.java](core/src/main/java/dev/xcolorful/cgctactical/core/item/throwable/ThrowableItem.java)：`extends Item implements IThrowable, ThrowableDataAccessor`
- [ModItems.java:24](core/src/main/java/dev/xcolorful/cgctactical/core/init/registry/ModItems.java:24)：新增 `THROWABLE`、`CONSUMABLE`，与 `MELEE` 一样用 `ITEMS.register(ItemType.* .getTagName(), ...)`，注册名分别为 `cgctactical:throwable` / `cgctactical:consumable`（与 LesRaisins 原版一致）
- [CgcTacticalForge.java:32](forge-compat/src/main/java/dev/xcolorful/cgctactical/forge/CgcTacticalForge.java:32)：补上 `ModItems.ITEMS.registerAll(modEventBus)`。此前这里只注册了粒子，`MELEE` 虽然声明了但从未真正挂到事件总线上。

`./gradlew compileJava --offline` 通过。

尚未做：创造模式标签页（原版有 `THROWABLE_TAB` / `CONSUMABLE_TAB` / `MELEE_TAB`）、物品模型/纹理与语言键；这些需要单独的注册与资源文件。

# 

```
继续tab注册，跟CGC相同格式
```

# 

已按 CGC 的写法完成标签页注册：

- [ModCreativeTabs.java](core/src/main/java/dev/xcolorful/cgctactical/core/init/registry/ModCreativeTabs.java)：`IRegistrar<CreativeModeTab> TABS = ...createCreativeTabs(MOD_ID)`，按 `MELEE_TAB → THROWABLE_TAB → CONSUMABLE_TAB` 注册，使用 `.title(...)`、`.icon(...)`、`.displayItems(...)`、`.withSearchBar()`、`.withTabsBefore(前一个.getRegistryName())`，与 CGC 的 `ModCreativeTabs` 结构一致。
- 三个内容构建类（放在 `core/api/minecraft/tab/`，对应 CGC 的 `GunTab`/`AmmoTab`）：
    - [MeleeTab.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/tab/MeleeTab.java)
    - [ThrowableTab.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/tab/ThrowableTab.java)
    - [ConsumableTab.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/tab/ConsumableTab.java)

  每个都含 `indexSort()`（按 `getSlotSort()`）、`buildXxxItems()`（遍历 `ResourceApi.getAllXxxIndexInstance()` 并用对应 accessor 写入 location）、`buildXxxTabItem()` 图标。
- 标题语言键沿用你已有的 `customgun.tacticalcategory.melee/throwable/consumable`，未新增键。
- [CgcTacticalForge.java:33](forge-compat/src/main/java/dev/xcolorful/cgctactical/forge/CgcTacticalForge.java:33)：补上 `ModCreativeTabs.TABS.registerAll(modEventBus)`。

`./gradlew compileJava --offline` 通过。

两点说明：图标用该类别排序后的第一个条目，数据为空时回落到无 location 的基础物品（CGC 用的是专用 icon location + mixin，本仓库暂无该 mixin）；`TabGroup` 暂未建，因为目前没有配方/结果系统消费它。

# 

```
按照我这个ConsumableTab改，跟CGC完全一致
```

# 

`ConsumableBuilder` 已存在，索引的 `getHideInGame()` 也继承自 CGC 的 `_DataIndex`。我现在补 `MeleeBuilder`/`ThrowableBuilder`、按同样格式改写两个 Tab，并让三个 Index 像 CGC 一样解析/写出 `hide_in_game`。

已完全对齐你改的 `ConsumableTab` 与 CGC 的 `GunTab`/`AmmoTab` 结构：

**Tab 类**
- [MeleeTab.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/tab/MeleeTab.java) / [ThrowableTab.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/tab/ThrowableTab.java)：与 `ConsumableTab` 同构
  - `indexSort()` 按 `slot_sort`
  - `buildXxxItems()` 先过滤 `index.getHideInGame()`
  - `buildXxxItem(ResourceLocation)` 通过 `XxxBuilder.create(...).setProperty(XxxProperty.XXX_LOCATION, ResourceLocation.class, location).build()`
  - `XXX_ICON_LOCATION` 静态常量 + `if (true) return ItemStack.EMPTY;` 的 mixin 注入占位

**Builder**
- [MeleeBuilder.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/builder/MeleeBuilder.java) / [ThrowableBuilder.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/builder/ThrowableBuilder.java)：与你的 `ConsumableBuilder` 完全同构，继承 CGC 的 `ItemBuilder`。

**索引**
- [ConsumableIndex.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/ConsumableIndex.java)、[MeleeIndex.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/MeleeIndex.java)、[ThrowableIndex.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/ThrowableIndex.java)：补上 `hide_in_game` / `hidden` 的读取与写出，和 CGC 各 Index 一致（之前 `getHideInGame()` 恒为 false）。

`./gradlew compileJava --offline` 通过（仅有原有的 Loom/Forge 弃用警告）。未提交。

# 

```
_DataIndex是专门更新过的，CGC Tactical里三个index里跟CGC内置子类实现不一样的需要补上来对齐
```

# 

`_DataIndex`/`ResourcePojo` 提供的是 `readCustomKey`/`writeCustomValue` 钩子，CGC 内置子类都会接上；三个 Addon index 还缺这部分，另外 `ConsumableIndex` 仍在借用 `GunIndexTag`。我按内置子类的写法对齐。

已按 CGC 内置 `_DataIndex` 子类（`GunIndex` / `AmmoIndex` / `AttachmentIndex`）的写法对齐三个 Index：

**[ConsumableIndex.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/ConsumableIndex.java)**
- 基础字段全部改用本类自己的 `ConsumableIndexTag`（原来借用 `GunIndexTag`）
- `default -> this.readCustomKey(reader, key)`
- 补 `readCustomKey` 覆盖
- `toJson` 末尾调用 `writeCustomValue(writer)`，并补上该覆盖

**[MeleeIndex.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/MeleeIndex.java) / [ThrowableIndex.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/ThrowableIndex.java)**
- 同样是 `readCustomKey` 默认分支 + 两个钩子覆盖 + `writeCustomValue` 写出
- 保留各自已有的 `hide_in_game` 读写与 `base_item` 回填

原先已一致的部分（`validatePojo` 的 `super` + `|` 分组校验、`copyTo` 调 `super.copyTo`、`copy()`、`applyBackCompatibility` 调 `super`）未改动。

`./gradlew compileJava --offline` 通过。一点保留：静态 `toJson(writer, pojo)` 的 `else writer.nullValue()` 在 CGC 内置子类之间本身就不统一（`AmmoIndex`/`AttachmentIndex` 有，`GunIndex` 没有），我保持了与 `GunIndex` 一致。未提交。

# 

```
TacticalCategory、TabGroup都还没补全
```

# 

`TacticalCategory`/`TabGroup` 是空骨架。我按 CGC 的 `GunCategory`/`TabGroup` 模式补全，并让 `ModCreativeTabs` 改用 `TacticalCategory`（与 CGC 用 `*Category` 的写法一致）。

两个类已按 CGC 的对应实现补全：

**[TacticalCategory.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/tactical/TacticalCategory.java)** + **[TacticalCategoryTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/tactical/TacticalCategoryTag.java)**（对照 `GunCategory`/`GunCategoryTag`）
- `MELEE` / `THROWABLE` / `CONSUMABLE`
- 实现 `ResourceTag.CategoryTag`，含 `categoryName`、`categoryLang`（复用已有 `customgun.tacticalcategory.*` 语言键）
- `getCategoryLang()`、`fromString`（支持原名与大写别名）、`toString`

**[TabGroup.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/minecraft/tab/TabGroup.java)**（对照 CGC 的 `TabGroup`）
- 实现 `ResourceTag.RegistryTag`，绑定 `MELEE_TAB` / `THROWABLE_TAB` / `CONSUMABLE_TAB`
- `typeName` / `registryName` / `registryLocation` / `registryObject` 字段
- `fromString(String)`、`fromLocation(ResourceLocation)` 与两个静态查找表

**[ModCreativeTabs.java](core/src/main/java/dev/xcolorful/cgctactical/core/init/registry/ModCreativeTabs.java)**
- 注册名与标题改为 `TacticalCategory.X.getCategoryName()` / `getCategoryLang()`，与 CGC 用 `*Category` 的写法一致（原来直接拼 `ItemType` 与语言键）。

`./gradlew compileJava --offline` 通过。说明：`TabGroup` 目前还没有消费方（等配方/结果系统接入）；`TacticalCategory` 也尚未被 Index 使用（Index 的 category 字段是 `ResourceLocation`，与 CGC 内置子类的处理一致）。未提交。
