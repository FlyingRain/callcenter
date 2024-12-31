import {defineStore} from 'pinia';
import {ref, reactive, computed} from 'vue'
import {useRouter} from 'vue-router'

export const userStore = defineStore('userStore', () => {
    let user = reactive({name: '', age: '', number: '', pwd: '', showNumber: ''})
    let age = ref(18)
    const router = useRouter()

    function login(data: any) {
        console.log('login')
        router.push('/callwindow')
    }

    let doubleAge = computed(() => {
        return age.value * 2
    })
    return {user, doubleAge, login}
})