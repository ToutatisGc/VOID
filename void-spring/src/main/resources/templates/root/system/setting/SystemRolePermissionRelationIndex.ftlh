<!--创建日期:2020-08-13-->
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign tableName='角色与权限关系' controllerPrefix="/api/systemRolePermissionRelation" pageSize='15' >
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
            <el-form-item label="角色ID"><el-input v-model="searchInline.roleId" placeholder="请填写角色ID" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="权限ID"><el-input v-model="searchInline.permissionId" placeholder="请填写权限ID" size="mini" class="search-item"></el-input></el-form-item>
    </@INIT.searchElementInit>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
            <el-button type="success" @click="location.reload()"><i class="el-icon-refresh"></i></el-button>
            <el-button type="warning" @click="dataBus">传递数据</el-button>
        </el-col>
        <el-col :span="12">
            <@INIT.operationElementInit allowAccessRealDelete=false>
                <el-form-item label="角色ID"><el-input v-model="form.roleId"></el-input></el-form-item>
                <el-form-item label="权限ID"><el-input v-model="form.permissionId"></el-input></el-form-item>
            </@INIT.operationElementInit>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <@INIT.tableInit>
        <el-table-column sortable prop="id" label="自增"></el-table-column>
        <el-table-column sortable prop="roleId" label="角色ID"></el-table-column>
        <el-table-column sortable prop="permissionId" label="权限ID"></el-table-column>
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
                'roleId':'',
                'permissionId':'',
            </@INIT.searchInline>
            <@INIT.form>
                'id':'',
                'roleId':'',
                'permissionId':'',
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