const webpack = require('webpack');
const path = require('path');

module.exports = {
    plugins: [
        new webpack.ContextReplacementPlugin(
            /angular(\\|\/)core/,
            root('../../app/javascript/hello_angular'),
            { }
        )
    ]
};

function root(__path) {
    return path.join(__dirname, __path);
}