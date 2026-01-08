import { createApp } from 'vue'
import router from "./router/index.js"
import { createPinia } from 'pinia'
import { createVfm } from 'vue-final-modal'
import App from './App.vue'
import 'vue-final-modal/style.css'
import './assets/main.css'

const pinia = createPinia();
const vfm = createVfm()

createApp(App)
  .use(pinia)
  .use(vfm)
  .use(router)
  .mount('#app')