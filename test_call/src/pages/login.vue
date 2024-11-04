<script setup lang="ts">

</script>

<template>
  <div class="content">
    <div class="sideBar">
      <el-menu >
        asd
        <el-sub-menu index="1">
          <template #title>
            <span>话务平台</span>
          </template>
          <el-menu-item index="1-2" >总览</el-menu-item>
          <el-menu-item index="1-3">记录</el-menu-item>
          <el-menu-item index="1-4">操作</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="2">渠道配置</el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<style scoped>

.content{
  background-color: aquamarine;
  min-height: 100vh;
}
.sideBar{
  position: fixed;
  width: 300px;
  background-color: dodgerblue;
  height: 100%;
}
</style>