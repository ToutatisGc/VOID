<#assign projectPath=request.contextPath/>

<#import "/modules/init/INIT.ftlh" as INIT >
<#import "/modules/html/HTML.ftlh" as HTML >
<@INIT.DOC_INIT title=title>
</@INIT.DOC_INIT>
<body>
<#--HTML body起始-->
<div v-cloak id="app" class="xv-base-background xv-flex-box">
    <div style="background:linear-gradient(to top, #00416a, #e4e5e6);height: 90%;width: 47.5vw;border-radius: 6px">
        <div style="padding-top: 25%">
            <el-carousel height="70vh">
                <el-carousel-item v-for="item in 4" :key="item">
                    <div class="xv-flex-box_vertical" style="height: 100%">
                        <el-image src="https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg" />
                    </div>
                </el-carousel-item>
            </el-carousel>
        </div>
    </div>
    <div style="width: 47.5vw;height: 90%;padding-left: 15px">
        <div class="xv-flex-box xv-login-title" style="margin-top:25%;">
            <img class="xv-logo" height="80" src="${projectPath}/static/assets/img/logo.png" alt="">
            <div class="xv-main-text">春野 | 后台管理系统</div>
        </div>
        <div style="width: 50%;margin-left: 2.5%">
            <el-form v-loading="loading" :model="loginForm" label-width="120px" label-position="top" size="large"
                     ref="ruleFormRef" :rules="rules" status-icon element-loading-background="rgba(240, 240, 240, 0.8)"
                     element-loading-text="登录中..."
                     style="margin-top: 20px">
                <el-form-item label="用户名" prop="account">
                    <input class="xv-mock-input" type="text" v-model="loginForm.account" placeholder="请输入用户名/手机号/邮箱">
<#--                    <el-input v-model="loginForm.account" :suffix-icon="apple" placeholder="请输入用户名/手机号/邮箱" clearable style="background-color: rgb(225,228,237)">-->
<#--                        <template #prepend><@HTML.ICON name="User"/></template>-->
<#--                    </el-input>-->
                </el-form-item>
                <el-form-item label="密码" prop="password">
<#--                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password clearable style="width: 300px">-->
<#--                        <template #prepend><@HTML.ICON name="Lock"/></template>-->
<#--                    </el-input>-->
                    <input class="xv-mock-input" type="password" v-model="loginForm.password" placeholder="请输入密码">
                    <div>忘记密码?</div>
                </el-form-item>
<#--                <el-form-item label="登录类型">-->
<#--                    <el-radio-group v-model="loginForm.type">-->
<#--                        <el-radio-button label="GUEST">会 员</el-radio-button>-->
<#--                        <el-radio-button label="MANAGER">管理员</el-radio-button>-->
<#--                    </el-radio-group>-->
<#--                </el-form-item>-->
                <el-form-item>
                    <el-button type="primary" @click="login" :disabled="buttonDisabled" style="width: 100%;height: 3.5vh;background-color: rgb(75,78,221)">
                        <el-icon class="el-icon--left"><D-Arrow-Right/></el-icon><span>登 录</span>
                    </el-button>
                    <el-button type="success" @click="login" :disabled="buttonDisabled"
                               style="width: 100%;height: 3.5vh;background-color: rgb(68,155,54);margin-left: 0;margin-top: 10px;border: 1px solid rgb(68,155,54)">
                        <el-icon class="el-icon--left"><Full-Screen/></el-icon><span>扫码登录</span>
                    </el-button>
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
        /*background-color: white;*/
    }

    .xv-logo {
        margin: 20px;
        border-radius: 4px;
    }
</style>
<#--VUE 实例-->
<script>
    const { createApp,ref } = Vue
    const { FormRules,ElNotification} = ElementPlus

    let app = createApp({
        created(){
            // $http.get("/auth/login/pre-check?account=xvoid").then(
            //     function (response) {
            //         console.log(response)
            //     }
            // )
        },
        data() {
            return {
                rules: {
                    account: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 2, max: 32, message: '用户名不小于2位', trigger: 'blur' },
                    ],
                    password:[
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 32, message: '密码不小于6位', trigger: 'blur' },
                    ]
                },
                loginForm:{
                    account:'',
                    password:'',
                    type:'GUEST',
                    authType:'ACCOUNT_NORMAL'
                },
                loading:false,
                buttonDisabled: false,
            }
        },
        methods:{
            login: function () {
                console.log(this.loginForm);
                this.$refs['ruleFormRef'].validate((res) => {
                    if (res) {
                        this.loading = true;
                        let param ={
                            identity:JSON.stringify({
                                account:this.loginForm.account,
                                authType:this.loginForm.authType
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
                    }else {
                        this.loginForm.password = '';
                        ElNotification({
                            type:'error',
                            title: "验证失败",
                            message: "请填写正确的用户名密码",
                            duration:3000
                        })
                    }
                })

            }
        }
    })
   <@HTML.VUE_INIT/>
</script>

<@INIT.DOC_SIGN_END/> <#--不可删除-->