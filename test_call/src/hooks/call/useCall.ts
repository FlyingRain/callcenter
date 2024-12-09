import JsSIP from "jssip";
import type {RTCSessionEvent} from "jssip/lib/UA";

export default function (callConfig: any) {

    let coolPhone = new JsSIP.UA(callConfig);

    coolPhone.on('registered', function (e) {
        console.log('registered!');
    });

    coolPhone.on('unregistered', function (e) { /* Your code here */
    });

    coolPhone.on('registrationFailed', function (e) {
        console.log('registrationFailed!');
        console.log(e);
    });

    // 当有呼入或者呼出的事件时触发
    coolPhone.on('newRTCSession', function (e: any) {
        if (e.session._direction == 'outgoing') {
            console.log('call outcome!');
        } else {
            console.log('call income!', e.session._direction);
        }
        console.log('newRTCSession!', e);
    })

    coolPhone.start()

    function makeCall(number: string) {
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
            'confirmed': (event: any) => {
                console.log('confirmed', event);
            }
        }
        let options = {
            'eventHandlers': eventHandles,
            'sessionTimersExpires':120,
            'mediaConstraints': {'audio': true, 'video': false}
        }
        return coolPhone.call(number, options);
    }

    return {makeCall}
}