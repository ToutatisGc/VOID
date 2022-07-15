<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/web/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <title>易扬云-经典案例</title>
    <meta name="keywords" content="易扬云,易扬云微商城,小程序,公众号">
    <meta name="description" content="易扬云，提供易扬云微商城、易扬云教育、易扬云小程序、易扬云商盟等全面帮助商家经营社交电商和全渠道新零售的SaaS软件产品和服务">
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/web.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>

        .caseType{
            width: 70%;
            margin-left: 15%;
            height: 80px;
            display:flex;
            align-items:center;
            justify-content:center;
            background-color: #F0F8FF;
        }

        .case-box{
            height: auto;
            margin: 30px auto;
            display:flex;
            align-items:center;
            justify-content:center;
        }

        .caseType-box{
            text-align: center;
            line-height: 25px
        }

        .caseType-box:hover{
            cursor: pointer;
            color: #5CACEE;
        }

        .caseType-txt{
            color: #6B6B6B;
        }

        .caseType-icon{
            font-size: 4rem;
            color: #A2B5CD;
        }

        .case-banner-content{
            position: absolute;
            width: 60%;
            height: auto;
            left: 20%;
            top: 80px;
            z-index: 1000;
        }

        .case-banner-content-head{
            text-align: center;
            font-size: 40px;
            font-weight: bolder;
            color: white;
        }

        .case-banner-content-body{
            text-align: center;
            font-size: 30px;
            color: white;
            margin-top: 30px;
        }

        .box-card {
            height: 500px;
            margin: 30px 20px;
        }
        .case-footer{
            position: relative;
            width: 100%;
            padding: 10px 0;
            text-align: center;
            margin-top: -150px;
            background-color: white;
            display: none;
        }
        .noMore{
            text-align: center;
            font-size: 1.2rem;
            color: silver;
        }
        .case-card:hover{
            cursor: pointer;
        }
        .case-card:hover .case-footer{
            display: block;
            margin-top: -400px;
        }
        .case-card:hover .case-img{
            filter: blur(2px) grayscale(0.1);
        }
    </style>
</head>
<body style="height: 100%">
<div id="vueInstance" v-cloak>
    <@site.service/>
    <@site.header/>
    <div class="index-banner">
        <el-carousel type="card" indicator-position="outside">
            <el-carousel-item style="width: 1000px">
                <img src="${projectPath}/static/image/web/case-index1.png" style="width: inherit;height: inherit;border-radius: 0.8rem">
                <div class="case-banner-content">
                    <div class="case-banner-content-head">
                        深度剖析行业
                    </div>
                    <div class="case-banner-content-body">
                        为众多行业商户提供更完善的营销管理
                    </div>
                </div>
            </el-carousel-item>
            <el-carousel-item style="width: 1000px">
                <img src="${projectPath}/static/image/web/case-index2.png" style="width: inherit;height: inherit;border-radius: 0.8rem">
                <div class="case-banner-content">
                    <div class="case-banner-content-head">
                        稳定、安全的技术支持
                    </div>
                    <div class="case-banner-content-body">
                        强大的研发实力保驾护航
                    </div>
                </div>
            </el-carousel-item>
            <el-carousel-item style="width: 1000px">
                <img src="${projectPath}/static/image/web/case-index3.png" style="width: inherit;height: inherit;border-radius: 0.8rem">
                <div class="case-banner-content">
                    <div class="case-banner-content-head">
                        多行业解决案例
                    </div>
                    <div class="case-banner-content-body">
                        选择一款适合您的产品方案
                    </div>
                </div>
            </el-carousel-item>
        </el-carousel>

    </div>
    <div class="caseType">
        <div style="width: 100%;height: inherit;">
            <el-row>
                <el-col class="caseType-box" span="2">
                    <i class="caseType-icon el-icon-menu" @click="changeType('')"></i>
                    <div class="caseType-txt" @click="changeType('')">全部类目</div>
                </el-col>
                <el-col class="caseType-box" span="2" v-for="(type,index) in caseTypeList" :key="index">
                    <i class="caseType-icon" :class="typeIcon[index]" @click="changeType(type.code)"></i>
                    <div class="caseType-txt" @click="changeType(type.code)">{{type.name}}</div>
                </el-col>
                <el-col class="caseType-box" span="2">
                    <i class="el-icon-more" style="font-size: 5rem;color: #A2B5CD"></i>
                    <div class="caseType-txt">其他案例</div>
                </el-col>
            </el-row>
        </div>
    </div>
    <div class="case-box">
        <div style="width: 70%;height: inherit;">
            <el-row>
                <el-col class="case-card" span="6" v-for="(list,index) in caseList" :key="index">
                    <el-card class="box-card" style="height: 600px;" :body-style="{ padding: '0px' }">
                        <img class="case-img" :src="'${projectPath}/img'+list.caseImg" style="z-index: -1;width: 100%;height: 600px;"/>
                        <div class="case-footer">
                            <img :src="'${projectPath}/img'+list.caseCodeImg" style="height: 100px;"/>
                            <div style="margin:10px auto">
                                <el-link type="primary" style="color: #0e57ea">扫码查看案例</el-link>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <div class="noMore" v-if="caseList.length === 0">没有更多行业案例</div>
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
            caseType:'',
            typeIcon:[
                'el-icon-box',
                'el-icon-food',
                'el-icon-dish',
                'el-icon-toilet-paper',
                'el-icon-table-lamp',
                'el-icon-present',
                'el-icon-office-building',
                'el-icon-mic',
                'el-icon-position',
                'el-icon-baseball'
            ],
            caseTypeList:[],
            caseList:[],

        },
        created(){
            this.fixedHeader();
            this.getCaseTypeList();
            this.getCaseList();
        },
        mounted () {

        },
        methods: {
            <@site.siteMethod/>
            getCaseTypeList:function () {
                let that = this;
                $.post("${projectPath}/api/systemSiteCase/external/getCaseTypeList_OPEN",function (response) {
                    that.caseTypeList = response.content;
                });
            },
            getCaseList:function () {
                let that = this;
                that.caseList = [];
                $.post("${projectPath}/api/systemSiteCase/external/getOutCaseList_OPEN", {'caseType': this.caseType}, function (response) {
                    that.caseList = response.content;
                });
            },
            changeType:function(code){
                this.caseType = code;
                this.getCaseList();
            }
        }
    })
</script>
</html>