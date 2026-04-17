toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          
好的，我们从 `main.tsx` 文件的头部开始，逐步拆解这个庞大的入口文件。我刚才通过工具读取了前 400 行的代码，这部分主要包含了**预加载优化、核心依赖引入、以及启动时的环境检测与初始化逻辑**。

以下是 **第 1 - 400 行** 代码的核心内容分析：

### 1. 顶层副作用与性能优化 (第 1-15 行)
在所有常规 `import` 之前，代码执行了几个带有“副作用”（side-effects）的函数调用：
*   `profileCheckpoint('main_tsx_entry')`：用于记录应用启动的性能检查点。
*   `startMdmRawRead()`：并行触发 MDM（移动设备管理）的子进程读取，以节省后续约 135ms 的等待时间。
*   `startKeychainPrefetch()`：并行触发 macOS 钥匙串的读取（如 OAuth 和旧 API Key），避免后续同步读取造成的阻塞（约 65ms）。
**💡 分析**：作为一个 CLI 工具，启动速度至关重要。Anthropic 在这里利用了 Node.js / Bun 的模块加载机制，在加载庞大的依赖树之前，提前派生子进程去执行耗时的系统 I/O 操作。

### 2. 核心模块与按需引入 (第 16-200 行)
这部分是大量的 `import` 语句，展示了项目的宏大架构：
*   **基础框架**：`Commander.js` (命令行解析), `Ink` (React 渲染终端 UI), `Chalk` (终端颜色)。
*   **服务与工具**：导入了 API 服务、MCP（Model Context Protocol）支持、分析服务 (GrowthBook)、插件 (Plugins) 和技能 (Skills) 模块。
*   **按需加载/避免循环依赖**：
    ```typescript
    // 延迟引入以避免 teammate.ts 和 AppState.tsx 之间的循环依赖
    const getTeammateUtils = () => require('./utils/teammate.js');
    ```
*   **特性开关 (Feature Flags)**：
    ```typescript
    // 通过 Bun 的 feature 机制在构建时进行死代码消除 (Dead code elimination)
    const coordinatorModeModule = feature('COORDINATOR_MODE') ? require('./coordinator/coordinatorMode.js') : null;
    const assistantModule = feature('KAIROS') ? require('./assistant/index.js') : null;
    ```
**💡 分析**：代码高度模块化，并通过特性开关在编译时剔除不需要的代码（如内部的 `KAIROS` 助理模式或协调器模式），以减小最终打包体积。

### 3. 反调试与安全机制 (第 218-250 行)
代码中包含了一个 `isBeingDebugged()` 函数和立即退出的逻辑：
*   它会检查 `process.execArgv` 和 `NODE_OPTIONS` 中是否包含 `--inspect` 或 `--debug` 标志。
*   如果检测到 Node.js 检查器处于活动状态，并且当前环境不是内部环境 (`"external" !== 'ant'`)，则直接调用 `process.exit(1)` 退出应用。
**💡 分析**：这是一种安全/防篡改机制，防止外部用户通过 Node.js 调试器附加到进程上来窃取运行时的敏感数据（如上下文、API 密钥或内部协议）。

### 4. 遥测与数据统计 (第 251-294 行)
包含了 `logSessionTelemetry` 和 `logStartupTelemetry` 函数：
*   记录插件/技能的加载情况、报错信息。
*   记录当前工作区的状态（如 Git 工作树数量、是否沙盒模式、系统偏好设置等）。
**💡 分析**：官方工具通常需要完善的埋点来监控命令的健康度和用户使用环境，这里严格排除了 PII（个人身份信息）或具体的代码/文件路径。

### 5. 自动迁移机制 (第 296-320 行)
`runMigrations()` 函数：
*   检查全局配置中的 `migrationVersion`（当前版本为 11）。
*   执行一系列迁移脚本，例如：将旧版模型名迁移到新版（`migrateSonnet1mToSonnet45`, `migrateOpusToOpus1m`），或者将权限配置从旧结构迁移到新结构。
**💡 分析**：这是客户端软件常见的最佳实践，保证旧版本用户升级到新版本时，本地的配置文件能平滑过渡。

### 6. 安全的预加载机制 (第 322-365 行)
`prefetchSystemContextIfSafe()` 和 `startDeferredPrefetches()`：
*   **Git 钩子安全**：由于获取 Git 状态可能会触发恶意的 Git Hooks（执行任意代码），工具会检查用户是否处于“非交互模式”或者“已接受信任对话框”。只有在安全的情况下才会去读取系统上下文。
*   **延迟加载**：为了保证终端 UI (REPL) 能以最快速度渲染第一帧，把一些不紧急的缓存预热操作（如读取历史用户上下文、获取建议、读取文件数量等）推迟到首屏渲染之后。

---

**下一步**：
前 400 行主要是准备工作。接下来的代码通常会进入 **CLI 命令定义（Commander 注册）** 和 **主事件循环（React 渲染）**。

请问你对这部分（启动优化、特性开关、反调试等）有需要深入了解的具体函数吗？如果没有，我可以继续读取 `401 - 600 行`，看看它是如何解析命令行参数并启动主进程的。

toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          
好的，我们继续往下看 `main.tsx` 文件。刚才读取了 **第 401 - 1400 行** 的部分代码，这部分的核心是 **依赖预热、命令行参数解析（Commander.js 的配置）以及启动模式的分发**。

以下是这段代码的核心内容分析：

### 1. 延迟预热与事件循环检测 (第 401 - 425 行)
在 `startDeferredPrefetches()` 函数中，代码执行了一系列在后台进行的异步任务，以加快用户的首屏响应：
*   读取用户上下文 (`getUserContext()`)、提示 (`getRelevantTips()`)。
*   根据环境变量，预取 AWS Bedrock 或 GCP Vertex 的认证信息。
*   使用 Ripgrep 计算项目文件数量 (`countFilesRoundedRg`)，最多等待 3000ms。
*   初始化各种变更检测器（监听设置文件和技能文件的变更）。
*   **💡 亮点：Event loop stall detector**：如果是内部 Ant 环境，会启动一个事件循环阻塞检测器。如果主线程阻塞超过 500ms，它会记录日志，这对于调试 Node.js/Bun CLI 应用的卡顿非常有帮助。

### 2. 参数预解析与环境变量应用 (第 426 - 579 行)
*   **提前解析 `--settings`**：在 `init()` 运行之前就加载配置，因为很多初始化逻辑依赖于配置文件。如果传入的是 JSON 字符串，它会通过计算哈希值生成临时文件，并缓存，以**防止破坏 Anthropic API 的 prompt 缓存**（这可以节省 12 倍的输入 token 成本）。
*   **入口点标记 (`CLAUDE_CODE_ENTRYPOINT`)**：根据不同的执行环境（如 MCP Server、GitHub Actions、VSCode 插件、SDK 脚本或普通 CLI）设置环境变量。

### 3. 特殊协议处理 (Deep Links & SSH & Assistant) (第 601 - 795 行)
这部分是对 `process.argv` 进行了精巧的重写（Rewrite）和拦截：
*   **`cc://` 协议处理**：支持通过 `claude open cc://...` 或直接通过系统协议启动（Lodestone 特性）。在 macOS 上，会检查 `__CFBundleIdentifier` 是否为官方 URL Handler。
*   **SSH 远程模式**：允许像 `claude ssh <host> [dir]` 这样使用。由于 Commander 不容易处理这种特殊的子命令+选项组合，代码在 Commander 解析前拦截了 `ssh` 关键字，并提取了相关参数（如 `--local`, `--permission-mode`），然后重写了 `process.argv` 传递给主逻辑。
*   **Assistant (KAIROS) 模式**：类似地拦截了 `claude assistant` 命令。

### 4. Commander.js CLI 命令注册 (第 900 - 1000 行)
这是定义 `claude` 命令所有可用 flag（标志）的地方。一些值得注意的高级选项：
*   `-p, --print`：非交互模式，通常用于管道 (`echo "hi" | claude -p`)。
*   `--bare`：极简模式，跳过所有 Hook、LSP、自动上下文、预热等操作，纯粹作为一个基础大模型客户端运行。
*   `--output-format <format>`：支持 `text`, `json`, `stream-json`，允许开发者通过代码调用 CLI 时获取结构化输出。
*   `--permission-mode <mode>`：权限模式（如 auto, manual）。
*   `--ide`：自动连接到本地可用的 IDE。
*   `--plugin-dir` / `--mcp-config`：动态加载 MCP（Model Context Protocol）服务器或插件。

### 5. `action` 处理器的前置逻辑 (第 1001 - 1400 行)
当用户执行 `claude [prompt]` 时触发的逻辑：
*   **过滤无意义的 Prompt**：如果用户输入了 `claude code`，它会忽略 `code` 并给出一个黄色的提示（“Tip: You can launch Claude Code with just `claude`”）。
*   **KAIROS（助理模式）与信任校验**：这是一个多 Agent 协作或后台静默运行的模式。如果在不受信任的目录运行，会警告并拒绝启动助理模式。
*   **Tmux 与 Worktree 支持**：代码支持在 Git worktree 下运行，并能通过 `tmux` 派生（spawn）出其他 Agent（Teammate）来协同工作。如果使用了 `--tmux` 参数，会检查当前系统是否安装了 tmux，并拦截 Windows 系统（不支持）。
*   **系统提示词组装**：如果使用了 `--system-prompt-file`，会读取本地文件。如果是团队模式（Teammate），会自动将团队的系统提示词（`TEAMMATE_SYSTEM_PROMPT_ADDENDUM`）追加进去。

---

**总结与下一步：**
从这 1400 行可以看出，Claude Code CLI 不仅仅是一个简单的问答工具，它内置了：
1. **多端支持**（终端、VSCode、GitHub Actions、远程 SSH）。
2. **多 Agent 协作**（通过 Tmux 或内部协议）。
3. **MCP 协议支持**（挂载本地/远程工具）。
4. **严格的权限控制与防篡改**。

由于 `main.tsx` 文件非常大，后面的代码将涉及 **如何初始化 React (Ink) 终端界面**，以及 **如何建立 MCP Server 和处理用户的 Prompt**。

你想继续往下看 `action` 处理器的核心逻辑（例如它是如何调用 AI 模型或渲染界面的），还是想去看看它引用的其他模块（比如 `tools/` 工具夹库或 `services/mcp`）？


toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          
好的，这段代码（**第 1401 - 3000 行**）是 `main.tsx` 文件最核心的启动逻辑之一，主要处理了**MCP 服务的加载、环境变量的合并、交互模式/非交互模式（Print Mode）的分发，以及 React 终端 UI (Ink) 的初始化准备。**

以下是对这段代码的关键拆解和分析：

### 1. 动态 MCP 配置与策略过滤 (第 1401 - 1600 行)
*   **解析 `--mcp-config`**：允许用户通过命令行传入 JSON 字符串或文件路径，动态加载 MCP（Model Context Protocol）服务器。
*   **名称保留与安全检查**：检查 MCP 服务器名称是否占用了内置的保留名称（如 `Claude in Chrome` 或 `Computer Use`）。
*   **企业策略过滤 (`filterMcpServersByPolicy`)**：这是一个重要的安全特性。即使是通过命令行强行传入的 MCP 服务器，如果被企业策略（Enterprise Policy）拦截（如不允许加载特定的本地工具），也会被自动过滤掉并警告。

### 2. 特殊 MCP 挂载：Claude in Chrome & Computer Use (第 1601 - 1800 行)
*   **Claude in Chrome**：如果用户是订阅者且启用了此功能，CLI 会挂载一个与 Chrome 浏览器通信的 MCP 服务器，从而允许 AI 读取浏览器上下文或操作浏览器。
*   **Computer Use (Chicago MCP)**：这是一个内测特性（Ant-only）。如果运行在 macOS 且启用，会挂载一个可以进行系统级截图、控制键鼠的 MCP 服务器（需要严格的前台权限确认）。

### 3. 工具加载与权限上下文初始化 (第 1801 - 2200 行)
*   **初始化工具权限 (`initializeToolPermissionContext`)**：在这里处理 `--allowed-tools` 和 `--disallowed-tools`，以及判断是否处于 `bypassPermissions`（跳过权限确认）模式。如果发现危险权限（例如 Ant 员工意外使用了 `Bash(*)`），系统会主动降级或警告。
*   **加载可用工具 (`getTools`)**：结合权限上下文，生成当前会话可用的工具列表。
*   **设置主进程 Agent (`agentDefinitions`)**：如果用户指定了特定的 Agent（如 `reviewer`），会读取它的系统提示词 (`initialPrompt`) 并将其追加到用户的 Prompt 前面。

### 4. `setup()` 环境初始化与性能优化 (第 2060 - 2200 行)
*   **`setup()` 函数的调用**：这是整个项目中最重要的环境准备函数。它处理了 CWD（当前工作目录）、Worktree、Unix Domain Socket (UDS) 消息队列等底层基础设施。
*   **并行加载**：为了优化启动时间，`getCommands`（加载所有斜杠命令）和 `getAgentDefinitionsWithOverrides`（加载所有 Agent 配置）被设计为与 `setup()` 并行执行（通过 `Promise.all`）。

### 5. 交互式 UI 初始化 (Ink 渲染前准备) (第 2201 - 2500 行)
*   **处理系统提示词 (`appendSystemPrompt`)**：如果是 Proactive（主动）模式或者 Assistant 模式，会动态生成一些行为准则附加到系统提示词中。
*   **启动首次渲染 (`showSetupScreens`)**：
    *   通过 `createRoot` 创建 Ink 终端 React 的渲染根节点。
    *   显示**信任对话框 (Trust Dialog)**、**登录引导**或**用户协议**。
    *   只有在用户确认了这些对话框之后，程序才会继续向下执行（如加载 LSP 语言服务器、发送 API 获取用量配额等）。这也是为了防止在不受信目录下触发恶意代码（如 Git Hooks）。

### 6. 非交互模式 (Print Mode / Headless) 分支 (第 2501 - 3000 行)
这是给 CI/CD、脚本管道或者外部 SDK 调用的核心分支。如果用户传入了 `-p` 或 `--print`：
*   **跳过 UI 渲染**，直接创建 `headlessStore`（模拟应用状态）。
*   **同步加载 MCP**：阻塞式等待所有 MCP 服务器连接完毕，因为单次问答通常立刻就需要用到这些工具。
*   **启动 `runHeadless`**：动态引入 `src/cli/print.ts`，它会管理无头模式下的会话、工具调用循环、并将结果直接通过 stdout 打印（支持 `stream-json` 或纯文本格式），执行完毕后自动退出。

### 7. 状态机 (AppState) 初始化 (第 2900 - 3000 行)
*   在这里我们看到了非常完整的 `AppState` 接口定义。这是支撑整个终端 UI 的核心数据结构，包含了：
    *   `settings` (用户配置)
    *   `mcp` (MCP 客户端列表)
    *   `plugins` (已加载的插件)
    *   `replBridge*` (远程控制状态)
    *   `notifications` (通知队列，比如模型废弃警告)
    *   `fileHistory` (文件修改历史快照，用于 Undo/Rewind)
    *   `todos` (任务列表)

---

### 下一步计划：
这 3000 行代码已经涵盖了 CLI 从启动、环境校验、权限过滤、并行预热，到分支路由（Headless vs Interactive）的全部流程。

接下来 `main.tsx` 剩下的代码（大概几百行）将是：
1. **处理历史会话恢复（Resume / Teleport）**。
2. **初始化交互式 REPL 界面 (`<App />` React 组件的挂载)**。
3. **主事件循环的收尾工作**。

你希望我把剩下的代码全部读完并总结，还是你想针对前面的某个模块（比如 `setup.ts` 的底层操作，或者 `src/cli/print.ts` 的无头调用逻辑）进行深入挖掘？


toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          

        
            
toolName: view_files
            
status: success
          
            
filePath: /Users/zroc/Desktop/Zroc/AI/claude-code-cli/main.tsx
          
好的，我已经通读了整个 `main.tsx` 文件。这个文件是 Claude Code CLI 的**核心总调度站**，它不包含具体的业务逻辑，但负责把所有的底层设施、配置、命令和渲染引擎串联起来。

以下是对整个文件及其核心架构的全面解析总结，你可以把它作为深入研究此项目其他部分的**地图**：

---

### 1. 架构总览：多层级启动与分发
整个 `main.tsx` 的设计理念是**“延迟加载 + 并行预热 + 严格的安全检查”**。
启动流程大致分为以下几个阶段：

1. **第 0 阶段：进程防篡改与底层预热** (行 1-250)
   - 触发 macOS Keychain 和 MDM 配置的后台读取。
   - 检测 Node.js 的 `--inspect` 标志，防止通过调试器窃取运行时内存中的 API Token（反调试）。
2. **第 1 阶段：参数预解析与特殊协议拦截** (行 600-800)
   - 拦截特殊的 URI 或指令，比如 `cc://` (用于 DeepLink 唤起)、`claude ssh` (远程服务器执行)、`claude assistant`。
   - 确认运行模式：`Interactive`（终端 TUI 模式）还是 `Non-Interactive`（`-p/--print` 管道输出模式/SDK模式）。
3. **第 2 阶段：Commander.js 注册与解析** (行 800-1400)
   - 注册海量的 CLI Flag（如 `--bare`, `--mcp-config`, `--tools`, `--agent` 等）。
   - 定义所有的子命令（如 `mcp`, `plugin`, `auth`, `doctor`, `update` 等），这些子命令的处理器被单独拆分到了 `cli/handlers/` 目录下。
4. **第 3 阶段：环境、权限与并行加载 (`action` 核心)** (行 1400-3000)
   - 调用 `setup()` 初始化工作区（Worktree、Socket）。
   - 并行读取本地命令（`getCommands`）和 Agent 角色配置。
   - 严格的权限校验（`initializeToolPermissionContext`），如果用户试图在未授权的目录运行高危权限（如 `Bash(*)`），会被主动降级。
   - **动态加载 MCP 服务**：处理 `--mcp-config`，过滤企业策略 (`filterMcpServersByPolicy`)，以及初始化 `Claude in Chrome` 和 `Computer Use`（键鼠控制）特性。
5. **第 4 阶段：应用状态 (AppState) 初始化与渲染** (行 3000-结尾)
   - 组装庞大的 `AppState` 数据结构（包含历史会话、通知、插件状态、Agent 上下文等）。
   - 如果是 `print` 模式，直接调用 `runHeadless`；
   - 如果是交互模式，则挂载 Ink (React) 的 `<App />` 根节点（`launchRepl`），进入主事件循环。

---

### 2. 核心亮点与学习价值分析

#### 🌟 极致的性能优化 (Performance Optimizations)
作为一个用 TypeScript/Bun 编写的 CLI，启动速度是命门。Anthropic 用了大量的高级技巧：
*   **提前派生子进程 (Subprocess spawning)**：像 Git 状态检查、读取文件数量 (`countFilesRoundedRg`) 都在后台运行，把等待 I/O 的时间隐藏在用户打字的间隙里。
*   **死代码消除 (Dead Code Elimination)**：使用 Bun 的 `feature('FLAG')` 在构建时切除不需要的代码（比如将内部的 `ant-only` 逻辑从外部开源版本中剥离）。
*   **延迟导入 (Lazy Imports)**：几乎所有的 UI 组件和重型逻辑（如 `ink.js`, `server/server.js`）都是在具体的 `action` 触发时才 `await import(...)`，这极大地减少了 `require` 树的体积。

#### 🛡️ 极其严苛的安全与权限模型
因为 Claude Code 能够直接修改代码和执行 Bash 命令，其安全性设计堪称典范：
*   **信任对话框 (Trust Dialog)**：除非用户处于非交互模式（隐式信任）或者明确点击了“Trust”，否则所有的 LSP 语言服务器、Git 钩子读取都会被阻塞。
*   **企业策略拦截**：即使开发者强行通过 `--mcp-config` 挂载本地危险工具，依然会经过一层企业安全策略的过滤。
*   **目录逃逸防护**：在 Windows 上启动前强制设置 `process.env.NoDefaultCurrentDirectoryInExePath = '1'`，防止 PATH 劫持。

#### 🤖 Agent 与 Swarm 协作机制 (KAIROS / Teammate)
代码中暴露了 Anthropic 内部极具野心的 Agent 协作架构：
*   **Tmux 多进程协同**：支持在工作树（Worktree）中通过 tmux 派生（spawn）出多个子 Agent（称为 Teammate），每个子 Agent 有自己的 `AgentType`（如 reviewer, planner）。
*   **Coordinator Mode (协调者模式)**：CLI 可以作为一个领导者（Leader），将任务分发给不同的后台会话（Session）。
*   **Assistant Mode**：作为一个后台守护进程运行，主动推送消息，甚至可以通过 `--channels` 接收外部服务的 Webhook 事件（比如 PR 评论）。

#### 🔌 MCP (Model Context Protocol) 的深度集成
Claude Code 是 MCP 协议的最佳实践参考：
*   它支持多级配置：Local (本地目录), User (用户全局), Project (项目级), SDK (动态注入)。
*   它内置了 `Claude in Chrome` MCP，允许 AI 直接读取用户的 Chrome 浏览器标签页。
*   内置 `Computer Use` (Chicago) MCP，直接让大模型接管屏幕和鼠标。

---

### 3. 如何进一步拆解研究？

如果你打算深入学习这个项目，基于 `main.tsx` 的线索，我建议你可以从以下几个方向进行下一步探索：

1. **终端 UI 渲染机制 (React in CLI)**
   *   **目标文件**: `ink.tsx` 或 `screens/`、`components/` 目录。
   *   **看点**: 了解他们是如何用 React 来管理复杂的终端布局（对话流、Spinner 树、悬浮窗）的，以及 `AppState` 是如何通过 Hook 响应变化的。

2. **工具与沙盒执行引擎 (Tools & Sandbox)**
   *   **目标文件**: `tools.ts` 以及 `tools/` 目录（如 `BashTool`, `FileEditTool`）。
   *   **看点**: AI 究竟是如何执行命令的？他们的输入输出 Schema 是如何定义的？Bash 拦截和权限审批流是怎么做的？

3. **大模型对话循环 (The Agentic Loop)**
   *   **目标文件**: `src/cli/print.ts` (无头模式的主循环) 或对应的 REPL 处理逻辑。
   *   **看点**: 观察 AI 发起 Tool Call -> 挂起等待结果 -> 再次发给大模型进行 reasoning（推理）的完整闭环。

4. **MCP 客户端实现 (MCP Client)**
   *   **目标文件**: `services/mcp/client.ts`
   *   **看点**: 学习如何在 Node.js 中实现标准的 MCP Stdio/SSE 客户端，以及如何把 MCP Tools 动态合并到主程序的 Tool Pool 中。

你需要我进入哪个模块为你继续拆解代码？