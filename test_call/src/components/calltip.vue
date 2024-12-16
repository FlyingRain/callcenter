<template>
  <div>
    <div ref="MsgPop" class="calltip">
      <div class="title">
        <span>来电：{{ phone }}</span>
      </div>
      <div class="option">
        <el-button size="small" type="primary" @click="acceptCall">接听</el-button>
        <el-button size="small" type="danger" @click="hangupCall">挂断</el-button>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted} from "vue";
import useCall from "@/hooks/call/useCall"

const {accept,hangup} = useCall()
const props = defineProps(["audioRef"])

let show: number = 0, hide: number = 0;
let MsgPop = ref();//获取窗口这个对象,即ID为winpop的对象
let phone = ref('');

function tips_pop(tel: string, direction: string): void {
  console.log('callin start to pop:', MsgPop);
  console.log('callin start to pop:', tel,direction);
  if (!MsgPop) {
    return
  }
  phone.value = tel
  let popH = parseInt(MsgPop.value.style.height);//用parseInt将对象的高度转化为数字,以方便下面比较
  if (popH == 0) {//如果窗口的高度是0
    MsgPop.value.style.display = "block";//那么将隐藏的窗口显示出来
    show = setInterval(function () {
      changeH(direction)
    }, 2);//开始以每0.002秒调用函数changeH("up"),即每0.002秒向上移动一次

  } else if (direction === 'down') {//否则
    hide = setInterval(function () {
      changeH(direction)
    }, 2);//开始以每0.002秒调用函数changeH("down"),即每0.002秒向下移动一次
  } else {
    console.log("unknown direction", direction)
  }
}

function changeH(str: string) {
  if (!MsgPop) {
    return
  }
  var popH = parseInt(MsgPop.value.style.height);
  if (str == "up") {     //如果这个参数是UP
    if (popH <= 100) {    //如果转化为数值的高度小于等于100
      MsgPop.value.style.height = (popH + 4).toString() + "px";//高度增加4个象素
    } else {
      console.log("show", show)
      clearInterval(show);//否则就取消这个函数调用,意思就是如果高度超过100象度了,就不再增长了
    }
  }
  if (str == "down") {
    if (popH >= 4) {//如果这个参数是down
      MsgPop.value.style.height = (popH - 4).toString() + "px";//那么窗口的高度减少4个象素
    } else {//否则
      clearInterval(hide);    //否则就取消这个函数调用,意思就是如果高度小于4个象度的时候,就不再减了
      MsgPop.value.style.display = "none";  //因为窗口有边框,所以还是可以看见1~2象素没缩进去,这时候就把DIV隐藏掉
    }
  }
}

function acceptCall() {
  console.log('audio:',props.audioRef)
  accept(props.audioRef)
  tips_pop("","down")
}

function hangupCall(){
  hangup()
  tips_pop("","down")
}

defineExpose({tips_pop})

onMounted(() => {
  MsgPop.value.style.height = 0
})
</script>

<style scoped>
.calltip {
  position: absolute;
  width: 200px;
  height: 0;
  right: 0;
  bottom: 0;
  border: 1px solid #4d2d00;
  margin: 0;
  padding: 1px;
  overflow: hidden;
  display: none;
  background: #EEEEEE;
}

.title {
  width: 100%;
  height: 30px;
  background-color: skyblue;
  text-align: center;
}

.option {
  width: 100%;
  height: 100%;
  background-color: azure;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>