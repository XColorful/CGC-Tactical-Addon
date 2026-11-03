> #6 提示词

# 

```
按照dev.xcolorful.customgun.core.api.gun相同模式，把dev.xcolorful.cgctactical.core.api.melee下的文件补全，类别仅attach和physics
```

# 

I'll start by examining the gun API package to understand the pattern, then the existing melee files.

Now let me check the melee item interface and docs for the naming/manager-tag pattern.

The prompt is already recorded in a docs file — let me read it for context on how this was scoped before.

Pattern is clear. I'll mirror the gun structure: sub-manager, manager group, main/manager interfaces, and per-category manager + package-info for **attack** and **physics** (existing melee packages — reading "attach" as "attack").

Now verifying the build through the project's IDEA MCP.

已按 `dev.xcolorful.customgun.core.api.gun` 的结构补全 melee 包，类别为已有的 **attack** 和 **physics**（把“attach”按笔误理解为 attack，因为 gun 中没有 attach 类别，且 `IMeleeRuntime` 也只聚合这两个）。

新增文件：
- [IMeleeSubManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/IMeleeSubManager.java) — 对应 `IGunSubManager`
- [MeleeManagerGroup.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/MeleeManagerGroup.java) — 对应 `GunManagerGroup`，含 `managerGroupTag`
- [IMeleeAttackManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/attack/IMeleeAttackManager.java) / [IMeleePhysicsManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/IMeleePhysicsManager.java)
- [attack/package-info.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/attack/package-info.java) / [physics/package-info.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/package-info.java)

修改文件：
- [IMeleeMainManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/IMeleeMainManager.java) — 继承 `IMeleeSubManager`，加入 `registerRuntimeGroup` / `getManagerGroup(String)`
- [IMeleeManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/IMeleeManager.java) — 继承 `IMeleeMainManager`

两点说明：
1. gun 的按物品便利方法（`getGunAttackManager(IGun, ItemStack)`）依赖 `IGunDataAccess#getManagerGroupTag(ItemStack)`，但 CGC 的 `IMeleeDataAccess` 没有对应方法，所以暂未添加这些便利方法。补上它需要改动 melee 包之外的 `IMeleeDataAccess`，因此留给你决定。
2. `package-info.java` 的译名用了“近战攻击 / 近战物理”，若文档已有既定译法请告知。

验证：IDEA MCP 构建通过，仅有骨架阶段预期的 `never used` 警告（以及原有的 `FMLJavaModLoadingContext.get()` 弃用警告）。未提交，等你看过再决定。

# 

```
补上
```

# 

I'll add the manager-group tag access to the melee item API and restore the gun-style convenience methods.

已补上，完全对应 gun 的写法：

- [IMeleeDataAccess.java:20](core/src/main/java/dev/xcolorful/cgctactical/core/api/item/melee/IMeleeDataAccess.java:20)：新增 `@Nullable String getManagerGroupTag(ItemStack)` / `void setManagerGroupTag(ItemStack, String)`（对应 `IGunDataAccess`；CGC 目前没有 `MeleeDataAccessor` 之类的实现类，所以按骨架阶段只声明抽象方法）
- [IMeleeMainManager.java:17](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/IMeleeMainManager.java:17)：新增按物品的便利方法 `getManagerGroup(IMelee, ItemStack)`、`getMeleeAttackManager(IMelee, ItemStack)`、`getMeleePhysicsManager(IMelee, ItemStack)`

验证：IDEA MCP 构建通过。仅剩骨架预期内的 `never used` 警告，以及 `IMeleeDataAccess` 中原本就有的两个 `livingCombatant` 未使用参数警告。未提交，等你审阅。

# 

```
跟Term translation.md一样补一下新增译名
```

# 

已在 [Term Translation.md](docs/Term%20Translation.md) 新增 `### Melee` 节，格式与 gun 仓库的文档一致：

- Melee Manager Group：近战管理器组 → `{@link ...melee.MeleeManagerGroup}`
- Melee Attack：近战攻击 → `@see ...melee.attack`
- Melee Physics：近战物理 → `@see ...melee.physics`

这三条正好对应本次新增代码里出现的译名注释（`MeleeManagerGroup` 与两个 `package-info.java`）。仅文档改动，未触发构建；未提交。

# 

```
照dev.xcolorful.customgun.core.gun补全dev.xcolorful.cgctactical.core.melee文件
并补上docs（见git工作区）
```

# 

I'll first look at the gun implementation package and what's new in the docs working tree.

Now implementing the manager classes. First I'll read the target files and the runtime interfaces (they contain a wrong `javax.swing` `Entity` import that blocks a correct implementation).

Code compiles. Now the docs — let me check the gun English API-index wording to mirror, then write the melee pages.

已按 `dev.xcolorful.customgun.core.gun` 补全 `core.melee` 并完成文档。

**代码**
- [MeleeManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/melee/MeleeManager.java)：照 `GunManager` 写成单例 + `defaultGroup`（Attack/Physics）+ `managerGroups` 注册表，实现 `registerRuntimeGroup` / `getManagerGroup`（未命中回落默认组）
- [MeleeAttackManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/melee/attack/MeleeAttackManager.java)：单例、`getManagerName`，实现两个运行时方法；**逻辑仍是 TODO**，`combatantAttack` 返回 `ERROR`、`meleeAttack` 返回 `0`
- [MeleePhysicsManager.java](core/src/main/java/dev/xcolorful/cgctactical/core/melee/physics/MeleePhysicsManager.java)：`IMeleePhysicsRuntime` 当前只有 deprecated 的 default 方法，所以仅单例与命名
- 修正了 [IMeleeAttackRuntime.java:9](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/attack/IMeleeAttackRuntime.java:9) 和 [IMeleePhysicsRuntime.java:4](core/src/main/java/dev/xcolorful/cgctactical/core/api/melee/physics/IMeleePhysicsRuntime.java:4) 中误导入的 `javax.swing.text.html.parser.Entity`（否则实现类的方法签名无法对上），改为 `net.minecraft.world.entity.Entity`

**文档**
- 新增 [melee-framework.md](docs/architecture/core/melee/melee-framework.md)、[melee-manager.md](docs/architecture/core/melee/melee-manager.md)（含 mermaid 与中英双语，照 gun 对应文档）
- 新增 `melee/attack/melee-attack-manager.md`、`melee/physics/melee-physics-manager.md`、`melee/register-melee-manager-group.md`（与 gun 同名文档一样为占位空文件）
- 新增 `docs/api/core/melee/` 下 7 个接口文档（照 gun 的代码片段格式）
- 补全 [Home.md](docs/architecture/core/Home.md)（中文节的 Attack/Physics 与深度定制链接、新增英文 Melee 节）与 [api-index.md](docs/architecture/core/api/api-index.md) 的中英近战条目

**验证**：IDEA MCP 构建通过；lint 只剩骨架预期的未使用警告。未提交，等你审阅。
