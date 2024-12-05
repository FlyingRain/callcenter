<template>

  <div class="callwindow-container">

    <div ref="draggable" class="draggable" @mousedown="dragStart" @touchstart="dragStart">
      <span>阿森松</span>
      <span style="color: green"> 在线</span>
    </div>
  </div>


</template>

<script setup lang="ts">
import {ref} from 'vue';
import useDrag from '@/components/drag/dragUtil'

const draggable = ref(null)
const {dragStart} = useDrag(draggable)
import JsSIP from 'jssip'
import {c} from "vite/dist/node/types.d-aGj9QkWt";

var socket = new JsSIP.WebSocketInterface('ws:/192.168.100.197:5066');
var configuration = {
  sockets: [socket],
  uri: 'sip:1003@172.17.0.2',
  password: '1234',
  register: true
};
var coolPhone = new JsSIP.UA(configuration);
coolPhone.on('registered', function (e) {
  console.log('registered!');
});
coolPhone.on('unregistered', function (e) { /* Your code here */
});
coolPhone.on('registrationFailed', function (e) {
  console.log('registrationFailed!');
  console.log(e);
});
coolPhone.start()
</script>


<style scoped>
.asd {
  background-color: skyblue;
  position: absolute;
  width: 300px;
  cursor: move;
}

.draggable {
  position: absolute;
  width: 200px;
  cursor: move;
  background-color: skyblue;
}
</style>