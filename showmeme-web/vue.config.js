module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    proxy: {
      "/api/post": {
        target: "http://127.0.0.1:8082",
        logLevel: 'debug'
      },
      "/api/auth": {
        target: "http://127.0.0.1:8080",
        logLevel: 'debug'
      },
      "/api/comment": {
        target: "http://127.0.0.1:8082",
        logLevel: 'debug'
      },


    }
  }
}
