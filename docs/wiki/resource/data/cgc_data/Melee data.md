[English](#English)

# 近战数据
> wiki 版本：`0.0.35.1`.4

- `draw_time`（int）：
- `put_away_time`（int）：
- `attack`（\_AttackData）：
	- `primary_action`/`attack_left`（List<\_MeleeAttackInfo>）：
		- `factor`（float）：
		- `knockback`（float）：
		- `cooldown`（int）：
		- `delay`（int）：
		- `target_filter_data`/`hitbox`（\_TargetFilterData）：
			- `hit_volume_type`/`type`（HitVolumeType）：
			- `max_range`（float）：
			- `exclude_self`（boolean）：
			- `max_angle`（float）：
			- `pierce`/`penetration`（int）：
			- `half_width`（float）：
			- `half_height`（float）：
			- `roll`（float）：
		- `durability_damage`（int）：
		- `movement`（\_MeleeMovement）：
			- `delay`（int）：
			- `speed`（float）：
	- `secondary_action`/`attack_right`（List<\_MeleeAttackInfo>）：
		- `factor`（float）：
		- `knockback`（float）：
		- `cooldown`（int）：
		- `delay`（int）：
		- `target_filter_data`（\_TargetFilterData）：
			- `hit_volume_type`/`type`（String）：
			- `max_range`（float）：
			- `exclude_self`（boolean）：
			- `max_angle`（float）：
			- `pierce`/`penetration`（int）：
			- `half_width`（float）：
			- `half_height`（float）：
			- `roll`（float）：
		- `durability_damage`（int）：
		- `movement`（\_MeleeMovement）：
			- `delay`（int）：
			- `speed`（float）：
- `attribute_data`/`attributes`（Map<ResourceLocation, \_AttributeInfo>）：
	- `amount`（float）：
	- `modifier_operation`/`operation`（AttributeModifierOperation）：
- `max_durability`（int）：
- `enchantment_value`（int）：

```json
{
	"draw_time": 0,
	"put_away_time": 0,
	"attack": {
		"primary_action": [
			{
				"factor": 0.0,
				"knockback": 0.0,
				"cooldown": 0,
				"delay": 0,
				"target_filter_data": {
					"hit_volume_type": "",
					"max_range": 0.0,
					"exclude_self": false,
					"max_angle": 0.0,
					"pierce": 0,
					"half_width": 0.0,
					"half_height": 0.0,
					"roll": 0.0
				},
				"durability_damage": 0,
				"movement": {
					"delay": 0,
					"speed": 0.0
				}
			}
		],
		"secondary_action": [
			{
				"factor": 0.0,
				"knockback": 0.0,
				"cooldown": 0,
				"delay": 0,
				"target_filter_data": {
					"hit_volume_type": "",
					"max_range": 0.0,
					"exclude_self": false,
					"max_angle": 0.0,
					"pierce": 0,
					"half_width": 0.0,
					"half_height": 0.0,
					"roll": 0.0
				},
				"durability_damage": 0,
				"movement": {
					"delay": 0,
					"speed": 0.0
				}
			}
		]
	},
	"attribute_data": {
		"attribute_location": {
			"amount": 0.0,
			"modifier_operation": ""
		}
	},
	"max_durability": 0,
	"enchantment_value": 0
}
```

# English
> wiki version: `0.0.35.1`.4

- `draw_time`(int):
- `put_away_time`(int):
- `attack`(\_AttackData):
	- `primary_action`/`attack_left`(List<\_MeleeAttackInfo>):
		- `factor`(float):
		- `knockback`(float):
		- `cooldown`(int):
		- `delay`(int):
		- `target_filter_data`/`hitbox`(\_TargetFilterData):
			- `hit_volume_type`/`type`(HitVolumeType):
			- `max_range`(float):
			- `exclude_self`(boolean):
			- `max_angle`(float):
			- `pierce`/`penetration`(int):
			- `half_width`(float):
			- `half_height`(float):
			- `roll`(float):
		- `durability_damage`(int):
		- `movement`(\_MeleeMovement):
			- `delay`(int):
			- `speed`(float):
	- `secondary_action`/`attack_right`(List<\_MeleeAttackInfo>):
		- `factor`(float):
		- `knockback`(float):
		- `cooldown`(int):
		- `delay`(int):
		- `target_filter_data`/`hitbox`(\_TargetFilterData):
			- `hit_volume_type`/`type`(String):
			- `max_range`(float):
			- `exclude_self`(boolean):
			- `max_angle`(float):
			- `pierce`/`penetration`(int):
			- `half_width`(float):
			- `half_height`(float):
			- `roll`(float):
		- `durability_damage`(int):
		- `movement`(\_MeleeMovement):
			- `delay`(int):
			- `speed`(float):
- `attribute_data`/`attributes`(Map<ResourceLocation, \_AttributeInfo>):
	- `amount`(float):
	- `modifier_operation`/`operation`(AttributeModifierOperation):
- `max_durability`(int):
- `enchantment_value`(int):

```json
{
	"draw_time": 0,
	"put_away_time": 0,
	"attack": {
		"primary_action": [
			{
				"factor": 0.0,
				"knockback": 0.0,
				"cooldown": 0,
				"delay": 0,
				"target_filter_data": {
					"hit_volume_type": "",
					"max_range": 0.0,
					"exclude_self": false,
					"max_angle": 0.0,
					"pierce": 0,
					"half_width": 0.0,
					"half_height": 0.0,
					"roll": 0.0
				},
				"durability_damage": 0,
				"movement": {
					"delay": 0,
					"speed": 0.0
				}
			}
		],
		"secondary_action": [
			{
				"factor": 0.0,
				"knockback": 0.0,
				"cooldown": 0,
				"delay": 0,
				"target_filter_data": {
					"hit_volume_type": "",
					"max_range": 0.0,
					"exclude_self": false,
					"max_angle": 0.0,
					"pierce": 0,
					"half_width": 0.0,
					"half_height": 0.0,
					"roll": 0.0
				},
				"durability_damage": 0,
				"movement": {
					"delay": 0,
					"speed": 0.0
				}
			}
		]
	},
	"attribute_data": {
		"attribute_location": {
			"amount": 0.0,
			"modifier_operation": ""
		}
	},
	"max_durability": 0,
	"enchantment_value": 0
}
```
