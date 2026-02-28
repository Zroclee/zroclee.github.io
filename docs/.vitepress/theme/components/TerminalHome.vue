<script setup>
// No script needed for this component currently
</script>

<template>
  <div class="terminal-container">
    <div class="terminal-window">
      <div class="terminal-header">
        <div class="buttons">
          <span class="btn red"></span>
          <span class="btn yellow"></span>
          <span class="btn green"></span>
        </div>
        <div class="title">zroc@agent: ~/workspace</div>
      </div>
      <div class="terminal-body">
        <div class="content-wrapper">
          <!-- Step 1: Initial Command -->
          <div class="command-line">
            <span class="prompt">zroc@agent:~$</span>
            <span class="command">create-agent --name "Assistant" --role "Full Stack"</span>
          </div>

          <!-- Step 2: Processing Output -->
          <div class="output">
            <div class="log-line">
              <span class="info">[INFO]</span> Initializing agent workspace...
            </div>
            <div class="log-line">
              <span class="info">[INFO]</span> Loading context from <span class="highlight">/docs/frontend</span>
            </div>
            <div class="log-line">
              <span class="info">[INFO]</span> Loading context from <span class="highlight">/docs/backend</span>
            </div>
            <div class="log-line">
              <span class="success">[SUCCESS]</span> Knowledge base indexing complete.
            </div>
            <div class="log-line">
              <span class="info">[INFO]</span> Generating skills...
            </div>
          </div>

          <!-- Step 3: Code Snippet Simulation -->
          <div class="code-block">
            <div class="line"><span class="keyword">const</span> <span class="variable">agent</span> = <span class="keyword">new</span> <span class="class-name">Agent</span>({</div>
            <div class="line indent">  <span class="property">name</span>: <span class="string">'ZROC'</span>,</div>
            <div class="line indent">  <span class="property">capabilities</span>: [</div>
            <div class="line double-indent">    <span class="string">'Vue3'</span>, <span class="string">'NestJS'</span>, <span class="string">'Python'</span>, <span class="string">'LLM'</span></div>
            <div class="line indent">  ],</div>
            <div class="line indent">  <span class="property">status</span>: <span class="string">'Ready to code'</span></div>
            <div class="line">});</div>
          </div>

          <!-- Step 4: Final Prompt -->
          <div class="command-line active">
            <span class="prompt">zroc@agent:~$</span>
            <span class="cursor">_</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.terminal-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 20px;
  font-family: 'JetBrains Mono', 'Fira Code', 'Courier New', monospace;
  perspective: 1000px;
}

.terminal-window {
  width: 100%;
  max-width: 580px;
  background-color: #0d1117;
  border-radius: 12px;
  box-shadow: 
    0 20px 50px rgba(0, 0, 0, 0.5), 
    0 0 0 1px rgba(255, 255, 255, 0.1);
  overflow: hidden;
  transition: transform 0.3s ease;
}

.terminal-container:hover .terminal-window {
  transform: translateY(-5px) rotateX(2deg);
}

.terminal-header {
  background-color: #161b22;
  padding: 12px 16px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  position: relative;
}

.buttons {
  display: flex;
  gap: 8px;
  z-index: 2;
}

.btn {
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

.red { background-color: #ff5f56; }
.yellow { background-color: #ffbd2e; }
.green { background-color: #27c93f; }

.title {
  position: absolute;
  left: 0;
  right: 0;
  text-align: center;
  color: #8b949e;
  font-size: 13px;
  font-weight: 500;
  pointer-events: none;
}

.terminal-body {
  padding: 20px;
  color: #c9d1d9;
  font-size: 14px;
  line-height: 1.6;
  min-height: 300px;
}

.command-line {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}

.prompt {
  color: #7ee787;
  font-weight: bold;
}

.command {
  color: #fff;
}

.info { color: #58a6ff; }
.success { color: #7ee787; }
.highlight { color: #d2a8ff; }

.output {
  margin-bottom: 16px;
  color: #8b949e;
}

.code-block {
  background: rgba(255, 255, 255, 0.05);
  padding: 12px;
  border-radius: 6px;
  border-left: 3px solid #7ee787;
  margin-bottom: 16px;
  font-family: 'JetBrains Mono', monospace;
}

.keyword { color: #ff7b72; }
.variable { color: #79c0ff; }
.class-name { color: #d2a8ff; }
.property { color: #79c0ff; }
.string { color: #a5d6ff; }

.indent { padding-left: 20px; }
.double-indent { padding-left: 40px; }

.cursor {
  display: inline-block;
  width: 8px;
  height: 18px;
  background-color: #c9d1d9;
  animation: blink 1s step-end infinite;
  vertical-align: middle;
}

@keyframes blink {
  50% { opacity: 0; }
}

@media (max-width: 640px) {
  .terminal-window {
    font-size: 12px;
  }
}
</style>
