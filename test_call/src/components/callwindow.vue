<template>

  <div class="callwindow-container">
    <calltip ref="tip" :audioRef="audio"></calltip>
    <div ref="draggable" class="draggable" @mousedown="dragStart" @touchstart="dragStart">
      <el-row :gutter="12">
        <el-col :span="4">
          <span>阿波罗</span>
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
    <div class="animated-text">韭菜乐</div>

    <div style="display: flex;justify-content:center;min-width: 100vh;min-height: 100vh;">
      <div style="width: 30%">
        <p>外显号码设置</p>
        <el-input size="default" placeholder="设置要显示的号码" style="width: 200px" v-model="user.showNumber"
        />
      </div>
      <div style="border:  2px solid black;min-height: 100vh;margin: 10px">
      </div>
      <div style="height: 100%;width: 30%">
        <p>呼入转接配置</p>
        <el-button size="small" type="primary" @click="addCallInMap">增加</el-button>
        <div v-for="(item,index) in callSettings.callInMap" style="margin-top: 10px;">
          <div style="display: flex">
            <span>呼入号码：</span>
            <el-input size="default" v-model="item.callInNumber" style="width: 200px"
                      placeholder="呼入号码"></el-input>
          </div>
          <div style="display: flex">
            <span>匹配坐席：</span>
            <el-select v-model="item.agent" style="width: 200px" placeholder="选择坐席">
              <el-option label="普罗米修斯" key="1" value="1003"/>
              <el-option label="阿波罗" key="2" value="1001"/>
            </el-select>
            <el-button @click="deleteCallInMap(item)">删除</el-button>
          </div>
        </div>
        <div style="display: flex;justify-content:end;width: 100%;">
          <el-button type="primary" @click="changeSetting">应用</el-button>
        </div>
      </div>
      <div style="border:  2px solid black;min-height: 100vh;margin: 10px">
      </div>
      <div style="min-height: 100vh;width: 30%;">
        <p>转接</p>
        <div style="display: flex">
          <el-input style="width: 200px;" placeholder="转接号" v-model="transferNumber" size="small"></el-input>
          <el-button type="primary" @click="transferCall">转接</el-button>
        </div>
      </div>
    </div>

    <div>
      <audio ref="audio"></audio>
    </div>

  </div>


</template>

<script setup lang="ts">
import {ref} from 'vue';
import useDrag from '@/hooks/drag/dragUtil'
import JsSIP from 'jssip'
import {URI} from "jssip";
import useCall from '@/hooks/call/useCall'
import calltip from "@/components/calltip.vue"
import {userStore} from "@/store/userStore";
import {callStore} from "@/store/callStore";
import {storeToRefs} from "pinia";
import {makeCall, callSetting, transfer} from '@/api/callApi'

const {getCurrentCallId} = callStore()

const draggable = ref()
const {dragStart} = useDrag(draggable)
const store = userStore()
const {user} = storeToRefs(store)
console.log(user)

var socket = new JsSIP.WebSocketInterface('ws:/192.168.100.197:5066');
// var socket = new JsSIP.WebSocketInterface('ws:/111.231.64.229:5896');
//5896
let uri = new URI("sip", user.value.number, '192.168.100.197', 5066);
// let uri = new URI("sip", '1003', '111.231.64.229', 5896);
let tip = ref()

var configuration = {
  sockets: [socket],
  uri: uri.toString(),
  password: user.value.pwd,
  contact_uri: '',
  register: true
};
uri.setParam('transport', 'ws')
configuration.contact_uri = uri.toString()
let audio = ref()

const {connectFS, hangup, accept} = useCall()
connectFS(configuration, (callInNumber: string, autoReceive: boolean) => {
  if (autoReceive) {
    accept(audio.value)
  } else {
    tip.value.tips_pop(callInNumber, "up")
  }
})
let number = ref('')


function callTips() {
  if (tip.value) {
    tip.value.tips_pop(number, "up")
  }
}

function call(number: string) {
  let showNumber = user.value.showNumber
  if (!showNumber) {
    showNumber = user.value.number
  }
  makeCall({
    callerNumber: user.value.number,
    calleeNumber: number,
    showNumber: showNumber
  })
}

// let callSession = makeCall('sip:1002@192.168.0.116')


let callSettings = ref({showNumber: '', callInMap: [{callInNumber: '', agent: ''}]})

function changeSetting() {
  callSetting({
    callInMap: callSettings.value.callInMap
  })
}

function addCallInMap() {
  callSettings.value.callInMap.push({callInNumber: '', agent: ''})
}

function deleteCallInMap(item: any) {
  let index = callSettings.value.callInMap.indexOf(item)
  callSettings.value.callInMap.splice(index, 1);
}

let transferNumber = ref('')

function transferCall() {
  transfer({
    callId: getCurrentCallId(),
    targetNumber: transferNumber.value,
  })
}

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


/* 定义动画 */
@keyframes colorFade {
  0% {
    background-position: left;
    color: #ff2424; /* 开始时字体颜色透明 */
  }
  30% {
    background-position: left;
    color: #7df816; /* 中间时的目标颜色 */
  }
  60% {
    background-position: right;
    color: #fd0f87; /* 中间时的目标颜色 */
  }
  100% {
    background-position: right;
    color: #53ff29; /* 结束时字体颜色透明 */
  }
}

/* 应用动画到元素 */
.animated-text {
  animation: colorFade 5s infinite; /* 动画名称，持续时间，和重复次数 */
  font-size: 45px;
  font-weight: bold;
  min-width: 100vh;
  text-align: center;
  height: 100px;
}
</style>