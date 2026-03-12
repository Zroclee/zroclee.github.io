# OpenClaw 安装和使用 - MacOS

## 准备环境

- 一台 Mac 电脑
- 安装

1. 安装[nodejs](https://nodejs.org/zh-cn/download)
2. 安装[openclaw](https://openclaw.ai/)

## 安装openclaw

1. 打开终端应用程序
2. 输入以下命令安装 OpenClaw：

```bash
npm install -g openclaw
# 如安装失败，尝试使用管理员权限安装
sudo npm install -g openclaw
```

3. 安装完成后，输入以下命令检查安装是否成功：

```bash
# 如果安装成功，将显示 OpenClaw 的版本号。
openclaw --version
```
4. 配置openclaw

1. 输入以下命令配置 OpenClaw：

```bash
openclaw config
```

2. 按照提示输入您的 OpenClaw API 密钥和其他配置选项。


## 常用指令


# OpenClaw CLI 参考指南（中文版）

## 核心概念

| 概念 | 描述 | 关键细节 |
|--------|--------|-------------|
| **工作空间 (Workspace)** | 隔离的项目容器 | 每个工作空间包含 `.openclaw` 目录 |
| **代理 (Agents)** | 具有能力的 AI 代理 | 在工作空间范围内，可绑定到通道 |
| **绑定 (Bindings)** | 代理→通道连接的路由规范 | 格式：`channel[:accountId]` |
| **会话 (Sessions)** | 对话历史存储 | 按代理/工作空间存储 |

## 安装与服务管理

```bash
# 网关服务 (systemd/launchd/schtasks)
openclaw gateway install --port 18790
openclaw gateway status
openclaw gateway restart
openclaw gateway uninstall

# Node 主机（无头服务）
openclaw node install --port 18789 --runtime bun|node
openclaw node status
```

### 注意事项：
- **默认运行时**：Node.js（避免使用 Bun，存在 WhatsApp/Telegram 兼容问题）
- **身份验证**：使用 `OPENCLAW_GATEWAY_TOKEN` / `OPENCLAW_GATEWAY_PASSWORD`
- **端口**：网关使用 18790，Node 主机使用 18789

## 代理 (Agent) 管理

```bash
# 列出所有代理
openclaw agents list

# 在工作空间本地运行代理
openclaw run --workspace 项目名 --agent 代理名

# 配置默认绑定
openclaw bind anthropic:default --agent 代理名

# 删除代理（同时清理工作空间和状态）
openclaw agents delete 代理名 --force
```

### 绑定格式：
```
channel[:accountId]
# 示例：
- anthro|claude3.5-sonnet     # 显式账户绑定
- anthropic                    # 通过通道默认值解析
```

## 模型与认证

```bash
# 设置默认模型
openclaw models set --provider anthropic --model claude-3-5-sonnet-latest

# 检查认证状态（包括 OAuth 过期）
openclaw models status --probe  # 可能会消耗令牌或触发限流

# 添加认证配置
openclaw models auth add --provider openai

# 为特定代理设置模型
openclaw models set anthropic:default --model claude-3.5-sonnet-latest --agent 我的代理
```

### 支持的提供商：
| 提供商 | 设置方式 | 备注 |
|---------|----------|--------|
| Anthropic (Claude) | OAuth token / API key | 检查当前条款用于生产环境 |
| OpenAI | OAuth (Codex) | 通过 GitHub 认证 |
| GitHub Copilot | OAuth token | GitHub 基于的认证流程 |
| Gemini CLI | Antigravity 插件 | 如启用提供商插件可用 |

## 系统事件与自动化

### Cron Jobs（定时任务）：
```bash
openclaw cron add \
  --at "0 9 * * *" \
  --system-event system-heartbeat \
  --text "早晨问候"

openclaw cron list
openclaw cron rm 任务名
```

### 系统事件（网关 RPC）：
```bash
openclaw system event \
  --text "检测到用户登录" \
  --mode gateway-rpc

openclaw system heartbeat enable
```

## Node/主机服务

```bash
# 运行一个 node 或无头主机
openclaw node run \
  --host --port 18789 \
  --runtime bun|node

# 摄像头/屏幕功能（仅限 macOS）
openclaw nodes camera snap --node 我的节点 --facing front
openclaw nodes screen record --node 我的节点 --screen desktop
```

### 认证解析：
- Node 命令不需要 `--token` / `--password` 标志
- 从环境变量解析：`OPENCLAW_GATEWAY_*`
- 回退至配置/存储中的认证配置文件

## 浏览器控制

```bash
# 资料栏管理
openclaw browser create-profile --name work --cdp-url chrome://inspect
openclaw browser profiles list

# 操作
openclaw browser click --target-id 123
openclaw browser type --target-id 123 "你好"
openclaw browser snapshot --format aria
```

## 日志与状态

```bash
# 网关日志（实时跟踪）
openclaw logs --follow --json
openclaw logs --limit 200 --plain

# 带使用情况的状态信息
openclaw status --usage --deep
openclaw health --verbose --json
```

### 使用情况追踪：
- macOS 菜单栏的 Context → Usage 部分
- CLI：`openclaw status --usage`
- REST API 端点：`/status`（提供商使用行为）
- 数据来自提供商端点（非估算值）

## 配置路径

| 类型 | 路径 | 备注 |
|--------|--------|--------|
| 本地配置 | `$HOME/.openclaw/config.json` | 用户偏好设置 |
| 服务配置 | `openclaw-gateway.service` | Systemd/launchd 服务单元文件 |
| 工作空间配置 | `.openclaw/{代理名}/config.json` | 每个代理的设置 |

## 重要警告

1. **OAuth 过期**：使用 `--check` 标志监控（退出码 1=已过期，2=即将过期）
2. **限流**：模型探测会消耗令牌并触发限流
3. **遗留环境变量**：`CLAWDBOT_GATEWAY_*` 已被故意忽略
4. **生产环境 Anthropic**：在生产部署前请验证当前条款
5. **非交互模式**：需要 `--scope`、`--yes`、`--all` 等参数

## 重置/卸载

```bash
# 重置本地配置（保留 CLI）
openclaw reset --yes --non-interactive

# 完整卸载（CLI 仍保留）
openclaw uninstall --yes --all
```

---

此 CLI 提供对 OpenClaw 代理网关基础设施的完整生命周期管理，从安装到操作再到退役。



