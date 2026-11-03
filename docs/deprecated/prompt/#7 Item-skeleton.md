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
