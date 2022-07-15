<!--创建日期:2020-06-26-->
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#assign title = applicationName>
<#assign systemName = shortName>
<#assign projectPath=request.contextPath/>
<!DOCTYPE html>
<html lang="en" style="height: 100%;">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <script src="${projectPath}/webjars/echarts/4.8.0/echarts.js"></script>
    <script src="${projectPath}/static/ethereal-ui/echarts/theme/dark.js"></script>
    <script src="${projectPath}/static/ethereal-ui/echarts/theme/walden.js"></script>
    <@INIT.importScript openFlex=true hiddenVersion=true/>
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/system-index.css">
    <style>
        html{
            overflow-y: hidden;
        }
        /*.el-aside{
            width: 200px !important;
        }*/
        .main{
            width: 100%;
            /*height: 95%;*/
            transition:height 200ms,width 200ms;
            /*background-color: rgba(0,0,0,0.2);*/
            background-color: rgba(255, 255, 255,0.5);
            border: 1px solid rgba(0,0,0,0.1);
            border-radius: 5px;
            overflow-y: auto;
            scrollbar-width: none; /* Firefox */
            -ms-overflow-style: none; /* IE 10+ */
        }
        ::-webkit-scrollbar {
            display: none; /* Chrome Safari */
        }
        .el-progress-circle{
            width: 5vw !important;
            height: 5vw !important;
        }

        .void-menu{
            background-color: #316dd7;
        }

        .void-menu::after{
            content: '';
            width: 100%;
            height: 2px;
            background-color: #2862cb;
            border-radius: 2px;
        }

        .void-menu-item{
            color: rgba(0, 0, 0, 0.75) !important;
        }
    </style>
</head>

<body style="width: 100%;height: 100%;margin: 0">
<div id="vueInstance" v-cloak>
    <el-container>
        <el-header>
            <el-menu class="void-menu" :default-active="activeIndex" mode="horizontal" @select="topSelect" text-color="#fff" active-text-color="#ffd04b">
                <el-menu-item index="systemName" style="padding:0;">
                    <div class="system-name">${systemName}</div>
                </el-menu-item>
                <el-submenu index="systemSetting" style="float: right;">
                    <template slot="title" class="topHoverColor">操作</template>
                    <el-menu-item class="void-menu-item" index="userDetail" style=""><span class="iconfont icon-account"></span> Toutatis_Gc</el-menu-item>
                    <el-menu-item class="void-menu-item" index="userDetail"><span class="iconfont icon-account"></span> 个人信息</el-menu-item>
                    <el-menu-item class="void-menu-item" index="logout"><span class="iconfont icon-poweroff"></span> 注 销</el-menu-item>
                </el-submenu>
                <el-submenu index="messageBox" style="float: right">
                    <template slot="title" class="topHoverColor">消息通知</template>
                    <el-menu-item class="void-menu-item" index="message:as879d98as7d789asd789sa7">商品上架 待审核</el-menu-item>
                    <el-menu-item class="void-menu-item" index="message:qwkj9qw9fqu9d0as9qdc8asa">采购入库 待审核</el-menu-item>
                    <el-menu-item class="void-menu-item" index="message:90aucasd9890q8wd8dqwdqaa">员工信息 待审核</el-menu-item>
                </el-submenu>
                <el-menu-item index="headPortrait" style="float: right">
                    <div>
                        <el-image style="width: 50px; height: 50px"
                                  src="${projectPath}/static/image/userHead.png"
                                  fit="fill"></el-image>
                    </div>
                </el-menu-item>
            </el-menu>
        </el-header>
        <el-container>
            <el-aside :width="collapseWidth" :style="{'width':collapseWidth,'height':screenHeight}">
                <el-menu class="el-menu-vertical-demo" default-active="openCollapse" background-color="rgba(255,255,255,0)" text-color="#4e5054"
                         active-text-color="#4bd5ff" :collapse="isOpenCollapse" :unique-opened="true"
                         @select="selectMenu">
                    <el-menu-item index="openCollapse" style="color: #0e57ea">
                        <i class="el-icon-full-screen"></i>
                        <span>展开/收起</span>
                    </el-menu-item>
                    <#list menuList as menu>
                        <#if menu.parentId == "0">
                            <el-submenu index="${menu.id!}">
                                <template slot="title">
                                    <i class="${menu.label!}"></i>
                                    <span>${menu.name!}</span>
                                </template>
                                <#list menuList as childMenu>
                                    <#if childMenu.parentId == menu.id>
                                    <el-menu-item index="${childMenu.id}">
                                        <#if childMenu.label??>
                                            <i class="${childMenu.label!}"></i>
                                        </#if>
                                        <span slot="title">${childMenu.name!}</span>
                                    </el-menu-item>
                                    </#if>
                                </#list>
                            </el-submenu>
                        </#if>
                    </#list>
                </el-menu>
            </el-aside>
            <el-container>
                <el-main>
                    <div style="margin: 0.15rem 0;color:#4e5054;font-size: 0.28rem;font-weight: lighter">
                        {{homeSign ? '系统首页' : currentActive}}
                    </div>
                    <div v-show="homeSign" style="" class="main">
                        <el-row>
                            <el-col :span="24">
                                <div class="main-title main-panel-window">
                                    <el-row>
                                        <el-col class="totalBox" :span="5" style="height: 12vh;background-color:rgba(255,255,255,0.25);border-radius: 0.1rem;padding: 0.2rem;text-align: center;line-height: 12vh">
                                            <div style="font-size: 1.3vw;font-weight: bolder">用户[17703433544]</div>
                                        </el-col>
                                        <el-col :span="19" style="height: 12vh;">
                                            <div style="display: flex;justify-content: center;align-items: center;height: 100%">
                                                <div class="totalBox">
                                                    <div class="totalName">
                                                        今日访客
                                                    </div>
                                                    <div style="float:left;width: 40%;text-align: center;">
                                                        <img class="leftIcon" src="${projectPath}/static/image/icon2.png"/>
                                                    </div>
                                                    <div style="float:right;width: 60%;text-align: center;font-size: 0.9vw;margin-top: 0.3rem;color: #7EC0EE;"><span
                                                                style="font-weight: lighter;font-size:1.8vw;color: #6495ED">1804</span>&nbsp;人
                                                    </div>
                                                </div>
                                                <div class="totalBox">
                                                    <div class="totalName">
                                                        今日订单
                                                    </div>
                                                    <div style="float:left;width: 40%;text-align: center;">
                                                        <img class="leftIcon" src="${projectPath}/static/image/icon1.png"/>
                                                    </div>
                                                    <div style="float:right;width: 60%;text-align: center;font-size: 0.9vw;margin-top: 0.3rem;color: #7EC0EE"><span
                                                                style="font-weight: lighter;font-size: 1.8vw;color: #6495ED">255</span>&nbsp;单
                                                    </div>
                                                </div>
                                                <div class="totalBox">
                                                    <div class="totalName">
                                                        今日收入
                                                    </div>
                                                    <div style="float:left;width: 40%;text-align: center;">
                                                        <img class="leftIcon" src="${projectPath}/static/image/icon3.png"/>
                                                    </div>
                                                    <div style="float:right;width: 60%;text-align: center;font-size: 0.9vw;margin-top: 0.3rem;color: #7EC0EE"><span
                                                                style="font-weight: lighter;font-size: 1.8vw;color: #6495ED">1.56</span>&nbsp;k
                                                    </div>
                                                </div>
                                                <div class="totalBox">
                                                    <div class="totalName">
                                                        本周收入
                                                    </div>
                                                    <div style="float:left;width: 40%;text-align: center;">
                                                        <img class="leftIcon" src="${projectPath}/static/image/icon4.png"/>
                                                    </div>
                                                    <div style="float:right;width: 60%;text-align: center;font-size: 0.9vw;margin-top: 0.3rem;color: #7EC0EE"><span
                                                                style="font-weight: lighter;font-size: 1.8vw;color: #6495ED">12.00</span>&nbsp;k
                                                    </div>
                                                </div>
                                                <div class="totalBox">
                                                    <div class="totalName">
                                                        本月收入
                                                    </div>
                                                    <div style="float:left;width: 40%;text-align: center;margin-bottom: 0.1rem">
                                                        <img class="leftIcon" src="${projectPath}/static/image/icon5.png"/>
                                                    </div>
                                                    <div style="float:right;width: 60%;text-align: center;font-size: 0.9vw;margin-top: 0.3rem;color: #87CEFA"><span
                                                                style="font-weight: lighter;font-size: 1.8vw;color: #6495ED">60.35</span>&nbsp;k
                                                    </div>
                                                </div>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <div class="main-panel-window" style="height: 57vh">
                                    <div style="display: inline-block;width: 100%;height: 5vh;border-bottom: 1px solid rgba(255, 255, 255, 0.37);font-size: 1.3vw;font-weight: 700;">
                                        <img class="headIcon" src="${projectPath}/static/image/icon7.png"/>
                                        <span style="margin: 0 -0.5vw">店铺概况</span>
                                    </div>
                                    <div id="store-data" style="width: 100%;height: 52vh;"></div>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="main-panel-window" style="height: 18vh">
                                    <div style="display: inline-block;width: 100%;height: 5vh;border-bottom: 1px solid rgba(255, 255, 255, 0.37);font-size: 1.3vw;font-weight: 700;">
                                        <img class="headIcon" src="${projectPath}/static/image/icon6.png"/>
                                        &nbsp;&nbsp;<span style="margin: 0 -1vw">今日同比昨日销售</span>
                                    </div>
                                    <el-row>
                                        <el-col :span="14">
                                            <div class="grid-content bg-purple" style="margin-left: 20px;">
                                                <div style="display: inline-block;font-size: 1.6vw;font-weight: 700;margin-left: 0.2vw;margin-top: 2vw">24567元</div>
                                                <div style="display: inline-block;font-size: 0.8vw;margin: 0.2rem 0.2vw;color: #EE3B3B">同比昨日下降60%</div>
                                            </div>
                                        </el-col>
                                        <el-col :span="10">
                                            <div class="grid-content bg-purple" style="text-align: center;">
                                                <el-progress type="circle" :percentage="60" color="#EE3B3B" style="margin: 0.2rem"></el-progress>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                                <div class="main-panel-window" style="height: 18vh">
                                    <div style="display: inline-block;width: 100%;height: 5vh;border-bottom: 1px solid rgba(255, 255, 255, 0.37);font-size: 1.3vw;font-weight: 700;">
                                        <img class="headIcon" src="${projectPath}/static/image/icon6.png"/>
                                        &nbsp;&nbsp;<span style="margin: 0 -1vw">本周同比上周销售</span>
                                    </div>
                                    <el-row>
                                        <el-col :span="14">
                                            <div class="grid-content bg-purple" style="margin-left: 20px;">
                                                <div style="display: inline-block;font-size: 1.6vw;font-weight: 700;margin-left: 0.2vw;margin-top: 2vw">115467元</div>
                                                <div style="display: inline-block;font-size: 0.8vw;margin: 0.2rem 0.2vw;color: green">同比上周上升15.3%</div>
                                            </div>
                                        </el-col>
                                        <el-col :span="10">
                                            <div class="grid-content bg-purple" style="text-align: center ">
                                                <el-progress type="circle" :percentage="15.3" color="#76EE00" style="margin: 0.2rem"></el-progress>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                                <div class="main-panel-window" style="height: 18vh">
                                    <div style="display: inline-block;width: 100%;height: 5vh;border-bottom: 1px solid rgba(255, 255, 255, 0.37);font-size: 1.3vw;font-weight: 700;">
                                        <img class="headIcon" src="${projectPath}/static/image/icon6.png"/>
                                        &nbsp;&nbsp;<span style="margin: 0 -1vw">本月同比上月销售</span>
                                    </div>
                                    <el-row>
                                        <el-col :span="14">
                                            <div class="grid-content bg-purple" style="margin-left: 20px;">
                                                <div style="display: inline-block;font-size: 1.6vw;font-weight: 700;margin-left: 0.2vw;margin-top: 2vw">1456206元</div>
                                                <div style="display: inline-block;font-size: 0.8vw;margin: 0.2rem 0.2vw;color: green">同比上月上升6.35%</div>
                                            </div>
                                        </el-col>
                                        <el-col :span="10">
                                            <div class="grid-content bg-purple" style="text-align: center ">
                                                <el-progress type="circle" :percentage="6.35" color="#76EE00" style="margin: 0.2rem"></el-progress>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div class="main-panel-window" style="height: 57vh">
                                    <div id="order-data" style="width: 100%;height:100%;"></div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <el-tabs v-model="activeTab" type="border-card" style="height: 95%" v-show="!homeSign"
                             @tab-remove="removeTable">
                        <el-tab-pane :label="item.label" :name="item.name" v-for="(item,index) in openTabs" :key="index"
                                     :closable="item.name != 'runtime'" :lazy="true">
                            <iframe :src="item.url" style="width: 100%;height: 100%;border: none"></iframe>
                        </el-tab-pane>
                    </el-tabs>
                </el-main>
                <#-- <el-footer>Toutatis_Gc</el-footer>-->
            </el-container>
        </el-container>
    </el-container>

</div>
</body>
<script>
    const VOID_MCH = localStorage.getItem("Void-mchId");
    Vue.prototype.$echarts = echarts;
    let storeEchartsInstance;
    let orderEchartsInstance;
    var menuList = "";
    let vueInstance = new Vue({
        el: '#vueInstance',
        data: {
            screenHeight: '',
            activeIndex: '1',
            activeTab: 'runtime',
            isOpenCollapse: false,
            collapseWidth: '200px',
            menuList: [],
            currentActive: '',
            bus: '',
            openTabs: [
                {'label': '系统状态', 'name': 'runtime', 'url': 'root/systemRuntimeInfo'}
            ],
            homeSign: true,
            orderEchartsInstance: {},
            isCollapse: true,
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                },
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },
            value1: '',
            value2: '',
            percentage: 20,
            customColor: '#409eff',
            customColors: [
                {color: '#B2DFEE', percentage: 20},
                {color: '#9FB6CD', percentage: 40},
                {color: '#EEE685', percentage: 60},
                {color: '#BCEE68', percentage: 80},
                {color: '#1874CD', percentage: 100}
            ]
        },
        created: function () {
            console.log(menuList)
            this.screenHeight = this.getScreenSize();
            $('.main').height($(window).height()-100);
            this.currentActive = this.openTabs[0]['label'];
            // this.initLoad();
        },
        mounted: function () {
            let $this = this;
            this.witchScreenResize();
            orderEchartsInstance = this.$echarts.init(document.getElementById('order-data'),'dark');
            storeEchartsInstance = this.$echarts.init(document.getElementById('store-data'),'dark');
            // Generate data
            var category = [];
            var dottedBase = +new Date();
            var lineData = [];
            var barData = [];

            for (var i = 1; i<20; i++) {
                var date = new Date(dottedBase += 3600 * 24 * i);
                category.push([
                    date.getFullYear(),
                    date.getMonth() + 1,
                    date.getDate()
                ].join('-'));
                var b = Math.random() * 200;
                var d = Math.random() * 200;
                barData.push(b)
                lineData.push(d + b);
            }


// option
            let orderOption = {
                title: {
                    text: '近七日订单销售占比',
                    left: 'center',
                    bottom: 10,
                    textStyle: {
                        color: '#3e3e3e'
                    }
                },
                backgroundColor: 'rgba(255,255,255,0.2)',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: ['销售额曲线', '销售额占比'],
                    textStyle: {
                        color: 'rgba(255,255,255,0.2)'
                    }
                },
                xAxis: {
                    data: category,
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    }
                },
                yAxis: {
                    splitLine: {show: false},
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    }
                },
                series: [{
                    name: '销售额曲线',
                    type: 'line',
                    smooth: true,
                    showAllSymbol: true,
                    symbol: 'emptyCircle',
                    symbolSize: 15,
                    data: lineData
                }, {
                    name: '销售额占比',
                    type: 'bar',
                    barWidth: 10,
                    itemStyle: {
                        barBorderRadius: 5,
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#c23531'},
                                {offset: 1, color: '#EE4000'}
                            ]
                        )
                    },
                    data: barData
                }, {
                    name: '销售额曲线',
                    type: 'bar',
                    barGap: '-100%',
                    barWidth: 10,
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: 'rgba(255,31,9,0.58)'},
                                {offset: 0.2, color: 'rgba(206,34,10,0.2)'},
                                {offset: 1, color: 'rgba(20,200,212,0)'}
                            ]
                        )
                    },
                    z: -12,
                    data: lineData
                }, {
                    name: 'dotted',
                    type: 'pictorialBar',
                    symbol: 'rect',
                    itemStyle: {
                        color: 'rgba(119,134,122,0.55)'
                    },
                    symbolRepeat: true,
                    symbolSize: [12, 4],
                    symbolMargin: 1,
                    z: -10,
                    data: lineData
                }]
            };
            let storeOption = {
                backgroundColor: 'rgba(128, 128, 128, 0.2)',

                title: {
                    text: '本月店铺经营占比',
                    left: 'center',
                    top: 20,
                    textStyle: {
                        color: '#ccc'
                    }
                },

                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                        colorLightness: [0, 1]
                    }
                },
                series: [
                    {
                        name: '收入权重',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '50%'],
                        data: [
                            {value: 335, name: '母婴保健'},
                            {value: 310, name: '数码配件'},
                            {value: 274, name: '护肤美妆'},
                            {value: 235, name: '个护清洁'},
                            {value: 400, name: '外卖超市'}
                        ].sort(function (a, b) { return a.value - b.value; }),
                        roseType: 'radius',
                        label: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        },
                        labelLine: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        },
                        itemStyle: {
                            color: '#c23531',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },

                        animationType: 'scale',
                        animationEasing: 'elasticOut',
                        animationDelay: function (idx) {
                            return Math.random() * 200;
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            orderEchartsInstance.setOption(orderOption);
            storeEchartsInstance.setOption(storeOption);
            window.onresize = function () {
                $this.screenHeight = $this.getScreenSize();
                let $main = $('.main');
                let innerHeight = window.innerHeight;
                $main.css('height',(innerHeight-140)+'px');
                // echartsInstance.resize();
                orderEchartsInstance.resize();
                storeEchartsInstance.resize();
            }
        },
        methods: {
            customColorMethod(percentage) {
                if (percentage < 30) {
                    return '#909399';
                } else if (percentage < 70) {
                    return '#e6a23c';
                } else {
                    return '#67c23a';
                }
            },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            initLoad: function () {
                let that = this;
            },
            removeTable: function (name) {
                for (let i = 0; i < this.openTabs.length; i++) {
                    if (this.openTabs[i]['name'] === name) {
                        this.openTabs.splice(i, 1);
                        if (this.openTabs.length === 0) {
                            this.activeTab = '';
                        } else if (this.openTabs.length === 1) {
                            this.activeTab = this.openTabs[0]['name'];
                        } else {
                            this.activeTab = this.openTabs[i - 1]['name'];
                        }
                        break;
                    }
                }
            },
            selectMenu: function (index, indexPath) {
                let that = this;
                switch (index) {
                    case 'openCollapse':
                        this.isOpenCollapse ? this.isOpenCollapse = !this.isOpenCollapse : this.isOpenCollapse = true;
                        this.collapseWidth = this.collapseWidth === '200px' ? '64px' : '200px';
                        break;
                    default:
                        this.homeSign = false;
                        for (let i = 0; i < this.openTabs.length; i++) {
                            if (this.openTabs[i]['name'] === index) {
                                that.activeTab = index;
                                that.currentActive = this.openTabs[i]['label'];
                                return;
                            }
                        }
                        $.ajax({
                            headers: {
                                'Void-mchId': localStorage.getItem("Void-mchId")
                            },
                            url : "${projectPath}/root/systemMenu/getById",
                            type : "post",
                            data:{
                                'id': index
                            },
                            success:function(response){
                                that.homeSign = false;
                                that.openTabs.push({
                                    label: response.content.name,
                                    name: response.content.id,
                                    url: response.content.url+'?mchId='+VOID_MCH
                                });
                                that.activeTab = response.content.id;
                                that.currentActive = response.content.name;
                            }
                        });
                        break;
                }
            },
            topSelect: function (currentSelect, childTree, element) {
                console.log(currentSelect);
                switch (currentSelect) {
                    case 'systemName':
                        this.homeSign = !this.homeSign;
                        break;
                    default:
                        break;
                }
            },
            witchScreenResize: function () {
                let $this = this;
                $this.screenHeight = $this.getScreenSize();
                let $main = $('.main');
                let innerHeight = window.innerHeight;
                $main.css('height',(innerHeight-140)+'px');
            },
            getScreenSize: function () {
                return $(window).height() - 60 + 'px';
            }
        }
    });

    function notice(title, msg, type) {
        vueInstance.$notify({
            title: title,
            message: msg,
            type: type,
            duration: 2000,
            position: 'bottom-right'
        });
    }

    function dataBus(field, value) {
        vueInstance.$data[field] = value;
    }

</script>
</html>