[English](#English)

## 近战管理器

- 外部只需要获取全局静态API`IMeleeManager`即可，`IMeleeManager`具体类再往下层层委派功能
- 外部调用视为拥有所有权限

[![IMeleeManager](/docs/api/core/melee/IMeleeManager.md)](/docs/api/core/melee/IMeleeManager.md)

### 近战主管理器

- 主管理器调度各子管理器
- 提供其下所有子管理器的获取接口

[![IMeleeMainManager](/docs/api/core/melee/IMeleeMainManager.md)](/docs/api/core/melee/IMeleeMainManager.md)

# English

## Melee Manager

- Externally, only the static API `IMeleeManager` needs to be accessed. The concrete `IMeleeManager` class then delegates functionality layer by layer.
- External calls are treated as having full permissions.

[![IMeleeManager](/docs/api/core/melee/IMeleeManager.md)](/docs/api/core/melee/IMeleeManager.md)

### Melee Main Manager

- The Main Manager orchestrates all Sub-Managers.
- Provides getter interfaces for all its Sub-Managers.

[![IMeleeMainManager](/docs/api/core/melee/IMeleeMainManager.md)](/docs/api/core/melee/IMeleeMainManager.md)
