<#assign projectPath=request.contextPath/>

<#import "/modules/init/INIT.ftlh" as INIT >

<@INIT.DOC_INIT title=title > <#--不可删除-->

</@INIT.DOC_INIT> <#--不可删除-->
<body>
<div v-cloak id="app" class="xv-base-background">
    <br style="line-height:0;"/>
    <div class="xv-flex-box" style="margin-top: 20vh">
        <div>
            <div class="xv-primary-title_clr xv-disable-select xv-decorate-text">PAGE</div>
            <div class="xv-primary-title_clr xv-disable-select xv-decorate-text" style="font-size: 3vh;font-weight:lighter;position:fixed">${subject}</div>
        </div>
        <div class="xv-vertical-line" style="height: 10vh"></div>
        <div class="xv-decorate-text xv-code-circle xv-disable-select">${httpCode}</div>
    </div>

    <div class="xv-flex-box_vertical" style="margin-top:5vh">
        <div class="xv-box xv-box_shadow" style="width:60vw">
            <div class="xv-horizontal-center">
                <div class="xv-primary-title_clr xv-disable-select" style="font-weight: normal;letter-spacing: 5px"><i class="xv-primary-title_clr xv-disable-select">Oops!</i>${title}!</div>
                <div class="xv-subhead">${message}</div>
                <div class="xv-main-text" style="margin-bottom: 2vh;margin-top: 3vh">操作:<a class="xv-link" href="#" @click="back">返回上级</a></div>
            </div>
        </div>
    </div>
    <div style="position: absolute;bottom: 10px;right: 10px;">
        <div class="xv-light-text">ARCH-NAME:${appName}</div>
        <div class="xv-light-text">VERSION:${version}</div>
        <div class="xv-light-text">RID:${rid}</div>
        <div class="xv-light-text">DATE:${date}</div>
    </div>
</div>
</body>
<style>
    .xv-code-circle{
        border-radius: 50%;
        border:2vh solid red;
        font-size: 5.5vh !important;
        width:11vh !important;
        height:11vh;
        line-height: 11vh;
        text-align: center;
    }

    .xv-decorate-text{
        position: relative;
        font-size: 11vh;
        font-weight:bolder;
        opacity: 0.5;
        margin:0;
        width:max-content;
    }
</style>
<script>
    const { createApp } = Vue
    createApp({
        data() {
            return {
            }
        },
        methods:{
            back: function () {
                history.go(-1);
            }
        }
    }).mount('#app')
</script>

<@INIT.DOC_SIGN_END/> <#--不可删除-->