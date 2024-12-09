<template>

  <div class="callwindow-container">

    <div ref="draggable" class="draggable" @mousedown="dragStart" @touchstart="dragStart">
      <el-row :gutter="12">
        <el-col :span="4">
          <span>阿森松</span>
        </el-col>
        <el-col :span="4">
          <span style="color: green"> 在线</span>
        </el-col>
        <el-col :span="12">
          <el-input size="small" type="text" placeholder="请输入号码" v-model="number"/>
        </el-col>
        <el-col :span="3">
          <el-button size="small" type="primary" @click="makeCall(number)">呼叫</el-button>
        </el-col>
      </el-row>
    </div>
  </div>


</template>

<script setup lang="ts">
import {ref} from 'vue';
import useDrag from '@/hooks/drag/dragUtil'
import JsSIP from 'jssip'
import {URI} from "jssip";
import useCall from '@/hooks/call/useCall'

const draggable = ref(null)
const {dragStart} = useDrag(draggable)



var socket = new JsSIP.WebSocketInterface('ws:/192.168.0.111:5066');
let uri = new URI("sip", '1003', '192.168.0.116',5066);

console.log(uri.toString())
var configuration = {
  sockets: [socket],
  uri: uri.toString(),
  password: '1234',
  contact_uri: '',
  register: true
};
uri.setParam('transport','ws')
configuration.contact_uri = uri.toString()

const {makeCall}  = useCall(configuration)

let number = ref('')

// let callSession = makeCall('sip:1002@192.168.0.116')
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
  width: 380px;
  cursor: move;
  background-color: skyblue;
}
</style>