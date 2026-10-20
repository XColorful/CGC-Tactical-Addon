> #2 提示词

# 

```
按照dev.xcolorful.cgctactical.core.network.message.combatant.C2SMessageCombatantMelee_attack已有的stub模式把dev.xcolorful.cgctactical.core.network.message剩下的Message类都填充
- C2SMessageCombatantConsumable_cancel已经完成，不用管
- 并按照dev.xcolorful.customgun.core.network.NetworkHandler相同的注册模式和me.xjqsh.lrtactical.network.NetworkHandler里的注册顺序及空行，补充dev.xcolorful.cgctactical.core.network.NetworkHandler#registerMessages
- 你的修改应只涉及dev.xcolorful.cgctactical.core.network.message下的类
```

# 

I'll start by examining the existing stub pattern and the reference implementations.

The prompt doc for this task exists in the repo. Let me read it and the reference message implementations.

Now I have the full picture: this is the skeleton branch — lrtactical counterparts carry real payloads, but the CGC stubs are fieldless `IMessage` skeletons, matching `C2SMessageCombatantMelee_attack`. Filling all 10 remaining classes.

Now the registration list, following lrtactical's order/blank-line grouping and customgun's call pattern.

Signatures match. Building the project to verify.

Build passes (only a pre-existing deprecation warning in forge-compat, untouched). Verifying the final registration file.

Done. Build passes.

**Message classes filled** (10 files, all under `core.network.message`) — each now implements `IMessage<T>` with the same skeleton as `C2SMessageCombatantMelee_attack`: empty `encode`, static `decode` returning a new instance, empty `handle`:

- `combatant/` — `C2SMessageCombatantMelee_prepare`, `S2CMessageCombatantItemCooldown`, `S2CMessageCombatantMelee_reset` (kept its existing javadoc), `S2CMessageCombatantShield_block`, `S2CMessageCombatantShield_disable`
- `resource/` — `S2CMessageResourceSound`, `S2CMessageSyncDataPack`
- `sync/` — `S2CMessageSyncMeleeAnimation`
- `world/` — `S2CMessageNearbyExplosion`, `S2CMessageSplashParticle`

**Registration** ([NetworkHandler.java:38](app://localhost/epitaxy/core/src/main/java/dev/xcolorful/cgctactical/core/network/NetworkHandler.java:38)) — filled using customgun's `adapter.registerMessage(ID_COUNT.getAndIncrement(), X.class, X::decode, DIRECTION)` form, ordered per lrtactical's `init()` and matching its blank-line grouping (3 S2C → 2 C2S → 3 S2C → 1 S2C → 2 S2C + 1 C2S). Directions per class prefix; `C2SMessageCombatantConsumable_cancel` left untouched as instructed.

One judgment call to flag: I kept the stubs fieldless, exactly mirroring `C2SMessageCombatantMelee_attack` — even where the lrtactical counterpart carries a payload (e.g. `CMeleeAttackRequest` → action/target IDs, `SSplashParticle` → blockPos/color). That fits the "skeleton" branch, but if you wanted payload fields declared now, say so and I'll add them.

# 
---

```
现在CGC更新到了26.10.28，CGC Tactical Addon的dev.xcolorful.cgctactical.core.network.message包下现有IMessage类都需要改成不带T message的接口实现
- 批量去掉CGC Tactical Addon里所有T message参数
```

# 

我先查看 CGC 新版的 `IMessage` 接口定义和 Addon 中现有的实现。

目标风格已确认，现在批量修改这 12 个文件。

代码修改完成，现在检查 Addon 对 CGC 版本依赖是否已指向 26.10.28。

完成。12 个 `IMessage` 实现类已全部改为无参重载，`core` 模块编译通过（`BUILD SUCCESSFUL`，使用的是 `libs/customgun-26.10.28-forge-1.20.1.jar`，`javap` 确认该 jar 中 `IMessage` 已提供 `encode(FriendlyByteBuf)` / `handle(Consumer, NetworkContext)`）。

改动内容（每个文件相同，各 2 处）：

```java
// 之前
public void encode(XxxMessage message, FriendlyByteBuf buffer)
public void handle(XxxMessage message, Consumer<Runnable> handler, NetworkContext context)
// 之后
public void encode(FriendlyByteBuf buffer)
public void handle(Consumer<Runnable> handler, NetworkContext context)
```

`implements IMessage<XxxMessage>` 的泛型参数保留不变，与原版 CGC 实现（如 [S2CMessageShooterBaseTimestamp.java:10](core/src/main/java/dev/xcolorful/customgun/core/network/message/shooter/S2CMessageShooterBaseTimestamp.java:10)）风格一致。已确认 Addon 内没有任何地方以旧签名调用 `encode`/`handle`。

唯一带逻辑的 `C2SMessageCombatantConsumable_cancel.handle` 未引用 `message` 参数，删除后行为不变。

另外说明：编译输出里的 `unmappable character for encoding windows-1252` 来自既有中文注释（`SyncDataType.java`、`SendUtils.java` 等未改动文件同样出现），属既有问题，与本次改动无关。未提交。
