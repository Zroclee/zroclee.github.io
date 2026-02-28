<template>
  <div class="tech-stack-section">
    <p class="stack-label">Powered by modern technologies</p>
    <div class="stack-container">
      <div class="stack-item" v-for="tech in technologies" :key="tech">
        {{ tech }}
      </div>
    </div>
  </div>
</template>

<script setup>
const technologies = [
  'Vue 3',
  'Vite',
  'TypeScript',
  'Python',
  'NestJS',
  'LangChain',
  'OpenAI',
  'Docker',
  'PostgreSQL'
]
</script>

<style scoped>
.tech-stack-section {
  text-align: center;
  padding: 40px 24px;
  border-bottom: 1px solid var(--vp-c-divider);
  background: var(--vp-c-bg-soft);
}

.stack-label {
  font-size: 14px;
  font-weight: 500;
  color: var(--vp-c-text-2);
  margin-bottom: 24px;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.stack-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 16px;
  max-width: 1000px;
  margin: 0 auto;
}

.stack-item {
  padding: 8px 16px;
  background: var(--vp-c-bg);
  border: 1px solid var(--vp-c-divider);
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  color: var(--vp-c-text-1);
  transition: all 0.2s;
  cursor: default;
}

.stack-item:hover {
  border-color: var(--vp-c-brand);
  color: var(--vp-c-brand);
  transform: translateY(-2px);
}
</style>
