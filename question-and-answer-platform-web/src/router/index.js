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
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth); // 目标路由是否需要认证

    // 根据路由的 meta.title 更新页面标题
    if (to.meta.title) {
        document.title = to.meta.title + ' - ' + '线上问答平台'; // 根据路由的 meta.title 更新页面标题
    } else {
        document.title = '线上问答平台'; // 默认标题
    }

    // 如果目标路由需要认证，且用户未登录
    if (requiresAuth && !isAuthenticated) {
        // 保存重定向路径到查询参数
        next({
            path: '/LoginPage',
            query: { redirect: to.fullPath }, // 将目标路径保存为查询参数
        });
    } else if ((to.path === '/LoginPage' || to.path === '/RegisterPage') && isAuthenticated) {
        // 如果用户已经登录，且访问的是登录或注册页面，则跳转到主页面
        next('/');
    } else {
        next(); // 其他情况放行
    }
});


export default router;
