import {defineStore} from 'pinia';
import {ref, reactive, computed} from 'vue'
import {useRouter} from 'vue-router'

export const userStore = defineStore('userStore', () => {
    let user = reactive({name: '', age: ''})
    let age = ref(18)
    const router = useRouter()

    function login() {
        console.log('login')
        user.name = 'wally'
        age.value += 1
        console.log(age.value)
        router.push('/record')
    }

    let doubleAge = computed(() => {
        return age.value * 2
    })
    return {user, doubleAge, login}
})