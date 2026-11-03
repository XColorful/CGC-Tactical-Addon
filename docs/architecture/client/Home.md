[English](#English)

# 架构总览

> 本文档作为项目架构的导航索引

## 项目结构

基于`dev.xcolorful.cgctactical.client`顶层包的模块划分

### API
> _./client/api_

- [客户端API索引](./api/api-index.md)：通过接口分类和职责介绍进行筛选，并传送至 _./docs/api_ 下内容以查看详细参数

### 配置
> _./client/config_

- RenderConfig：渲染配置

### 初始化
> _./client/init_

- ClientModConfig：客户端配置初始化
- ClientModParticles：客户端粒子注册

### 粒子
> _./client/particle_

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

### Config
> _./client/config_

- RenderConfig: Render config

### Initialization
> _./client/init_

- ClientModConfig: Client config initialization
- ClientModParticles: Client particle registration

### Particle
> _./client/particle_

### Resource
> _./client/resource_

[Resource introduction](/docs/wiki/resource/Resource%20introduction.md#Resourcepack):
- Assets: Resource pack POJO
- Instance: POJO objects
	- Assets: Resource pack POJO object
	- Data: Datapack POJO object
- Network: Data sync
	- SyncDataCache: Client cached sync data
