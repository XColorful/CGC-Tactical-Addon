[English](#English)

# API索引

## 实体
> _./core/api/entity_

- [ILivingCombatant](/docs/api/core/entity/ILivingCombatant.md)：战斗生物

## 物品
> _./core/api/item_

- [IMelee](/docs/api/core/item/IMelee.md)：近战接口
- [IThrowable](/docs/api/core/item/IThrowable.md)：投掷物接口
- [IConsumable](/docs/api/core/item/IConsumable.md)：消耗品接口

## 近战
> _./core/api/melee_

- [IMeleeManager](/docs/api/core/melee/IMeleeManager.md)：近战管理器（全能易用门面）
	> - [IMeleeMainManager](/docs/api/core/melee/IMeleeMainManager.md)：近战主管理器，提供子管理器及热插拔
	> - [MeleeManagerGroup](/docs/api/core/melee/MeleeManagerGroup.md)：近战管理器组
- [IMeleeSubManager](/docs/api/core/melee/IMeleeSubManager.md)：近战子管理器（主/子管理器同构，即`IMeleeManager`也是`IMeleeSubManager`）
	- Attack：近战攻击
		- [IMeleeAttackManager](/docs/api/core/melee/attack/IMeleeAttackManager.md)：近战攻击管理器
	- Physics：近战物理
		- [IMeleePhysicsManager](/docs/api/core/melee/physics/IMeleePhysicsManager.md)：近战物理管理器
- [IMeleeRuntime](/docs/api/core/melee/IMeleeRuntime.md)：近战运行时接口，聚合近战子管理器 Runtime 接口

## 资源
> _./core/api/resource_

- [ResourceApi](/docs/api/core/resource/ResourceApi.md)：资源API（全能易用门面），提供数据包资源获取

# English

## Entity
> _./core/api/entity_

- [ILivingCombatant](/docs/api/core/entity/ILivingCombatant.md): Living combatant

## Item
> _./core/api/item_

- [IMelee](/docs/api/core/item/IMelee.md): Melee interface
- [IThrowable](/docs/api/core/item/IThrowable.md): Throwable interface
- [IConsumable](/docs/api/core/item/IConsumable.md): Consumable interface

## Melee
> _./core/api/melee_

- [IMeleeManager](/docs/api/core/melee/IMeleeManager.md): Melee manager (almighty easy-to-use facade)
	> - [IMeleeMainManager](/docs/api/core/melee/IMeleeMainManager.md): Melee main manager, provides sub-managers and hot-swapping
	> - [MeleeManagerGroup](/docs/api/core/melee/MeleeManagerGroup.md): Melee manager group
- [IMeleeSubManager](/docs/api/core/melee/IMeleeSubManager.md): Melee sub-manager (isomorphic to main/sub manager, i.e., `IMeleeManager` is also `IMeleeSubManager`)
	- Attack:
		- [IMeleeAttackManager](/docs/api/core/melee/attack/IMeleeAttackManager.md): Melee attack manager
	- Physics:
		- [IMeleePhysicsManager](/docs/api/core/melee/physics/IMeleePhysicsManager.md): Melee physics manager
- [IMeleeRuntime](/docs/api/core/melee/IMeleeRuntime.md): Melee runtime interface, aggregating Melee sub-manager Runtime interfaces

## Resource
> _./core/api/resource_

- [ResourceApi](/docs/api/core/resource/ResourceApi.md): Resource API (almighty easy-to-use facade), provides Datapack resource retrieval
