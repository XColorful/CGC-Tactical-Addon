[English](#English)

# 架构总览

> 本文档作为项目架构的导航索引

## 项目结构

基于`dev.xcolorful.cgctactical.core`顶层包的模块划分

### 网络
> _./core/network_

- Message：网络消息
	- event：事件消息
	- 客户端->服务端消息
	- 服务端->客户端消息
- NetworkHandler：网络处理器（注册网络消息、发送消息）

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

### Network
> _./core/network_

- Message: Network messages
	- event: Event message
	- Client -> Server message
	- Server -> Client message
- NetworkHandler: Network handler (register network messages, send messages)

### Utility
> _./core/util_

> Shared wrappers for common project functionality.
>
> When an equivalent utility exists, it must be used instead of calling platform or Minecraft APIs directly, to preserve consistency and compatibility.

- SendUtils: Unified entry point for sending network messages
	- All network messages must be sent through this utility, decoupling callers from the underlying `NetworkHandler`
