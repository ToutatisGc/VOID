<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/web/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <title>易扬云-渠道合作</title>
    <meta name="keywords" content="易扬云,易扬云微商城,小程序,公众号">
    <meta name="description" content="易扬云，提供易扬云微商城、易扬云教育、易扬云小程序、易扬云商盟等全面帮助商家经营社交电商和全渠道新零售的SaaS软件产品和服务">
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/web.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>

        .index-banner-btn{
            position: absolute;
            width: 40%;
            left: 30%;
            top: 330px;
            height: 50px;
            z-index: 100;
            text-align: center;
        }

        .cooperate-box{
            height: auto;
            margin: 30px auto;
            display:flex;
            align-items:center;
            justify-content:center;
        }

        .cooperate-banner{
            background-image: linear-gradient(#262626,#242424);
        }

        .cooperate-banner-content{
            position: absolute;
            width: 100%;
            height: auto;
            left: 0;
            top: 70px;
            z-index: 1000;
        }

        .cooperate-banner-content-head{
            text-align: center;
            font-size: 55px;
            font-weight: bolder;
            color: white;
        }

        .cooperate-banner-content-body{
            text-align: center;
            font-size: 30px;
            color: white;
            margin-top: 30px;
        }

        .box-card {
            height: 500px;
            margin: 30px 20px;
        }
        .box-card:hover{
            margin: 20px;
            cursor: pointer;
        }
        .box-card-head{
            width: 100%;
            height: 250px;
            background-color: rgba(85, 85, 85, 0.5);
        }
        .box-card-content{
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
            line-height: 35px;
            color: silver;
        }
        .box-card-btn{
            margin-top: 30px;
            text-align: center;
        }
    </style>
</head>
<body style="height: 100%">
<div id="vueInstance" v-cloak>
    <@site.service/>
    <@site.header/>
    <div class="index-banner">
        <el-carousel type="card" arrow="never">
            <el-carousel-item class="cooperate-banner" style="text-align: center;width: 1000px">
                <img src="${projectPath}/static/image/web/cooperate-banner-bg4.png">
                <div class="cooperate-banner-content">
                    <div class="cooperate-banner-content-head">
                        渠道代理商邀请函
                    </div>
                    <div class="cooperate-banner-content-body">
                        等您加入
                    </div>
                </div>
            </el-carousel-item>
            <el-carousel-item class="cooperate-banner" style="text-align: center;width: 1000px">
                <img src="${projectPath}/static/image/web/cooperate-banner-bg2.png">
                <img src="${projectPath}/static/image/web/cooperate-banner-bg1.png">
                <div class="cooperate-banner-content">
                    <div class="cooperate-banner-content-head">
                        易扬云代理商火热招募中
                    </div>
                    <div class="cooperate-banner-content-body">
                        与代理商伙伴建立共赢发展模式 构建本地化移动互联平台
                    </div>
                </div>
            </el-carousel-item>
            <el-carousel-item class="cooperate-banner" style="text-align: center;width: 1000px">
                <img src="${projectPath}/static/image/web/cooperate-banner-bg3.png">
                <div class="cooperate-banner-content">
                    <div class="cooperate-banner-content-head">
                        数字化服务代理招商中
                    </div>
                    <div class="cooperate-banner-content-body">
                        火爆的深度行业解决方案 助您畅享流量红利
                    </div>
                </div>
            </el-carousel-item>
        </el-carousel>
        <div class="index-banner-btn">
            <el-button type="primary" plain style="width: 150px;height: 50px">申请成为代理商</el-button>
            <el-button type="primary" plain style="width: 150px;height: 50px">代理商登录</el-button>
        </div>
    </div>
    <div class="cooperate-box">
        <div style="width: 70%;height: inherit;">
            <el-row>
                <el-col span="7" offset="1">
                    <el-card class="box-card" style="height: 500px;">
                        <div class="box-card-head">
                            <img src="${projectPath}/static/image/web/cooperate1.jpg" style="width: inherit;height: inherit;">
                        </div>
                        <div class="box-card-content">
                            <div style="font-size: 25px;color: #575757;font-weight: bold">电商业务线</div>
                            <div>小程序+公众号一体化微信商城</div>
                            <div>助力企业快速进入移动社交电商新时代</div>
                        </div>
                        <div class="box-card-btn">
                            <el-button @click="toCooperateForm" type="primary" plain> 申请代理 </el-button>
                        </div>
                    </el-card>
                </el-col>
                <el-col span="7" offset="1">
                    <el-card class="box-card" style="height: 500px;">
                        <div class="box-card-head">
                            <img src="${projectPath}/static/image/web/cooperate2.jpg" style="width: inherit;height: inherit">
                        </div>
                        <div class="box-card-content">
                            <div style="font-size: 25px;color: #575757;font-weight: bold">零售业务线</div>
                            <div>智慧零售</div>
                            <div>赋能线下门店，重构消费连接</div>
                        </div>
                        <div class="box-card-btn">
                            <el-button @click="toCooperateForm" type="primary" plain> 申请代理 </el-button>
                        </div>
                    </el-card>
                </el-col>
                <el-col span="7" offset="1">
                    <el-card class="box-card" style="height: 500px;">
                        <div class="box-card-head">
                            <img src="${projectPath}/static/image/web/cooperate3.jpg" style="width: inherit;height: inherit">
                        </div>
                        <div class="box-card-content">
                            <div style="font-size: 25px;color: #575757;font-weight: bold">餐饮业务线</div>
                            <div>实现小程序+公众号餐饮解决方案</div>
                            <div>营销+会员+点餐+外卖等应用打造餐饮一体化服务</div>
                        </div>
                        <div class="box-card-btn">
                            <el-button @click="toCooperateForm" type="primary" plain> 申请代理 </el-button>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-row>
                <el-col span="7" offset="1">
                    <el-card class="box-card" style="height: 500px;">
                        <div class="box-card-head">
                            <img src="${projectPath}/static/image/web/cooperate4.jpg" style="width: inherit;height: inherit">
                        </div>
                        <div class="box-card-content">
                            <div style="font-size: 25px;color: #575757;font-weight: bold">娱乐业务线</div>
                            <div>实现小程序+公众号休娱解决方案</div>
                            <div>提升流量到店，大数据会员管理，提高营销效率</div>
                        </div>
                        <div class="box-card-btn">
                            <el-button @click="toCooperateForm" type="primary" plain> 申请代理 </el-button>
                        </div>
                    </el-card>
                </el-col>
                <el-col span="7" offset="1">
                    <el-card class="box-card" style="height: 500px;">
                        <div class="box-card-head">
                            <img src="${projectPath}/static/image/web/cooperate5.jpg" style="width: inherit;height: inherit">
                        </div>
                        <div class="box-card-content">
                            <div style="font-size: 25px;color: #575757;font-weight: bold">酒旅业务线</div>
                            <div>小程序+公众号一体化酒店智慧营销管理解决方案</div>
                            <div>助力酒店精准营销，增强管理效率</div>
                        </div>
                        <div class="box-card-btn">
                            <el-button @click="toCooperateForm" type="primary" plain> 申请代理 </el-button>
                        </div>
                    </el-card>
                </el-col>
                <el-col span="7" offset="1">
                    <el-card class="box-card" style="height: 500px;">
                        <div class="box-card-head">
                            <img src="${projectPath}/static/image/web/cooperate6.jpg" style="width: inherit;height: inherit;opacity: 0.2;">
                        </div>
                        <div class="box-card-content" style="margin-top: 100px">
                            <div style="font-size: 25px;color: #C7C7C7;font-weight: bold">敬请期待</div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
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
        },
        created(){
            this.fixedHeader();
        },
        mounted () {

        },
        methods: {
            <@site.siteMethod/>
            toCooperateForm:function () {
                window.location.href = "${projectPath}/web/cooperate/form";
            }
        }
    })
</script>
</html>