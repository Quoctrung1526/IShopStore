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
    // Đảm bảo cấu hình proxy chính xác
    proxy: {
      '/api': {
        target: 'http://localhost:9090', // <-- PHẢI LÀ CỔNG SPRING BOOT ĐANG CHẠY
        changeOrigin: true
      }
    }
  },
  // Cấu hình Build cho Spring Boot
  build: {
    outDir: '../../resources/static',
    emptyOutDir: true,
    minify: true,
    assetsDir: 'assets',
  }
})