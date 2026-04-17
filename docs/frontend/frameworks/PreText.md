# PreText
![PreText](https://pretextjs.net/zh#install)
![PreText - Github](https://github.com/chenglou/pretext.png)

## 为什么用PreText

> 在执行文本测量与布局时，完全跳过 DOM 测量与布局重排，用数学运算（如几何度量）在内存中计算文本结果。
>
> 这个特性尤其适合构建高性能 UI，如：

虚拟化列表（virtual scroll）中预测文本高度；
动态聊天气泡的自动尺寸调整；
自定义 Canvas / SVG 文本布局；
以及任何需要在渲染前准确测量文本尺寸的场景。([news.ycombinator.com][2])
为什么要绕过 DOM 测量
浏览器自身的布局引擎十分复杂，一次简单的测量调用往往导致：

浏览器重排（Reflow）：重新计算布局树；
主线程阻塞：影响响应速度；
性能瓶颈在视觉更新与动态界面中尤为显著。
Pretext 通过提前 逐词/片段测量（基于 Canvas 的 measureText）＋ Unicode 分词与断行算法，预先构建文本的度量数据，并在此基础上用纯算术方法得出动态布局结果，而无需再次触发浏览器布局机制。

- 在AI时代这种技术会极大改变AI交互方式

## 什么是PreText

# Pretext

用于多行文本测量和布局的纯 JavaScript/TypeScript 库。快速、准确，支持所有你可能闻所未闻的语言。可渲染到 DOM、Canvas、SVG，不久还将支持服务端。

Pretext 绕开了对 DOM 测量（例如 `getBoundingClientRect`、`offsetHeight`）的需求——这些操作会触发布局重排，是浏览器中最昂贵的操作之一。它实现了自己的文本测量逻辑，同时以浏览器自身的字体引擎作为基准（一种非常适合 AI 的迭代方法）。

## 安装

```sh
npm install @chenglou/pretext
```

## 示例

克隆仓库，运行 `bun install`，然后 `bun start`，在浏览器中打开 `/demos/index`。在 Windows 上，请使用 `bun run start:windows`。
或者在线查看： [chenglou.me/pretext](https://chenglou.me/pretext/)。更多示例见 [somnai-dreams.github.io/pretext-demos](https://somnai-dreams.github.io/pretext-demos/)

## API

Pretext 服务于两种使用场景：

### 1. 测量段落高度——*完全不触碰 DOM*

```ts
import { prepare, layout } from '@chenglou/pretext'

const prepared = prepare('AGI 春天到了. بدأت الرحلة 🚀‎', '16px Inter')
const { height, lineCount } = layout(prepared, textWidth, 20) // 纯算术运算。没有 DOM 布局和重排！
```

`prepare()` 执行一次性工作：规范化空白符、分割文本、应用粘合规则、通过 canvas 测量片段，并返回一个不透明的句柄。`layout()` 是之后的高频热路径：基于缓存的宽度进行纯算术计算。不要对相同的文本和配置重复调用 `prepare()`，否则会破坏预计算的优势。例如，在窗口大小改变时，只需重新运行 `layout()`。

如果你希望文本像 textarea 那样保留普通空格、`\t` 制表符和 `\n` 硬换行，可以向 `prepare()` 传入 `{ whiteSpace: 'pre-wrap' }`：

```ts
const prepared = prepare(textareaValue, '16px Inter', { whiteSpace: 'pre-wrap' })
const { height } = layout(prepared, textareaWidth, 20)
```

如果你需要类似 CSS 的 `word-break: keep-all` 行为，也可以向 `prepare()` 传入 `{ wordBreak: 'keep-all' }`。

返回的高度值是解锁 Web UI 诸多能力的关键：
- 真正的虚拟化/遮挡，无需猜测和缓存
- 花哨的用户自定义布局：瀑布流、类 JS 驱动的 flexbox 实现、在不需要 CSS 黑科技的情况下微调布局值（想象一下），等等
- *开发时*验证（尤其在 AI 辅助下）按钮等标签是否不会溢出到下一行，且无需浏览器
- 当新文本加载并需要重新锚定滚动位置时，防止布局偏移

### 2. 手动自行布局段落行

将 `prepare` 替换为 `prepareWithSegments`，然后：

- `layoutWithLines()` 在固定宽度下给出所有行：

```ts
import { prepareWithSegments, layoutWithLines } from '@chenglou/pretext'

const prepared = prepareWithSegments('AGI 春天到了. بدأت الرحلة 🚀', '18px "Helvetica Neue"')
const { lines } = layoutWithLines(prepared, 320, 26) // 最大宽度 320px，行高 26px
for (let i = 0; i < lines.length; i++) ctx.fillText(lines[i].text, 0, i * 26)
```

- `measureLineStats()` 和 `walkLineRanges()` 可在不构建文本字符串的情况下，获取行数、宽度和光标信息：

```ts
import { measureLineStats, walkLineRanges } from '@chenglou/pretext'

const { lineCount, maxLineWidth } = measureLineStats(prepared, 320)
let maxW = 0
walkLineRanges(prepared, 320, line => { if (line.width > maxW) maxW = line.width })
// maxW 现在是最宽行的宽度——能够恰好容纳这段文本的最紧凑容器宽度！Web 上一直缺少这种多行“收缩包裹”能力
```

- `layoutNextLineRange()` 让你在行宽动态变化时逐行路由文本。如果你还需要实际的字符串，可以用 `materializeLineRange()` 将那个范围还原为完整的行：

```ts
import { layoutNextLineRange, materializeLineRange, prepareWithSegments, type LayoutCursor } from '@chenglou/pretext'

const prepared = prepareWithSegments(article, BODY_FONT)
let cursor: LayoutCursor = { segmentIndex: 0, graphemeIndex: 0 }
let y = 0

// 使文本围绕浮动图片流动：图片旁的行更窄
while (true) {
  const width = y < image.bottom ? columnWidth - image.width : columnWidth
  const range = layoutNextLineRange(prepared, cursor, width)
  if (range === null) break

  const line = materializeLineRange(prepared, range)
  ctx.fillText(line.text, 0, y)
  cursor = range.end
  y += 26
}
```

这种用法支持渲染到 Canvas、SVG、WebGL，以及（最终）服务端。更丰富的示例请参见 `/demos/dynamic-layout` 示例。

如果你在手动布局时需要一个小型辅助工具来处理富文本内联流、代码片段、@提及、标签（chip）以及类似浏览器的边界空白折叠，可以使用 `@chenglou/pretext/rich-inline` 中的辅助函数。它刻意保持仅内联且仅支持 `white-space: normal`：

```ts
import { materializeRichInlineLineRange, prepareRichInline, walkRichInlineLineRanges } from '@chenglou/pretext/rich-inline'

const prepared = prepareRichInline([
  { text: 'Ship ', font: '500 17px Inter' },
  { text: '@maya', font: '700 12px Inter', break: 'never', extraWidth: 22 },
  { text: "'s rich-note", font: '500 17px Inter' },
])

walkRichInlineLineRanges(prepared, 320, range => {
  const line = materializeRichInlineLineRange(prepared, range)
  // 每个片段保留其源条目索引、文本切片、gapBefore 和光标信息
})
```

该辅助函数刻意保持功能范围狭窄：
- 输入原始内联文本，包括边界空格
- 调用方拥有 `extraWidth` 用于标签（pill）的额外宽度
- `break: 'never'` 用于原子项，如标签（chip）和提及
- 仅支持 `white-space: normal`
- 不是嵌套标记树，也不是通用的 CSS 内联格式化引擎

### API 术语表

场景 1 的 API：
```ts
prepare(text: string, font: string, options?: { whiteSpace?: 'normal' | 'pre-wrap', wordBreak?: 'normal' | 'keep-all' }): PreparedText // 一次性文本分析 + 测量，返回一个不透明值传递给 `layout()`。确保 `font` 与你用于测量文本的 CSS `font` 简写声明（如字号、字重、样式、字体族）同步。`font` 的格式与你给 `myCanvasContext.font = ...` 使用的相同，例如 `16px Inter`。
layout(prepared: PreparedText, maxWidth: number, lineHeight: number): { height: number, lineCount: number } // 给定最大宽度和行高，计算文本高度。确保 `lineHeight` 与你用于测量文本的 CSS `line-height` 声明同步。
```

场景 2 的 API：
```ts
prepareWithSegments(text: string, font: string, options?: { whiteSpace?: 'normal' | 'pre-wrap', wordBreak?: 'normal' | 'keep-all' }): PreparedTextWithSegments // 与 `prepare()` 相同，但返回更丰富的结构用于手动行布局需求
layoutWithLines(prepared: PreparedTextWithSegments, maxWidth: number, lineHeight: number): { height: number, lineCount: number, lines: LayoutLine[] } // 用于手动布局需求的高级 API。所有行使用相同的固定最大宽度。返回值类似 `layout()`，但额外包含行信息
walkLineRanges(prepared: PreparedTextWithSegments, maxWidth: number, onLine: (line: LayoutLineRange) => void): number // 用于手动布局需求的低级 API。所有行使用相同的固定最大宽度。对每一行调用 `onLine`，传入实际计算出的行宽和起始/结束光标，不构建行文本字符串。在需要推测性测试几个宽度和高度边界时非常有用（例如通过反复调用 walkLineRanges 并检查行数，二分搜索一个“合适”的宽度值，从而使行数和高度也“合适”。这样你可以实现文本消息的收缩包裹和平衡文本布局）。在 walkLineRanges 调用之后，再用你满意的最大宽度调用一次 layoutWithLines 来获取实际的行信息。
measureLineStats(prepared: PreparedTextWithSegments, maxWidth: number): { lineCount: number, maxLineWidth: number } // 仅返回该宽度产生的行数以及最宽行的宽度。避免分配行/字符串。
measureNaturalWidth(prepared: PreparedTextWithSegments): number // 返回不受宽度限制时最宽的强制行宽（即不因宽度限制而换行时的最大行宽）
layoutNextLineRange(prepared: PreparedTextWithSegments, start: LayoutCursor, maxWidth: number): LayoutLineRange | null // 类似迭代器的 API，用于可变宽度的布局，不构建行文本字符串
layoutNextLine(prepared: PreparedTextWithSegments, start: LayoutCursor, maxWidth: number): LayoutLine | null // 用于每行宽度都不同的布局的迭代器式 API！返回从 `start` 开始的 LayoutLine，当段落结束时返回 `null`。将前一行的 `end` 光标作为下一次的 `start` 传入。
materializeLineRange(prepared: PreparedTextWithSegments, line: LayoutLineRange): LayoutLine // 将先前计算出的行范围还原为带有文本的完整行
type LineStats = {
  lineCount: number // 换行后的行数，例如 3
  maxLineWidth: number // 最宽行的宽度，例如 192.5
}
type LayoutLine = {
  text: string // 该行的完整文本内容，例如 'hello world'
  width: number // 该行的测量宽度，例如 87.5
  start: LayoutCursor // 在 prepared 片段/字素中的起始光标（包含）
  end: LayoutCursor // 在 prepared 片段/字素中的结束光标（不包含）
}
type LayoutLineRange = {
  width: number // 该行的测量宽度，例如 87.5
  start: LayoutCursor // 在 prepared 片段/字素中的起始光标（包含）
  end: LayoutCursor // 在 prepared 片段/字素中的结束光标（不包含）
}
type LayoutCursor = {
  segmentIndex: number // 在 prepareWithSegments 产生的富文本片段流中的片段索引
  graphemeIndex: number // 该片段内的字素索引；在片段边界处为 `0`
}
```

富文本内联流的辅助函数：
```ts
prepareRichInline(items: RichInlineItem[]): PreparedRichInline // 编译原始内联条目及其原始文本。编译器负责处理跨条目的折叠空白，并缓存每个条目的自然宽度
layoutNextRichInlineLineRange(prepared: PreparedRichInline, maxWidth: number, start?: RichInlineCursor): RichInlineLineRange | null // 一次流式生成一行富文本内联流，不构建片段文本字符串
walkRichInlineLineRanges(prepared: PreparedRichInline, maxWidth: number, onLine: (line: RichInlineLineRange) => void): number // 非物化行遍历器，用于富文本内联流的收缩包裹/统计工作
materializeRichInlineLineRange(prepared: PreparedRichInline, line: RichInlineLineRange): RichInlineLine // 将先前计算出的富内联行范围还原为完整的片段文本
measureRichInlineStats(prepared: PreparedRichInline, maxWidth: number): { lineCount: number, maxLineWidth: number } // 仅返回该宽度产生的行数以及最宽行的宽度。避免分配片段文本。
type RichInlineItem = {
  text: string // 原始作者文本，包括首尾可折叠空格
  font: string // 该条目的 canvas 字体简写
  break?: 'normal' | 'never' // `never` 使条目保持原子性，例如标签（chip）
  extraWidth?: number // 调用方拥有的水平额外宽度，例如内边距 + 边框宽度
}
type RichInlineCursor = {
  itemIndex: number // 当前光标位于哪个源 RichInlineItem 中
  segmentIndex: number // 该条目内 prepared 文本中的片段索引
  graphemeIndex: number // 该片段内的字素索引；在片段边界处为 `0`
}
type RichInlineFragment = {
  itemIndex: number // 回溯到原始 RichInlineItem 数组的索引
  text: string // 此片段的文本切片
  gapBefore: number // 在此行中此片段之前支付的折叠边界间隙
  occupiedWidth: number // 文本宽度加上 extraWidth
  start: LayoutCursor // 条目内 prepared 文本中的起始光标
  end: LayoutCursor // 条目内 prepared 文本中的结束光标
}
type RichInlineLine = {
  fragments: RichInlineFragment[] // 此行上的物化片段
  width: number // 该行的测量宽度，包括 gapBefore/extraWidth
  end: RichInlineCursor // 用于继续下一行的独占结束光标
}
type RichInlineFragmentRange = {
  itemIndex: number // 回溯到原始 RichInlineItem 数组的索引
  gapBefore: number // 在此行中此片段之前支付的折叠边界间隙
  occupiedWidth: number // 文本宽度加上 extraWidth
  start: LayoutCursor // 条目内 prepared 文本中的起始光标
  end: LayoutCursor // 条目内 prepared 文本中的结束光标
}
type RichInlineLineRange = {
  fragments: RichInlineFragmentRange[] // 此行上的非物化片段所有权/范围
  width: number // 该行的测量宽度，包括 gapBefore/extraWidth
  end: RichInlineCursor // 用于继续下一行的独占结束光标
}
type RichInlineStats = {
  lineCount: number // 换行后的行数，例如 3
  maxLineWidth: number // 最宽行的宽度，例如 192.5
}
```

其他辅助函数：
```ts
clearCache(): void // 清除 Pretext 在 prepare() 和 prepareWithSegments() 中使用的共享内部缓存。如果你的应用会循环使用许多不同的字体或文本变体，并且希望释放累积的缓存，则此函数很有用。
setLocale(locale?: string): void // 可选（默认使用当前 locale）。为后续的 prepare() 和 prepareWithSegments() 设置 locale。内部也会调用 clearCache()。设置新的 locale 不会影响已存在的 prepare() 和 prepareWithSegments() 状态（不会对它们进行修改）。
```

注意：
- `PreparedText` 是不透明的高速路径句柄。`PreparedTextWithSegments` 是更丰富的手动布局句柄。
- `LayoutCursor` 是片段/字素光标，不是原始字符串偏移量。
- 对空字符串调用 `layout()` 返回 `{ lineCount: 0, height: 0 }`。但浏览器仍会将空块的高度设为一个 `line-height`，因此如果你需要那种行为，可以用 `Math.max(1, lineCount) * lineHeight` 来限制。
- 更丰富的句柄还包含 `segLevels`，用于自定义双向文本感知的渲染。换行 API 不会读取它。
- 片段宽度是浏览器 canvas 宽度，用于换行，而不是用于自定义阿拉伯语或混合方向 x 坐标重建的精确字形定位数据。
- 如果软连字符被选为断点，物化后的行文本会包含可见的尾部 `-`。
- `measureNaturalWidth()` 返回最宽的强制行宽。硬换行仍然计入。
- `prepare()` 和 `prepareWithSegments()` 只做水平方向的工作。`lineHeight` 仍是布局时的输入。

## 注意事项

Pretext 并不试图成为一个完整的字体渲染引擎（目前还不是）。它目前针对常见的文本设置：
- `white-space: normal` 和 `pre-wrap`
- `word-break: normal` 和 `keep-all`
- `overflow-wrap: break-word`。非常窄的宽度仍可能在单词内断开，但仅在字素边界处断开。
- `line-break: auto`
- 制表符遵循默认的浏览器样式 `tab-size: 8`
- 支持 `{ wordBreak: 'keep-all' }`。它在 CJK/韩文文本上的行为符合预期，同时对超长运行保留相同的 `overflow-wrap: break-word` 后备机制。
- 在 macOS 上，`system-ui` 对于 `layout()` 的准确性不安全。请使用命名字体。

## 开发

参见 [DEVELOPMENT.md](DEVELOPMENT.md) 了解开发设置和命令。

## 致谢

Sebastian Markbage 在十年前通过 [text-layout](https://github.com/chenglou/text-layout) 首次播下了种子。他的设计——使用 canvas `measureText` 进行字形塑形、从 pdf.js 引入双向文本处理、流式换行——为我们不断推进的架构提供了基础。