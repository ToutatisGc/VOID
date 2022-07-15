<!--创建日期:2020-08-13-->
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign tableName='' controllerPrefix="/api/systemUserRoleRelation" pageSize='15' >
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
            <el-form-item label="用户id"><el-input v-model="searchInline.userId" placeholder="请填写用户id" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="角色id"><el-input v-model="searchInline.roleId" placeholder="请填写角色id" size="mini" class="search-item"></el-input></el-form-item>
    </@INIT.searchElementInit>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
            <el-button type="success" @click="location.reload()"><i class="el-icon-refresh"></i></el-button>
            <el-button type="warning" @click="dataBus">传递数据</el-button>
        </el-col>
        <el-col :span="12">
            <@INIT.operationElementInit allowAccessRealDelete=false>
                <el-form-item label="用户id"><el-input v-model="form.userId"></el-input></el-form-item>
                <el-form-item label="角色id"><el-input v-model="form.roleId"></el-input></el-form-item>
            </@INIT.operationElementInit>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <@INIT.tableInit>
        <el-table-column sortable prop="id" label="自增id"></el-table-column>
        <el-table-column sortable prop="userId" label="用户id"></el-table-column>
        <el-table-column sortable prop="roleId" label="角色id"></el-table-column>
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
                'userId':'',
                'roleId':'',
            </@INIT.searchInline>
            <@INIT.form>
                'id':'',
                'userId':'',
                'roleId':'',
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