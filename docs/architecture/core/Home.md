[English](#English)

# 架构总览

> 本文档作为项目架构的导航索引

## 项目结构

基于`dev.xcolorful.cgctactical.core`顶层包的模块划分

### API
> _./core/api_

- [API索引](./api/api-index.md)：通过接口分类和职责介绍进行筛选，并传送至 _./docs/api_ 下内容以查看详细参数

### 配置
> _./core/config_

- GrenadeConfig：手雷配置
- MeleeConfig：近战配置
- SyncConfig：同步配置

### 初始化
> _./core/init_

- Registry：模组注册表
	- ModParticles：模组粒子
- CommonSetup：双端设置
- ModConfig：模组配置初始化

### Mixin
> _./core/mixin_

- Entity：实体

### 网络
> _./core/network_

- Message：网络消息
	- 客户端->服务端消息
	- 服务端->客户端消息
- NetworkHandler：网络处理器（注册网络消息、发送消息）

### 资源
> _./core/resource_

[资源文件说明](/docs/wiki/resource/Resource%20introduction.md#数据包)：
- Data：数据包POJO
- Instance：POJO对象
	- Data：数据包POJO对象
- Network：数据同步
	- SyncDataType：同步的数据包POJO类型

### 工具
> _./core/util_

> 统一封装项目通用能力
> 
> 当存在对应工具时，必须优先使用工具提供的统一接口，而非直接调用平台或 Minecraft API，以保持一致性与兼容性

- SendUtils：网络消息统一发送入口
	- 所有消息发送均通过此处，与网络实现（`NetworkHandler`）解耦

# English

> This document serves as a navigation index for the project architecture

## Project Structure

Module division based on the `dev.xcolorful.cgctactical.core` top-level package

### API
> _./core/api_

- [API Index](./api/api-index.md#English): Filter by interface classification and responsibility introduction, and link to content under _./docs/api_ to view detailed parameters

### Config
> _./core/config_

- GrenadeConfig: Grenade config
- MeleeConfig: Melee config
- SyncConfig: Sync config

### Initialization
> _./core/init_

- Registry: Mod registry
	- ModParticles: Mod particles
- CommonSetup: Common set up
- ModConfig: Mod config initialization

### Mixin
> _./core/mixin_

- Entity: Entity

### Network
> _./core/network_

- Message: Network messages
	- Client -> Server message
	- Server -> Client message
- NetworkHandler: Network handler (register network messages, send messages)

### Resource
> _./core/resource_

[Resource-introduction](/docs/wiki/resource/Resource%20introduction.md#Datapack):
- Data: Datapack POJO
- Instance: POJO object
	- Data: Datapack POJO object
- Network: Data sync
	- SyncDataType: The type of synced datapack POJO

### Utility
> _./core/util_

> Shared wrappers for common project functionality.
>
> When an equivalent utility exists, it must be used instead of calling platform or Minecraft APIs directly, to preserve consistency and compatibility.

- SendUtils: Unified entry point for sending network messages
	- All network messages must be sent through this utility, decoupling callers from the underlying `NetworkHandler`
