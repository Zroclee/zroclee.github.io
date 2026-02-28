import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Zroc's Blog",
  description: "Sharing daily life in learning and development",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    logo: '/zroc-logo.png',
    search: {
      provider: 'local'
    },
    nav: [
      { text: 'Home', link: '/' },
      { 
        text: 'Frontend', 
        items: [
          { text: 'Basics', link: '/frontend/basics/' },
          { text: 'Architecture', link: '/frontend/architecture/' },
          { text: 'Frameworks', link: '/frontend/frameworks/' },
          { text: 'Performance', link: '/frontend/performance/' }
        ]
      },
      { 
        text: 'Backend', 
        items: [
          { text: 'Python', link: '/backend/python/' },
          { text: 'Node.js', link: '/backend/nodejs/' },
          { text: 'NestJS', link: '/backend/nestjs/' }
        ]
      },
      { 
        text: 'AI', 
        items: [
          { text: 'Prompts', link: '/ai/prompts/' },
          { text: 'Context', link: '/ai/context/' },
          { text: 'LangChain', link: '/ai/langchain/' },
          { text: 'Principles', link: '/ai/principles/' }
        ]
      },
      { text: 'Examples', link: '/markdown-examples' }
    ],

    sidebar: {
      '/frontend/': [
        {
          text: 'Frontend',
          items: [
            { text: 'Overview', link: '/frontend/' },
            { text: 'Basics', link: '/frontend/basics/' },
            { text: 'Architecture', link: '/frontend/architecture/' },
            { text: 'Frameworks', link: '/frontend/frameworks/' },
            { text: 'Performance', link: '/frontend/performance/' }
          ]
        }
      ],
      '/backend/': [
        {
          text: 'Backend',
          items: [
            { text: 'Overview', link: '/backend/' },
            { text: 'Python', link: '/backend/python/' },
            { text: 'Node.js', link: '/backend/nodejs/' },
            { text: 'NestJS', link: '/backend/nestjs/' }
          ]
        }
      ],
      '/ai/': [
        {
          text: 'AI',
          items: [
            { text: 'Overview', link: '/ai/' },
            { text: 'Prompts', link: '/ai/prompts/' },
            { text: 'Context', link: '/ai/context/' },
            { text: 'LangChain', link: '/ai/langchain/' },
            { text: 'Principles', link: '/ai/principles/' }
          ]
        }
      ],
      '/': [
        {
          text: 'Examples',
          items: [
            { text: 'Markdown Examples', link: '/markdown-examples' },
            { text: 'Runtime API Examples', link: '/api-examples' }
          ]
        }
      ]
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/Zroclee/zroclee.github.io' }
    ]
  }
})
