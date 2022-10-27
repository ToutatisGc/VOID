<#assign projectPath=request.contextPath/>

<#import "/modules/init/INIT.ftlh" as INIT >

<@INIT.DOC_INIT title=title > <#--不可删除-->
    <#--填充head头-->
<#--    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue/3.2.41/vue.global.js"></script>-->
</@INIT.DOC_INIT> <#--不可删除-->
<body>
<div v-cloak id="app" class="xv-base-background">
    <div class="xv-horizontal-center xv-top-window">
        <div class="xv-primary-title_clr example">欢迎使用 ${appName} 框架</div>
        <div class="xv-primary-title_clr">{{message}}</div>
    </div>
</div>
</body>
<style>
    .xv-top-window{
        padding-top:10vh;
        width: 50%;
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