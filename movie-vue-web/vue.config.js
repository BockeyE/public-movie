module.exports = {
  devServer: {
    host: "localhost",
    port: 80,
    proxy: { //匹配规则
      '/api': {
        //要访问的跨域的域名
        target: 'http://localhost:8081',
        ws: true,
        secure:false, // 使用的是http协议则设置为false，https协议则设置为true
        changOrigin: true, //开启代理
        pathRewrite: {
          '^/': ''
        }
      }
    }
  }
};
