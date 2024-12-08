import {defineStore} from "pinia";

export const callStore = defineStore("callStore", () => {

    let callSession = undefined

    function setCallSession(session: any) {
        callSession = session
    }

    return {callSession, setCallSession}

})