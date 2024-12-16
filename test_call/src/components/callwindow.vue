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
        <el-col :span="10">
          <el-input size="small" type="text" placeholder="请输入号码" v-model="number"/>
        </el-col>
        <el-col :span="3">
          <el-button size="small" type="primary" @click="call(number)">呼叫</el-button>
        </el-col>
        <el-col :span="3">
          <el-button size="small" type="primary" @click="hangup">挂断</el-button>
        </el-col>
      </el-row>
    </div>
    <div>
      <audio ref="audio"></audio>
    </div>
    <div>
      <el-button type="primary" @click="callTips">弹窗</el-button>
    </div>
    <calltip ref="tip" :audioRef="audio"></calltip>
  </div>


</template>

<script setup lang="ts">
import {ref} from 'vue';
import useDrag from '@/hooks/drag/dragUtil'
import JsSIP from 'jssip'
import {URI} from "jssip";
import useCall from '@/hooks/call/useCall'
import calltip from "@/components/calltip.vue"

const draggable = ref()
const {dragStart} = useDrag(draggable)

var socket = new JsSIP.WebSocketInterface('ws:/192.168.100.197:5066');
let uri = new URI("sip", '1003', '192.168.100.197', 5066);
let tip = ref()

var configuration = {
  sockets: [socket],
  uri: uri.toString(),
  password: '1234',
  contact_uri: '',
  register: true
};
uri.setParam('transport', 'ws')
configuration.contact_uri = uri.toString()
let audio = ref()

const {connectFS, makeCall, hangup} = useCall()
connectFS(configuration, (callInNumber: string) => {
  tip.value.tips_pop(callInNumber, "up")
})
let number = ref('')


function callTips() {
  if (tip.value) {
    tip.value.tips_pop(number, "up")
  }
}

function call(number: string) {
  makeCall(number, audio.value)
}

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
  width: 400px;
  cursor: move;
  background-color: skyblue;
}
</style>