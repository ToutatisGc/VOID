<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/web/component.ftlh" as site>
<!DOCTYPE html>
<html lang="en" style="height: 100%;padding-top: 0">
<head>
    <meta charset="UTF-8">
    <title>易扬云-定价与服务</title>
    <meta name="keywords" content="易扬云,易扬云微商城,小程序,公众号">
    <meta name="description" content="易扬云，提供易扬云微商城、易扬云教育、易扬云小程序、易扬云商盟等全面帮助商家经营社交电商和全渠道新零售的SaaS软件产品和服务">
    <link rel="shortcut icon" href="${projectPath}/templates/root/favicon.ico" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/site/web.css">
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <style>
        .edition-box{
            width: 80%;
            margin: 30px 10%;
            display:flex;
            align-items:center;
            justify-content:center;
        }
        .edition-box-head{
            width: 100%;
            height: 300px;
            text-align: center;
        }
        .edition-box-head-content{
            position: absolute;
            top: 50px;
            left: 28%;
            text-align: center;
            z-index: 1000;
        }
        .edition-box-head-box{
            margin: -150px 13%;
            z-index: 1000;
        }
        .edition-box-head-box-content{
            width: 250px;
            height: 320px;
            background-color: #fff;
            text-align: center;
            padding: 30px;
            box-shadow:0 1px 4px rgba(0, 0, 0, 0.3), 0 0 20px rgba(0, 0, 0, 0.1) inset;
        }
        .edition-box-head-box-content:hover{
            transform: scale(1.1);
            -webkit-transform: scale(1.1);
            -moz-transform: scale(1.1);
            -o-transform: scale(1.1);
            -ms-transform: scale(1.1);
            cursor: pointer;
        }
    </style>
</head>
<body style="height: 100%">
<div id="vueInstance" v-cloak>
    <@site.service/>
    <@site.header/>
    <div class="edition-box">
        <el-tabs v-model="activeName" stretch @tab-click="handleClick">
            <el-tab-pane label="微商城" name="first">
                <div style="width: 1200px;height: auto">
                    <div class="edition-box-head">
                        <img src="${projectPath}/static/image/web/edition-bg.png" style="width: 100%;filter:hue-rotate(45deg);">
                    </div>
                    <div class="edition-box-head-content">
                        <div style="font-size: 2.5rem;color: white">你可以根据经营情况，选择合适的微商城单店版本</div>
                        <div style="font-size: 1.6rem;color: white">（想了解微商城单店和连锁版价格，请咨询 177 3515 2388）</div>
                    </div>
                    <div class="edition-box-head-box">
                        <el-row>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">基础版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合个人或三人以下运营的团队开店，满足商品销售、推广营销等基础经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">专业版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合成长型电商、门店商家，满足推广获客、成交转化、客户留存、复购增购、分享裂变等经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">旗舰版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合规模化扩张、有多个经营场景需求的成熟商家，满足创新营销玩法等深度经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div style="margin: 200px 0 30px 0;">
                        <el-table
                                :data="tableData"
                                border
                                style="width: 100%">
                            <el-table-column
                                    prop="funcName"
                                    align="center"
                                    label="功能配置"
                                    width="360">
                                <template slot-scope="scope">
                                    <el-popover trigger="hover" placement="right" v-if="scope.row.childFunc.length > 0">
                                        <div v-for="(item,index) in scope.row.childFunc" :key="index" style="text-align: center">
                                            <p>{{ item }}</p>
                                        </div>
                                        <span slot="reference">
                                            {{ scope.row.funcName }}
                                        </span>
                                    </el-popover>
                                    <div v-else>
                                        {{ scope.row.funcName }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="base"
                                    align="center"
                                    label="基础版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.base === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.base === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="specialty"
                                    align="center"
                                    label="专业版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.specialty === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.specialty === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="flagship"
                                    align="center"
                                    label="旗舰版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.flagship === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.flagship === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <div style="margin: 30px 20%;">
                        <el-row>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/shop.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        免费试用
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        现在注册，即可免费体验开店
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">免费试用</el-button>
                                    </div>
                                </div>
                            </el-col>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/service-user.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        全程服务
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        全程指导，助你快速上手，提升经营能力
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">了解更多</el-button>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="易扬云餐饮" name="second">
                <div style="width: 1200px;height: auto">
                    <div class="edition-box-head">
                        <img src="${projectPath}/static/image/web/edition-bg.png" style="width: 100%;filter:hue-rotate(45deg);">
                    </div>
                    <div class="edition-box-head-content">
                        <div style="font-size: 2.5rem;color: white">你可以根据经营情况，选择合适的微商城单店版本</div>
                        <div style="font-size: 1.6rem;color: white">（想了解微商城单店和连锁版价格，请咨询 139 0296 1286）</div>
                    </div>
                    <div class="edition-box-head-box">
                        <el-row>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">基础版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合个人或三人以下运营的团队开店，满足商品销售、推广营销等基础经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">专业版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合成长型电商、门店商家，满足推广获客、成交转化、客户留存、复购增购、分享裂变等经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">旗舰版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合规模化扩张、有多个经营场景需求的成熟商家，满足创新营销玩法等深度经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div style="margin: 200px 0 30px 0;">
                        <el-table
                                :data="tableData"
                                border
                                style="width: 100%">
                            <el-table-column
                                    prop="funcName"
                                    align="center"
                                    label="功能配置"
                                    width="360">
                                <template slot-scope="scope">
                                    <el-popover trigger="hover" placement="right" v-if="scope.row.childFunc.length > 0">
                                        <div v-for="(item,index) in scope.row.childFunc" :key="index" style="text-align: center">
                                            <p>{{ item }}</p>
                                        </div>
                                        <span slot="reference">
                                            {{ scope.row.funcName }}
                                        </span>
                                    </el-popover>
                                    <div v-else>
                                        {{ scope.row.funcName }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="base"
                                    align="center"
                                    label="基础版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.base === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.base === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="specialty"
                                    align="center"
                                    label="专业版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.specialty === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.specialty === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="flagship"
                                    align="center"
                                    label="旗舰版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.flagship === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.flagship === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <div style="margin: 30px 20%;">
                        <el-row>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/shop.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        免费试用
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        现在注册，即可免费体验开店
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">免费试用</el-button>
                                    </div>
                                </div>
                            </el-col>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/service-user.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        全程服务
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        全程指导，助你快速上手，提升经营能力
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">了解更多</el-button>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="易扬云酒旅" name="third">
                <div style="width: 1200px;height: auto">
                    <div class="edition-box-head">
                        <img src="${projectPath}/static/image/web/edition-bg.png" style="width: 100%;filter:hue-rotate(45deg);">
                    </div>
                    <div class="edition-box-head-content">
                        <div style="font-size: 2.5rem;color: white">你可以根据经营情况，选择合适的微商城单店版本</div>
                        <div style="font-size: 1.6rem;color: white">（想了解微商城单店和连锁版价格，请咨询 139 0296 1286）</div>
                    </div>
                    <div class="edition-box-head-box">
                        <el-row>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">基础版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合个人或三人以下运营的团队开店，满足商品销售、推广营销等基础经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">专业版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合成长型电商、门店商家，满足推广获客、成交转化、客户留存、复购增购、分享裂变等经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">旗舰版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合规模化扩张、有多个经营场景需求的成熟商家，满足创新营销玩法等深度经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div style="margin: 200px 0 30px 0;">
                        <el-table
                                :data="tableData"
                                border
                                style="width: 100%">
                            <el-table-column
                                    prop="funcName"
                                    align="center"
                                    label="功能配置"
                                    width="360">
                                <template slot-scope="scope">
                                    <el-popover trigger="hover" placement="right" v-if="scope.row.childFunc.length > 0">
                                        <div v-for="(item,index) in scope.row.childFunc" :key="index" style="text-align: center">
                                            <p>{{ item }}</p>
                                        </div>
                                        <span slot="reference">
                                            {{ scope.row.funcName }}
                                        </span>
                                    </el-popover>
                                    <div v-else>
                                        {{ scope.row.funcName }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="base"
                                    align="center"
                                    label="基础版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.base === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.base === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="specialty"
                                    align="center"
                                    label="专业版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.specialty === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.specialty === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="flagship"
                                    align="center"
                                    label="旗舰版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.flagship === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.flagship === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <div style="margin: 30px 20%;">
                        <el-row>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/shop.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        免费试用
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        现在注册，即可免费体验开店
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">免费试用</el-button>
                                    </div>
                                </div>
                            </el-col>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/service-user.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        全程服务
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        全程指导，助你快速上手，提升经营能力
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">了解更多</el-button>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="易扬云教育" name="fourth">
                <div style="width: 1200px;height: auto">
                    <div class="edition-box-head">
                        <img src="${projectPath}/static/image/web/edition-bg.png" style="width: 100%;filter:hue-rotate(45deg);">
                    </div>
                    <div class="edition-box-head-content">
                        <div style="font-size: 2.5rem;color: white">你可以根据经营情况，选择合适的微商城单店版本</div>
                        <div style="font-size: 1.6rem;color: white">（想了解微商城单店和连锁版价格，请咨询 139 0296 1286）</div>
                    </div>
                    <div class="edition-box-head-box">
                        <el-row>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">基础版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合个人或三人以下运营的团队开店，满足商品销售、推广营销等基础经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">专业版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合成长型电商、门店商家，满足推广获客、成交转化、客户留存、复购增购、分享裂变等经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                            <el-col span="8">
                                <div class="edition-box-head-box-content">
                                    <div style="font-size: 2.5rem;">旗舰版</div>
                                    <div style="font-size: 1.6rem;color: #969696;margin:30px auto">适合规模化扩张、有多个经营场景需求的成熟商家，满足创新营销玩法等深度经营需求</div>
                                    <el-button type="success" plain style="width: 150px;height: 50px">免费试用</el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div style="margin: 200px 0 30px 0;">
                        <el-table
                                :data="tableData"
                                border
                                style="width: 100%">
                            <el-table-column
                                    prop="funcName"
                                    align="center"
                                    label="功能配置"
                                    width="360">
                                <template slot-scope="scope">
                                    <el-popover trigger="hover" placement="right" v-if="scope.row.childFunc.length > 0">
                                        <div v-for="(item,index) in scope.row.childFunc" :key="index" style="text-align: center">
                                            <p>{{ item }}</p>
                                        </div>
                                        <span slot="reference">
                                            {{ scope.row.funcName }}
                                        </span>
                                    </el-popover>
                                    <div v-else>
                                        {{ scope.row.funcName }}
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="base"
                                    align="center"
                                    label="基础版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.base === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.base === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="specialty"
                                    align="center"
                                    label="专业版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.specialty === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.specialty === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="flagship"
                                    align="center"
                                    label="旗舰版">
                                <template slot-scope="scope">
                                    <i :class="scope.row.flagship === true ? 'el-icon-success' : 'el-icon-error'"
                                       :style="{fontSize: '28px',color: scope.row.flagship === true ? '#2ecc40':'#FF4040'}"
                                    ></i>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <div style="margin: 30px 20%;">
                        <el-row>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/shop.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        免费试用
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        现在注册，即可免费体验开店
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">免费试用</el-button>
                                    </div>
                                </div>
                            </el-col>
                            <el-col span="12">
                                <div style="width: 300px;height: 350px;border: 1px solid #D4D4D4;text-align: center">
                                    <div style="margin: 60px 0 20px 0">
                                        <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/service-user.png"/>
                                    </div>
                                    <div style="font-size: 2.5rem">
                                        全程服务
                                    </div>
                                    <div style="margin: 30px auto;color: #B0B0B0">
                                        全程指导，助你快速上手，提升经营能力
                                    </div>
                                    <div style="margin: 60px auto">
                                        <el-button type="success" plain style="width: 150px;height: 45px">了解更多</el-button>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

    </div>
    <@site.footer/>
</div>
</body>
<script>
    let vueInstance = new Vue({
        el:'#vueInstance',
        data:{
            <@site.serviceForm/>
            activeName: 'first',
            tableData: [{
                funcName: '店铺管理',
                childFunc:[
                    '网店装修',
                    '小程序装修',
                    '微页面',
                    '全店风格',
                    '店铺导航',
                    '会员主页',
                    '我的文件'
                ],
                base: true,
                specialty: true,
                flagship:true
            },
            {
                funcName: '订单管理',
                childFunc:[
                    '实物订单',
                    '同城送订单',
                    '自提订单',
                    '分销供货订单',
                    '货到付款订单',
                    '退款维权',
                    '评价管理',
                    '批量发货',
                    '批量退款'
                ],
                base: true,
                specialty: true,
                flagship:true
            },
            {
                funcName: '商品管理 ',
                childFunc:[
                    '实物商品',
                    '虚拟商品',
                    '电子卡券',
                    '酒店商品',
                    '蛋糕烘焙商品',
                    '商品分组',
                    '我要分销'
                ],
                base: true,
                specialty: true,
                flagship:true
            },
            {
                funcName: '客户管理 ',
                childFunc:[
                    '客户管理',
                    '会员卡',
                    '积分管理',
                    '标签管理',
                    '粉丝管理'
                ],
                base: true,
                specialty: true,
                flagship:true
            },
            {
                funcName: '数据分析 ',
                childFunc:[
                    '店铺分析',
                    '商品分析',
                    '交易分析',
                    '客户分析',
                    '数据工具',
                    '分销分析'
                ],
                base: false,
                specialty: true,
                flagship:true
            },
            {
                funcName: '资产管理 ',
                childFunc:[
                    '我的资产',
                    '交易记录',
                    '广告投放金',
                    '对账单'
                ],
                base: false,
                specialty: true,
                flagship:true
            },
            {
                funcName: '微信公众号 ',
                childFunc:[],
                base: true,
                specialty: true,
                flagship:true
            },
            {
                funcName: '微信小程序 ',
                childFunc:[],
                base: false,
                specialty: true,
                flagship:true
            },
            {
                funcName: '多人拼团 ',
                childFunc:[],
                base: false,
                specialty: true,
                flagship:true
            },
            {
                funcName: '秒杀 ',
                childFunc:[],
                base: false,
                specialty: true,
                flagship:true
            },
            {
                funcName: '限时折扣 ',
                childFunc:[],
                base: false,
                specialty: false,
                flagship:true
            },
            {
                funcName: '优惠码 ',
                childFunc:[],
                base: false,
                specialty: false,
                flagship:true
            },
            {
                funcName: '优惠券 ',
                childFunc:[],
                base: false,
                specialty: true,
                flagship:true
            },
            {
                funcName: '团购返现 ',
                childFunc:[],
                base: false,
                specialty: false,
                flagship:true
            },
            {
                funcName: '订单返现 ',
                childFunc:[],
                base: false,
                specialty: false,
                flagship:true
            },
            {
                funcName: '赠品 ',
                childFunc:[],
                base: false,
                specialty: true,
                flagship:true
            },
            {
                funcName: '满减/送 ',
                childFunc:[],
                base: true,
                specialty: true,
                flagship:true
            },
            {
                funcName: '降价拍 ',
                childFunc:[],
                base: false,
                specialty: false,
                flagship:true
            },
            {
                funcName: '砍价0元购 ',
                childFunc:[],
                base: true,
                specialty: true,
                flagship:true
            },
            ]
        },
        created(){
            this.fixedHeader();
        },
        mounted () {

        },
        methods: {
            <@site.siteMethod/>
            handleClick(tab, event) {
                console.log(tab, event);
            }
        }
    })
</script>
</html>