module.exports = {
    outputDir: '../src/main/resources/static',
    indexPath: '../templates/index.html',  // Nếu dùng Thymeleaf
    publicPath: '/',
    devServer: {
        port: 8081,
        proxy: {
            '/api': {
                target: 'http://localhost:9090',
                changeOrigin: true
            }
        }
    }
}