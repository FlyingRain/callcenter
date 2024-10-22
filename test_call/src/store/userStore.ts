import {defineStore} from 'pinia';
import {ref, reactive, computed} from 'vue'


export const userStore = defineStore('userStore', () => {
    let user = reactive({name: '', age: ''})
    let age = ref(18)

    function login() {
        console.log('login')
        user.name = 'wally'
        age.value+=1
        console.log(age.value)
    }

    let doubleAge = computed(() => {
        return age.value * 2
    })
    return {user, doubleAge, login}
})