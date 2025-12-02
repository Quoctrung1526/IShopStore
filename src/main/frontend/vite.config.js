import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  base: '/',
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:9090',
        changeOrigin: true
      }
    }
  },
  // Thêm dòng này để dev không bị lỗi khi reload
  preview: {
    port: 5173
  }
})