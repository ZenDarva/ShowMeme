module.exports = {
    devServer: {
        proxy: {
            "/api/account": {
                target: "http://localhost:8080",
                logLevel: 'debug',
            },
            "/api/post": {
                target: "http://localhost:8082",
                logLevel: 'debug'
            }
        }
    }
};