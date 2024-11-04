import {createRouter, createWebHistory} from 'vue-router'

let router = createRouter({
    history: createWebHistory(""),
    routes: [
        {
            path: '/index',
            name: 'index',
            component: () => import('@/pages/index.vue')
        },
        {
            path: '/record',
            name: 'record',
            component: () => import('@/pages/record.vue')
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/pages/login.vue')
        }
    ]
})

export default router