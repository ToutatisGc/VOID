<#assign projectPath=request.contextPath/>

<#import "/modules/init/INIT.ftlh" as INIT >
<#import "/modules/html/HTML.ftlh" as HTML >
<@INIT.DOC_INIT title=title>
</@INIT.DOC_INIT>
<body>
<#--HTML body起始-->
<div v-cloak id="app" class="xv-base-background">
    <div style="position: fixed;background-color: black;width: 100%;height: 100%;z-index: 0">
        <video style="width: 100%;height: 100%;object-fit: fill" autoplay playsinline loop
               poster="${projectPath}/static/assets/video/videoBGPoster.png" class="videoBG">
            <source src="${projectPath}/static/assets/video/videoBG.mp4" type="video/mp4">
        </video>
    </div>
    <br style="line-height:0;"/>
    <div class="xv-dialog xv-flex-box xv-box_shadow" style="position: absolute;z-index: 1">
        <div class="xv-half-container xv-background-img">
<#--            <div class="xv-box_shadow xv-flex-box xv-login-title xv-horizontal-center">-->
<#--                <img class="xv-logo" height="50" src="${projectPath}/static/assets/img/logo.png" alt="">-->
<#--                <div class="xv-main-text">春野 | 后台管理系统</div>-->
<#--            </div>-->
            <div class="xv-flex-box_vertical xv-horizontal-center xv-box xv-box_shadow" style="width: 80%;height: 70%;margin-top:15%;background-color:white">
                <div class="xv-main-text">2022-11-21 更新公告</div>
                <div class="xv-update-window">
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
            <div class="xv-box_shadow xv-flex-box xv-login-title xv-horizontal-center" style="margin-top:15%">
                <img class="xv-logo" height="50" src="${projectPath}/static/assets/img/logo.png" alt="">
                <div class="xv-main-text">春野 | 后台管理系统</div>
            </div>
<#--            <div class="xv-primary-title__dark" style="margin-top: 15%;font-size: 2vw">管理系统登录</div>-->
            <el-form v-loading="loading" class="xv-box_shadow" :model="loginForm" label-width="120px" label-position="top"
                     ref="ruleFormRef" :rules="rules" status-icon  <#--element-loading-background="rgba(122, 122, 122, 0.8)"-->
                     element-loading-text="登录中..."
                     style="background-color:rgba(255,255,255,1);padding: 20px;border-radius: 4px;margin-top: 20px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username" :suffix-icon="apple" placeholder="请输入用户名/手机号/邮箱" clearable>
                        <template #prepend><@HTML.ICON name="User"/></template>
                    </el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password clearable>
                        <template #prepend><@HTML.ICON name="Lock"/></template>
                    </el-input>
                </el-form-item>
                <el-form-item label="登录类型">
                    <el-radio-group v-model="loginForm.type">
                        <el-radio label="GUEST">会 员</el-radio>
                        <el-radio label="MANAGER">管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="操作">
                    <el-button type="primary" @click="login" :disabled="buttonDisabled"><el-icon class="el-icon--left"><Arrow-Left-Bold/></el-icon>登 录</el-button>
                    <el-button type="success"><el-icon class="el-icon--left"><User/></el-icon>注册账户</el-button>
                    <el-button type="warning"><el-icon class="el-icon--left"><Key/></el-icon>忘记密码</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>

</div>
</body>
<style>
    .xv-dialog {
        width: 65vw;
        height: 55vh;
        margin: 20vh auto;
        left: 50%;
        transform: translateX(-50%);
    }

    .xv-update-window{
        overflow-y: scroll;
    }

    .xv-half-container {
        width: 50%;
        height: 100%;
    }

    .xv-background-img {
        background-color: rgba(0,0,0,0.5);
        <#--background: url("${projectPath}/static/assets/img/login-banner.png") no-repeat center;-->
        <#--background-size: 100% 100%;-->
    }

    .xv-login-title {
        width: max-content;
        /*margin: 20px 0 0 20px;*/
        margin-top: 20px;
        height: auto;
        padding-right: 20px;
        border-radius: 4px;
        background-color: white;
    }

    .xv-logo {
        margin: 20px;
        border-radius: 8px;
    }
</style>
<#--VUE 实例-->
<script>
    const { createApp,ref } = Vue
    const { FormRules,ElNotification} = ElementPlus

    let app = createApp({
        created(){
            $http.get("/auth/login/pre-check?account=xvoid").then(
                function (response) {
                    console.log(response)
                }
            )
        },
        data() {
            return {
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 4, max: 32, message: '用户名不小于4位', trigger: 'blur' },
                    ],
                    password:[
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 32, message: '密码不小于6位', trigger: 'blur' },
                    ]
                },
                loginForm:{
                    username:'',
                    password:'',
                    type:'GUEST'
                },
                loading:false,
                buttonDisabled: false,
            }
        },
        methods:{
            login: function () {
                this.$refs['ruleFormRef'].validate((res) => {
                    if (res) {
                        this.loading = true;
                        let param ={
                            identity:JSON.stringify({
                                username:this.loginForm.username,
                                authType:'ACCOUNT_NORMAL'
                            }),
                            secret:this.loginForm.password
                        }
                        let $this = this;
                        $http.post("/auth/authentication",param).then(
                            function (response) {
                                $this.loading = false;
                                if (response.success) {
                                    ElNotification({
                                        type:'success',
                                        title: response.message,
                                        message: "登录成功,请稍候.即将跳转页面...",
                                        duration:3000
                                    });
                                    // $this.buttonDisabled = true;
                                }else {
                                    $this.loading = false;
                                    ElNotification({
                                        type:'error',
                                        title: response.message,
                                        message: response.supportMessage,
                                        duration:3000
                                    })
                                }
                            }
                        )
                    }
                })


            }
        }
    })
   <@HTML.VUE_INIT/>
</script>

<@INIT.DOC_SIGN_END/> <#--不可删除-->