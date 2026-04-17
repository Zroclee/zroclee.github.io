# DFlash

![dflash](https://github.com/z-lab/dflash)
## 什么事DFlash



DFlash是一种创新的投机解码框架，其核心是利用**轻量级的块扩散模型（Block Diffusion Model）作为“草稿生成器”（Drafter）**，为大模型生成多个候选Token，再由原始大模型一次性验证，从而将生成过程从“单字输出”提速到“整句输出”，实现最高超过6倍的无损加速。

要理解DFlash为何如此高效，需要先简要了解它的加速思路，然后再深入其具体的工作原理。

### ⚡️ 加速思路：从“串行猜字”到“并行整段预测”

大模型生成文本默认的方式是“自回归”（Autoregressive），即一个字一个字地输出，GPU的强大并行算力被浪费在漫长的等待上。

*   **常规方案**：推测解码使用一个快速的小模型（草稿模型），先自回归地“猜”出一串Token，再让大模型并行验证。以**EAGLE**为例，它需要跑4次小模型才能“猜”出4个Token，存在串行瓶颈。
*   **DFlash方案**：DFlash的草稿模型不再“串行”地猜测，而是通过一次前向传播，**并行地预测出整个块（Block）的Token**。这种“一次性猜出一整句”的能力，是其大幅领先的关键。

### 🧠 原理拆解：DFlash为何能“猜”得又快又准？

DFlash通过两项核心创新解决了草稿模型精度与速度的矛盾：

1.  **并行生成整块Token**：它引入的**块扩散模型**可以一次性预测多个Token，其时间复杂度为O(1)，不随预测Token数量的增加而增长。例如，EAGLE猜8个Token要跑8次，而DFlash只用跑1次。
2.  **特征注入，让“小模型”洞悉“大模型”思路**：扩散模型的核心在于“去噪”，但若无信息指导，其生成质量很差。DFlash的草稿模型会**直接从原始大模型中抽取“隐藏状态”（Hidden States）作为条件**。这些隐藏状态蕴含了丰富的上下文和关于未来的“隐式”信息。DFlash会将这些状态注入到草稿模型的**每一层**（即KV Injection），这好比给小模型配备了“导航”，使其预测质量远超“盲猜”。草稿模型也因此可以做到**5层**（EAGLE-3仅1层），更深的结构使其能力更强，预测更准。

### 📊 速度飞跃：数据印证

根据相关论文和开源数据，DFlash在实测中表现突出：

*   **加速比**：在**Qwen3-8B**模型上，实现了高达**6.17倍**的无损加速。
*   **对比优势**：解码速度比目前最先进的推测解码方法**EAGLE-3**快了近**2.5倍**。
*   **低成本**：它的训练仅需**8×RTX 3090（约1-2天）**，普通开发者也能负担。

### 🛠️ 从实验室到生产：DFlash的落地生态

DFlash已逐步从论文走向实际应用，并集成到了主流推理框架中：

*   **开源框架集成**：目前已**完全集成到 `SGLang` 和 `vLLM`** 中。这意味着开发者可以像调用常规模型一样，在框架内轻松开启DFlash加速。
*   **开源与上手**：其官方代码库 `z-lab/dflash` 已在GitHub收获超过**1500个Star**，并被集成到`SpecForge`等训练框架中，方便用户训练和定制。

总的来说，DFlash通过块扩散的并行生成能力和特征注入的精度保障，为AI推理带来了一个“量变引起质变”的加速效果。如果你打算在 `SGLang` 或 `vLLM` 这类生产环境里尝试DFlash，需要我为你整理一份更具体的部署和配置指南吗？

## 官方文档

# DFlash：用于闪电投机解码的块扩散模型
[**论文**](https://arxiv.org/abs/2602.06036) | [**博客**](https://z-lab.ai/projects/dflash/) | [**模型库**](https://huggingface.co/collections/z-lab/dflash)

**DFlash** 是一个轻量级的**块扩散**模型，专为投机解码而设计。它能够实现高效且高质量的并行草稿生成。

![DFlash 架构图](https://raw.githubusercontent.com/jianc99/jianc99.github.io/master/images/dflash_system.png)

https://github.com/user-attachments/assets/5b29cabb-eb95-44c9-8ffe-367c0758de8c

## 支持的模型

| 模型 | DFlash 草稿模型 |
|---|---|
| Qwen3.6-35B-A3B (预览版) | [z-lab/Qwen3.6-35B-A3B-DFlash](https://huggingface.co/z-lab/Qwen3.6-35B-A3B-DFlash) |
| Kimi-K2.5 | [z-lab/Kimi-K2.5-DFlash](https://huggingface.co/z-lab/Kimi-K2.5-DFlash) |
| Qwen3.5-4B | [z-lab/Qwen3.5-4B-DFlash](https://huggingface.co/z-lab/Qwen3.5-4B-DFlash) |
| Qwen3.5-9B | [z-lab/Qwen3.5-9B-DFlash](https://huggingface.co/z-lab/Qwen3.5-9B-DFlash) |
| Qwen3.5-27B | [z-lab/Qwen3.5-27B-DFlash](https://huggingface.co/z-lab/Qwen3.5-27B-DFlash) |
| Qwen3.5-35B-A3B | [z-lab/Qwen3.5-35B-A3B-DFlash](https://huggingface.co/z-lab/Qwen3.5-35B-A3B-DFlash) |
| Qwen3-Coder-Next | [z-lab/Qwen3-Coder-Next-DFlash](https://huggingface.co/z-lab/Qwen3-Coder-Next-DFlash) |
| Qwen3-Coder-30B-A3B | [z-lab/Qwen3-Coder-30B-A3B-DFlash](https://huggingface.co/z-lab/Qwen3-Coder-30B-A3B-DFlash) |
| gpt-oss-20b | [z-lab/gpt-oss-20b-DFlash](https://huggingface.co/z-lab/gpt-oss-20b-DFlash) |
| gpt-oss-120b | [z-lab/gpt-oss-120b-DFlash](https://huggingface.co/z-lab/gpt-oss-120b-DFlash) |
| Qwen3-4B (非思考模式) | [z-lab/Qwen3-4B-DFlash-b16](https://huggingface.co/z-lab/Qwen3-4B-DFlash-b16) |
| Qwen3-8B (非思考模式) | [z-lab/Qwen3-8B-DFlash-b16](https://huggingface.co/z-lab/Qwen3-8B-DFlash-b16) |
| Llama-3.1-8B-Instruct | [z-lab/LLaMA3.1-8B-Instruct-DFlash-UltraChat](https://huggingface.co/z-lab/LLaMA3.1-8B-Instruct-DFlash-UltraChat) |
| Qwen3.5-122B-A10B | 即将推出 |
| Qwen3.5-397B-A17B | 即将推出 |
| GLM-5.1 | 即将推出 |

> 欢迎通过 GitHub Issue 请求支持更多模型。我们也将很快开源训练方法，方便您为自己的大语言模型训练专属的 DFlash 草稿模型。

## 📦 安装

为避免冲突，请为每个后端使用独立的虚拟环境。

| 后端 | 安装命令 |
|---|---|
| **Transformers** | `uv pip install -e ".[transformers]"` |
| **SGLang** | `uv pip install -e ".[sglang]"` |
| **vLLM** | 见下文 |
| **MLX** (Apple Silicon) | `pip install -e ".[mlx]"` |

**vLLM：** DFlash 支持需要 nightly 版本：
```bash
uv pip install -e ".[vllm]"
uv pip install -U vllm --torch-backend=auto --extra-index-url https://wheels.vllm.ai/nightly
```

## 🚀 快速开始

### vLLM

```bash
vllm serve Qwen/Qwen3.5-27B \
  --speculative-config '{"method": "dflash", "model": "z-lab/Qwen3.5-27B-DFlash", "num_speculative_tokens": 15}' \
  --attention-backend flash_attn \
  --max-num-batched-tokens 32768
```

### SGLang

```bash
export SGLANG_ALLOW_OVERWRITE_LONGER_CONTEXT_LEN=1

# 可选：启用调度重叠（实验性功能，可能不稳定）
# export SGLANG_ENABLE_SPEC_V2=1
# export SGLANG_ENABLE_DFLASH_SPEC_V2=1
# export SGLANG_ENABLE_OVERLAP_PLAN_STREAM=1

python -m sglang.launch_server \
    --model-path Qwen/Qwen3.5-35B-A3B \
    --speculative-algorithm DFLASH \
    --speculative-draft-model-path z-lab/Qwen3.5-35B-A3B-DFlash \
    --speculative-num-draft-tokens 16 \
    --tp-size 1 \
    --attention-backend trtllm_mha \
    --speculative-draft-attention-backend fa4 \
    --mem-fraction-static 0.75 \
    --mamba-scheduler-strategy extra_buffer \
    --trust-remote-code
```

### Transformers

仅 Qwen3 和 LLaMA-3.1 模型支持 Transformers 后端。

```python
from transformers import AutoModel, AutoModelForCausalLM, AutoTokenizer

draft = AutoModel.from_pretrained("z-lab/Qwen3-8B-DFlash-b16", trust_remote_code=True, dtype="auto", device_map="cuda:0").eval()
target = AutoModelForCausalLM.from_pretrained("Qwen/Qwen3-8B", dtype="auto", device_map="cuda:0").eval()
tokenizer = AutoTokenizer.from_pretrained("Qwen/Qwen3-8B")

messages = [{"role": "user", "content": "196 有多少个正整因数？"}]
input_ids = tokenizer.apply_chat_template(messages, return_tensors="pt", add_generation_prompt=True, enable_thinking=False).to(draft.device)

output = draft.spec_generate(input_ids=input_ids, max_new_tokens=2048, temperature=0.0, target=target, stop_token_ids=[tokenizer.eos_token_id])
print(tokenizer.decode(output[0], skip_special_tokens=False))
```

### MLX (Apple Silicon)

社区已经为 MLX 贡献了许多优秀的 DFlash 实现；这里提供一个简洁高效的版本，已在 Apple M5 Pro 上使用 Qwen3 和 Qwen3.5 模型测试通过。

```python
from dflash.model_mlx import load, load_draft, stream_generate

model, tokenizer = load("Qwen/Qwen3.5-4B")
draft = load_draft("z-lab/Qwen3.5-4B-DFlash")

messages = [{"role": "user", "content": "196 有多少个正整因数？"}]
prompt = tokenizer.apply_chat_template(messages, tokenize=False, add_generation_prompt=True, enable_thinking=True)
tps = 0.0
for r in stream_generate(model, draft, tokenizer, prompt, block_size=16, max_tokens=2048, temperature=0.6):
    print(r.text, end="", flush=True)
    tps = r.generation_tps
print(f"\n吞吐量: {tps:.2f} tok/s")
```

## 📊 评估

所有基准测试使用相同的数据集（gsm8k, math500, humaneval, mbpp, mt-bench）。首次运行时，数据集会自动下载并作为 JSONL 缓存在 `cache/` 目录中。

**vLLM**：
```bash
python -m dflash.benchmark --backend vllm \
    --base-url http://127.0.0.1:8000 --model Qwen/Qwen3.5-27B \
    --dataset gsm8k --num-prompts 128 --concurrency 1 --enable-thinking
```

**SGLang**：
```bash
python -m dflash.benchmark --backend sglang \
    --base-url http://127.0.0.1:30000 --model Qwen/Qwen3.5-35B-A3B \
    --dataset gsm8k --num-prompts 128 --concurrency 1 --enable-thinking
```

**Transformers**（仅限 Qwen3 和 LLaMA）：
```bash
torchrun --nproc_per_node=8 -m dflash.benchmark --backend transformers \
    --model Qwen/Qwen3-8B --draft-model z-lab/Qwen3-8B-DFlash-b16 \
    --dataset gsm8k --max-samples 128
```

**MLX**：
```bash
python -m dflash.benchmark --backend mlx \
    --model Qwen/Qwen3.5-4B --draft-model z-lab/Qwen3.5-4B-DFlash \
    --dataset gsm8k --max-samples 128 --enable-thinking
```

## 致谢

衷心感谢 [@dcw02](https://github.com/dcw02)、[@gongy](https://github.com/gongy) 以及 [@modal-labs](https://github.com/modal-labs) 团队为 DFlash 在 SGLang 中落地提供的快速、高质量支持。同样衷心感谢 NVIDIA 的 [@benchislett](https://github.com/benchislett) 为将 DFlash 引入 vLLM 并助力其在更广泛的推理社区中可用所付出的努力。

## 引用

如果您发现 DFlash 对您的工作有帮助，请引用我们的论文。如需分享对 DFlash 的反馈或请求新模型支持，请填写此表格：[DFlash 反馈](https://forms.gle/4YNwfqb4nJdqn6hq9)。

```bibtex
@article{chen2026dflash,
  title   = {{DFlash: Block Diffusion for Flash Speculative Decoding}},
  author  = {Chen, Jian and Liang, Yesheng and Liu, Zhijian},
  journal = {arXiv preprint arXiv:2602.06036},
  year    = {2026}
}
```