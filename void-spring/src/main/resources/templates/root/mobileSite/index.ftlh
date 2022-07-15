<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/mobileSite/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <title>易扬云-易扬云微商城，小程序，免费试用</title>
    <meta name="keywords" content="易扬云,易扬云微商城,小程序,公众号">
    <meta name="description" content="易扬云，提供易扬云微商城、易扬云教育、易扬云小程序、易扬云商盟等全面帮助商家经营社交电商和全渠道新零售的SaaS软件产品和服务">
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/mobile.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>
        .carousel-box{
            width: 100%;
            margin:0;
        }

        .Introduction {
            width: 80%;
            margin-left: 10%;
            display:flex;
            align-items:center;
            justify-content:center;
            background-color: #F0F8FF;
            border-radius: 0.5rem;
        }

        .Animation {
            position: relative;
            width: 100%;
            height: 600px;
            background-image: url("${projectPath}/static/image/Animation-bg.png");
            background-size: 100% 100%;

        }

        .Animation-card-title{
            float: left;
            font-size: 2.5rem;
            line-height: 30px;
            margin: auto 10px;
            color: white;
        }

        .Animation-card-more{
            float: right;
            line-height: 30px;
            margin: auto 10px;
            color: #0e57ea;
        }

        .Animation-card-title,.Animation-card-more:hover{
            color: white !important;
        }

        .function{
            position: relative;
            width: 80%;
            margin-left: 10%;
        }

        .function-icon{
            font-size: 3.5rem;
            color: #4876FF;
            margin: 0.5rem auto;
        }

        .function-title{
            font-size: 2rem;
        }
        .function-sub-title{
            color: #BDBDBD;
        }

        .safe {
            margin-top: 60px;
            width: 100%;
            height: auto;
        }

        .safe-content{
            width: 100%;
            height: 900px;
            background-color: rgba(14, 14, 14, 0.8);
            text-align: center;

        }

        .safe-content-card{
            width: 100%;
            min-width: 300px;
            max-width: 1000px;
            height: auto;
            margin: 50px auto;
            background-color: rgba(49, 49, 49, 0.8);
            border-radius: 0.5rem;
            border: none;
            text-align: left;

        }

        .safe-content-card-txt{
            text-align: left;
            font-size: 1.5rem;
            color: white;
            line-height: 30px;
        }

        .item {
            margin: 0.5rem auto;
            height: 100px;
            border-bottom: 1px solid #EBEBEB;
        }

        .item:hover{
            cursor: pointer;
            background-image: linear-gradient(#FCFCFC,#CAE1FF);
        }

        .item-icon{
            font-size: 4rem;
            line-height: 100px;
            color: #4876FF;
        }

        .item-title{
            font-size: 1.6rem;
            margin: 10px auto;
        }

        .item-content{
            color: #AAAAAA;
        }

        .box-card {
            position: inherit;
            width: 320px;
            margin: 50px 20px;
            background-color: white;
            display: inline-block;
            border-radius: 0.5rem;
        }
        .box-card:hover{
            transform: scale(1.1);
            -webkit-transform: scale(1.1);
            -moz-transform: scale(1.1);
            -o-transform: scale(1.1);
            -ms-transform: scale(1.1);
        }
        .box-card-header{
            width: 100%;
            height: 60px;
            background-image:linear-gradient(to left top,#48D1CC,#4876FF);
            border-radius: 0.5rem 0.5rem 0 0;
        }
        .el-carousel__arrow{
            background-color: rgba(12, 22, 36, 0.41);
        }
    </style>
</head>
<body style="height: 100%;width: 100%">

<div id="vueInstance" v-cloak>
    <#--返回顶部层-->
    <@site.service/>
    <@site.header/>
    <div class="carousel-box">
        <el-carousel height="200px">
            <el-carousel-item v-for="item in carouselList" :key="item" style="border-radius: 0.8rem">
                <img :src="item" style="width: 100%;height: 200px;border-radius: 0.8rem"/>
            </el-carousel-item>
        </el-carousel>
    </div>
    <div style="width: 100%;text-align: center;margin-top: 30px;font-size: 30px;">关于我们</div>
    <div style="color: #7A7A7A">
        <el-divider >提供全生命周期的综合性服务</el-divider>
    </div>
    <div class="Introduction">
        <el-row>
            <el-col span="24">
                <div style="margin: 30px">
                    以互联网定制开发为主营业务，专注微信服务号、企业号、微信公众平台开发和微信小程序等的微信开发服务。凭借专业的研发团队，在微信商城、会员、分销、营销互动以及商业地产、酒店、医疗美容、汽车和零售等行业具有丰富的开发经验，全方位满足企业个性化需求。
                </div>
            </el-col>
            <el-col span="24" style="text-align: right">
                <div class="more">
                    了解更多
                    <i class="el-icon-arrow-right"></i>
                </div>
            </el-col>
        </el-row>
    </div>
    <div style="width: 100%;text-align: center;margin-top: 30px;font-size: 25px;">了解我们的SaaS产品</div>
    <div style="color: #7A7A7A">
        <el-divider >多行业解决产品</el-divider>
    </div>
    <div class="Animation">
        <div style="width: 100%">
            <el-carousel height="600px" indicator-position="none" interval="4000">
                <el-carousel-item>
                    <div class="box-card">
                        <div class="box-card-header">
                            <el-button class="Animation-card-title" type="text">电商零售</el-button>
                            <el-button class="Animation-card-more" type="text">了解更多</el-button>
                        </div>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">微商城</div>
                                <div class="item-content">助力企业构建私域流量池</div>
                                <div class="item-content">跨入移动电商时代</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">智慧零售</div>
                                <div class="item-content">打通线上线下业务场景</div>
                                <div class="item-content">助力品牌数字化经营</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">直客推</div>
                                <div class="item-content">专为直营电商打造</div>
                                <div class="item-content">有效提升广告抵达率，转化率，复购率</div>
                            </el-col>
                        </el-row>
                        <el-row class="item" style="border: none">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">无人零售</div>
                                <div class="item-content">AI赋能零售升级</div>
                                <div class="item-content">SaaS助力企业营销</div>
                            </el-col>
                        </el-row>
                    </div>
                </el-carousel-item>
                <el-carousel-item>
                    <div class="box-card">
                        <div class="box-card-header">
                            <el-button class="Animation-card-title" type="text">餐饮外卖</el-button>
                            <el-button class="Animation-card-more" type="text">了解更多</el-button>
                        </div>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">智慧餐厅</div>
                                <div class="item-content">专业餐厅解决方案</div>
                                <div class="item-content">让餐厅经营更智慧</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">易扬外卖</div>
                                <div class="item-content">整合业务、营销、会员、物流等</div>
                                <div class="item-content">商户经营所需的功能</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">云管家</div>
                                <div class="item-content">餐饮小程序+外卖平台聚合接单</div>
                                <div class="item-content">多种物流智能配送，发单更省钱、更高效</div>
                            </el-col>
                        </el-row>
                        <el-row class="item" style="border: none">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">云小店</div>
                                <div class="item-content">打造外卖私域流量</div>
                                <div class="item-content">精准营销提升效益</div>
                            </el-col>
                        </el-row>
                    </div>
                </el-carousel-item>
                <el-carousel-item>
                    <div class="box-card">
                        <div class="box-card-header">
                            <el-button class="Animation-card-title" type="text">本地生活</el-button>
                            <el-button class="Animation-card-more" type="text">了解更多</el-button>
                        </div>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">客来店</div>
                                <div class="item-content">助力线下门店</div>
                                <div class="item-content">快速实现交易变现</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">智慧酒店</div>
                                <div class="item-content">微信订房一键触达</div>
                                <div class="item-content">酒店营销智慧升级</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">智慧旅游</div>
                                <div class="item-content">旅游营销轻而易举</div>
                                <div class="item-content">商家管理快捷简单</div>
                            </el-col>
                        </el-row>
                        <el-row class="item" style="border: none">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">智慧美业</div>
                                <div class="item-content">一站式美业营销管理</div>
                                <div class="item-content">开启美业新时代</div>
                            </el-col>
                        </el-row>
                    </div>
                </el-carousel-item>
                <el-carousel-item>
                    <div class="box-card">
                        <div class="box-card-header">
                            <el-button class="Animation-card-title" type="text">营销推广</el-button>
                            <el-button class="Animation-card-more" type="text">了解更多</el-button>
                        </div>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">微站</div>
                                <div class="item-content">3分钟打造可视化企业官网</div>
                                <div class="item-content">集品牌、产品、营销、数据为一体</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">销售推</div>
                                <div class="item-content">重新定义销售</div>
                                <div class="item-content">帮助企业获客</div>
                            </el-col>
                        </el-row>
                        <el-row class="item">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">智营销</div>
                                <div class="item-content">一站式数字化营销解决方案</div>
                                <div class="item-content">沉淀企业营销数据，提升企业营销效果</div>
                            </el-col>
                        </el-row>
                        <el-row class="item" style="border: none">
                            <el-col span="4" style="text-align: center">
                                <i class="el-icon-caret-right item-icon"></i>
                            </el-col>
                            <el-col span="20">
                                <div class="item-title">会务</div>
                                <div class="item-content">智能化SaaS软件应用</div>
                                <div class="item-content">一站式 全流程数字化会务解决方案</div>
                            </el-col>
                        </el-row>
                    </div>
                </el-carousel-item>
            </el-carousel>
        </div>
    </div>
    <div style="width: 100%;text-align: center;margin-top: 30px;font-size: 30px;">做生意，找易扬云</div>
    <div style="color: #7A7A7A">
        <el-divider >打造共赢的商业生态，为商家提供全方位服务</el-divider>
    </div>
    <div class="function">
        <el-row style="text-align: center;margin: 2.5rem auto">
            <el-col span="12">
                <div>
                    <i class="el-icon-phone function-icon"></i>
                </div>
                <div class="function-title">在线客服</div>
                <div class="function-sub-title">专业解答，贴心陪伴</div>
            </el-col>
            <el-col span="12">
                <div>
                    <i class="el-icon-video-camera-solid function-icon"></i>
                </div>
                <div class="function-title">视频教学</div>
                <div class="function-sub-title">教你开店，教你运营</div>
            </el-col>
        </el-row>
        <el-row style="text-align: center;margin: 2.5rem auto">
            <el-col span="12">
                <div>
                    <i class="el-icon-question function-icon"></i>
                </div>
                <div class="function-title">新店上线服务</div>
                <div class="function-sub-title">服务经理，全程指导</div>
            </el-col>
            <el-col span="12">
                <div>
                    <i class="el-icon-platform-eleme function-icon"></i>
                </div>
                <div class="function-title">服务市场</div>
                <div class="function-sub-title">装修/拍摄/代运营</div>
            </el-col>
        </el-row>
        <el-row style="text-align: center;margin: 2.5rem auto">
            <el-col span="12">
                <div>
                    <i class="el-icon-menu function-icon"></i>
                </div>
                <div class="function-title">应用市场</div>
                <div class="function-sub-title">各种玩法，应有尽有</div>
            </el-col>
            <el-col span="12">
                <div>
                    <i class="el-icon-s-order function-icon"></i>
                </div>
                <div class="function-title">商家社区</div>
                <div class="function-sub-title">产品动态，商家交流</div>
            </el-col>
        </el-row>
        <el-row style="text-align: center;margin: 2.5rem auto">
            <el-col span="12">
                <div>
                    <i class="el-icon-s-check function-icon"></i>
                </div>
                <div class="function-title">本地商盟</div>
                <div class="function-sub-title">资源对接，抱团发展</div>
            </el-col>
            <el-col span="12">
                <div>
                    <i class="el-icon-s-shop function-icon"></i>
                </div>
                <div class="function-title">定制服务</div>
                <div class="function-sub-title">个性化服务，帮你实现</div>
            </el-col>
        </el-row>
    </div>
    <div class="safe">
        <div style="width: 100%;text-align: center;margin-top: 30px;font-size: 30px;">系统安全</div>
        <div style="color: #7A7A7A">
            <el-divider >安全-稳定的支持</el-divider>
        </div>
        <div class="safe-content">
            <div style="padding-top: 30px;">
                <el-row style="font-family:Avenir;font-style: italic">
                    <el-col span="17" offset="7">
                        <div class="safe-content-card-txt" style="border-left: 5px solid #436EEE">&nbsp;&nbsp;<span style="font-size: 3.5rem;font-weight: bold;">98.99</span>%</div>
                        <div class="safe-content-card-txt">&nbsp;&nbsp;&nbsp;&nbsp;系统可用</div>
                    </el-col>
                    <el-col span="17" offset="7">
                        <div class="safe-content-card-txt" style="border-left: 5px solid #436EEE">&nbsp;&nbsp;<span style="font-size: 3.5rem;font-weight: bold;">32,000</span>笔</div>
                        <div class="safe-content-card-txt">&nbsp;&nbsp;&nbsp;&nbsp;每秒处理订单数</div>
                    </el-col>
                    <el-col span="17" offset="7">
                        <div class="safe-content-card-txt" style="border-left: 5px solid #436EEE">&nbsp;&nbsp;<span style="font-size: 3.5rem;font-weight: bold;">< 300</span>毫秒</div>
                        <div class="safe-content-card-txt">&nbsp;&nbsp;&nbsp;&nbsp;服务平均响应</div>
                    </el-col>
                </el-row>
            </div>

            <el-card class="safe-content-card" shadow="always">
                <el-row>
                    <el-col span="20">
                        <div class="safe-content-card-txt">安全稳定</div>
                        <div class="safe-content-card-txt" style="font-size: 1.2rem">集易扬云多年技术积累，混合云部署，云防火墙，多地互备，服务治理，弹性伸缩能力，全方位监控，为商户的业务保驾护航。</div>
                    </el-col>
                    <el-col span="4">
                        <img src="${projectPath}/static/image/web/safe.png" style="width: 70px;height: 70px;"/>
                    </el-col>
                </el-row>
            </el-card>
            <el-card class="safe-content-card" shadow="always">
                <el-row>
                    <el-col span="20">
                        <div class="safe-content-card-txt">极致体验</div>
                        <div class="safe-content-card-txt" style="font-size: 1.2rem">BGP网络，CDN加速，单页面技术，优秀的技术，让你的用户无论身在何处，均能获得灵活流畅的极致体验。</div>
                    </el-col>
                    <el-col span="4">
                        <img src="${projectPath}/static/image/web/speed.png" style="width: 70px;height: 70px"/>
                    </el-col>
                </el-row>
            </el-card>
            <el-card class="safe-content-card" shadow="always">
                <el-row>
                    <el-col span="20">
                        <div class="safe-content-card-txt">智慧高效</div>
                        <div class="safe-content-card-txt" style="font-size: 1.2rem">大数据和人工智能技术，为您提供BI报表，智能客服，精准会员营销等，易扬云科技助您全面飞跃发展。</div>
                    </el-col>
                    <el-col span="4">
                        <img src="${projectPath}/static/image/web/wisdom.png" style="width: 70px;height: 70px"/>
                    </el-col>
                </el-row>
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
            <@site.serviceForm/>
            carouselList:[
                '${projectPath}/static/image/carousel1.png',
                '${projectPath}/static/image/carousel2.png',
                '${projectPath}/static/image/carousel3.png',
                '${projectPath}/static/image/carousel4.png',
            ]
        },
        created(){

        },
        mounted () {

        },
        methods: {
            <@site.siteMethod/>
        }
    })
</script>
</html>