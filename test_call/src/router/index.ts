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
            path: '/callwindow',
            name: 'callwindow',
            component: () => import('@/components/callwindow.vue')
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/pages/login.vue'),
            children: [
                {
                    path: 'record',
                    name: 'record',
                    component: () => import('@/pages/record.vue')
                }
            ]
        },
        {
            path: '/',
            redirect: '/index',
        }
        ]
    })

export default router