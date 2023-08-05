import Vue from 'vue'
import VueI18n from 'vue-i18n'
import zh from './modules/zh'
import en from './modules/en'
Vue.use(VueI18n)
const i18n = new VueI18n({
	locale: localStorage.getItem('locale') || 'en',
	messages: {
		en: {
			...en
		},
		zh: {
			...zh
		}
	}
})
export default i18n
