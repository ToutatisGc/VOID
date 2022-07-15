<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/mobileSite/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <title>易扬云-渠道合作-招商信息</title>
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/mobile.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>

        .index-banner{
            height: auto;
            width: 100%;
            opacity: 85%;
        }

        .inner-header-logo-div{
            float: left;
            width: 50px;
            height: 100%;
            background: url("${projectPath}/static/image/logo.png") center no-repeat;
            background-size: 100% 70%;
            text-align: center;
            margin-left: 10px;
        }

        .cooperate-form{
            height: auto;
            margin: 30px auto;
            display:flex;
            align-items:center;
            justify-content:center;
        }

        .cooperate-form-title{
            font-size: 25px;
            color: #575757;
            text-align: center;
            height: 80px
        }

    </style>
</head>
<body style="height: 100%">
<div id="vueInstance" v-cloak>
    <#--返回顶部层-->
    <el-backtop target="#vueInstance" right="10" bottom="100" :visibility-height="60"></el-backtop>
    <div class="index-banner">
        <@site.header/>
    </div>
    <div class="cooperate-form">
        <div style="width: 90%;height: inherit;">
            <div class="cooperate-form-title">申请成为代理商</div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="申请人姓名" prop="name">
                    <el-input v-model="ruleForm.name" placeholder="您的真实姓名"/>
                </el-form-item>
                <el-form-item label="申请人手机" prop="tel">
                    <el-input v-model="ruleForm.tel" placeholder="您的联系电话"/>
                </el-form-item>
                <el-form-item label="短信验证码" prop="telCode">
                    <el-input placeholder="请输入验证码" v-model="ruleForm.telCode">
                        <template slot="append">
                            <el-button type="primary" plain style="width: 100px;height: 50px">获取验证码</el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item label="渠道类型" prop="regionType">
                    <el-select v-model="ruleForm.regionType" placeholder="请选择渠道类型">
                        <el-option label="电商" value="dianshang"></el-option>
                        <el-option label="零售" value="lingshou"></el-option>
                        <el-option label="餐饮" value="canyin"></el-option>
                        <el-option label="娱乐" value="yule"></el-option>
                        <el-option label="酒旅" value="jiulv"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请区域" prop="areaType1">
                    <el-cascader
                            v-model="ruleForm.value"
                            :options="ruleForm.options"
                            @change="handleChange"></el-cascader>
                </el-form-item>
                <el-form-item label="申请人邮箱">
                    <el-input placeholder="请输入邮箱地址" v-model="ruleForm.email"/>
                </el-form-item>
                <el-form-item label="公司名称">
                    <el-input v-model="ruleForm.businessName" placeholder="您的主体名称"/>
                </el-form-item>
                <el-form-item label="公司所在地">
                    <el-input v-model="ruleForm.businessArea" placeholder="主体所在地"/>
                </el-form-item>
                <el-form-item label="公司概况">
                    <el-input
                            type="textarea"
                            placeholder="请输入您的主体概况..."
                            v-model="ruleForm.businessRemark"
                            maxlength="200"
                            rows="6"
                            show-word-limit
                    >
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')" style="width: 150px">提交</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
    <@site.footer/>

</div>
</body>
<script>

    let vueInstance = new Vue({
        el:'#vueInstance',
        data:{
            showConsult:true,
            ruleForm: {
                name: '',
                tel:'',
                telCode:'',
                regionType: '',
                areaType1: '',
                areaType2: '',
                areaType3: '',
                email: '',
                businessName: '',
                businessArea: '',
                businessRemark: '',
                value: [],
                options:[{
                    value: 'shanxisheng',
                    label: '山西省',
                    children: [{
                        value: 'taiyuanshi',
                        label: '太原市',
                        children: [{
                            value: 'yingzequ',
                            label: '迎泽区'
                        },
                        {
                            value: 'xiaodianqu',
                            label: '小店区'
                        },
                        {
                            value: 'xinghualing',
                            label: '杏花岭'
                        }]
                    }]
                }]
            },
            rules: {
                name: [
                    { required: true, message: '请输入申请人姓名', trigger: 'blur' },
                    { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                ],
                tel: [
                    { required: true, message: '请输入申请人联系电话', trigger: 'blur' },
                    { min: 11, max: 11, message: '长度在11 个字符', trigger: 'blur' }
                ],
                telCode: [
                    { required: true, message: '请输入短信验证码', trigger: 'blur' },
                    { min: 6, max: 6, message: '长度在 6 个字符', trigger: 'blur' }
                ],
                regionType: [
                    { required: true, message: '请选择渠道类型', trigger: 'change' }
                ],
                areaType: [
                    { required: true, message: '请选择申请区域', trigger: 'change' }
                ]
            }
        },
        created(){

        },
        mounted () {

        },
        methods: {
            closeConsult:function () {
                this.showConsult = false;
            },
            submitForm:function(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                return false;
            }
            });
            },
            resetForm:function(formName) {
                this.$refs[formName].resetFields();
            },
            handleChange(value) {
                console.log(value);
            }
        }
    })
</script>
</html>