import LoginPage from '@/views/auth/LoginPage.vue';
import MainPage from '@/views/main/MainPage.vue';
import RegisterPage from "@/views/auth/RegisterPage.vue";
import personalHome from "@/views/auth/personalHome.vue";

export default [
    {
        path: '/LoginPage',
        name: 'LoginPage',
        component: LoginPage,
        meta: { title: '登录页面' },
    },
    {
        path: '/',
        name: 'MainPage',
        component: MainPage,
        meta: { requiresAuth: true},
    },
    {
        path: '/RegisterPage',
        name: 'RegisterPage',
        component: RegisterPage,
        meta: { title: '注册页面' },
    },
    {
        path: '/personalHome',
        name: 'personalHome',
        component: personalHome,
    },
];
