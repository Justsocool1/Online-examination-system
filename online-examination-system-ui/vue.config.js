const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    // 由之前的 'localhost'改为如下，端口默认8080
     host:"0.0.0.0",   
  }   
})

