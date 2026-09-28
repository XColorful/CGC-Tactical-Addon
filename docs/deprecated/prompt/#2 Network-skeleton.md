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
