import {createRouter, createWebHistory} from 'vue-router'

let router = createRouter({
    history:createWebHistory(""),
    routes:[
        {
            path:'/index',
            name:'index',
            component: ()=>import('@/pages/index.vue')
        },
        {
            path:'/record',
            name:'record',
            component:()=>import('@/pages/record.vue')
        }
    ]
})

export default router