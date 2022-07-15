<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/web/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <title>易扬云-关于我们</title>
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/web.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>
        .about-box{
            width: 60%;
            height: auto;
            margin: 30px 20%;
            text-align: center;
            align-items:center;
            justify-content:center;
        }

        .about-box-title{
            font-size: 2.5rem;
        }

        .about-box-content{
            font-size: 1.6rem;
            color: #919191;
            line-height: 30px;
        }

        .about-banner{
            background-image: linear-gradient(#262626,#242424);
        }

        .about-banner-content{
            position: absolute;
            width: 100%;
            height: auto;
            top: 150px;
            z-index: 1000;
        }

        .about-banner-content-body{
            text-align: center;
            font-size: 35px;
            color: white;
            margin-top: 30px;
        }

        .box-card {
            height: auto;
            margin: 30px 20px;
        }
        .box-card:hover{
            margin: 20px;
            cursor: pointer;
        }
        .cooperate-img{
            width: 250px;
            height: 150px;
            border-radius: 0.5rem;
            opacity: 0.6;
            filter: alpha(opacity=60);
        }
    </style>
</head>
<body style="height: 100%">
<div id="vueInstance" v-cloak>
    <@site.service/>
    <@site.header/>
    <div class="index-banner" style="margin-top: 0">
        <el-carousel height="500px" arrow="never">
            <el-carousel-item class="about-banner" style="text-align: center;">
                <img src="${projectPath}/static/image/web/about-index.png" style="width: inherit;height: inherit">
                <div class="about-banner-content">
                    <div class="about-banner-content-body">
                        让商业变得更简单
                    </div>
                </div>
            </el-carousel-item>
        </el-carousel>
    </div>
    <div class="about-box">
        <div class="about-box-title" style="font-size: 2.5rem">易扬云理念</div>
        <div class="about-box-content" style="width: 60%;margin-left: 20%">
            <p>始终秉持创新、合作、服务至上的理念。</p>
        </div>
        <div class="about-box-title" style="font-size: 2.5rem">易扬云经营理念</div>
        <div class="about-box-content" style="width: 60%;margin-left: 20%">
            <p>一切以客户成功为基准</p>
            <p>注重长远发展，不因商业利益伤害客户价值</p>
            <p>关注并深刻理解客户需求，不断以产品和服务满足客户需求</p>
            <p>重视与客户的情感沟通，尊重客户感受，与客户共成长</p>
            <p>秉承成就他人就是成就自己的理念，努力让客户成功</p>
        </div>
        <div class="about-box-title" style="font-size: 2.5rem">易扬云管理理念</div>
        <div class="about-box-content" style="width: 60%;margin-left: 20%">
            <p>关心员工成长</p>
            <p>为员工提供良好的工作环境和激励机制</p>
            <p>完善员工培养体系和职业发展通道，使员工获得与企业同步成长的快乐</p>
            <p>充分尊重和信任员工，不断引导和鼓励，使其获得成就的喜悦</p>
        </div>
        <div class="about-box-title" style="font-size: 2.5rem">做软件，用易扬云</div>
        <div class="about-box-content" style="width: 60%;margin-left: 20%">
            <p>易扬云在各省代理商超过1000家，入驻商户突破12000家。</p>
            <p>易扬云是一家专注研发软件、运营商业平台、代理服务、广告营销集一身的科技公司。目前已为多行业商户解决商业问题。
                易扬云围绕商业云、营销云、销售云打造智慧云端生态体系，通过去中心化的智慧商业解决方案赋能中小企业实现数字化
                转型。目前旗下拥有:微商城、智慧零售、智慧餐厅、微店外卖、智慧酒店、云管家、智慧旅游、营销圈、分销管家
                等解决方案，帮助客户在新零售时代提高营销效率和盈利能力。</p>
            <p>易扬云致力于通过产品和服务，助力中小企业向数字化转型，通过科技驱动商业革新，让管理变得更简单。</p>
        </div>
        <div class="about-box-title" style="font-size: 2.5rem">我们拥有以下特征</div>
        <div class="about-box-content" style="width: 80%;margin: 30px 10%;">
            <el-row>
                <el-col span="6">
                    <div style="width: 160px;height: 200px;background-color: #F0F8FF;border-radius: 0.5rem">
                        <div class="about-box-title" style="font-size: 2rem;text-align: left;padding:20px 15px 2px 15px">更加专业</div>
                        <div class="about-box-content" style="font-size: 1.2rem;text-align: left;margin-left: 15px">Professional</div>
                        <div class="about-box-content" style="font-size: 1.2rem;margin: 10px 20px">
                            有手艺、懂业务、善创新，并不断要求自己更专业
                        </div>
                    </div>
                </el-col>
                <el-col span="6">
                    <div style="width: 180px;height: 200px;background-color: #F0F8FF;border-radius: 0.5rem">
                        <div class="about-box-title" style="font-size: 2rem;text-align: left;padding:20px 15px 2px 15px">主动担当</div>
                        <div class="about-box-content" style="font-size: 1.2rem;text-align: left;margin-left: 15px">Ownership</div>
                        <div class="about-box-content" style="font-size: 1.2rem;margin: 10px 20px">
                            把每件事当做自己的，全情投入、勇于担当、及时补位
                        </div>
                    </div>
                </el-col>
                <el-col span="6">
                    <div style="width: 180px;height: 200px;background-color: #F0F8FF;border-radius: 0.5rem">
                        <div class="about-box-title" style="font-size: 2rem;text-align: left;padding:20px 15px 2px 15px">诚意服务</div>
                        <div class="about-box-content" style="font-size: 1.2rem;text-align: left;margin-left: 15px">Service</div>
                        <div class="about-box-content" style="font-size: 1.2rem;margin: 10px 20px">
                            站在对方的角度思考，以服务赢得口碑，以服务赢得信任
                        </div>
                    </div>
                </el-col>
                <el-col span="6">
                    <div style="width: 180px;height: 200px;background-color: #F0F8FF;border-radius: 0.5rem">
                        <div class="about-box-title" style="font-size: 2rem;text-align: left;padding:20px 15px 2px 15px">协作执行</div>
                        <div class="about-box-content" style="font-size: 1.2rem;text-align: left;margin-left: 15px">Execution</div>
                        <div class="about-box-content" style="font-size: 1.2rem;margin: 10px 20px">
                            遇问题直接沟通，有结论后高效执行，过程中充分同步、协作
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="about-box-title" style="font-size: 2.5rem">我们绝不允许</div>
        <div class="about-box-content" style="width: 80%;margin: 30px 10%;">
            <el-row style="margin: 40px auto">
                <el-col span="8">
                    <div>
                        <i class="el-icon-error" style="font-size: 5rem;color: #EE4000"></i>
                    </div>
                    <div style="margin: auto 50px">
                        <p>不尊重客户</p>
                    </div>
                </el-col>
                <el-col span="8">
                    <div>
                        <i class="el-icon-error" style="font-size: 5rem;color: #EE4000"></i>
                    </div>
                    <div style="margin: auto 50px">
                        <p>在任何场合与合作伙伴</p>
                        <p>或客户收钱、向其行贿</p>
                    </div>
                </el-col>
                <el-col span="8">
                    <div>
                        <i class="el-icon-error" style="font-size: 5rem;color: #EE4000"></i>
                    </div>
                    <div style="margin: auto 50px">
                        <p>因任何理由收合作伙伴</p>
                        <p>或客户的礼物</p>
                    </div>
                </el-col>
            </el-row>
            <el-row>
                <el-col span="8">
                    <div>
                        <i class="el-icon-error" style="font-size: 5rem;color: #EE4000"></i>
                    </div>
                    <div style="margin: auto 50px">
                        <P>拉帮结伙</P>
                        <p>搞内部政治</p>
                    </div>
                </el-col>
                <el-col span="8">
                    <div>
                        <i class="el-icon-error" style="font-size: 5rem;color: #EE4000"></i>
                    </div>
                    <div style="margin: auto 50px">
                        <p>因一己私利</p>
                        <p>损害公司和客户利益</p>
                    </div>
                </el-col>
                <el-col span="8">
                    <div>
                        <i class="el-icon-error" style="font-size: 5rem;color: #EE4000"></i>
                    </div>
                    <div style="margin: auto 50px">
                        <p>因个人私利</p>
                        <p>泄漏公司或客户信息</p>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="about-box-title" style="font-size: 2.5rem">合作行业</div>
        <div class="about-box-content" style="width: 80%;margin: 30px 10%;">
            <el-row style="margin: 40px auto">
                <el-col span="8">
                    <img class="cooperate-img" src="${projectPath}/static/image/web/cooperate1.jpg">
                    <div>电商业</div>
                </el-col>
                <el-col span="8">
                    <img class="cooperate-img" src="${projectPath}/static/image/web/cooperate2.jpg">
                    <div>零售业</div>
                </el-col>
                <el-col span="8">
                    <img class="cooperate-img" src="${projectPath}/static/image/web/cooperate3.jpg">
                    <div>餐饮业</div>
                </el-col>
            </el-row>
            <el-row>
                <el-col span="8">
                    <img class="cooperate-img" src="${projectPath}/static/image/web/cooperate4.jpg">
                    <div>生活服务业</div>
                </el-col>
                <el-col span="8">
                    <img class="cooperate-img" src="${projectPath}/static/image/web/cooperate5.jpg">
                    <div>酒店旅游业</div>
                </el-col>
                <el-col span="8">
                    <img class="cooperate-img" src="${projectPath}/static/image/web/cooperate6.jpg">
                    <div>期待您加入</div>
                </el-col>
            </el-row>
        </div>
        <div class="about-box-title" style="font-size: 2.5rem">咨询热线</div>
        <div class="about-box-content" style="width: 80%;margin: 30px 10%;">
            <img style="width: 200px;height: 200px;margin:20px 15px" src="${projectPath}/static/image/web/service-tel.jpg"/>
            <div style="text-align: center;font-size: 1.5rem;">微信扫码拨打电话</div>
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
        }
    })
</script>
</html>