
import {createApp} from 'vue'
import {createPinia} from "pinia";
import App from './App.vue'
import axios from 'axios'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


let app = createApp(App)

const pinia = createPinia()

app.use(router)
app.use(pinia)
app.use(ElementPlus)
app.mount('#app')
