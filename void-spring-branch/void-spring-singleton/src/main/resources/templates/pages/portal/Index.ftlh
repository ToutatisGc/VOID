<#assign projectPath=request.contextPath/>

<#import "/modules/init/INIT.ftlh" as INIT >

<@INIT.DOC_INIT title=title > <#--不可删除-->
    <#--填充head头-->
<#--    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue/3.2.41/vue.global.js"></script>-->
</@INIT.DOC_INIT> <#--不可删除-->
<body>
<div v-cloak id="app" class="xv-base-background">
    <br style="line-height:0;"/>
    <div class="xv-horizontal-center xv-box xv-box_shadow xv-top-window">
        <div class="xv-flex-box">
            <img class="xv-horizontal-center xv-logo" height="200" src="${projectPath}/static/assets/img/logo.png" alt="">
            <img class="xv-horizontal-center xv-logo" height="200" src="${projectPath}/static/assets/img/spring-logo.jpg" alt="">
        </div>
        <div class="xv-primary-title_clr">欢迎使用 ${appName} 框架</div>
        <div class="xv-main-text">如果您看见此页面,则说明应用服务器成功安装并启动,需要进一步配置.</div>
        <div class="xv-main-text">有关在线文档和支持,请参阅<a class="xv-link" href="http://doc.xvoid.cn">doc.xvoid.cn</a>,或者参考根目录下README.md获取帮助,</div>
<#--        <div class="xv-main-text"></div>-->
        <div class="xv-main-text">商业支持请在<a class="xv-link" href="http://support.xvoid.cn">support.xvoid.cn</a>获得.</div>
        <div class="xv-main-text"><i>Thank you for using VOID!</i></div>
    </div>
</div>
</body>
<style>
    #app{
        background: url("${projectPath}/static/assets/img/opening-background.jpeg") center no-repeat;
        background-size: cover;
        height:100vh;
    }

    .xv-logo{
        margin: 20px;
        border-radius: 8px;
    }

    .xv-top-window{
        margin-top:20vh;
    }
</style>
<script>
    const { createApp } = Vue

    createApp({
        data() {
            return {
                message: document.body.scrollWidth
                // message: window.screen.width
            }
        }
    }).mount('#app')
</script>

<@INIT.DOC_SIGN_END/> <#--不可删除-->