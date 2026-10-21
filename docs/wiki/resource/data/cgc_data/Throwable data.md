[English](#English)

# 投掷物数据
> wiki 版本：`0.0.0`.0

- `prepare_time`（int）：
- `cookable`（boolean）：
- `initial_speed`（float）：
- `cooldown`（int）：
- `cooldown_category`（ResourceLocation）：
- `max_stack_size`/`stack_size`（int）：
- `entity`（\_EntityData）：
	- `life_time`（int）：
	- `gravity`（float）：
	- `should_bounce`（boolean）：
	- `broke_on_ground`（boolean）：
	- `bounce_factor`（float）：
	- `hit_damage`（float）：
	- `tail_particles`（ResourceLocation）：
- `put_away_time`（long）：
---
- `explode`（\_ExplodeData）：
	- `explode_scale`/`radius`（float）：
	- `explode_damage`/`damage`（float）：
	- `enable_world_destruction`/`destroy_blocks`（boolean）：
	- `destroy_multiplier`（float）：
	- `trigger_on_explode`（boolean）：
	- `remote_detonation`（boolean）：
	- `screen_shake_time`（float）：
	- `screen_shake_amplitude`（float）：
- `cloud`（\_CloudData）：
	- `area_cloud`（boolean）：
	- `radius`（float）：
	- `radius_per_tick`（float）：
	- `wait_time`（int）：
	- `duration`（int）：
	- `particles`（ResourceLocation）：
	- `ignite`（boolean）：
	- `ignite_time`（int）：
	- `extinguish_by_smoke`（boolean）：
	- `effects`（List<\_EffectData>）：
		- `effect_location`/`type`（ResourceLocation）：
		- `duration`（int）：
		- `amplifier`（int）：
		- `visible`（boolean）：
		- `show_icon`（boolean）：
- `stun`（\_StunData）：
	- `radius`（float）：
	- `blind`（\_BlindData）：
		- `max_duration`（int）：
		- `min_duration`（int）：
		- `max_angle`（float）：
		- `view_angle_factor`（float）：
	- `deafened`（\_DeafenedData）：
		- `max_duration`（int）：
		- `min_duration`（int）：

```json
{
	"prepare_time": 0,
	"cookable": false,
	"initial_speed": 0.0,
	"cooldown": 0,
	"cooldown_category": "namespace:location",
	"max_stack_size": 0,
	"entity": {
		"life_time": 0,
		"gravity": 0.0,
		"should_bounce": false,
		"broke_on_ground": false,
		"bounce_factor": 0.0,
		"hit_damage": 0.0,
		"tail_particles": "namespace:location"
	},
	"put_away_time": 0,
	
	"explode": {
		"explode_scale": 0.0,
		"explode_damage": 0.0,
		"enable_world_destruction": false,
		"destroy_multiplier": 0.0,
		"trigger_on_explode": false,
		"remote_detonation": false,
		"screen_shake_time": 0.0,
		"screen_shake_amplitude": 0.0
	},
	"cloud": {
		"area_cloud": false,
		"radius": 0.0,
		"radius_per_tick": 0.0,
		"wait_time": 0,
		"duration": 0,
		"particles": "namespace:location",
		"ignite": false,
		"ignite_time": 0,
		"extinguish_by_smoke": false,
		"effects": [
			{
				"effect_location": "namespace:location",
				"duration": 0,
				"amplifier": 0,
				"visible": false,
				"show_icon": false
			}
		]
	},
	"stun": {
		"radius": 0.0,
		"blind": {
			"max_duration": 0,
			"min_duration": 0,
			"max_angle": 0.0,
			"view_angle_factor": 0.0
		},
		"deafened": {
			"max_duration": 0,
			"min_duration": 0
		}
	}
}
```

# English
> wiki version: `0.0.0`.0

- `prepare_time`(int):
- `cookable`(boolean):
- `initial_speed`(float):
- `cooldown`(int):
- `cooldown_category`(ResourceLocation):
- `max_stack_size`/`stack_size`(int):
- `entity`(\_EntityData):
	- `life_time`(int):
	- `gravity`(float):
	- `should_bounce`(boolean):
	- `broke_on_ground`(boolean):
	- `bounce_factor`(float):
	- `hit_damage`(float):
	- `tail_particles`(ResourceLocation):
- `put_away_time`(long):
---
- `explode`(\_ExplodeData):
	- `explode_scale`/`radius`(float):
	- `explode_damage`/`damage`(float):
	- `enable_world_destruction`/`destroy_blocks`(boolean):
	- `destroy_multiplier`(float):
	- `trigger_on_explode`(boolean):
	- `remote_detonation`(boolean):
	- `screen_shake_time`(float):
	- `screen_shake_amplitude`(float):
- `cloud`(\_CloudData):
	- `area_cloud`(boolean):
	- `radius`(float):
	- `radius_per_tick`(float):
	- `wait_time`(int):
	- `duration`(int):
	- `particles`(ResourceLocation):
	- `ignite`(boolean):
	- `ignite_time`(int):
	- `extinguish_by_smoke`(boolean):
	- `effects`(List<\_EffectData>):
		- `effect_location`/`type`(ResourceLocation):
		- `duration`(int):
		- `amplifier`(int):
		- `visible`(boolean):
		- `show_icon`(boolean):
- `stun`(\_StunData):
	- `radius`(float):
	- `blind`(\_BlindData):
		- `max_duration`(int):
		- `min_duration`(int):
		- `max_angle`(float):
		- `view_angle_factor`(float):
	- `deafened`(\_DeafenedData):
		- `max_duration`(int):
		- `min_duration`(int):

```json
{
	"prepare_time": 0,
	"cookable": false,
	"initial_speed": 0.0,
	"cooldown": 0,
	"cooldown_category": "namespace:location",
	"max_stack_size": 0,
	"entity": {
		"life_time": 0,
		"gravity": 0.0,
		"should_bounce": false,
		"broke_on_ground": false,
		"bounce_factor": 0.0,
		"hit_damage": 0.0,
		"tail_particles": "namespace:location"
	},
	"put_away_time": 0,
	
	"explode": {
		"explode_scale": 0.0,
		"explode_damage": 0.0,
		"enable_world_destruction": false,
		"destroy_multiplier": 0.0,
		"trigger_on_explode": false,
		"remote_detonation": false,
		"screen_shake_time": 0.0,
		"screen_shake_amplitude": 0.0
	},
	"cloud": {
		"area_cloud": false,
		"radius": 0.0,
		"radius_per_tick": 0.0,
		"wait_time": 0,
		"duration": 0,
		"particles": "namespace:location",
		"ignite": false,
		"ignite_time": 0,
		"extinguish_by_smoke": false,
		"effects": [
			{
				"effect_location": "namespace:location",
				"duration": 0,
				"amplifier": 0,
				"visible": false,
				"show_icon": false
			}
		]
	},
	"stun": {
		"radius": 0.0,
		"blind": {
			"max_duration": 0,
			"min_duration": 0,
			"max_angle": 0.0,
			"view_angle_factor": 0.0
		},
		"deafened": {
			"max_duration": 0,
			"min_duration": 0
		}
	}
}
```
