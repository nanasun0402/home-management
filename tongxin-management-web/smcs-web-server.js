var path = require('path');
var express = require('express');
var serveStatic = require('serve-static');
var app = express();
var indexPath = path.join(__dirname, 'dist/index.html');
var staticPath = path.join(__dirname, 'dist/static');
app.use('/smcs/web/*', serveStatic(indexPath));
app.use('/smcs/static', serveStatic(staticPath));
console.log('indexPath: ', indexPath, '\nstaticPath: ', staticPath)

var proxy = require('http-proxy-middleware');

var apiProxy = proxy('/api', {
    target: 'http://180.76.244.97:9092/tongxinrs/api/v1',
    changeOrigin: true,
    prependPath: false
});
app.use('/api', apiProxy);

var port = 9095;
app.listen(port, function() {
    console.log("Caring listening " + port);
})
