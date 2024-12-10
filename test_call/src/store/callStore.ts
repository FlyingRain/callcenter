import {defineStore} from "pinia";
import type {RTCSession} from "jssip/lib/RTCSession";

export const callStore = defineStore("callStore", () => {

    let callSession: any = undefined;

    function setCallSession(session: any) {
        callSession = session
    }

    function getCallSession() {
        return callSession
    }

    return {callSession, setCallSession,getCallSession}

})