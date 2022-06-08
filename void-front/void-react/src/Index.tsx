import FIXED_CONFIG from "./Config";
import React, { Suspense } from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Provider } from 'react-redux'
import store from './data/Index'
import reportWebVitals from './reportWebVitals';
import './css/index-mobile.scss';
import Root from './ui/mobile/Root';
import Error from "./ui/mobile/base/Error";
import {TabBar} from "antd-mobile";
import {UnorderedListOutline} from "antd-mobile-icons";
import {checkBrowser} from "./js/SupportLib";
import Debugger from "./ui/dev/Debugger";

FIXED_CONFIG.init()

type State = {
    tabs : any[],
    height:string,
    hasError:boolean,
    errorInfo:string
}

class Index extends React.Component<any,State>{

    constructor(props:any) {
        super(props);
        this.state = {
            hasError: false,
            errorInfo: '',
            tabs : [
                {
                    key: '/a',
                    title: '首页',
                },
                {
                    key: '/todo',                                                                                                                                                                           
                    title: '我的待办',
                    icon: <UnorderedListOutline />,
                },
            ],
            height : '0'
        }

    }
    render() {
        if (this.state.hasError){
            return <Error hasError code={500} message={this.state.errorInfo} />
        }
        return (
            <React.StrictMode>
                <Error hasError={false}>
                    <Suspense fallback={<div>Loading...</div>}>
                        <Provider store={store}>
                            {FIXED_CONFIG.debug && <Debugger/>}
                            <BrowserRouter>
                                <Routes>
                                    <Route path="/" element={<Root height={this.state.height}/>}/>
                                    <Route path="*" element={<Error hasError message="您访问的页面不存在"/>}/>
                                </Routes>
                            </BrowserRouter>
                            <TabBar className="void-nav void-nav__with_border"
                                    style={{height:FIXED_CONFIG.nav.navBarHeight+FIXED_CONFIG.nav.navBarHeightUnit}}
                                    safeArea>
                                {this.state.tabs.map(item => (
                                    <TabBar.Item key={item.key} icon={item.icon} title={item.title} />
                                ))}
                            </TabBar>
                        </Provider>
                    </Suspense>
                </Error>
            </React.StrictMode>
        );
    }

    componentDidMount() {
        // 推送redux窗体整体高度
        let _this = this;
        let availHeight = 0;
        let browserType2Height = checkBrowser(window.navigator.userAgent);
        if (browserType2Height != null){
            availHeight = browserType2Height
        }

        function setClientHeight(){
            /*TODO ISSUE 高度计算组件传递有问题*/
            if (FIXED_CONFIG.nav.alwaysHiddenNavBar){
                document.body.style.height =
                    (window.screen.availHeight - FIXED_CONFIG.nav.navBarHeight-availHeight)
                    +FIXED_CONFIG.nav.navBarHeightUnit;
                _this.setState({
                    height: document.body.style.height
                })
            }
        }
        setClientHeight();
        window.onresize = () =>{
            setClientHeight();
        }
    }

    componentDidCatch(error: any, errorInfo: React.ErrorInfo) {
        //捕获异常
        this.setState({
            hasError: true
        })
        console.warn(error)
    }
}

ReactDOM.render(<Index/>, document.getElementById('root'),
    function (){
    // console.log("渲染回调")
        }
);

store.subscribe(() =>{
    console.log(store.getState().clientHeight)
})


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
