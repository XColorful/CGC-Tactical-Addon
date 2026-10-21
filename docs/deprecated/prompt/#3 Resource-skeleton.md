> #3 提示词

# 

```
对照目前CGC对TaCZ的修改，同构完成LR tactical到CGC Tactical Addon：
- TaCZ到CGC迁移映射见./docs-tacz/TaCZ Migration Mapping.md
- CGC包为dev.xcolorful.customgun.core.resource和dev.xcolorful.customgun.client.resource
- CGC Tactical Addon的类名跟CGC相同，如当前git工作区已有的_AllDataManager，结构相同
- ResourcePojo用相同方式，当前ConsumableIndex是已完成的示范
- 对于变量类型里CGC已经有JsonUtils的，使用CGC的JsonUtils（跟CGC用相同方式读写）
- 对于ThrowableDisplay的thrid_person_animation，该字段为tag里的_OLD1
- 对于LR tactical里的字段，如果在TaCZ里也有同名/功能字段且CGC已更正过语义，则CGC Tactical Addon里非_OLD tag直接用新名称；对于上一条提到的thrid_person_animation则直接作为_OLD1，不再把third_person_animation作为_OLD2
- 对于目前没有的ConsumableData，先不实现
- DisplayInstance的实现照搬CGC里DisplayInstance的检查流程、排版、ERROR掩码等；对于有相同变量的，则检查顺序跟CGC DisplayInstance保持一致
- CGC Tactical Addon也使用ResourceApi + ClientResourceApi
- 注意CGC里所有使用ResourceLocation的地方，左值都用了var（1.21.11该类变为Identifier，用于减少移植时的改动）
- CGC Tactical Addon用同样的监听事件方式
- DataFolderType目前用CGC的（目前CGC Tactical Addon无新增文件夹大类）
- 对于ThirdPersonAnimationConfig这个非常规的Resource的config先用Object占位代替，不参与读写，在变量后加"; // TODO"即可，无需更多说明
- 对于所有ResourceLocation getter，CGC里成员变量命名一般都是xxLocation()，如果有像_DataIndex这样直接复用的则不需要单独重复，直接复用CGC的
- 注意_AllAssetsManager挂载的事件优先级为LOW

按以上要求完成dev.xcolorful.cgctactical.core.resource和dev.xcolorful.cgctactical.client.resource
```

# 

I'll start by reading the migration mapping doc and surveying both codebases.

Now let me survey the CGC resource packages and the addon's existing skeleton.

Now let me read the CGC foundational resource classes that the addon mirrors.

Now let me examine the original LR Tactical resource code, which is the source spec.

I've mapped the CGC patterns and the LR original. This is a substantial multi-package port, so I'll track it in tasks and start with the core API tags and data model.

Core and client pipelines are written. Now I'll compile with JDK 17 to catch API mismatches.

Compilation passes. Let me review the full diff for correctness and consistency before reporting.

已完成 `dev.xcolorful.cgctactical.core.resource` 与 `dev.xcolorful.cgctactical.client.resource` 的同构迁移。核心结果：

**核心资源层**
- `MeleeIndex` / `ThrowableIndex`、`MeleeData` / `ThrowableData` 及其嵌套 `ResourcePojo`（`_CombatData`、`_MeleeAttackInfo`、`_TargetFilterData`、`_EntityData`、`_CloudData`、`_ExplodeData`、`_StunData` 等）
- `DataManager` / `IndexManager`、`_AllDataManager`、`_DataInstanceManager`、`SyncDataType`、服务端索引实例、`ResourceApi`
- 标签按 CGC 语义更正：`name_lang`、`tooltip_lang`、`data_location`、`display_index_location`、`slot_sort`；LR 的 `type` → `*_category`、`stack_size` → `max_stack_size`、爆炸参数 → `explode_scale` / `explode_damage` / `enable_world_destruction`

**客户端资源层**
- `ConsumableDisplay` / `MeleeDisplay` / `ThrowableDisplay`、`DisplayManager`、`_AllAssetsManager`、`_AssetsInstanceManager`、`SyncDataCache`、`ClientResourceApi`
- 三个 `*DisplayInstance` 共用 `_AssetsDisplayInstance`，沿用 CGC 的 `resetCache → isPojoValid → logAllErrors` 流程与 ERROR 掩码（模型/动画/脚本缺失分别置位），检查顺序为 model → animation → script
- `third_person_animation` 保持非 `_OLD` 名称；`ThrowableDisplayTag.THIRD_PERSON_ANIMATION_OLD1 = "thrid_person_animation"`，未增加 `_OLD2`
- `ThirdPersonAnimationConfig` 用 `Object` 占位、不参与读写

**事件与同步**
- `_AllDataManager` 注册三个服务端资源事件（NORMAL）；新增 `client.event.custom.ClientEventHandlers`，将 `_AllAssetsManager` 以 `EventPriority.LOW` 注册，保证在 CGC 的模型/动画/脚本管理器之后重载
- 补齐 `S2CMessageSyncDataPack` 的缓存载荷与 `_S2CMessageSyncDataPack.doSync`（异步解析 → 主线程合流 → 重建实例）

**验证**
- `:core:compileJava` 与 `:forge-compat:compileJava` 均通过（含全量 `--rerun-tasks` 重编译）:
```
JAVA_HOME=/e/Software/Minecraft/JDK/jdk-17 GRADLE_OPTS="-Dfile.encoding=UTF-8" ./gradlew :forge-compat:compileJava --console=plain
```
未提交。

**需要你确认/注意的点**
1. `ConsumableData` 按指示未实现；`ConsumableIndex`、consumable display/实例管线已就位。
2. `item_transforms` / `entity_transform` 走 CGC 的 `ClientJsonUtils.readItemTransforms`，该方法目前仍是 CGC 的 TODO stub（返回 `null`、写出为空），因此 `transforms` 暂由 back-compat 填 `Transformation` 单位值；等 CGC 补全后自动生效。
3. 新格式沿用 CGC 的分离数据文件（`data_location`）。原 LR 包把 `data` 内联在 index 中，旧包不会自动拆分——这属于后续 legacy-pack 兼容工作。
4. CGC 的 `INetworkCacheReloadListener.getSyncDataType()` 返回 CGC 自己的 `SyncDataType`，无法与扩展模组的枚举兼容，因此 addon 新增了同名的本地接口，并在 `DataManager`/`IndexManager` 中覆写 `onWriteNetworkCache()` 以照常生成网络缓存。
5. 新引入的平台 API 供你评估是否需要兼容封装：`AttributeModifier.Operation`（1.21 起结构变化最大）、`com.mojang.math.Transformation`、`Mth.DEG_TO_RAD`、`org.joml.Vector3f/Quaternionf`。
6. 本仓库源码含中文注释，shell 下 Gradle 默认按 windows-1252 编译会报编码错误；我仅通过 `GRADLE_OPTS=-Dfile.encoding=UTF-8` 验证，未改动构建文件。未做实际游戏内加载测试。

# 

```
- 看样子ConsumableData这种是被藏到了不正确的位置导致我误判，ConsumableData还是要补上
- 在CGC Tactical Addon的./docs-lrtactical/Migration Mapping.md里需要像CGC那样给Resource补上映射，其中对于me.xjqsh.lrtactical.item.throwable.ThrowableData这样的，在Mapping里仍然放在原始包### Item而不是Resource下
- 对于下划线（Pojo内部类型）则跟CGC一样基本只需要精确到包即可
```

# 

I'll first check the new CGC interface the addon now references, then read the original ConsumableData.

CGC now exposes a public `INetworkCacheManager`, so the addon interface can reuse the base's cache writing. I'll drop the temporary override, add `ConsumableData` end-to-end, and then update the mapping doc.

ConsumableData is wired and compiles. Now I'll update the migration mapping document.

已按反馈补齐：

**ConsumableData 全链路**
- 新增 [ConsumableData.java](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/ConsumableData.java)：`use_duration`、`cooldown`、`cooldown_category`、`max_stack_size`（OLD1 `stack_size`）、`max_durability`、`durability_damage`、`draw_time`、`put_away_time`、`heal`、`food`、`saturation`、`effects`、`remove_effects`、`use_mode`
- 内部 Pojo：`consumable._EffectData`（`effect_location`，OLD1 `id`）、`consumable._RemoveEffectSelector`（支持 `@beneficial` / `@harmful` / `@neutral` 与效果 id）
- 接入 `DataManager.ConsumableDataManager`、`DataSubFolderType.CONSUMABLE`、`SyncDataType.CONSUMABLE_DATA`、`_AllDataManager`、`ResourceApi`、`SyncDataCache`、服务端/客户端 `ConsumableIndexInstance`
- 顺带把 throwable 的 `_EffectData` 标签统一为 `effect_location`（OLD1 `type`），与 CGC 的 `_TargetEffectData` 命名一致

**Mapping 文档**
[Mapping Mapping.md](docs-lrtactical/Migration%20Mapping.md) 新增：
- `### Item`：索引/数据类全部放在原始 `me.xjqsh.lrtactical.item` 包下，包括 `throwable.ThrowableData`；`ConsumableData` 的内部类型、`melee.*`、`throwable.*` 等下划线 Pojo 只精确到目标包
- `### Resource`：三个 IndexManager、`CommonAssetsManager`、`CommonNetworkCache`、序列化器、`ICommonResourceProvider`，以及 `api.collision` → `data.data.melee`
- `## Client` → `### Resource (Client)`：三个 DisplayManager、DisplayInstance/Display 的拆分映射、`LrClientAssetsManager` → `_AllAssetsManager` / `ClientResourceApi`

**验证**
- `:forge-compat:compileJava --rerun-tasks` 通过（JDK 17 + `-Dfile.encoding=UTF-8`）
- 新的 `INetworkCacheManager` 已生效，不再需要 `onWriteNetworkCache` 覆写；`INetworkCacheReloadListener` 保持你更新后的结构

新增平台 API 一项：`MobEffectCategory`（`_RemoveEffectSelector` 的分类判定），需要的话可后续换成兼容封装。未提交。

# 

```
- 保留_AttributeData注释，去掉剩余_AttributeData attributes字段读写
- 保留_RemoveEffectSelector注释，目前_RemoveEffectSelector不符合ResourcePojo读写规范
- 保留ConsumableData注释，去掉剩余ConsumableData UseMode读写
- _AttributeInfo不符合ResourcePojo规范
- _MeleeAttackInfo包路径不符合规范，应放在dev.xcolorful.cgctactical.core.resource.data.data.melee.combat（属于_CombatData内部）；_EffectData同理；_BlindData、_DeafenedData同理
- 保留_AssetsDisplay注释，去掉itemTransforms字段读写；ThrowableDisplay同理
- 检查是否还有残留的引入的平台API
```

# 

I'll inspect the current on-disk state of the files you edited, then apply the removals and package moves consistently.

已按你的标注完成清理与移动，编译通过。

**按注释保留并移除读写**
- [_AttributeData](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/melee/_AttributeData.java)：保留 `// private List<_AttributeInfo> attributes; // TODO`，不再解析/写出 attributes，空对象跳过
- 删除 `_AttributeInfo` 与 `_AttributeInfoTag`（不再被引用，且不符合 ResourcePojo 规范）
- [_RemoveEffectSelector](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/consumable/_RemoveEffectSelector.java)：保留 `@beneficial/@harmful/@neutral` 与 category 的 TODO 注释，移除不符合规范的字符串读写与 `MobEffectCategory`；`ConsumableData.remove_effects` 不再读写（字段保留为空占位）
- [ConsumableData](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/ConsumableData.java)：保留 `// private UseMode useMode; // TODO`，移除 use_mode 的读写、校验、getter/setter 与默认值（`UseMode` 枚举保留）
- [_AssetsDisplay](core/src/main/java/dev/xcolorful/cgctactical/client/resource/assets/display/_AssetsDisplay.java)：保留 `// private Transformation itemTransforms; // TODO`，移除 item_transforms 的读写、校验、默认值与 getter/setter；`ConsumableDisplay`、`MeleeDisplay` 同步去掉读写
- [ThrowableDisplay](core/src/main/java/dev/xcolorful/cgctactical/client/resource/assets/display/ThrowableDisplay.java)：保留 `// private Transformation entityTransform; // TODO`，移除 entity_transform 的读写、默认常量与 getter/setter；`ThrowableDisplayInstance` 的对应 getter 一并移除

**包路径调整（内部 Pojo 下沉）**
- `_MeleeAttackInfo`、`_MeleeMovement` → `core.resource.data.data.melee.combat`
- 对应标签 `_MeleeAttackInfoTag`、`_MeleeMovementTag` → `core.api.resource.data.data.melee.combat`
- throwable `_EffectData` → `core.resource.data.data.throwable.cloud`，标签 → `core.api.resource.data.data.throwable.cloud`
- `_BlindData`、`_DeafenedData` → `core.resource.data.data.throwable.stun`，标签 → `core.api.resource.data.data.throwable.stun`
- `_CombatData`、`_CloudData`、`_StunData` 的引用已同步更新

**平台 API 残留检查**
- 代码中已无 `AttributeModifier`、`MobEffectCategory`、`Transformation`、`Mth` 的实际引用，仅剩 TODO 注释
- 仍保留的均为 CGC 既有模式或不可避免项：`ResourceLocation`、`PackType`/`PreparableReloadListener`（CGC 管理器基类要求）、`MinecraftServer`/`PackRepository`（`reloadAllPack` 照搬 CGC）、`Connection`/`FriendlyByteBuf`（网络消息）、`Vector3f`（display offset，CGC 同样使用 joml）

验证：`:forge-compat:compileJava` 成功（JDK 17 + UTF-8），未提交。

# 

```
Pojo下沉的方式不符合要求，参考CGC GunData里pojo.fireSoundData = JsonUtils.read(reader, _FireSoundData::fromJson);这种写法
```

# 

I'll revert the extra subpackage sinking so nested pojos sit in the same package as their parent (like `_FireSoundData` in `data.data.gun`) and are read inline.

已按 CGC `GunData` → `_FireSoundData` 的方式调整：嵌套 Pojo 与其父 Pojo 放在同一个分类包内，并继续通过 `JsonUtils.read(reader, X::fromJson)` 内联读取。

**调整结果**
- `_MeleeAttackInfo`、`_MeleeMovement` → 回到 `core.resource.data.data.melee`
- `_MeleeAttackInfoTag`、`_MeleeMovementTag` → 回到 `core.api.resource.data.data.melee`
- throwable `_EffectData` → 回到 `core.resource.data.data.throwable`
- `_BlindData`、`_DeafenedData` → 回到 `core.resource.data.data.throwable`
- 对应标签回到 `core.api.resource.data.data.throwable`
- 删除了空的 `melee.combat`、`throwable.cloud`、`throwable.stun` 目录
- `_CombatData`、`_CloudData`、`_StunData` 中的跨包 import 已移除，恢复为同包直接引用

现在 `_CombatData`、`_CloudData`、`_StunData` 的字段仍分别是 `JsonUtils.readList(..., _MeleeAttackInfo::fromJson)`、`JsonUtils.readList(..., _EffectData::fromJson)`、`JsonUtils.read(..., _BlindData::fromJson)` 等写法，与 `GunData` 的嵌套 Pojo 读取方式一致。

验证：`:forge-compat:compileJava --rerun-tasks` 通过，无残留的 `melee.combat` / `throwable.cloud` / `throwable.stun` 引用。若你之前要求的 `melee.combat` 是有意保留的，请告诉我，我再按那个结构单独调整。

# 

```
- GunData读的_FireSoundData本身就跟GunData不在一个包，撤回你私自执行的“嵌套 Pojo 与其父 Pojo 放在同一个分类包内”
- 向量/Vec3的读取方式改成ResourcePojo用float[]，参考CGC _ShellEjectionParam的JsonUtils.readFloatArrayFast(reader, 3);，不自动转换成Vec3
```

# 

已按两点修正：

**1. 恢复嵌套 Pojo 的子包**
`_FireSoundData` 确实在 `data.data.gun`，相对 `GunData`（`data.data`）是子包，所以撤回了我错误执行的“同包”调整：
- `_MeleeAttackInfo`、`_MeleeMovement` → `core.resource.data.data.melee.combat`
- `_EffectData` → `core.resource.data.data.throwable.cloud`
- `_BlindData`、`_DeafenedData` → `core.resource.data.data.throwable.stun`
- 对应标签同步回到 `core.api.resource.data.data.melee.combat`、`throwable.cloud`、`throwable.stun`
- `_CombatData`、`_CloudData`、`_StunData` 的 import 已恢复为跨子包引用
- Mapping 文档补充了这些子包对应的行

**2. 向量改为 float[]，不再转 Vec3**
参考 `_ShellEjectionParam`：
- `_AssetsDisplay.displayOffset` 改为 `float[]`
- 读取：`JsonUtils.readFloatArrayFast(reader, 3)`
- 写出：`JsonUtils.writeFloatArray(writer, ..., this.getDisplayOffset())`
- 删除 `readDisplayOffset` / `writeDisplayOffset` 与 `org.joml.Vector3f`，实例 getter 也改为 `float[]`
- back-compat 默认值为 `new float[]{0f, 0f, 0f}`

验证：`:forge-compat:compileJava --rerun-tasks` 通过；资源包内已无 `Vector3f`、`Transformation`、`AttributeModifier`、`MobEffectCategory`、`Mth` 的实际引用。未提交。

# 
---

```
检查当前git工作区的修改里，所有ResourcePojo是否有引入自定义枚举或非基本类型（Minecraft类型）；如果有特殊类型，检查CGC的resource里是否有使用过，有使用则视为无需兼容的类型
```

# 

我先查看当前工作区的改动文件和 ResourcePojo 相关定义。

工作区里有一批 resource 相关的新文件。我先定位所有 ResourcePojo 类。

输出较大，我改用更精准的方式：先找出所有 `extends ResourcePojo` 的类，再检查它们的字段类型与 import。

现在核对 CGC 侧 resource 包是否使用过这些特殊类型。

CGC 侧确认 ResourceLocation、float[]、Map、Object 都有使用。再核对 sounds 字段与枚举字段的具体用法。

检查完成。当前工作区共有 18 个 ResourcePojo 类（含 3 个 Index 与 1 个 Display 基类），其中引入的非基本类型只有 `ResourceLocation`、`float[]`、`Map`/`List`、嵌套 Pojo 和一个 `Object` 占位，**没有自定义枚举字段**。

## 特殊类型清单

| ResourcePojo | 特殊类型字段 | 位置 |
|---|---|---|
| `_AssetsDisplay`（三个 Display 的基类） | `ResourceLocation` ×5、`float[]`、`Map<String, ResourceLocation>`、`Object`（TODO） | [_AssetsDisplay.java:17](core/src/main/java/dev/xcolorful/cgctactical/client/resource/assets/display/_AssetsDisplay.java:17) |
| `_EntityData` | `ResourceLocation tailParticlesLocation` | [_EntityData.java:23](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/throwable/_EntityData.java:23) |
| `_CloudData` | `ResourceLocation particlesLocation`、`List<_EffectData>` | [_CloudData.java:24](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/throwable/_CloudData.java:24) |
| `cloud/_EffectData`、`consumable/_EffectData` | `ResourceLocation effectLocation` | [cloud/_EffectData.java:15](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/throwable/cloud/_EffectData.java:15) |
| `ConsumableData` | `ResourceLocation cooldownCategoryLocation`、`List<_EffectData>` | [ConsumableData.java:20](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/ConsumableData.java:20) |
| `ThrowableData` | `ResourceLocation cooldownCategoryLocation` | [ThrowableData.java:23](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/ThrowableData.java:23) |
| `MeleeIndex` / `ThrowableIndex` | `ResourceLocation` ×2 | [MeleeIndex.java:15](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/MeleeIndex.java:15) |
| `ConsumableIndex` | 无自有字段，继承 CGC `_DataIndex` | [ConsumableIndex.java:11](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/index/ConsumableIndex.java:11) |

其余 Pojo（`_CombatData`、`_AttributeData`、`_TargetFilterData`、`_MeleeAttackInfo`、`_MeleeMovement`、`_ExplodeData`、`_StunData`、`_BlindData`、`_DeafenedData`、`MeleeData`）均为基本类型或嵌套 Pojo。`_TargetFilterData.type` 目前是 `String` 占位（[第 13 行](core/src/main/java/dev/xcolorful/cgctactical/core/resource/data/data/melee/_TargetFilterData.java:13)），不是枚举。

## CGC 对照结果

- **`ResourceLocation`**：CGC resource 大量使用（`_DataIndex`、`GunIndex`、`GunDisplay`、`AttachmentDisplay` 等）→ 无需兼容。
- **`float[]`**：CGC 同样使用（`_ShellEjectionParam`、`_RecoilEntryData`、`_ModelTransformScale` 等）→ 无需兼容。
- **`List` / `Map`**：CGC 普遍使用；`Map<..., ResourceLocation>` 的先例是 `Map<GunSoundType, ResourceLocation>`、`Map<AttachmentSoundType, ResourceLocation>` → 无需兼容。唯一差异是 addon 的 sounds 用 `String` 作 key，而 CGC 用自定义枚举作 key，但 String 本身不是平台类型。
- **`Object`**：CGC 有 `Map<String, Object>`（`GunData`）与 `Map<AttachmentModifierType, Object>`（网络缓存），但没有直接的 `Object` 字段。当前 `thirdPersonAnimation` 是 TODO 占位、不参与读写，暂不构成序列化类型；后续若换成真实 `ThirdPersonAnimationConfig` 需重新评估。
- **自定义枚举**：addon 的 Pojo 里目前没有枚举字段（`UseMode` 只剩注释）。CGC 的 Pojo 确实以自定义枚举作字段（`BoltType`、`FireModeType`、`MeleeType`、`ChargeType` 等），所以将来补 `UseMode`、`_TargetFilterData.type` 时沿用枚举不会有兼容问题。

## 其他附带发现

- `net.minecraft` 平台类型在 Pojo 中只剩 `ResourceLocation`，此前的 `AttributeModifier`、`MobEffectCategory`、`Transformation`、`Vector3f` 均已清除。
- 不在 Pojo 范围内：新增的 [DataFolderType.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/resource/data/DataFolderType.java) 是空枚举且未被引用，实际使用的是 CGC 的同名枚举。若非有意占位，属于死代码。

结论：**没有需要额外做兼容封装的 ResourcePojo 字段类型**。

# 

```
现在浏览CGC Tactical Addon的git工作区，按照CGC ./docs/wiki相同的格式，对Addon现有Pojo的格式补全wiki
- 对于目前注释掉的，则不需写进wiki
- wiki里data/index/display具体格式展示文件里的换行跟ResourcePojo类里保持一致，参考CGC wiki对应CGC的ResourcePojo子类
```

# 

我先查看 CGC 的 wiki 结构和现有 Pojo 文档格式。

Addon 的 wiki 数据页目前是空占位。我先读 CGC 的对应 wiki 页面和 Addon 的 Pojo 实现，确定模板与字段顺序。

现在读取标签常量，确认每个字段的实际 JSON 键名与旧键名。

CGC wiki 的结构和换行分组规则已确认（`---` 与 JSON 空行对应 `toJson` 中的空行）。现在写入 9 个页面并更新导航。

现在更新 Resource introduction 与 Sidebar，把新页面接入导航。

页面已写入。现在做一致性校验：解析每个 JSON 示例，核对键顺序是否与对应 Pojo 的 `toJson` 写入顺序一致，并确认没有把已注释字段写进 wiki。

已按 CGC `docs/wiki` 的格式补全 Addon 的 Pojo wiki，并接入导航。

## 新增/补全的页面

**数据（cgc_data）**
- [Consumable data.md](docs/wiki/resource/data/cgc_data/Consumable%20data.md)
- [Melee data.md](docs/wiki/resource/data/cgc_data/Melee%20data.md)
- [Throwable data.md](docs/wiki/resource/data/cgc_data/Throwable%20data.md)

**索引（cgc_index）**
- [Consumable index.md](docs/wiki/resource/data/cgc_index/Consumable%20index.md)
- [Melee index.md](docs/wiki/resource/data/cgc_index/Melee%20index.md)
- [Throwable index.md](docs/wiki/resource/data/cgc_index/Throwable%20index.md)

**显示（cgc_display）**
- [Consumable display.md](docs/wiki/resource/assets/cgc_display/Consumable%20display.md)
- [Melee display.md](docs/wiki/resource/assets/cgc_display/Melee%20display.md)
- [Throwable display.md](docs/wiki/resource/assets/cgc_display/Throwable%20display.md)

## 格式遵循情况

- 每个页面均采用 CGC 的双语结构：`[English](#English)` → 中文标题 + `> wiki 版本` + 字段列表 + JSON 示例 → `# English` 镜像。
- 字段列表格式为 `` - `新键`/`旧键`（类型）： ``，旧键仅标注实际存在 `OLD1` 常量的字段。
- `---` 分区与 JSON 空行严格对应 `toJson` 中的空行：目前只有 `ThrowableData`（`put_away_time` 后）和 `MeleeIndex`/`ThrowableIndex`（`slot_sort` 后）有分区，其余类无空行故不分区。
- 嵌套 Pojo 按 `toJson` 写入顺序展开；`_AttributeData` 以空对象 `{}` 表示。
- 已注释字段未写入 wiki：`item_transforms`、`entity_transform`、`remove_effects`、`use_mode`、`third_person_animation` 均确认不存在于 wiki。

## 新增平台资源目录说明与导航

- [Resource introduction.md](docs/wiki/resource/Resource%20introduction.md) 补充了 `cgc_display/{consumable,melee,throwable}` 资源包目录树和六个数据/索引/显示链接（中英双语）。
- [_Sidebar.md](docs/wiki/_Sidebar.md) 增加 `📊data`、`🖼️assets` 及全部 9 个页面的中英文链接。

## 校验

- 所有 18 个 JSON 示例（每页中英文各一）均可被 `json.loads` 解析。
- 脚本比对确认：字段列表中的当前键集合与 JSON 键集合完全一致（唯一差异是 `sounds` 示例中的空字符串 map key，属预期占位）。
- 顶层与嵌套键顺序均与对应 `toJson` 的写入顺序一致。

未执行提交；`docs/wiki/Home.md` 的既有改动不是本次产生的，未触碰。

# 

```
- 本次git工作区新增文件的wiki版本为`0.0.0`.0
- 示例里不要写ResourcePojo里的默认值，参考CGC wiki里都写了0，其他类型的值也需要检查
```

# 

收到。版本统一改为 `0.0.0`.0，示例值按 CGC 的惯例全部置零（int/long→0、float→0.0、boolean→false、String→""、ResourceLocation→`namespace:location`、float[]→全 0、List 保留一个全 0 元素）。

已按要求调整：

- 9 个新增页面的版本全部改为 `0.0.0`.0（中英文两处）。
- 示例值不再使用 Pojo 默认值，统一按 CGC wiki 惯例置零：
    - `int`/`long` → `0`
    - `float` → `0.0`
    - `boolean` → `false`
    - `String` → `""`
    - `ResourceLocation` → `"namespace:location"`
    - `float[]` → `[0.0, 0.0, 0.0]`
    - `List` → 保留一个全零元素
    - `_AttributeData` → `{}`

校验结果：
- 所有 JSON 示例可解析。
- 递归检查所有值：非零数字、`true`、非空且非 `namespace:` 的字符串数量为 **0**。
- 字段列表与 JSON 键集合仍完全一致（仅 `sounds` 的空字符串 map key 属预期）。

补充：`docs/wiki/Home.md` 仍是 `0.0.0`.1，但它是工作区中已有的改动而非本次新增页面，未改动；如需一并改为 `.0` 请告诉我。

# 

```
浏览CGC的wiki仓库和CGC Tactical Addon的wiki仓库
- 注意wiki里使用的链接是不含文件夹层级的，因此.wiki仓库的文件直接位于根目录
- 且./docs/wiki里带空格的文件名，CGC wiki里是用横杠代替的（实际github wiki创建相应名称时会是这个结果）
- 对CGC Tactical Addon刚才的commit，在.wiki仓库里应用同样的修改
```

# 

我先核对 Addon 的提交状态、两个 wiki 仓库的现状，以及 CGC 的 `docs/wiki` 到 `.wiki` 的文件映射规律。

提交 `319aa65` 包含 12 个 wiki 文件改动。CGC 的 `docs/wiki` 与 `.wiki` 内容完全一致，仅路径/文件名平铺加连字符。我按同一映射复制到 Addon 的 `.wiki` 仓库。

已将提交 `319aa65` 中的 12 个 wiki 文件按 CGC 的映射规则同步到 `.wiki` 仓库。

## 映射与结果

| `docs/wiki` 源文件 | `.wiki` 目标文件 |
|---|---|
| `Home.md` | `Home.md`（更新） |
| `_Sidebar.md` | `_Sidebar.md`（更新） |
| `resource/Resource introduction.md` | `Resource-introduction.md`（新增） |
| `resource/data/cgc_data/Consumable data.md` | `Consumable-data.md`（新增） |
| `resource/data/cgc_data/Melee data.md` | `Melee-data.md`（新增） |
| `resource/data/cgc_data/Throwable data.md` | `Throwable-data.md`（新增） |
| `resource/data/cgc_index/Consumable index.md` | `Consumable-index.md`（新增） |
| `resource/data/cgc_index/Melee index.md` | `Melee-index.md`（新增） |
| `resource/data/cgc_index/Throwable index.md` | `Throwable-index.md`（新增） |
| `resource/assets/cgc_display/Consumable display.md` | `Consumable-display.md`（新增） |
| `resource/assets/cgc_display/Melee display.md` | `Melee-display.md`（新增） |
| `resource/assets/cgc_display/Throwable display.md` | `Throwable-display.md`（新增） |

## 验证

- 12 个目标文件与 `docs/wiki` 源文件逐一 `diff` 完全一致。
- `.wiki` 仓库保持扁平结构，无新增文件夹；空格文件名均已替换为横杠。
- `git status` 显示 2 个修改、10 个新增，与提交 `319aa65` 的 wiki 部分一致。

未在 `.wiki` 仓库执行提交，改动停留在工作区，等待你审阅。
