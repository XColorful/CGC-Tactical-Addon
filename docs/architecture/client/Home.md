[English](#English)

# 架构总览

> 本文档作为项目架构的导航索引

## 项目结构

基于`dev.xcolorful.cgctactical.client`顶层包的模块划分

### API
> _./client/api_

- [客户端API索引](./api/api-index.md)：通过接口分类和职责介绍进行筛选，并传送至 _./docs/api_ 下内容以查看详细参数

### 动画
> _./client/animation_

### 配置
> _./client/config_

- RenderConfig：渲染配置

### GUI
> _./client/gui_

- Tooltip：提示框
	- Consumable：消耗品
		- ClientConsumableTooltip：客户端消耗品提示框
		> - ConsumableBaseInfoPart：基础信息组件
		> - ConsumableDescriptionPart：描述组件
		> - ConsumableDetailInfoPart：详情信息组件
		> - ConsumableEnchantmentInfoPart：附魔信息组件
		> - ConsumableGuideTipPart：引导提示组件
		> - ConsumableStateInfoPart：状态信息组件
	- Melee：近战
		- ClientMeleeTooltip：客户端近战提示框
		> - MeleeBaseInfoPart：基础信息组件
		> - MeleeDescriptionPart：描述组件
		> - MeleeDetailInfoPart：详情信息组件
		> - MeleeEnchantmentInfoPart：附魔信息组件
		> - MeleeGuideTipPart：引导提示组件
		> - MeleeStateInfoPart：状态信息组件
	- Throwable：投掷物
		- ClientThrowableTooltip：客户端投掷物提示框
		> - ThrowableBaseInfoPart：基础信息组件
		> - ThrowableDescriptionPart：描述组件
		> - ThrowableDetailInfoPart：详情信息组件
		> - ThrowableEnchantmentInfoPart：附魔信息组件
		> - ThrowableGuideTipPart：引导提示组件
		> - ThrowableStateInfoPart：状态信息组件

### 初始化
> _./client/init_

- ClientKeyMappingRegistry：客户端按键注册
- ClientModConfig：客户端配置初始化
- ClientModParticles：客户端粒子注册
- ClientTooltipRegistry：客户端提示框注册

### 输入
> _./client/input_

- combatant：战斗按键

### 模型
> _./client/model_

- TacticalModelObject：战术模型对象

### 粒子
> _./client/particle_

### 渲染器
> _./client/renderer_

- Item：物品

### 资源
> _./client/resource_

[资源文件说明](/docs/wiki/resource/Resource%20introduction.md#资源包)：
- Assets：资源包POJO
- Instance：POJO对象
	- Assets：资源包POJO对象
	- Data：数据包POJO对象
- Network：数据同步
	- SyncDataCache：客户端缓存同步数据

# English

> This document serves as a navigation index for the project architecture

## Project Structure

Module division based on the `dev.xcolorful.cgctactical.client` top-level package

### API
> _./client/api_

- [Client API Index](./api/api-index.md#English): Filter by interface classification and responsibility introduction, and link to content under _./docs/api_ to view detailed parameters

### Animation
> _./client/animation_

### Config
> _./client/config_

- RenderConfig: Render config

### GUI
> _./client/gui_

- Tooltip:
	- Consumable:
		- ClientConsumableTooltip: Client consumable tooltip
		> - ConsumableBaseInfoPart: Base info part
		> - ConsumableDescriptionPart: Description part
		> - ConsumableDetailInfoPart: Detail info part
		> - ConsumableEnchantmentInfoPart: Enchantment info part
		> - ConsumableGuideTipPart: Guide tip part
		> - ConsumableStateInfoPart: State info part
	- Melee:
		- ClientMeleeTooltip: Client melee tooltip
		> - MeleeBaseInfoPart: Base info part
		> - MeleeDescriptionPart: Description part
		> - MeleeDetailInfoPart: Detail info part
		> - MeleeEnchantmentInfoPart: Enchantment info part
		> - MeleeGuideTipPart: Guide tip part
		> - MeleeStateInfoPart: State info part
	- Throwable:
		- ClientThrowableTooltip: Client throwable tooltip
		> - ThrowableBaseInfoPart: Base info part
		> - ThrowableDescriptionPart: Description part
		> - ThrowableDetailInfoPart: Detail info part
		> - ThrowableEnchantmentInfoPart: Enchantment info part
		> - ThrowableGuideTipPart: Guide tip part
		> - ThrowableStateInfoPart: State info part

### Initialization
> _./client/init_

- ClientKeyMappingRegistry: Client input key registration
- ClientModConfig: Client config initialization
- ClientModParticles: Client particle registration
- ClientTooltipRegistry: Client tooltip registration

### Input
> _./client/input_

- combatant: Combatant keys

### Model
> _./client/model_

- TacticalModelObject: Tactical model object

### Particle
> _./client/particle_

### Renderer
> _./client/renderer_

- Item

### Resource
> _./client/resource_

[Resource introduction](/docs/wiki/resource/Resource%20introduction.md#Resourcepack):
- Assets: Resource pack POJO
- Instance: POJO objects
	- Assets: Resource pack POJO object
	- Data: Datapack POJO object
- Network: Data sync
	- SyncDataCache: Client cached sync data
