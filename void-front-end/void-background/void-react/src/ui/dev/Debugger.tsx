import React from "react";
import '@/css/dev.scss'

class Debugger extends React.Component<any, any>{

    private timerID?: NodeJS.Timer;
    constructor(props:any) {
        super(props);
        this.state = {
            date: new Date(),
            hiddenInfo:false,
            localIp:"N/A",
            gamma:0
        };
    }

    componentDidMount() {
        let _this = this;
        this.timerID = setInterval(() => _this.setState({date: new Date()}), 1000);

        function updateGravity(event:DeviceOrientationEvent) {
            _this.setState({gamma:event.gamma})
        }
        //
        // window.addEventListener('deviceorientation', updateGravity);
        //
        // window.addEventListener("compassneedscalibration", function(event) {
        //     alert('您的罗盘需要校准，请将设备沿数字8方向移动。');
        //     event.preventDefault();
        // }, true);

        this.getUserIP(function (ip:any){
            if (ip){
                _this.setState({
                    localIp: ip
                })
            }
        })
    }

    componentWillUnmount() {
        clearInterval(this.timerID as NodeJS.Timeout)
    }

    hiddenOther = (event:any) => {
        // alert(this.state.hiddenInfo)
        this.setState({hiddenInfo:!this.state.hiddenInfo})
    };

    render() {
        return (
            <div className="debugger-window">
                <p className="debugger-window_title debugger-button" onClick={this.hiddenOther}>=调试器=</p>
                <p>TIME:{this.state.date.toLocaleString()+":"+(Date.now()/1000).toFixed(0)}</p>
                <div style={{display:this.state.hiddenInfo? 'none' : 'inherit'}} className={"debugger-info"}>
                    <p>AVAIL_H:{window.screen.availHeight}</p>
                    <p>AVAIL_W:{window.screen.availWidth}</p>
                    <p>LOCAL_IP:{this.state.localIp}</p>
                    <p>URL:{window.location.href}</p>
                    <p>GAMMA:{this.state.gamma}</p>
                    <div style={{width:'45%'}}>
                        <div className="debugger-window_title">UA信息</div>
                        <div>{window.navigator.userAgent}</div>
                    </div>
                </div>
                <iframe id="iframe" sandbox="allow-same-origin" style={{display:"none"}}/>
            </div>
        );
    }

    getUserIP(receiveFunc:Function) {
        // @ts-ignore
        let rtcPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
        if (!rtcPeerConnection){
            return;
        }
        let connection = new rtcPeerConnection({iceServers: []}),
            noop = function() {},
            localIPs = {} as any,
            ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g, key;

        function iterateIP(ip:any) {
            if (!localIPs[ip]){receiveFunc(ip);}
            localIPs[ip] = true;
        }

        connection.createDataChannel("");

        connection.createOffer().then(function(rtcSessionDes:any) {
            rtcSessionDes.sdp.split('\n').forEach(
                function(line:any) {
                    if (line.indexOf('candidate') < 0) {
                        return;
                    }
                    line.match(ipRegex).forEach(iterateIP);
                });
            connection.setLocalDescription(rtcSessionDes, noop, noop);
        });

        connection.onicecandidate = function(ice) {
            if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
            // @ts-ignore
            ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
        };
    }

}

export default Debugger;