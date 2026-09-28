[English](#English)

# API索引

## 双端
> _./api_

- LrTacticalAPI：资源API

## 动画
> _./api/animation_

- BaseAnimationStateContext：动画状态上下文基类
- ConsumableAnimationStateContext：消耗品动画状态上下文
- FlashShieldAnimationStateContext：闪光盾动画状态上下文
- MeleeAnimationStateContext：近战武器动画状态上下文
- ThrowableAnimationStateContext：投掷物动画状态上下文

## 碰撞检测
> _./api/collision_

- ITargetFilter：索敌过滤器接口
- ConeFilter：锥形索敌过滤器
- OBBFilter：有向包围盒索敌过滤器
- RayFilter：射线索敌过滤器
- OBB：有向包围盒

## 事件
> _./api/event_

- ConsumableUseEvent：消耗品使用事件
- MeleePreAttackEvent：近战攻击前事件

## 索引
> _./api/index_

- ICustomItemIndex：自定义物品索引接口

## 物品
> _./api/item_

- ICustomItem：自定义物品通用接口
- IConsumable：消耗品接口
- IMeleeWeapon：近战武器接口
- IThrowable：投掷物接口

## 近战
> _./api/melee_

- MeleeAction：近战动作枚举
- AttackResult：攻击结果枚举

# English

## Common
> _./api_

- LrTacticalAPI: Resource API

## Animation
> _./api/animation_

- BaseAnimationStateContext: Animation state context base class
- ConsumableAnimationStateContext: Consumable animation state context
- FlashShieldAnimationStateContext: Flash shield animation state context
- MeleeAnimationStateContext: Melee weapon animation state context
- ThrowableAnimationStateContext: Throwable animation state context

## Collision
> _./api/collision_

- ITargetFilter: Target filter interface
- ConeFilter: Cone target filter
- OBBFilter: Oriented bounding box target filter
- RayFilter: Ray target filter
- OBB: Oriented bounding box

## Event
> _./api/event_

- ConsumableUseEvent: Consumable use event
- MeleePreAttackEvent: Melee pre-attack event

## Index
> _./api/index_

- ICustomItemIndex: Custom item index interface

## Item
> _./api/item_

- ICustomItem: Custom item generic interface
- IConsumable: Consumable interface
- IMeleeWeapon: Melee weapon interface
- IThrowable: Throwable interface

## Melee
> _./api/melee_

- MeleeAction: Melee action enum
- AttackResult: Attack result enum
