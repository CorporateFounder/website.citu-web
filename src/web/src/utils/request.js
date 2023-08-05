import axios from 'axios'
import baseConfig from '@/config'
const http = axios.create({
	baseURL: `${baseConfig.baseUrl}`,
	timeout: 60000
})

http.interceptors.request.use(
	(config) => {
		const params = {
			'Content-Type': 'application/json'
		}
		config.header = {
			...params
		}
		return config
	},
	(config) => {
		return Promise.reject(config)
	}
)

http.interceptors.response.use(
	({ data, status }) => {
		if (status == 200) {
			return Promise.resolve(data)
		}
	},
	(error) => {
		console.log(error, 'error')
		return Promise.reject(error)
	}
)

export default http
