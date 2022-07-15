<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<#import "/root/component/element/CONFIG-ELEMENT.ftlh" as CONFIG/>
<#import "/root/component/element/FILE-ELEMENT.ftlh" as FILE>
<#assign projectPath=request.contextPath/>
<#assign keys = payLoad?keys/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>开发者配置中心</title>
    <@INIT.importScript openFlex=false hiddenVersion=false/>
    <@CONFIG.mixinStyle/>
    <script src="${projectPath}/static/js/editor/wangEditor.min.js"></script>
<#--    <script src="${projectPath}/static/js/editor/zh-CN.js"></script>-->
</head>
<body style="margin: 0">
<div id="vueInstance" v-cloak>

    <el-collapse v-model="currentClickConfig" accordion>
        <#list keys as key>
            <#assign payLoadItem = payLoad[key]>
            <#assign payLoadItemKeys = payLoadItem?keys>
            <@CONFIG.configItem itemName=key title=payLoadItem.name subHeading=payLoadItem.intro>
                <#list payLoadItemKeys as payloadInnerKey>
                    <#if payloadInnerKey != "intro" && payloadInnerKey != "name" && payloadInnerKey != "order">
                        <#assign innerPayLoad = payLoadItem[payloadInnerKey] />
                        <@CONFIG.configBlock name=innerPayLoad["name"] formName=(key+"."+payloadInnerKey) refName=payloadInnerKey hint=innerPayLoad["intro"]>
                            <#assign finalPayLoadKeys = innerPayLoad?keys>
                            <#list finalPayLoadKeys as finalKey>
                                <#if finalKey != "intro" && finalKey != "name" && finalKey != "order">
                                <#assign finalPayLoad = innerPayLoad[finalKey]>
                                <#if finalPayLoad["display"]!true || (finalPayLoad["display"]?? && finalPayLoad["display"] == true)>
                                <@CONFIG.field labelName=finalPayLoad["name"] hint=finalPayLoad["intro"] type=finalPayLoad["type"] model=(key+"."+payloadInnerKey+"."+finalKey)/>
                                </#if>
                                </#if>

                            </#list>
                        </@CONFIG.configBlock>
                    </#if>
                </#list>
<#--                    自定义-->
                <#if key == 'carouselSetting'>
                    <@CONFIG.configBlock name="移动端首页轮播图" formName="mobileSettingCustom" refName="mobileSettingCustom" hint="首页轮播图">
                        <@CONFIG.field labelName="轮播图权重" hint="提示信息" type="stepper" model="mobileSettingCustom.score" saveButton=false/>
                        <@CONFIG.field labelName="选择图片" hint="选择图片" type="custom" model="mobileSettingCustom.carouselFile" saveButton=false>
                            <@FILE.imageUploadElement limitSize="5" limitNum=1/>
                        </@CONFIG.field>
                        <@CONFIG.field labelName="设置轮播图详情" hint="设置轮播图详情" type="custom" model="mobileSettingCustom.carouselDetail" saveButton=false>
                            <span class="col-lg-8" id="editor"></span>
                        </@CONFIG.field>
                        <el-button type="primary" @click="carouselSubmit">保存</el-button>
                        <el-button @click="resetForm('mobileSettingCustom')">重置</el-button>
                    </@CONFIG.configBlock>
                </#if>
            </@CONFIG.configItem>
        </#list>
    </el-collapse>
</div>
</body>
<script>
    let mchId = localStorage.getItem("Void-MchId");
    let editor;
    let vueInstance = new Vue({
        el: '#vueInstance',
        data: {
            currentClickConfig: ['${keys[0]}'],
            <#list keys as key>
            ${key}: {
            <#assign payLoadItem = payLoad[key]>
            <#assign payLoadItemKeys = payLoadItem?keys>
            <#list payLoadItemKeys as payloadInnerKey>
                <#if payloadInnerKey != "intro" && payloadInnerKey != "name" && payloadInnerKey != "order">
                <#assign innerPayLoad = payLoadItem[payloadInnerKey] />
                ${payloadInnerKey}: {
                    <#assign finalPayLoadKeys = innerPayLoad?keys>
                    <#list finalPayLoadKeys as finalKey>
                    <#if finalKey != "intro" && finalKey != "name" && finalKey != "order">
                        <#assign finalPayLoad = innerPayLoad[finalKey] />
                    ${finalKey} : <#if finalPayLoad["type"] == "switch">${finalPayLoad["value"]?c}<#else >"${finalPayLoad["value"]}"</#if><#if finalKey_has_next>,</#if>
                    </#if>
                    </#list>
                }<#if payloadInnerKey_has_next>,</#if>
                </#if>
            </#list>
            },
            </#list>
            systemSettingCustom:{

            },
            mobileSettingCustom:{
                score: 0,
                    carouselFile:'',
                    carouselDetail:''
                    }
                },
        mounted:function () {
            var E = window.wangEditor;
            editor = new E('#editor');
            editor.create();
        },
        methods:{
            <@FILE.imageUploadMethod>
            //console.log(response.content);
                if(response.sign && response.code == '000000'){
                    this.mobileSettingCustom.carouselFile = response.content.filePath;
                }
            </@FILE.imageUploadMethod>
            updateValue:function (cluster,hash,key) {
                let parameters =  {cluster:cluster,hash:hash,key:key,value:this[cluster][hash][key]};
                let _this = this;
                this.$confirm('此操作可能会改变部分功能,确认修改?', '操作提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    $.post('${projectPath}/local/updateProperty',parameters,function(response){
                        let message = "状态更新失败";
                        let type = "error";
                        if (response.sign && response.code === '000000'){
                            message = "状态更新成功";
                            type = "success";
                        }
                        _this.$message({
                            message: message,
                            type: type,
                            duration:1500,
                            showClose:true,
                            center:true
                        });
                    });
                })
            },
            carouselSubmit:function(){
                let _this = this;
                let parameters =  {
                    url:_this.mobileSettingCustom.carouselFile,
                    score:_this.mobileSettingCustom.score
                };
                $.post('${projectPath}/api/businessOps/pushCarousel',parameters,function(response){

                });
            },
            resetForm:function(formName) {alert("3333")
                this.$refs[formName].resetFields();
            }
        }
    })
</script>
</html>