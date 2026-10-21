[English](#English)

# 同步配置
> wiki 版本：`0.0.0`.0

## 闪光盾

路径：`flash_shield`
- `FlashShieldMaxDurability`（整数）：闪光盾的最大耐久
- `FlashShieldCooldown`（整数）：闪光盾的冷却时间（tick）
```toml
[flash_shield]
	FlashShieldMaxDurability = 450
	FlashShieldCooldown = 200
```

## 投掷物

路径：`throwable`
- `CrouchingInitSpeedPercent`（浮点）：蹲下时初始速度的系数，1.0 表示不改变
```toml
[throwable]
	CrouchingInitSpeedPercent = 0.5
```

## 近战

路径：`melee`
- `MeleeMaxTargetPerPacket`（整数）：近战攻击请求包中可包含的最大目标数
```toml
[melee]
	MeleeMaxTargetPerPacket = 32
```

# English
> wiki version: `0.0.0`.0

## Flash Shield

Path: `flash_shield`
- `FlashShieldMaxDurability` (integer): Max durability of the flash shield
- `FlashShieldCooldown` (integer): Cooldown of the flash shield, in ticks
```toml
[flash_shield]
	FlashShieldMaxDurability = 450
	FlashShieldCooldown = 200
```

## Throwable

Path: `throwable`
- `CrouchingInitSpeedPercent` (float): Modifier of the initial speed when crouching, 1.0 means no change
```toml
[throwable]
	CrouchingInitSpeedPercent = 0.5
```

## Melee

Path: `melee`
- `MeleeMaxTargetPerPacket` (integer): The maximum number of targets that a melee attack request packet can contain
```toml
[melee]
	MeleeMaxTargetPerPacket = 32
```
