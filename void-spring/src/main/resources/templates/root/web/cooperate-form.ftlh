<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/web/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <title>易扬云-渠道合作-招商信息</title>
    <meta name="keywords" content="易扬云,易扬云微商城,小程序,公众号">
    <meta name="description" content="易扬云，提供易扬云微商城、易扬云教育、易扬云小程序、易扬云商盟等全面帮助商家经营社交电商和全渠道新零售的SaaS软件产品和服务">
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/web.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>

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
    <@site.service/>
    <@site.header/>
    <div class="cooperate-form">
        <div style="width: 30%;height: inherit;">
            <div class="cooperate-form-title">申请成为代理商</div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="申请人姓名" prop="name">
                    <el-input v-model="ruleForm.name" placeholder="请输入您的姓名"></el-input>
                </el-form-item>
                <el-form-item label="申请人手机" prop="tel">
                    <el-input v-model="ruleForm.tel" placeholder="请输入您的联系电话"></el-input>
                </el-form-item>
                <el-form-item label="短信验证码" prop="telCode">
                    <el-input placeholder="请输入6位数的验证码" v-model="ruleForm.telCode">
                        <template slot="append">
                            <el-button type="primary" plain style="width: 150px;height: 50px">获取验证码</el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item label="渠道类型" prop="regionType">
                    <el-select v-model="ruleForm.regionType" placeholder="请选择渠道类型">
                        <el-option label="零售" value="1"></el-option>
                        <el-option label="电商" value="2"></el-option>
                        <el-option label="餐饮" value="3"></el-option>
                        <el-option label="娱乐" value="4"></el-option>
                        <el-option label="酒旅" value="5"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请区域" prop="area">
                    <el-select v-model="ruleForm.area" placeholder="请选择申请区域">
                        <el-option :label="item.name" :value="item.code" v-for="(item,index) in areaList" :key="index"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请人邮箱">
                    <el-input placeholder="请输入您的邮箱" v-model="ruleForm.email">

                    </el-input>
                </el-form-item>
                <el-form-item label="公司名称">
                    <el-input v-model="ruleForm.businessName" placeholder="如申请主体为个人可不填"></el-input>
                </el-form-item>
                <el-form-item label="公司所在地">
                    <el-input v-model="ruleForm.businessArea" placeholder="如申请主体为个人可不填"></el-input>
                </el-form-item>
                <el-form-item label="公司概况">
                    <el-input
                            type="textarea"
                            placeholder="简单描述一下您目前的情况，如申请主体为个人可不填"
                            v-model="ruleForm.businessRemark"
                            maxlength="500"
                            show-word-limit
                            :rows="12"
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
            <@site.serviceForm/>
            ruleForm: {
                name: '',
                tel:'',
                telCode:'',
                regionType: '',
                area: '',
                email: '',
                businessName: '',
                businessArea: '',
                businessRemark: ''
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
                area: [
                    { required: true, message: '请选择申请区域', trigger: 'change' }
                ]
            },
            areaList:[
                {name:'北京',code:'110000'},
                {name:'天津',code:'120000'},
                {name:'河北省',code:'130000'},
                {name:'山西省',code:'140000'},
                {name:'内蒙古自治区',code:'150000'},
                {name:'辽宁省',code:'210000'},
                {name:'吉林省',code:'220000'},
                {name:'黑龙江省',code:'230000'},
                {name:'上海',code:'310000'},
                {name:'江苏省',code:'320000'},
                {name:'浙江省',code:'330000'},
                {name:'安徽省',code:'340000'},
                {name:'福建省',code:'350000'},
                {name:'江西省',code:'360000'},
                {name:'山东省',code:'370000'},
                {name:'河南省',code:'410000'},
                {name:'湖北省',code:'420000'},
                {name:'湖南省',code:'430000'},
                {name:'广东省',code:'440000'},
                {name:'四川省',code:'510000'},
                {name:'贵州省',code:'520000'},
                {name:'云南省',code:'530000'},
                {name:'陕西省',code:'610000'},
                {name:'甘肃省',code:'620000'},
                {name:'青海省',code:'630000'},
            ]
        },
        created(){
            this.fixedHeader();
        },
        mounted () {

        },
        methods: {
            <@site.siteMethod/>
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
            }
        }
    })
</script>
</html>