<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#assign projectPath=request.contextPath/>
<!DOCTYPE html>
<html lang="en" style="height: 100%">
<head>
    <meta charset="UTF-8">
    <title>易扬云-商户后台登录</title>
    <@INIT.importScript hiddenVersion=true/>
    <script src="${projectPath}/static/js/effect/vector.js"></script>
</head>
<style>
    [v-cloak] {
        display: none !important;
    }

    body{
        height: 100%;
        margin: 0;
    }

    #vueInstance{
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .auth-window{
        display: flex;
        height: 500px;
        background-color:rgba(0,0,0,0.5);
        box-shadow: 0 15px 15px rgba(0,0,0,0.5);
    }

    .inner-form{
        height: 100%;
        width: 500px;
        float: left
    }

    .logo-text{
        text-align: center;
        font-size: 40px;
        color: #ffffff;
        font-weight: bolder;
        margin: 50px 0 50px 0;
        user-select: none;
        -webkit-user-select: none;
    }

    .footer{
        position: fixed;
        width: 100%;
        text-align: center;
        bottom: 5px;
    }

    .effect{
        width: 100%;
        height: 100%;
        position: absolute;
    }

    .el-form-item__label{
        color: white;
    }
    .void_code_input>.el-input__inner{
        height: 55px !important;
    }
</style>
<body>
<div id="vueInstance" v-cloak>
    <div id="authEffect" class="effect"></div>
    <div class="auth-window" style="position:absolute;display:flex;z-index: 1;">
        <div class="inner-form" style="background-color:rgba(255,255,255,0.1) "  v-loading="authLoading" element-loading-text="请稍等" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
            <div class="logo-text">商户管理平台<span style="color: rgba(255,255,255,0.25);font-size: 12px;font-weight: lighter">${version}</span></div>
            <el-form :model="authForm" ref="authForm" :rules="rules" label-width="120px" style="width: 80%" :disabled="loginDisable" hide-required-asterisk="true" status-icon="true">
                <el-form-item label="用户名" prop="identity.account">
                    <el-input v-model="authForm.identity.account"></el-input>
                </el-form-item>
                <el-form-item label="密  码" prop="secret">
                    <el-input v-model="authForm.secret" show-password></el-input>
                </el-form-item>
                <el-form-item label="验证码" prop="identity.checkCode">
                    <el-row>
                        <el-col span="12">
                            <el-input class="void_code_input" v-model="authForm.identity.checkCode" clearable style="margin-top: 8px">

                            </el-input>
                        </el-col>
                        <el-col span="12" style="text-align: right">
                            <div v-loading="codeLoading" style="padding-top: 7px">
                                <img :src="code" alt="丢失图片" @click="resetCode" @load="loadFine" style="width: 120px;height: 55px">
                            </div>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item v-show="!alertStyle.alertDisplay">
                    <el-alert style="line-height: normal" title="请输入用户名密码" type="info" effect="dark" :closable="false"></el-alert>
                </el-form-item>
                <el-form-item v-show="alertStyle.alertDisplay">
                    <el-alert style="line-height: normal" :title="alertStyle.title" :type="alertStyle.type" effect="dark" :closable="false"></el-alert>
                </el-form-item>
                <el-form-item>
                    <div>
                        <el-button type="success" @click="authLogin" style="width: 100%">{{loginText}}</el-button>
<#--                        <el-button type="warning" @click="register" style="width: 130px">注册</el-button>-->
                    </div>
                </el-form-item>
            </el-form>
        </div>
    </div>
    <div class="footer">
        <el-link type="info" style="color: white">技术支持:${company}</el-link>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#vueInstance',
        data: {
            code:'${projectPath}/auth/generateCheckCode?t='+new Date().getTime(),
            codeLoading:false,
            authLoading:false,
            loginDisable:false,
            loginText:'登录',
            alertStyle:{
                alertDisplay: false,
                type:'success',
                title:''
            },
            authForm:{
                identity:{
                    account:'',
                    checkCode:'',
                    authType:'ACCOUNT_CHECK'
                },
                secret:''
            },
            rules: {
                'identity.account': [
                    { required: true, message: '请填写用户名', trigger: 'blur' }
                ],
                'secret': [
                    { required: true, message: '请填写密码', trigger: 'blur' }
                ],
                'identity.checkCode':[
                    {required : true ,message :'请填写验证码' , trigger: 'blur'}
                ]
            }
        },
        created: function () {
            let $this = this;
            $(document).keypress(function(e) {
                if((e.keyCode || e.which)===13) {
                    $this.authLogin();
                }
            });
        },
        methods:{
            authLogin:function () {
                let _this = this;
                this.$refs['authForm'].validate((valid) => {
                    if (valid) {
                        _this.authLoading = true;
                        let authForm = this.authForm;
                        let parameters = {identity:JSON.stringify(authForm.identity),secret:authForm.secret};
                        $.post('${projectPath}/authentication',parameters,function (response) {
                            if (response.sign === true){
                                _this.alertStyle.type = 'success';
                                _this.loginDisable = true;
                                _this.loginText = '请稍等';
                                setTimeout(() =>{
                                    <#--location.href = "${projectPath}/background"-->
                                    location.href = "${projectPath}/getManagerInfo"
                                },1500);
                            }else {
                                _this.resetCode();
                                _this.authForm.identity.checkCode = '';
                                _this.alertStyle.type = 'warning';
                            }
                            _this.alertStyle.alertDisplay = true;
                            _this.alertStyle.title = response.message;
                            _this.authLoading = false;
                        })
                    } else {
                        _this.alertStyle.alertDisplay = true;
                        _this.alertStyle.type = 'error';
                        _this.alertStyle.title = '请输入必填项';
                        return false;
                    }
                });
            },
            register:function () {

            },
            resetCode:function () {
                this.codeLoading = true;
                const timestamp = (new Date()).getTime();
                this.code = '${projectPath}/auth/generateCheckCode?t='+timestamp;
            },
            loadFine:function () {
                this.codeLoading = false;
            }
        }
    });

    $(function () {
        const victor = new Victor("vueInstance", "authEffect");
    })

    // let height = $(window).height();
    // $("#vueInstance").css("height",height);
    //
    // window.onresize = () => {
    //     return (() => {
    //         let height = $(window).height();
    //         $("#vueInstance").css("height",height);
    //     })()
    // };
</script>
</html>