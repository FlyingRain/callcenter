import {defineStore} from "pinia";
import type {RTCSession} from "jssip/lib/RTCSession";

export const callStore = defineStore("callStore", () => {

    let callSession: any = undefined;

    let currentUA: any = undefined;

    let currentCallId = '';

    function setCallSession(session: any) {
        callSession = session
    }

    function getCallSession() {
        return callSession
    }

    function setUA(ua: any) {
        currentUA = ua
    }

    function getUA(): any {
        return currentUA
    }

    function setCurrentCallId(callId:string){
        currentCallId = callId
    }

    function getCurrentCallId():string {
        return currentCallId
    }
    return {callSession, setCallSession, getCallSession, setUA,getUA,getCurrentCallId,setCurrentCallId}

})