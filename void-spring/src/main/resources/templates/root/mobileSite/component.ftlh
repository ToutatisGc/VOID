<#assign projectPath=request.contextPath/>
<#macro serviceForm>
    visible:false,
    serviceForm:{
    tel:'',
    area:'',
    product:'',
    areaList: [{
    value: '太原市',
    label: '太原市'
    }, {
    value: '晋中市',
    label: '晋中市'
    },{
    value: '阳泉市',
    label: '阳泉市'
    },{
    value: '长治市',
    label: '长治市'
    },{
    value: '晋城市',
    label: '晋城市'
    },{
    value: '朔州市',
    label: '朔州市'
    },{
    value: '运城市',
    label: '运城市'
    }, {
    value: '临汾市',
    label: '临汾市'
    },{
    value: '吕梁市',
    label: '吕梁市'
    }, {
    value: '忻州市',
    label: '忻州市'
    }, {
    value: '大同市',
    label: '大同市'
    }],
    productList:[{
    value:'易扬云微商城',
    label:'易扬云微商城'
    },
    {
    value:'易扬云餐饮',
    label:'易扬云餐饮'
    },
    {
    value:'易扬云酒旅',
    label:'易扬云酒旅'
    },
    {
    value:'易扬云教育',
    label:'易扬云教育'
    }]
    <#nested>
    },
</#macro>
<#macro service showConsult=true>
<#--返回顶部层-->
    <el-backtop right="10" bottom="150">
        <div
                style="{
        height: 50px;
        width: 70px;
        background-color: #f2f5f6;
        box-shadow: 0 0 6px rgba(0,0,0, .12);
        text-align: center;
        font-size: 1.2rem;
        border-radius: 0.5rem;
        padding: 1.5rem 0.2rem;
        color: #1989fa;
      }"
        >
            <i class="el-icon-caret-top"></i><br>
            返回顶部
        </div>
    </el-backtop>
<#--电话咨询层-->
    <#if showConsult>
        <div class="consult-box">
            <div class="consult-box-inner" @click="showService">
                <img class="consult-box-img-service img-hue" src="${projectPath}/static/image/web/service-user.png"/>
                <div>在线咨询</div>
            </div>
            <el-popover
                    placement="left"
                    width="250"
                    trigger="click">
                <div class="consult-box-tip">
                    <div style="margin: 1rem 1.5rem;font-size: 2.5rem;font-weight: bold">微信咨询</div>
                    <div style="margin: 0.8rem 1.5rem;font-size: 1.5rem;">现在咨询，送您999元大礼包</div>
                    <img style="width: 200px;height: 200px;margin:20px 15px" src="${projectPath}/static/image/web/wechat-qr.jpg"/>
                    <div style="text-align: center;font-size: 1.5rem;">手机扫码加我微信</div>
                </div>
                <div class="consult-box-inner" slot="reference">
                    <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/wechat.png"/>
                    <div>微信咨询</div>
                </div>
            </el-popover>
            <el-popover
                    placement="left"
                    width="250"
                    trigger="click">
                <div class="consult-box-tip">
                    <div style="margin: 1rem 1.5rem;font-size: 2.5rem;font-weight: bold">电话咨询</div>
                    <div style="margin: 0.8rem 1.5rem;font-size: 1.5rem;">致电领好礼(09:00-22:00)</div>
                    <img style="width: 200px;height: 200px;margin:20px 15px" src="${projectPath}/static/image/web/service-tel.jpg"/>
                    <div style="text-align: center;font-size: 1.5rem;">微信扫码拨打电话</div>
                </div>
                <div class="consult-box-inner" slot="reference">
                    <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/tel.png"/>
                    <div>电话咨询</div>
                </div>
            </el-popover>
            <el-popover
                    placement="left"
                    width="250"
                    v-model="visible"
                    trigger="click">
                <div class="consult-box-tip">
                    <div style="margin: 1rem 1.5rem;font-size: 2.5rem;font-weight: bold">预约演示</div>
                    <div style="margin: 0.8rem 1.5rem;font-size: 1.5rem;">技术顾问上门为您演示</div>
                    <el-form :model="serviceForm" style="margin: auto 0.8rem">
                        <el-form-item label="">
                            <el-input v-model="serviceForm.tel" placeholder="请输入手机号(必填)"></el-input>
                        </el-form-item>
                        <el-form-item label="">
                            <el-select v-model="serviceForm.area" placeholder="请选择所在地区">
                                <el-option
                                        v-for="item in serviceForm.areaList"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="">
                            <el-select v-model="serviceForm.product" placeholder="请选择意向产品">
                                <el-option
                                        v-for="item in serviceForm.productList"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-button style="width: 100%;margin-top: 3rem;background-color: #4876FF;color: white" @click="saveBooking">立即预约</el-button>
                    </el-form>
                </div>
                <div class="consult-box-inner" slot="reference">
                    <img class="consult-box-img img-hue" src="${projectPath}/static/image/web/demon.png"/>
                    <div>预约演示</div>
                </div>
            </el-popover>
        </div>
    </#if>
</#macro>

<#macro header>
    <div class="banner-inner-header">
        <#--<a href="${projectPath}/mobile/site" target="_self" class="inner-header-logo-div">
            <img src="${projectPath}/static/image/logo.png" style="width: 50px;height: 50px;">
        </a>-->
        <a href="${projectPath}/mobile/about" target="_self" class="inner-header-div">关于易扬云</a>
        <a href="${projectPath}/mobile/cooperate" target="_self" class="inner-header-div">渠道合作</a>
        <a href="${projectPath}/mobile/case" target="_self" class="inner-header-div">经典案例</a>
        <a href="${projectPath}/mobile/edition" target="_self" class="inner-header-div">定价与服务</a>
        <a href="${projectPath}/mobile/site" target="_self" class="inner-header-div">首页</a>
    </div>
</#macro>
<#macro footer>
    <div class="footer" style="width: 100%;">
        <div class="footer-head">
            <table class="footer-head-table">
                <caption>产品与服务</caption>
                <tbody>
                <tr>
                    <td><el-link type="success">微商城</el-link></td>
                    <td><el-link type="success">智慧餐厅</el-link></td>
                    <td><el-link type="success">微小店</el-link></td>
                    <td><el-link type="success">微站</el-link></td>
                    <td><el-link type="success">广告服务</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">智慧零售</el-link></td>
                    <td><el-link type="success">微店外卖</el-link></td>
                    <td><el-link type="success">智慧酒店</el-link></td>
                    <td><el-link type="success">推客</el-link></td>
                    <td><el-link type="success">定制开发</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">云管家</el-link></td>
                    <td><el-link type="success">智慧旅游</el-link></td>
                    <td><el-link type="success">营销圈</el-link></td>
                    <td><el-link type="success">分销管家</el-link></td>
                </tr>
                </tbody>
            </table>
            <table class="footer-head-table">
                <caption>开发与合作</caption>
                <tbody>
                <tr>
                    <td><el-link type="success">代理商登录</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">代理商查询</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">服务市场</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">易扬云</el-link></td>
                </tr>
                </tbody>
            </table>
            <table class="footer-head-table">
                <caption>帮助支持</caption>
                <tbody>
                <tr>
                    <td><el-link type="success">新手入门</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">使用指南</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">常见问题</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">联系客服</el-link></td>
                </tr>
                </tbody>
            </table>
            <table class="footer-head-table">
                <caption>关于易扬云</caption>
                <tbody>
                <tr>
                    <td><el-link type="success">易扬云介绍</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">加入易扬云</el-link></td>
                </tr>
                <tr v-for="a in 2" :key="a">
                    <td><el-link type="success"> </el-link></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="footer-content">
            <table class="footer-content-table">
                <tbody>
                <tr>
                    <td><el-link type="success">微商城解决方案</el-link></td>
                    <td><el-link type="success">智慧餐厅解决方案</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">智慧零售解决方案</el-link></td>
                    <td><el-link type="success">智慧美业解决方案</el-link></td>
                </tr>
                <tr>
                    <td><el-link type="success">智慧酒店解决方案</el-link></td>
                    <td><el-link type="success">智慧旅游解决方案</el-link></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="footer-bootom">
            <div>
                <el-link style="font-size: 0.9rem" href="">Copyright © www.sxonekey.cn All Rights Reserved 山西易扬云网络科技有限公司</el-link>
            </div>
            <div>
                <el-link style="font-size: 0.9rem" href="https://beian.miit.gov.cn/" target="_blank">版权所有 晋ICP备2020012587号-1</el-link>
            </div>
        </div>
    </div>
</#macro>
<#macro siteMethod>
    saveBooking:function(){
    let _this = this;
    if(_this.serviceForm.tel.trim() === ''){
    _this.$message({
    message: '您还没有填写联系电话',
    type: 'warning'
    });
    return false;
    }
    if(_this.serviceForm.area.trim() === ''){
    _this.$message({
    message: '您还没有选择所在地区',
    type: 'warning'
    });
    return false;
    }
    if(_this.serviceForm.product.trim() === ''){
    _this.$message({
    message: '您还没有选择意向产品',
    type: 'warning'
    });
    return false;
    }
<#--        待完成表单提交-->
    _this.$notify({
    title: '预约成功',
    message: '销售顾问将1-2日内与您联系',
    type: 'success',
    position: 'bottom-right'
    });
    _this.visible = false;
    },
    showService:function(){
    this.$message.error('暂时还未提供在线客服功能!');
    },
</#macro>