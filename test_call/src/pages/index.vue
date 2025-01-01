<template>
  <div class="home">

    <div class="login-container">
      <div class="item" style="display: flex; align-items: center;justify-content: center;margin: 10px">
        <span style="width: 80px">用户名:</span><el-input placeholder="用户名" style="width: 180px" v-model="user.number"></el-input>
      </div>
      <div class="item" style="display: flex; align-items: center;justify-content: center;margin: 10px">
        <span style="width: 80px">密码:</span><el-input placeholder="密码" style="width: 180px" v-model="user.pwd" type="password"></el-input>
      </div>
      <div style="display: flex; justify-content: center;margin: 10px;width: 100%">
        <el-button size="small" type="primary" @click="login"> 登陆</el-button>

      </div>

    </div>
  </div>

</template>

<script setup lang="ts">
import {storeToRefs} from "pinia";
import {userStore} from '@/store/userStore'



let store = userStore()
let {user} = storeToRefs(store)
let {login} = store


</script>

<style scoped>
.home {
  display: flex;
  align-items: center;
  min-height: 100vh;
  min-width: 100vw;
  background-image: url('@/assets/IMG_0811.PNG');
  background-repeat: no-repeat;
  background-size: cover;
}

.login-container {
  width: 500px;
  height: 400px;
  margin: auto;
}
</style>