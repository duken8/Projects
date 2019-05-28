const { environment } = require('@rails/webpacker');

environment.loaders.append('html', {
    test: /\.html$/,
    exclude: /node_modules/,
    loaders: ['html-loader']
});

environment.loaders.append('style', {
    test: /\.(scss|sass|css)$/,
    use: [{
        loader: "to-string-loader"
    }, {
        loader: "css-loader"
    }, {
        loader: "resolve-url-loader"
    }, {
        loader: "sass-loader"
    }]
}
);
module.exports = environment;