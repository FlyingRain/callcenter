<script setup lang="ts">

</script>

<template>
  <div class="content">
    <div class="sideBar">
      <el-menu default-active="" router="true">
        asd
        <el-sub-menu index="1">
          <template #title>
            <span>话务平台</span>
          </template>
          <el-menu-item index="/login">总览</el-menu-item>
          <el-menu-item index="/login/record">记录</el-menu-item>
          <el-menu-item index="/callwindow">操作</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="2">渠道配置</el-menu-item>
      </el-menu>
    </div>
    <div class="view">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>

.content {
  background-color: aquamarine;
  min-height: 100vh;
  display: flex;
}

.sideBar {
  width: 300px;
  background-color: dodgerblue;
  height: 100%;
}
.view{
  min-height: 100vh;
  min-width: 100vw;
  background-color: sandybrown;
}
</style>