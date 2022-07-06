<!--创建日期:${date}-->
<#noparse>
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign projectPath=request.contextPath/>
</#noparse>
<#noparse><#assign tableName='</#noparse>${table.comment!}' controllerPrefix="<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>" pageSize='15' >
<#noparse>
<#assign isSelect = (RequestParameters.select?? && RequestParameters.select =='true'),
         selectField=RequestParameters.selectField!,
         from=RequestParameters.from!'id',
         toField = RequestParameters.toField!>
</#noparse>
<#list table.fields as field>
<#if field.propertyName != 'id' && field.propertyName != 'uuid'>
<#noparse>
<#assign idName="</#noparse>${field.propertyName}"/>
</#if>
</#list>

<!DOCTYPE html>
<html lang="en" xmlns="">
<head>
    <meta charset="UTF-8">
    <title><#noparse>${tableName}</#noparse></title>
    <#--    引入必要js和css-->
    <#noparse><@INIT.importScript hiddenVersion=true/></#noparse>
</head>
<style>
    [v-cloak] {
        display: none !important;
    }
</style>
<body>
<div id="vueInstance" v-cloak>
    <el-divider content-position="left">查 找</el-divider>
    <#noparse><@INIT.searchElementInit></#noparse>
    <#list table.fields as field>
        <#if field.propertyName != 'id' && field.propertyName != 'uuid'>
            <el-form-item label="${field.comment!'未填写'}"><el-input v-model="searchInline.${field.propertyName}" placeholder="请填写${field.comment!'默认'}" size="mini" class="search-item"></el-input></el-form-item>
        </#if>
    </#list>
    <#noparse></@INIT.searchElementInit></#noparse>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
            <#noparse>
            <#--            选择部分-->
            <@INIT.leftOperationElementInit isSelect=isSelect selectField=selectField from=from toField=toField/>
            </#noparse>
        </el-col>
        <el-col :span="12">
            <#noparse>
            <#--            操作部分-->
            <@INIT.operationElementInit isSelect=isSelect allowAccessRealDelete=false>
            </#noparse>
            <#list table.fields as field>
                <#if field.propertyName != 'id' && field.propertyName != 'uuid'>
                <el-form-item label="${field.comment!'未填写'}"><el-input placeholder="请填写${field.comment!'默认'}" v-model="form.${field.propertyName}"></el-input></el-form-item>
                </#if>
            </#list>
            <#noparse></@INIT.operationElementInit></#noparse>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <#noparse><@INIT.tableInit idName=idName></#noparse>
    <#list table.fields as field>
    <#if field.propertyName != 'id' && field.propertyName != 'uuid'>

    </#if>
        <el-table-column sortable prop="${field.propertyName}" label="${field.comment!'未填写'}"></el-table-column>
    </#list>
    <#noparse></@INIT.tableInit></#noparse>
</div>
</body>

<script>
    const VOID_MCH = localStorage.getItem("Void-mchId");
    <#noparse><@INIT.outterMethod/></#noparse>
    let vueInstance = new Vue({
        el: '#vueInstance',
        data: {
            <#--        必要字段初始化-->
            <#noparse><@INIT.dataInit tableName=tableName/></#noparse>
            <#--        开启关闭字段初始化-->
            <#noparse><@VUE.switchData name='switchData' data=status/></#noparse>
            <#--        行内搜索表单-->
            <#noparse><@INIT.searchInline></#noparse>
            <#list table.fields as field>
                <#if field.propertyName != 'id' && field.propertyName != 'uuid'>
                '${field.propertyName}':'',
                </#if>
            </#list>
            <#noparse></@INIT.searchInline></#noparse>
            <#--         提交表单字段-->
            <#noparse><@INIT.form></#noparse>
            <#list table.fields as field>
                '${field.propertyName}':'',
            </#list>
            <#noparse></@INIT.form></#noparse>
        },
        created: function () {
            //初始化方法
            <#noparse><@INIT.createdInit/></#noparse>
        },
        methods:{
            //方法初始化
            <#noparse><@INIT.methodInit controllerPrefix=controllerPrefix allowAccessRealDelete=false idName=idName/></#noparse>
            init:function(){
                this.getList();
            },
        }
    })
</script>
</html>