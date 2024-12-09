import LoginPage from '../components/main/LoginPage.vue';
import MainPage from '../components/main/MainPage.vue';
import RegisterPage from "@/components/main/RegisterPage.vue";

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
];
