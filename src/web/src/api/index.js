import http from '@/utils/request'
const modulesFiles = require.context('./modules', true, /\.js$/)
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
	const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
	const value = modulesFiles(modulePath)
	modules[moduleName] = value.default
	return modules
}, {})
let Api = {}
Object.keys(modules).forEach((item) => {
	Object.assign(Api, modules[item])
})

const injectRequest = (apis) => {
	const request = {}
	Object.keys(apis).forEach((item) => {
		const { method, url } = apis[item]
		request[item] = (dataOrParams = {}) => {
			const param = ['PUT', 'POST', 'PATCH'].includes(method.toUpperCase()) ? dataOrParams : { params: dataOrParams }
			return http[method.toLowerCase()](url, param)
		}
	})
	return request
}
export default injectRequest(Api)
