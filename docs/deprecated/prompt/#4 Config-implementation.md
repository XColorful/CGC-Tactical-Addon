> #4 提示词

```
按照TaCZ到CGC的config迁移方式，完成Lrtactical到CGC Tactical Addon的移植
- 调用CGC的config封装
- 注：CGC Config已经不完全对应TaCZ的config，但是config结构是严格模板化的
- 提示词更多信息可参考#3 Resource-skeleton.md开头的，但本次任务仅处理config
- 完成代码后，继续照CGC wiki的方式，完成CGC Tactical Addon的config wiki，中文翻译先照已有英文翻
- wiki新建文件的版本号为`0.0.0`.0，对已有文件的修改则需要在末尾加1（见CGC Mod version.md），且wiki写法仍然跟CGC完全同构，差异仅为具体config内容和wiki链接
```

# 

I'll start by reading the project memory and architecture docs to understand the migration pattern.

找到了 Addon 仓库的 `#3 Resource-skeleton.md` 和 `#4 Config-implementation.md`。接下来读取迁移说明、Lrtactical 的三个 config 类，以及 CGC 的 config 封装。

现在读取 CGC 的 config 封装（API、初始化、各 config 类），以及 TaCZ 原 config 作为迁移对照。

CGC 的 config 模板已经清楚。现在查看 CGC 的 config 迁移历史与相关提示词，确认 TaCZ→CGC 在拆分/命名上的约定。

研究完成。CGC 的 config 模板是：`api.config` 里的 Tag 常量 → `core/config` / `client/config` 的分类配置类 → `core/init.ModConfig` / `client/init.ClientModConfig` 聚合注册，平台实现由 CGC 的 `IModConfigSpecBuilder` 提供。现在按此结构实现 addon 的 5 个配置分组。

配置类与注册钩子已完成，开始用 JDK 17 编译验证。

导入写错了：CGC 的 config 接口在 `dev.xcolorful.customgun.core.api.config`，我误引用成 addon 自己的包。修正后重新编译。

编译通过，并确认 Forge 在模组构造期间会把 `ModLoadingContext` 的 active container 设为该模组，因此配置会正确注册到 `cgctactical`。现在写 wiki。

已完成 Lrtactical config 到 CGC Tactical Addon 的移植，并按 CGC wiki 同构补齐了 config 文档。

## 代码

按 CGC 的 config 模板新增：

- Tag 常量：[ModConfigTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/config/ModConfigTag.java)、[ClientModConfigTag.java](core/src/main/java/dev/xcolorful/cgctactical/core/api/config/ClientModConfigTag.java)
- 配置类：
    - [GrenadeConfig.java](core/src/main/java/dev/xcolorful/cgctactical/core/config/GrenadeConfig.java)（`grenade`）
    - [MeleeConfig.java](core/src/main/java/dev/xcolorful/cgctactical/core/config/MeleeConfig.java)（`melee`，耐久与无敌帧阈值）
    - [SyncConfig.java](core/src/main/java/dev/xcolorful/cgctactical/core/config/SyncConfig.java)（`flash_shield`、`throwable`、`melee`）
    - [RenderConfig.java](core/src/main/java/dev/xcolorful/cgctactical/client/config/RenderConfig.java)（`render`）
- 聚合注册：[ModConfig.java](core/src/main/java/dev/xcolorful/cgctactical/core/init/ModConfig.java)、[ClientModConfig.java](core/src/main/java/dev/xcolorful/cgctactical/client/init/ClientModConfig.java)，通过 `CustomGun.getModConfigSpecBuilder()` 使用 CGC 封装
- 接入点：[CgcTactical.java:26](core/src/main/java/dev/xcolorful/cgctactical/CgcTactical.java:26)、[CgcTacticalClient.java:14](core/src/main/java/dev/xcolorful/cgctactical/client/CgcTacticalClient.java:14)

采用的结构调整：

- Lr 的 camelCase key 统一为 CGC 的 PascalCase，并保留 `_OLD1` 旧路径常量
- Lr 未分组的客户端配置归入 `[render]`
- `[flash shield]` 改为 CGC 风格的 `[flash_shield]`
- Lr 中 `throwable.CROUCHING_INIT_SPEED_PERCENT` 误用了 `flash_shield_cooldown` 作为 key，已改为 `CrouchingInitSpeedPercent`，`_OLD1` 记录原路径并加注释说明
- 没有需要缓存/重载的派生数据，因此未添加 CGC 的 `ModConfig.Event` 空壳

## Wiki

新增（版本 `0.0.0`.0）：

- [Grenade config.md](docs/wiki/configuration/Grenade%20config.md)
- [Melee config.md](docs/wiki/configuration/Melee%20config.md)
- [Render config.md](docs/wiki/configuration/Render%20config.md)
- [Sync config.md](docs/wiki/configuration/Sync%20config.md)

修改：

- [Home.md](docs/wiki/Home.md)：新增中英文配置文件索引；中文版本 `0.0.0`.2、英文 `0.0.0`.3
- [_Sidebar.md](docs/wiki/_Sidebar.md)：同步新增配置文件分组

## 验证

```bash
JAVA_HOME=/e/Software/Minecraft/JDK/jdk-17 GRADLE_OPTS="-Dfile.encoding=UTF-8" ./gradlew :forge-compat:compileJava --rerun-tasks --console=plain
```

`:forge-compat:compileJava` 全量重编译通过，config 类已进入 `cgctactical-0.0.0-core.jar`。同时确认 Forge 在模组构造期间会把 `ModLoadingContext` 的 active container 指向当前模组，因此配置会注册到 `cgctactical` 而非 `customgun`。

未引入新的平台 API；本次仅使用 CGC 的 config 封装。未做游戏内实际加载验证，未提交。

# 

```
移除ConfigTag里的_OLD1写法，CGC本体没在Config里用这个
```

# 

已移除两个 ConfigTag 类中所有 `_OLD1` 常量，以及依赖它的旧 key 说明注释。编译通过。

# 

```
目前CGC Tactical Addon里的config都不需要在wiki标范围，修改
```

# 

已移除四个 config wiki 页面中的全部数值范围标注，只保留类型。

# 

```
按#3 Resource-skeleton.md里提到的相同的方式，把刚才CGC本体和CGC Tactical Addon wiki修改的部分应用到.wiki仓库
```

# 

已按 `#3 Resource-skeleton.md` 的同步方式（`.wiki` 仓库文件平铺在根目录、文件名空格换横杠）应用到两个 wiki 仓库。

## CGC `.wiki`

来源 commit：`e3aaf9fa4 wiki: unify style in config wiki`

修改 9 个文件：
- `Ammo-config.md`、`Gun-config.md`、`Key-config.md`、`Other-config.md`
- `Render-config.md`、`Resource-config.md`、`Sound-config.md`、`Sync-config.md`、`Zoom-config.md`

改动为移除英文区 `## X Config` 标题、更新英文 wiki 版本号，以及 `Sync-config.md` 的子章节由 `###` 提升为 `##`。`git diff --stat` 与源 commit 一致：`9 files changed, 12 insertions(+), 30 deletions(-)`。

## CGC Tactical Addon `.wiki`

来源 commit：`c2b059f Add mod configs`

修改：
- `Home.md`、`_Sidebar.md`（新增配置文件导航与中英文版本号）

新增：
- `Grenade-config.md`
- `Melee-config.md`
- `Render-config.md`
- `Sync-config.md`

`git diff --stat`：`Home.md +34/-2`、`_Sidebar.md +16`，与源 commit 的 wiki 部分一致。

## 校验

- 所有目标文件与源 `docs/wiki` 文件用 `diff --strip-trailing-cr` 逐一分组比对，内容完全一致。
- 保留 `.wiki` 仓库原有的 CRLF 行尾。
- CGC 主仓库中未提交的 `Debug command.md` 改动仅是行尾差异，内容与 `.wiki` 现有文件一致，因此未产生额外修改。

两个 `.wiki` 仓库的改动均未提交，停留在工作区等待审阅。
