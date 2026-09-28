> #1 提示词

# 

```
现在有4个项目的工作目录，CGC Tactical Addon, Custom Gun Continued, TaCZ, LesRaisins Tactical Equipements，各自的代码和文档都可以通过IDEA提供的MCP浏览

背景说明：
- CGC基于TaCZ重构
- CGC和CGC Tactical Addon跟TaCZ和lrtactical的关系一致，lrtactical属于TaCZ的扩展

你需要完成以下工作：
- 完成CGC Tactical Addon目录下./docs-lrtactical/architecture/Home.md的编写，不需要碰这个文档以外的内容
- 格式、排版布局跟CGC./docs-tacz/architecture/Home.md是同构的，编写的精简程度、深度都保持一致；内容根据lrtactical实际情况编写
- 该文档不包含CGC Tactical Addon的重构的说明，见CGC目录#21 docs-tacz.md
- 由于CGC Tactical Addon跟CGC同构，且lrtactical跟TaCZ目录结构也相同，因此对于lrtactical跟tacz一致的结构，可以参照CGC文档的写法
- Home文档只需要跟CGC文档一样陈述架构，不需要做“为什么”的说明
- 由于上下文长度限制，可以开多个子Agent去浏览不同目录，其中目录结构相同的部分可以借鉴CGC已有文档的写法，避免全读代码导致上下文溢出
```

# 

I'll start by exploring the existing structure and the reference document.

Found the target file and reference. Let me read them.

Now let me understand the surrounding docs and the lrtactical source structure.

The task spec exists as a prompt doc. Let me read it.

Now I understand the task. Let me examine lrtactical's source structure and the addon's own refactored docs.

Let me look at the addon's own refactored docs to understand the target-side structure.

The target docs are stubs with no anchors, and the user excluded refactor commentary — so this will describe lrtactical's own architecture only, without migration-mapping links. Now let me gather class-level details via parallel subagents.

I have everything I need. Writing the document now.

Let me verify the indentation uses tabs and check for formatting issues.

Let me check line-ending conventions against the reference doc to avoid a diff-noise regression.

Done. Wrote [docs-lrtactical/architecture/Home.md](app://localhost/epitaxy/docs-lrtactical/architecture/Home.md) — the only file touched.

**What it contains.** A Chinese/English navigation index (398 lines) mirroring the layout of CGC's `docs-tacz/architecture/Home.md`: same header block, `### 包名` + `> _./pkg_` sections in package-alphabetical order, tab indentation, one class per line with a short Chinese gloss, and the client package split into `####` sub-sections. Sections are `API / 能力 / 客户端 / 模组联动 / 配置 / 状态效果 / 附魔 / 实体 / 事件处理 / 初始化 / 背包 / 物品 / Mixin / 网络 / 资源 / 工具`, with an identical English half.

**Content grounded in lrtactical's actual source** (161 Java files, gathered via 5 parallel subagents that read the code — I did not read the source wholesale). Notable lrtactical-specific shapes: `capability` is a player-attached combat-state pair (`CombatProperties` + `CustomItemCoolDowns`); `entity` is a throwable hierarchy (`ThrowableItemEntity` base, `sp/SpEffectCloudEntity` off to the side); `item/throwable` has `area/explode/flash/smoke` sub-packages whose `*Type` records register into the custom `ModRegistries`; networks are `C*`/`S*` message pairs.

**Two judgment calls you should confirm:**

1. **No 迁移映射 links.** Per "该文档不包含 CGC Tactical Addon 的重构的说明", I omitted the `> 迁移映射为 [...]` lines that CGC's doc carries. This is also forced by the target: the addon's own `docs/architecture/core/Home.md` and `client/Home.md` are still 17-line stubs, so those anchors don't exist yet.
2. **No `./api/api-index.md` link.** CGC's API section links to an api-index. You said not to touch anything outside this document, so I couldn't create one; the API section instead lists the package inline.

Left uncommitted for your review, as CLAUDE.md requires.

# 

```
现在api部分已经建立./docs-lrtactical/architecture/api/api-index.md，Home.md的api部分跟CGC文档一样移到api-index.md，要求同之前
- 本次仅允许修改Home.md和api-index.md
```

# 

Let me look at the current state of both API files and the CGC reference.

The stub has a `## 双端 > _./api_` scaffold. I'll fill api-index.md with the API listing and reduce Home.md's API section to a link, mirroring CGC.

Done — only `Home.md` and the new `api-index.md` were touched.

**`api-index.md`** — filled the scaffold's `## 双端 > _./api_` with `LrTacticalAPI`, then added one `##` section per lrtactical api subpackage, mirroring CGC's convention of translating the package name for the heading (`animation`→动画, `collision`→碰撞检测, `event`→事件, `index`→索引, `item`→物品, `melee`→近战). Same tab indentation, no backticks on mod classes, and the mirrored English half after `# English` (starting directly at `## Common`, as CGC does). All seven subpackages are common-side, so there is no `## 客户端 API` section here.

**`Home.md`** — the two inline API listings (Chinese and English) are replaced by a single link line, matching CGC:

```
### API
> _./api_

- [API索引](./api/api-index.md)：通过接口分类和职责介绍进行筛选
```

```
- [API Index](./api/api-index.md#English): Filter by interface classification and responsibility introduction
```

Two notes on consistency choices:

- No `- [事件API](./api/event-api.md)` second bullet — CGC has one only because `event-api.md` exists; lrtactical has no such file, and this round's file scope was limited to the two documents.
- No `> 迁移映射为 [...]` lines, consistent with the earlier decision: the addon's `docs/architecture/core|client` are still stubs with no anchors to target.

Left uncommitted for your review.
