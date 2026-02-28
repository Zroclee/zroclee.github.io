<template>
  <div class="features-section">
    <div class="container">
      <h2 class="section-title">Why ZROC?</h2>
      <p class="section-subtitle">Bridging the gap between traditional development and AI agents.</p>
      
      <div class="features-grid">
        <div class="feature-card frontend">
          <div class="icon">🎨</div>
          <h3>Modern Frontend</h3>
          <p>Crafting responsive, performant UIs with Vue 3, Vite, and modern CSS capabilities. Focusing on user experience and accessibility.</p>
          <a href="/frontend/" class="learn-more">Explore Frontend &rarr;</a>
        </div>
        
        <div class="feature-card backend">
          <div class="icon">⚡</div>
          <h3>Robust Backend</h3>
          <p>Building scalable microservices and APIs using NestJS, Node.js, and Python. Designed for high availability and performance.</p>
          <a href="/backend/" class="learn-more">Explore Backend &rarr;</a>
        </div>
        
        <div class="feature-card ai">
          <div class="icon">🤖</div>
          <h3>Agentic AI</h3>
          <p>Leveraging LLMs, LangChain, and prompt engineering to build intelligent agents that automate complex workflows.</p>
          <a href="/ai/" class="learn-more">Explore AI &rarr;</a>
        </div>

        <div class="feature-card learning">
          <div class="icon">📚</div>
          <h3>Continuous Learning</h3>
          <p>Sharing daily insights, code snippets, and architectural patterns. A knowledge base that grows with technology.</p>
          <a href="/markdown-examples" class="learn-more">View Examples &rarr;</a>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.features-section {
  padding: 64px 24px;
  background: var(--vp-c-bg-alt);
}

.container {
  max-width: 1152px;
  margin: 0 auto;
}

.section-title {
  text-align: center;
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 12px;
  background: -webkit-linear-gradient(315deg, #42d392 25%, #647eff);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.section-subtitle {
  text-align: center;
  color: var(--vp-c-text-2);
  margin-bottom: 48px;
  font-size: 18px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
}

.feature-card {
  background: var(--vp-c-bg);
  padding: 24px;
  border-radius: 12px;
  border: 1px solid var(--vp-c-divider);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
}

.feature-card:hover {
  border-color: var(--vp-c-brand);
  transform: translateY(-4px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.1);
}

.icon {
  font-size: 32px;
  margin-bottom: 16px;
}

.feature-card h3 {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 12px;
  color: var(--vp-c-text-1);
}

.feature-card p {
  color: var(--vp-c-text-2);
  line-height: 1.6;
  margin-bottom: 20px;
  flex-grow: 1;
}

.learn-more {
  color: var(--vp-c-brand);
  font-weight: 500;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  transition: opacity 0.2s;
}

.learn-more:hover {
  opacity: 0.8;
}
</style>
