
<template>
  <div class="all">
    <div style="display: flex;width: 100%;height: 100%;background-color: red;position:absolute;">
      <div class="route">
        <RouterLink active-class="choose" class="link" to="/index">首页</RouterLink>
        <RouterLink active-class="choose" class="link" to="/record">记录</RouterLink>
      </div>
      <div class="content">
        <RouterView/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">


</script>


<style scoped>
.route{
  background-color: lightblue;
  width: 20%;
  height: 100%;
  display: inline-block;
}
.content{
  background-color: green;
  height: 100%;
}
.link{
  font-size: 24px;
}
.choose{
  color: red;
}
.all{
  top:0;
  left: 0;
  min-width: 100%;
  min-height: 100%;
  position: absolute;
  background: yellow;
}
</style>
