<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/mobileSite/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <title>易扬云-关于我们</title>
    <meta name="keywords" content="易扬云,易扬云微商城,小程序,公众号">
    <meta name="description" content="易扬云，提供易扬云微商城、易扬云教育、易扬云小程序、易扬云商盟等全面帮助商家经营社交电商和全渠道新零售的SaaS软件产品和服务">
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/mobile.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>

        .about-box{
            height: auto;
            margin: 30px auto;
            align-items:center;
            justify-content:center;
        }

        .about-banner{
            background-image: linear-gradient(#262626,#242424);
        }

        .about-banner-content{
            position: absolute;
            width: 100%;
            height: auto;
            top: 40px;
            z-index: 1000;
        }

        .about-banner-content-body{
            text-align: center;
            font-size: 35px;
            color: white;
            margin-top: 30px;
        }

        .header-text{
            font-size: 25px;
            color: #4F4F4F;

        }

        .text {
            color: #666666;
            font-size: 12px;
            line-height: 20px;
        }

        .item {
            margin-bottom: 18px;
        }

        .clearfix:before,
        .clearfix:after {
            display: table;
            content: "";
        }
        .clearfix:after {
            clear: both
        }

        .box-card {
            width: 100%;
        }
    </style>
</head>
<body style="height: 100%">
<div id="vueInstance" v-cloak>
    <@site.header/>
    <div class="index-banner">
        <el-carousel height="200px" indicator-position="none">
            <el-carousel-item class="about-banner" style="text-align: center;">
                <img src="${projectPath}/static/image/web/about-index.png" style="width: inherit;height: inherit">
                <div class="about-banner-content">
                    <div class="about-banner-content-body">
                        让管理变得更简单
                    </div>
                </div>
            </el-carousel-item>
        </el-carousel>
    </div>
    <div class="about-box">
        <div>
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <div class="header-text">易扬云</div>
                    <div class="text">软件服务商</div>
                </div>
                <div class="text item">
                    易扬云在各省代理商超过1000家，入驻商户突破12000家。
                </div>
                <div class="text item">
                    易扬云是一家专注研发软件、运营商业平台、代理服务、广告营销集一身的科技公司。目前已为多行业商户解决商业问题。
                    易扬云围绕商业云、营销云、销售云打造智慧云端生态体系，通过去中心化的智慧商业解决方案赋能中小企业实现数字化
                    转型。目前旗下拥有:微商城、智慧零售、智慧餐厅、微店外卖、智慧酒店、推客、云管家、智慧旅游、营销圈、分销管家
                    等解决方案，帮助客户在新零售时代提高营销效率和盈利能力。
                </div>
                <div class="text item">
                    易扬云致力于通过产品和服务，助力中小企业向数字化转型，通过科技驱动商业革新，让管理变得更简单。
                </div>
            </el-card>
        </div>
        <div style="margin: 10px auto">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <div class="header-text">合作行业</div>
                </div>
                <div class="text item">
                    易扬云入驻商户突破12000家，覆盖电商、餐饮、物业、生活服务、旅游等多个行业。
                </div>
            </el-card>
        </div>
        <div style="margin: 10px auto">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <div class="header-text">合作咨询</div>
                </div>
                <div class="text item">
                    <div style="line-height: 40px;border: 1px solid #C9C9C9;border-radius: 0.5rem;text-align: center" @click="toTel">咨询电话:17735152388</div>
                </div>
            </el-card>
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

        },
        created(){

        },
        mounted () {

        },
        methods: {
            toTel:function () {
                window.location.href = 'tel:17735152388';
            }
        }
    })
</script>
</html>