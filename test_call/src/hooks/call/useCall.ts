import JsSIP from "jssip";
import {callStore} from "@/store/callStore";
import type {OutgoingAckEvent} from "jssip/lib/RTCSession";
import type {RTCSessionEvent} from "jssip/lib/UA";
import type {Ref} from "vue";

export default function () {
    const {setCallSession, getCallSession, setUA, getUA} = callStore()
    let coolPhone = getUA()

    function connectFS(callConfig: any, callIn: Function) {
        coolPhone = new JsSIP.UA(callConfig);
        setUA(coolPhone)
        coolPhone.on('registered', function (e: any) {
            console.log('registered!');
        });

        coolPhone.on('unregistered', function (e: any) { /* Your code here */
        });

        coolPhone.on('registrationFailed', function (e: any) {
            console.log('registrationFailed!');
            console.log(e);
        });

        // 当有呼入或者呼出的事件时触发
        coolPhone.on('newRTCSession', function (e: RTCSessionEvent) {
            const {originator, session} = e
            if (originator === 'local') {
                console.log('call outcome!');
            } else if (originator === 'remote') {
                console.log("call in ", session)
                callIn()
                console.log('call income!', originator);
            }
            console.log('newRTCSession!', e);
            setCallSession(session)
        })

        coolPhone.start()
    }

    function makeCall(number: string, audioRef: Ref) {
        let eventHandles = {
            //  来电 振铃
            'progress': (event: any) => {
                console.log('progress', event);
            },
            'failed': (event: any) => {
                console.log('failed', event);
            },
            //挂断处理
            'ended': (event: any) => {
                console.log('ended', event);
            },
            'confirmed': (event: OutgoingAckEvent) => {
                let callSession = getCallSession()
                const {connection} = callSession
                onCallOut(connection, audioRef)
            }
        };
        let options = {
            'eventHandlers': eventHandles,
            'sessionTimersExpires': 120,
            'mediaConstraints': {'audio': true, 'video': false}
        }
        coolPhone.call(number, options)
    }


    function onCallOut(connection: RTCPeerConnection, audioRef: Ref) {
        const stream = new MediaStream();
        const receivers = connection.getReceivers();
        if (receivers) {
            receivers.forEach(rec => stream.addTrack(rec.track));
        }
        if (audioRef.value) {
            audioRef.value.srcObject = stream;
            audioRef.value.play();
        }
    }

    function accept() {
        let currentSession = getCallSession()
        currentSession.answer({
            mediaConstraints: {audio: true, video: false}
        })
    }

    return {makeCall, accept,connectFS}
}