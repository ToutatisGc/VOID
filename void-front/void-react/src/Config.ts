const FIXED_CONFIG =
    {
        "debug" : true,
        "nav":{
            /*永久隐藏工具栏*/
            alwaysHiddenNavBar : true,
            navBarHeight: 50,
            navBarHeightUnit:'px'
        },
        init: () =>{
            init();
        },
        getClientHeight: () =>{
            return document.body.clientHeight;
        }
    };

export function init(){
    (window as any).$FC = FIXED_CONFIG;
}

export default FIXED_CONFIG;
