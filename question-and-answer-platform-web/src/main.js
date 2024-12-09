import Vue from 'vue'
import App from './App.vue'
import router from './router'; // 引入路由配置
// import axios from './axios';

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'  // 引入 ElementUI 的样式

Vue.use(ElementUI)  // 全局使用 ElementUI
Vue.config.productionTip = false

// 全局挂载到Vue原型
// App.config.globalProperties.$axios = axios;
// 设置 axios 默认 baseURL


new Vue({
  router, // 挂载路由
  render: h => h(App),
}).$mount('#app')



