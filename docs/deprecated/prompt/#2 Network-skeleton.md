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

# 

```
检查一下CGC里所有message的CustomGun.getSideExecutor().getLogicalSide()是否都该换为NetworkContext.direction.()？如果有必要则修改
```

# 

我先查看 `getLogicalSide()` 在消息处理中的所有用法、`NetworkContext.direction` 的含义，以及 SideExecutor 的实现。

TACZ 原版用的是 `context.getDirection().getReceptionSide()`，而 CGC 的 `NetworkContext.direction` 正是这个语义的抽象。我先通读所有涉及文件确认用法一致。

全部 32 处都位于 `handle` 入口、只有"在接收側才执行"的语义，与 `direction` 完全对应，且 TACZ 原版正是用 `context.getDirection()`。我将统一替换。

修改完成，现在编译验证。

已全部替换并编译通过。

## 结论：是，32 处都该换

原因：`handle(...)` 运行在网络线程（Forge `SimpleChannel` 的 consumer），此时 `getLogicalSide()` 依赖 `EffectiveSide`（按线程组名推断）加 dist 兜底来猜当前逻辑侧；而 `context.direction()` 由 `NetworkEvent.Context.getDirection()` 直接给出，是权威值。TACZ 原版的写法也正是 `context.getDirection().getReceptionSide()`（如 [ServerMessageSound.java:57](../TACZ/src/main/java/com/tacz/guns/network/message/ServerMessageSound.java:57)）。

## 改动

32 个消息类，每处形如：

```java
// S2C
if (CustomGun.getSideExecutor().getLogicalSide().isClient()) {
→ if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
// C2S
if (CustomGun.getSideExecutor().getLogicalSide().isServer()) {
→ if (context.direction() == MessageDirection.CLIENT_TO_SERVER) {
```

- 补上 `dev.xcolorful.customgun.core.api.network.MessageDirection` 导入。
- 仅用于该判断的 `CustomGun` 导入一并删除；仍用 `CustomGun.LOGGER` / `executeOn(...)` 的文件（`S2CMessageGunLevelUp`、`S2CMessageGunFire`、`S2CMessagePlayerCraft`、`S2CMessageResourceSound`、`S2CMessageSyncDataPack`、`S2CMessageShooterRefit_refresh`、`C2SMessageShooterShoot`、`C2SMessageShooterRefit_install`、`C2SMessageShooterBaseTimestamp`）保留。
- 判断位置、`handler.accept(...)` 调度、其余逻辑均未变。

未改动的是非消息场景的 `getLogicalSide()`（`GunAttackManager`、`ResourceApi`、`GunActionManager` 等）——那里问的是真实逻辑侧，不是消息方向。两个握手消息（`C2SMessageAcknowledge`、`S2CMessageSyncedEntityDataMapping`）本来就没有侧判断。

`./gradlew :forge-compat:compileJava` 结果 `BUILD SUCCESSFUL`。未提交。

另外：Addon 里还剩一处同类写法——[C2SMessageCombatantConsumable_cancel.java:23](../CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/core/network/message/combatant/C2SMessageCombatantConsumable_cancel.java:23) 的 `CgcTactical.getSideExecutor().getLogicalSide().isServer()`，是否需要一并改为 `MessageDirection.CLIENT_TO_SERVER`？

# 

```
需要检查以下在当前ForgeNetworkAdapter的网络线程下，原有的logicalside是否会正确？网络线程是否会判定为server side？（CGC Issue #40）
并且，按照./docs-lrtactical/Migration Mapping.md，把现有网络包跟git工作区（现有两个类）一样补上skeleton，先不补充message成员变量
```

# 

我先核对 Issue #40、`docs-lrtactical` 目录和 Addon 工作区里已有的两个 skeleton 类。

Issue #40 确认了背景：线程不在 Forge `SidedThreadGroup` 时 `EffectiveSide.get()` 默认返回 CLIENT。现在核实网络线程属于哪个线程组。

网络线程的问题已确认。现在读取 lrtactical 原版的 12 个消息类，以确定每个 skeleton 的结构。

现在给剩余 10 个消息类补上 skeleton 结构（方向判断 + `handler.accept` + C2S 的 sender 校验 + TODO），不添加 payload 成员。

两个部分都完成了。

## 一、网络线程下的 `getLogicalSide()` 是否正确

**结论：在网络线程上，原有写法实际上是正确的——服务端网络线程会被判定为 SERVER；客户端网络线程会被判定为 CLIENT。**

依据（Forge 1.20.1-47.4.23 源码）：

1. `EffectiveSide.get()` 的实现：
```java
final ThreadGroup group = Thread.currentThread().getThreadGroup();
return group instanceof SidedThreadGroup ? ((SidedThreadGroup) group).getSide() : LogicalSide.CLIENT;
```
非 `SidedThreadGroup` 的线程一律返回 CLIENT——这正是 Issue #40 描述的根因。

2. 但 **服务端 Netty 线程被 Forge 显式打上了 SERVER 组**（`ServerConnectionListener.java:52,55`）：
```java
return new NioEventLoopGroup(0, new ThreadFactoryBuilder()
    .setNameFormat("Netty Server IO #%d").setDaemon(true)
    .setThreadFactory(SidedThreadGroups.SERVER)   // Forge 补丁
    .build());
```
所以普通服务端和单人存档的内置服务端，其 IO 线程都在 `SidedThreadGroups.SERVER` 里 → 判定 SERVER。

3. **客户端 Netty 线程没有被打上 CLIENT 组**（`Connection.java:64,67`），因此落入默认分支 → 判定 CLIENT。对 S2C 消息来说恰好是期望值。

4. `handle(...)` 确实运行在 Netty IO 线程上：`NetworkHooks.onCustomPayload → NetworkInstance.dispatch → SimpleChannel.networkEventListener → IndexedMessageCodec.consume → messageConsumer.accept` 全程同步，没有切线程。

所以：
- 旧代码在**消息处理**这条路径上不存在 #40 的问题；
- #40 依然成立，但影响的是资源重载等非 Sided 异步线程（`TagsUpdatedEvent` 里的 `_DataInstanceManager` / `ResourceApi`），与 `ForgeNetworkAdapter` 无关；
- 正确性依赖两件偶然的事（Forge 给服务端 IO 线程挂 SERVER 组 + 默认回落 CLIENT），而 `context.direction()` 不依赖任何线程推断——所以上一轮的替换仍然是对的。

## 二、Addon 消息 skeleton

按 `Migration Mapping.md` 的对应关系，给剩余 10 个消息类补上了与工作区两个类一致的结构，未添加任何 payload 成员：

- C2S（`C2SMessageCombatantMelee_prepare`）：`direction == CLIENT_TO_SERVER` → `handler.accept` → `sender instanceof ServerPlayer` 校验 → TODO；
- S2C（其余 9 个）：`direction == SERVER_TO_CLIENT` → `handler.accept` → TODO（按 lrtactical 对应类标注了待实现行为，如 `normal_shake` / `hard_shake`、`screen shake`、`splash particles` 等）。

`encode`/`decode` 保持空实现，`implements IMessage<T>` 不变。Addon `./gradlew compileJava` 结果为 `BUILD SUCCESSFUL`（输出的 unmappable character 仍是既有中文注释的编码噪音）。

**需要你确认一处**：[C2SMessageCombatantMelee_attack.java:22-29](../CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/core/network/message/combatant/C2SMessageCombatantMelee_attack.java:22) 当前工作区版本是从 Consumable_cancel 复制来的（`getUseItem()` + `// TODO IConsumable`），但它的 lrtactical 对应物是 `CMeleeAttackRequest`（`postAttack(action, actionCount, entities)`），且它没有方向判断。我没有改动这个类——需要我按 `CMeleeAttackRequest` 修正这处 TODO 并补上方向判断吗？

未提交任何改动。

# 

```
调用capability的都像现在C2SMessageCombatantMelee_attack改成// TODO ILivingCombatant，调客户端的照S2CMessageCombatantItemCooldown的写法（跟CGC相同）新建客户端类
```

# 

先查看这两个被引用的类当前在磁盘上的状态，以及工作区改动。

模板已确认。现在按它改造 8 个 S2C 消息并新建对应客户端类，C2S 的 capability TODO 统一为 `// TODO ILivingCombatant`。

完成，Addon `compileJava` 通过（`BUILD SUCCESSFUL`）。

## 改动

**C2S（能力调用）** — 统一为 `// TODO ILivingCombatant`：
- [C2SMessageCombatantMelee_attack.java:28](../CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/core/network/message/combatant/C2SMessageCombatantMelee_attack.java:28)（你已改好）
- [C2SMessageCombatantMelee_prepare.java:28](../CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/core/network/message/combatant/C2SMessageCombatantMelee_prepare.java:28)（你已改好，保留了你补充的 `S2CMessageSyncMeleeAnimation`）
- `C2SMessageCombatantConsumable_cancel` 保持 `// TODO IConsumable`：lrtactical 对应实现走 `LrTacticalAPI` 索引 + `stopUsingItem()`，不经过 capability。

**S2C（客户端处理）** — 9 个全部按 `S2CMessageCombatantItemCooldown` 的模式改为委托到客户端类：

```java
if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
    handler.accept(() -> {
        CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                _S2CMessageX.handle(this)
        );
    });
}
```

新建客户端类（8 个，`@ApiStatus.Internal`，空 `handle`，与模板一致）：
- `client/network/message/combatant/`：`_S2CMessageCombatantMelee_reset`、`_S2CMessageCombatantShield_block`、`_S2CMessageCombatantShield_disable`
- `client/network/message/resource/`：`_S2CMessageResourceSound`、`_S2CMessageSyncDataPack`
- `client/network/message/sync/`：`_S2CMessageSyncMeleeAnimation`
- `client/network/message/world/`：`_S2CMessageNearbyExplosion`、`_S2CMessageSplashParticle`

（`_S2CMessageCombatantItemCooldown` 是你已建好的第 9 个。）

另外注意到 [S2CMessageCombatantItemCooldown.java:3](../CGC-Tactical-Addon/core/src/main/java/dev/xcolorful/cgctactical/core/network/message/combatant/S2CMessageCombatantItemCooldown.java:3) 的 4 条 import 被合并成了一行（应是粘贴所致，不影响编译）。需要我整理回逐行吗？

未提交。
