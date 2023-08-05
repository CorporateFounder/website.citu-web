import Vue from 'vue'
import App from './App.vue'
import plugin from '@/plugins'
import '@/assets/main.css'
new Vue({
	...plugin,
	render: (h) => h(App)
}).$mount('#app')
