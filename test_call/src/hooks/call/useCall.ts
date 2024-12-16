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
                callIn((session as any)._request?.from.uri.user)
                console.log('call income!', originator);
            }
            console.log('newRTCSession!', e);
            setCallSession(session)
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
        coolPhone.call(number, options)
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
            audioRef.srcObject = stream;
            audioRef.play();
        }
    }

    function accept(audioRef:any) {
        let currentSession = getCallSession()
        currentSession.answer({
            mediaConstraints: {audio: true, video: false}
        })
        currentSession.on('ended', function (e: any) {
            console.log('callin ended!', e)
        })

        currentSession.on('progress', function(e:any) {
            console.log('callin progress', e);
            // 获取自定义变量
            let vars = e.data.headers['Variables'];
            if (vars) {
                // 解析变量
                let customVars = vars.split(';').reduce((acc:any, pair:any) => {
                    let [key, value] = pair.split('=');
                    acc[key] = value;
                    return acc;
                }, {});
                console.log('自定义变量:', customVars);
            }
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