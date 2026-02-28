<template>
  <div class="terminal-container">
    <div class="terminal-window">
      <div class="terminal-header">
        <div class="buttons">
          <span class="btn red"></span>
          <span class="btn yellow"></span>
          <span class="btn green"></span>
        </div>
        <div class="title">zroc@blog: ~</div>
      </div>
      <div class="terminal-body">
        <div class="command-line">
          <span class="prompt">zroc@blog:~$</span>
          <span class="command typing-effect">whoami</span>
        </div>
        <div class="output">
          <p>Name: Zroc</p>
          <p>Role: Developer / Learner</p>
          <p>Mission: Sharing daily life in learning and development</p>
        </div>

        <div class="command-line">
          <span class="prompt">zroc@blog:~$</span>
          <span class="command">ls -la ./docs</span>
        </div>
        <div class="output file-list">
          <div class="file-item">
            <span class="permissions">drwxr-xr-x</span>
            <span class="owner">zroc</span>
            <span class="group">staff</span>
            <span class="size">4096</span>
            <span class="date">Feb 28</span>
            <a href="/markdown-examples" class="file-link">Markdown Examples/</a>
          </div>
          <div class="file-item">
            <span class="permissions">drwxr-xr-x</span>
            <span class="owner">zroc</span>
            <span class="group">staff</span>
            <span class="size">4096</span>
            <span class="date">Feb 28</span>
            <a href="/api-examples" class="file-link">API Examples/</a>
          </div>
          <div class="file-item" v-for="item in features" :key="item.title">
             <span class="permissions">-rw-r--r--</span>
             <span class="owner">zroc</span>
             <span class="group">staff</span>
             <span class="size">1024</span>
             <span class="date">Feb 28</span>
             <span class="file-link">{{ item.title }}</span>
          </div>
        </div>
        
        <div class="command-line">
          <span class="prompt">zroc@blog:~$</span>
          <span class="cursor">_</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useData } from 'vitepress'

const { frontmatter } = useData()
const features = frontmatter.value.features || []
</script>

<style scoped>
.terminal-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 10px;
  font-family: 'Courier New', Courier, monospace;
}

.terminal-window {
  width: 100%;
  max-width: 600px;
  background-color: #1e1e1e;
  border-radius: 8px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
  overflow: hidden;
  border: 1px solid #333;
}

.terminal-header {
  background-color: #2d2d2d;
  padding: 10px 15px;
  display: flex;
  align-items: center;
  position: relative;
}

.buttons {
  display: flex;
  gap: 8px;
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
  left: 50%;
  transform: translateX(-50%);
  color: #999;
  font-size: 14px;
}

.terminal-body {
  padding: 20px;
  color: #f0f0f0;
  font-size: 16px;
  line-height: 1.5;
}

.command-line {
  margin-bottom: 10px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.prompt {
  color: #4caf50;
  margin-right: 10px;
  font-weight: bold;
}

.command {
  color: #fff;
}

.output {
  margin-bottom: 20px;
  color: #ccc;
  padding-left: 0;
}

.file-list {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.file-item {
  display: grid;
  grid-template-columns: 100px 60px 60px 60px 80px auto;
  gap: 10px;
  font-size: 14px;
}

.file-link {
  color: #4ea8de;
  text-decoration: none;
  cursor: pointer;
}

.file-link:hover {
  text-decoration: underline;
  color: #64b5f6;
}

.cursor {
  display: inline-block;
  width: 10px;
  height: 20px;
  background-color: #ccc;
  animation: blink 1s infinite;
  vertical-align: middle;
}

@keyframes blink {
  0%, 100% { opacity: 1; }
  50% { opacity: 0; }
}

@media (max-width: 600px) {
  .file-item {
    display: flex;
    flex-wrap: wrap;
  }
  .permissions, .owner, .group, .size, .date {
    display: none;
  }
}
</style>
