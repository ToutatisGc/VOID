enum BrowserType {
    WX= (window.screen.availHeight - window.outerHeight),

    OTHER = 0
}

export function checkBrowser(agent:string): BrowserType{
    if (agent.indexOf("MicroMessenger") !== -1){
        return BrowserType.WX
    }else {
        return BrowserType.OTHER
    }
}