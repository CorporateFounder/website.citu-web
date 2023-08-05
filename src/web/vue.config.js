const path = require('path')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const TerserPlugin = require('terser-webpack-plugin')
const isProduction = process.env.NODE_ENV === 'production'

function resolve(dir) {
	return path.join(__dirname, dir)
}
const cdn = {
	externals: {
		vue: 'Vue',
		'vue-router': 'VueRouter',
		axios: 'axios'
	},
	js: [
		'https://cdn.bootcss.com/vue/2.6.11/vue.runtime.min.js',
		// 'https://cdn.bootcss.com/vue-router/3.1.2/vue-router.min.js',
		'https://cdn.bootcss.com/axios/0.19.2/axios.min.js'
	],
	css: []
}
module.exports = {
	// devServer: {
	// 	proxy: {
	// 		'/api': {
	// 			target: 'http://192.168.2.97:3001',
	// 			changeOrigin: true,
	// 			ws: true,
	// 			pathRewrite: {
	// 				'^/api': ''
	// 			}
	// 		}
	// 	}
	// },
	productionSourceMap: isProduction ? false : true,
	assetsDir: 'static',
	indexPath: 'index.html',
	lintOnSave: true,
	configureWebpack: (config) => {
		config.externals = cdn.externals
		config.output.filename = `static/js/[name].[hash].js`
		config.output.chunkFilename = `static/js/[name].[hash].js`
		if (isProduction) {
			const productionGzipExtensions = /\.(js|css|json|txt|html|ico|svg)(\?.*)?$/i
			config.plugins.push(
				new CompressionWebpackPlugin({
					filename: '[file].gz[query]',
					algorithm: 'gzip',
					test: productionGzipExtensions,
					threshold: 10240,
					minRatio: 0.8
				})
			)
			config.optimization = {
				minimizer: [
					new TerserPlugin({
						terserOptions: {
							ecma: undefined,
							warnings: false,
							parse: {},
							compress: {
								drop_console: true,
								drop_debugger: false,
								pure_funcs: ['console.log']
							}
						}
					})
				]
			}
		}
	},
	chainWebpack: (config) => {
		config.plugin('html').tap((args) => {
			args[0].cdns = `${cdn.js
				.map((item) => {
					return `<script src="${item}" crossorigin="anonymous"></script>`
				})
				.join('')}`
			return args
		})
		config.optimization.splitChunks({
			chunks: 'all',
			minSize: 30000,
			maxSize: 100000,
			minChunks: 1,
			maxAsyncRequests: 5,
			maxInitialRequests: 3,
			automaticNameDelimiter: '~',
			name: true,
			cacheGroups: {
				vendors: {
					test: /[\\/]node_modules[\\/]/,
					minChunks: 5,
					priority: 10,
					reuseExistingChunk: true
				},
				default: {
					minChunks: 2,
					priority: -20,
					reuseExistingChunk: true
				}
			}
		})
		config.resolve.alias
			.set('@', resolve('src'))
			.set('img', resolve('src/assets'))
			.set('apis', resolve('src/api'))
			.set('utils', resolve('src/utils'))
		// delete prefetch plugin
		config.plugins.delete('prefetch')
	}
}
