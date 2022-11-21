<#assign projectPath=request.contextPath/>

<#import "/modules/init/INIT.ftlh" as INIT >
<@INIT.DOC_INIT title=title>
</@INIT.DOC_INIT>
<body>
<#--HTML body起始-->
<div v-cloak id="app" class="xv-base-background">
    <br style="line-height:0;"/>
    <div class="xv-dialog xv-flex-box xv-box_shadow">
        <div class="xv-half-container xv-background-img">
<#--            <div class="xv-box_shadow xv-flex-box xv-login-title xv-horizontal-center">-->
<#--                <img class="xv-logo" height="50" src="${projectPath}/static/assets/img/logo.png" alt="">-->
<#--                <div class="xv-main-text">春野 | 后台管理系统</div>-->
<#--            </div>-->
            <div class="xv-flex-box_vertical xv-horizontal-center xv-box xv-box_shadow" style="width: 80%;height: 70%;margin-top:10%;background-color:white">
                <div class="xv-main-text">2022-11-21 更新公告</div>
                <div>
                    TODO 富文本和滚动
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    本次更新：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                </div>
            </div>
        </div>
        <div class="xv-half-container xv-background_theme xv-flex-box_vertical">
            <div class="xv-box_shadow xv-flex-box xv-login-title xv-horizontal-center" style="margin-top:12%">
                <img class="xv-logo" height="50" src="${projectPath}/static/assets/img/logo.png" alt="">
                <div class="xv-main-text">春野 | 后台管理系统</div>
            </div>
<#--            <div class="xv-primary-title__dark" style="margin-top: 15%;font-size: 2vw">管理系统登录</div>-->
            <el-form class="xv-box_shadow" :model="loginForm" label-width="120px" label-position="top"
                     style="width: 50%;background-color:rgba(255,255,255,1);padding: 20px;border-radius: 4px;margin-top: 20px">
                <el-form-item label="用户名">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名/手机号/邮箱"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
                <el-form-item label="登录类型">
                    <el-radio-group v-model="loginForm.type">
                        <el-radio label="GUEST">普通会员</el-radio>
                        <el-radio label="MANAGER">管理人员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="操作">
                    <el-button type="primary" @click="login">登录</el-button>
                    <el-button type="success">注册账户</el-button>
                    <el-button>重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>

</div>
</body>
<style>
    label{
        /*color: white !important;*/
    }

 .xv-dialog{
     width:70vw;
     height: 50vh;
     margin: 20vh auto;
 }

 .xv-half-container{
     width:50%;
     height:100%;
 }

 .xv-background-img{
     background: url("${projectPath}/static/assets/img/login-banner.png") no-repeat center;
     background-size: 100% 100%;
 }

 .xv-login-title{
     width: max-content;
     /*margin: 20px 0 0 20px;*/
     margin-top: 20px;
     height:auto;
     padding-right: 20px;
     border-radius: 4px;
     background-color: white;
 }

 .xv-logo{
     margin: 20px;
     border-radius: 8px;
 }
</style>
<#--VUE 实例-->
<script>
    const { createApp } = Vue
    createApp({
        data() {
            return {
                loginForm:{
                    username:'',
                    password:'',
                    type:'GUEST'
                },
                message: "Hello, world!"
            }
        },
        methods:{
            login: function () {
                alert("登录动作")
            }
        }
    }).use(ElementPlus).mount('#app')
</script>

<@INIT.DOC_SIGN_END/> <#--不可删除-->