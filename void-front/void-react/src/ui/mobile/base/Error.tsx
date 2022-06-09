import { Button } from "antd-mobile";
import React, {Component} from "react";
import { connect } from 'react-redux';
import {setClientHeight} from "../../../data/Actions";
import State from "../../../data/store/State";

type Prop = {
    message?:String
    code?:number,
    hasError:boolean
}

type State = {
    errorTypes :any,
    hasError:boolean
}

class Error extends Component<Prop,State>{
    constructor(props:any) {
        super(props)
        this.state = {
            hasError:this.props.hasError,
            errorTypes : {
                E404: {
                    title : 'NOT FOUND',
                    subtitle : '页面走丢啦!'
                },
                E500: {
                    title : 'NET CONNECTION ERROR',
                    subtitle : 'Oops!加载出错啦!'
                },
            }
        }
    }

    static getDerivedStateFromError(error:any) {

    }

    backToHome(event:any){
        console.log(event)
        alert("返回首页")
    };

    render() {
        if (this.state.hasError) {
            let key = this.props.code? 'E'+this.props.code : 'E404';
            let errorType = this.state.errorTypes[key];
            let message = key.replace('E','') + ' ' + errorType.title;
            return (
                <div className='error'>
                    <div>
                        <div className='error-text'>{message}</div>
                        <div className='error-text'>{errorType.subtitle}</div>
                        {this.props.message && <div className='error-text sub-text'>{this.props.message}</div>}
                        <div style={{marginTop:'25px'}}>
                            <Button block color='primary' onClick={this.backToHome}>返回首页</Button>
                        </div>
                    </div>
                </div>
            )
        }
        return this.props.children;
    }
}

const mapStateToProps = (state:Record<string, any>) => {
    return {
        clientHeight: state.clientHeight
    }
}

// mapDispatchToProps：将dispatch映射到组件的props中
const mapDispatchToProps = (dispatch:any) => {
    return {
        setClientHeight() {
            dispatch(setClientHeight())
        }
    }
}

export default Error;

// export default connect(mapStateToProps,mapDispatchToProps)(Error);