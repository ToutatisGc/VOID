<!--创建日期:2020-08-13-->
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign tableName='' controllerPrefix="/api/systemUserRole" pageSize='15' >
<#assign isSelect = (RequestParameters.select?? && RequestParameters.select =='true'),selectField=RequestParameters.selectField!,
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
            <el-form-item label="角色名"><el-input v-model="searchInline.roleName" placeholder="请填写角色名" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="ROLE角色"><el-input v-model="searchInline.roleType" placeholder="请填写角色" size="mini" class="search-item"></el-input></el-form-item>
    </@INIT.searchElementInit>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
            <@INIT.leftOperationElementInit isSelect=isSelect selectField=selectField from=from toField=toField/>
<#--        左侧选择部分-->
        </el-col>
        <el-col :span="12">
            <@INIT.operationElementInit allowAccessRealDelete=false isSelect=isSelect>
                <el-form-item label="角色名"><el-input v-model="form.roleName"></el-input></el-form-item>
                <el-form-item label="角色"><el-input v-model="form.roleType"></el-input></el-form-item>
                <el-form-item label="匹配路径"><el-input v-model="form.matchPattern"></el-input></el-form-item>
            </@INIT.operationElementInit>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <@INIT.tableInit idName='uuid'>
        <el-table-column sortable prop="uuid" label="UUID"></el-table-column>
        <el-table-column sortable prop="roleName" label="角色名"></el-table-column>
        <el-table-column sortable prop="roleType" label="security角色"></el-table-column>
        <el-table-column sortable prop="matchPattern" label="匹配路径"></el-table-column>
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
                'roleName':'',
                'roleType':'',
            </@INIT.searchInline>
            <@INIT.form>
                'uuid':'',
                'roleName':'',
                'roleType':'',
                'matchPattern':'',
            </@INIT.form>
        },
        created: function () {
            //初始化方法
            <@INIT.createdInit/>
        },
        methods:{
            //方法初始化
            <@INIT.methodInit controllerPrefix=controllerPrefix allowAccessRealDelete=false idName='uuid'/>
            init:function(){
                this.getList();
            },
        }
    })
</script>
</html>