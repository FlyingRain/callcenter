import request from '@/api/requests'
import {ElMessage} from "element-plus";

export function makeCall(data: any) {
    request.post('/makeCall', data,).then(response => {
        if (response.status === 200) {
            ElMessage({
                message: "拨打成功",
                type: 'success',
                duration: 2000 // 持续时间，单位毫秒
            });
        } else {
            ElMessage({
                message: response.data.msg,
                type: 'success',
                duration: 2000 // 持续时间，单位毫秒
            });
        }
    })
}

export function callSetting(setting: any) {
    request.post('/callSettings', setting).then(response => {
        if (response.status === 200) {
            ElMessage({
                message: "操作成功",
                type: 'success',
                duration: 2000 // 持续时间，单位毫秒
            });
        } else {
            ElMessage({
                message: response.data.msg,
                type: 'success',
                duration: 2000 // 持续时间，单位毫秒
            });
        }
    })
}

export function transfer(transferRequest: any) {
    request.post('/transfer', transferRequest).then(response => {
        if (response.status === 200) {
            ElMessage({
                message: "转接成功",
                type: 'success',
                duration: 2000 // 持续时间，单位毫秒
            });
        } else {
            ElMessage({
                message: response.data.msg,
                type: 'success',
                duration: 2000 // 持续时间，单位毫秒
            });
        }
    })
}