import JsSIP from "jssip";
import {callStore} from "@/store/callStore";
import type {OutgoingAckEvent} from "jssip/lib/RTCSession";
import type {RTCSessionEvent} from "jssip/lib/UA";

export default function () {
    const {setCallSession, getCallSession, setUA, getUA, setCurrentCallId} = callStore()
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
            const {originator, session, request} = e
            let callId = request.getHeader("Call-ID")
            console.log('callId:', callId)
            setCurrentCallId(callId)
            setCallSession(session)
            if (originator === 'local') {
                console.log('call outcome!');
            } else if (originator === 'remote') {
                callIn((session as any)._request?.from.uri.user, request.getHeader("X-My-Header") === "2222")
            }
            console.log('newRTCSession!', e);
        })


        coolPhone.start()
    }

    function makeCall(number: string, audioRef: any) {
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
                bindCallStream(audioRef)
            }
        };
        let options = {
            'eventHandlers': eventHandles,
            'sessionTimersExpires': 120,
            'mediaConstraints': {'audio': true, 'video': false}
        }
        let pcConfig = {
            iceServers: [
                {
                    urls: "stun:stun.l.google.com:19302",
                },
            ],
        }
        coolPhone.call(number, options, pcConfig)
    }


    function bindCallStream(audioRef: any) {
        let callSession = getCallSession()
        const {connection} = callSession
        const stream = new MediaStream();
        const receivers = connection.getReceivers();
        if (receivers) {
            receivers.forEach((rec: any) => stream.addTrack(rec.track));
        }
        if (audioRef) {
            console.log('audio:', audioRef)
            audioRef.srcObject = stream;
            audioRef.play();
        }
    }

    function accept(audioRef: any) {
        let currentSession = getCallSession()
        console.log('start to answer call')
        currentSession.answer({
            mediaConstraints: {audio: true, video: false}
        })
        console.log('end answer call')

        currentSession.on('ended', function (e: any) {
            console.log('callin ended!', e)
        })

        currentSession.on('progress', function (e: any) {
            console.log('callin progress', e);
        });
        currentSession.on('confirmed', function (e: any) {
            console.log('callin confirmed!', e)
            bindCallStream(audioRef)
        })
    }

    function hangup() {
        const {getCallSession} = callStore()
        let session = getCallSession()
        session.terminate()
    }

    return {makeCall, accept, connectFS, hangup}
}