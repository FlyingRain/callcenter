<template>
  <div class="home">

    <el-button size="small" type="primary" @click="login"> 登陆</el-button>
    {{ user.name }}-{{ doubleAge }}
  </div>

</template>

<script setup lang="ts">
import {storeToRefs} from "pinia";
import {userStore} from '@/store/userStore'

let store = userStore()
let {user, doubleAge} = storeToRefs(store)
let {login} = store


</script>

<style scoped>
</style>