// https://vitepress.dev/guide/custom-theme
import { h } from 'vue'
import type { Theme } from 'vitepress'
import DefaultTheme from 'vitepress/theme'
import './style.css'
import TerminalHome from './components/TerminalHome.vue'

export default {
  extends: DefaultTheme,
  Layout: () => {
    return h(DefaultTheme.Layout, null, {
      // https://vitepress.dev/guide/extending-default-theme#layout-slots
      'home-hero-image': () => h(TerminalHome)
    })
  },
  enhanceApp({ app, router, siteData }) {
    app.component('TerminalHome', TerminalHome)
  }
} satisfies Theme
