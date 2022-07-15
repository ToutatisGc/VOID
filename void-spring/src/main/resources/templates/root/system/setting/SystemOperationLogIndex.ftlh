<!--创建日期:2020-09-05-->
<#-- @ftlroot "../../.." -->
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign tableName='' controllerPrefix="/root/systemOperationLog" pageSize='15' >
<#assign isSelect = (RequestParameters.select?? && RequestParameters.select =='true'),
        selectField=RequestParameters.selectField!,
        from=RequestParameters.from!'id',toField = RequestParameters.toField!>
<#assign projectPath=request.contextPath/>
<!DOCTYPE html>
<html lang="en" xmlns="">
<head>
    <meta charset="UTF-8">
    <title>${tableName}</title>
    <@INIT.importScript hiddenVersion=true/>
</head>
<style>
    [v-cloak] {
        display: none !important;
    }
</style>
<body>
<div id="vueInstance" v-cloak>
    <el-divider content-position="left">查 找</el-divider>
    <@INIT.searchElementInit>
            <el-form-item label="请求地址"><el-input v-model="searchInline.requestUrl" placeholder="请填写请求地址" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="访问IP"><el-input v-model="searchInline.ipAddress" placeholder="请填写访问IP" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="访问参数"><el-input v-model="searchInline.parameter" placeholder="请填写访问参数" size="mini" class="search-item"></el-input></el-form-item>
    </@INIT.searchElementInit>
<#--    <el-divider content-position="left">操 作</el-divider>-->
<#--    <el-row>-->
<#--        <el-col :span="12" style="min-height: 1px">-->
<#--            &lt;#&ndash;            选择部分&ndash;&gt;-->
<#--            <@INIT.leftOperationElementInit isSelect=isSelect selectField=selectField from=from toField=toField/>-->
<#--        </el-col>-->
<#--        <el-col :span="12">-->
<#--            &lt;#&ndash;            操作部分&ndash;&gt;-->
<#--            <@INIT.operationElementInit allowAccessRealDelete=false isSelect=isSelect>-->
<#--                <el-form-item label="请求地址"><el-input placeholder="请填写请求地址" v-model="form.requestUrl"></el-input></el-form-item>-->
<#--                <el-form-item label="访问IP"><el-input placeholder="请填写访问IP" v-model="form.ipAddress"></el-input></el-form-item>-->
<#--                <el-form-item label="访问参数"><el-input placeholder="请填写访问参数" v-model="form.parameter"></el-input></el-form-item>-->
<#--            </@INIT.operationElementInit>-->
<#--        </el-col>-->
<#--    </el-row>-->
    <el-divider content-position="left">数 据</el-divider>
    <@INIT.tableInit>
        <el-table-column sortable prop="uuid" label="UUID"></el-table-column>
        <el-table-column sortable prop="visitStatus" label="访问情况"></el-table-column>
        <el-table-column sortable prop="methodType" label="请求类型"></el-table-column>
        <el-table-column sortable prop="methodName" label="访问方法"></el-table-column>
        <el-table-column sortable prop="requestUrl" label="请求地址"></el-table-column>
        <el-table-column sortable prop="ipAddress" label="访问IP"></el-table-column>
        <el-table-column sortable prop="parameter" label="访问参数"></el-table-column>
    </@INIT.tableInit>
</div>
</body>

<script>
    <@INIT.outterMethod/>
    let vueInstance = new Vue({
        el: '#vueInstance',
        data: {
            <@INIT.dataInit tableName=tableName/>
            <@VUE.switchData name='switchData' data=status/>
            <@INIT.searchInline>
                'requestUrl':'',
                'ipAddress':'',
                'parameter':'',
            </@INIT.searchInline>
            <@INIT.form>
                'uuid':'',
                'requestUrl':'',
                'ipAddress':'',
                'parameter':'',
            </@INIT.form>
        },
        created: function () {
            //初始化方法
            <@INIT.createdInit/>
        },
        methods:{
            //方法初始化
            <@INIT.methodInit controllerPrefix=controllerPrefix allowAccessRealDelete=false/>
            init:function(){
                this.getList();
            },
        }
    })
</script>
</html>