// src/router/index.js
import Vue from 'vue';
import VueRouter from 'vue-router';


import routes from './routes';

// 注册 VueRouter 插件
Vue.use(VueRouter);



// 创建路由实例
const router = new VueRouter({
    mode: 'history', // 路由模式：history 或 hash
    routes,
});

// 路由守卫
router.beforeEach((to, from, next) => {
    const isAuthenticated = !!localStorage.getItem('authToken'); // 是否登录
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

    if (to.meta.title) {
        document.title = to.meta.title + ' - '+ '线上问答平台'// 根据路由的 meta.title 更新页面标题
    } else {
        document.title = '线上问答平台'; // 默认标题
    }
    if (requiresAuth && !isAuthenticated) {
        next({
            path: '/LoginPage',
            query: { redirect: to.fullPath }, // 将目标路径保存为查询参数
        });
    } else if ((to.path === '/LoginPage' || to.path === '/RegisterPage') && isAuthenticated) {
        next('/'); // 已登录用户重定向到主页面
    } else {
        next(); // 其他情况放行
    }
});

export default router;
