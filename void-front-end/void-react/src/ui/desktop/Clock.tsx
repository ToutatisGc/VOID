import React from "react";
import cstyle from '@/css/modules/clock.module.scss'


type State = {
    date:Date
}

class Clock extends React.Component<any,State> {
    private timerID?: NodeJS.Timer;
    constructor(props:any) {
        super(props);
        this.state = {date: new Date()};
    }

    componentDidMount() {
        let _this = this;
        this.timerID = setInterval(
            () => _this.tick(),
            1000
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID as NodeJS.Timeout)
        console.log(this.timerID)
    }


    tick(){
        this.setState({
            date: new Date()
        });
    }

    render() {
        return (
            <div className={cstyle.clockText}>
                <div>Hello, World!</div>
                <div>{this.state.date.toLocaleTimeString()}</div>
            </div>
        );
    }
}

export default Clock;