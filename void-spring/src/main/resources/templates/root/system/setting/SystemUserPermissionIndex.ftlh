<!--创建日期:2020-08-13-->
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign tableName='允许访问接口关联' controllerPrefix="/api/systemUserPermission" pageSize='15' >
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
            <el-form-item label="允许访问路径"><el-input v-model="searchInline.antUrl" placeholder="请填写允许访问路径" size="mini" class="search-item"></el-input></el-form-item>
    </@INIT.searchElementInit>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
            <el-button type="success" @click="location.reload()"><i class="el-icon-refresh"></i></el-button>
            <el-button type="warning" @click="dataBus">传递数据</el-button>
        </el-col>
        <el-col :span="12">
            <@INIT.operationElementInit allowAccessRealDelete=false>
                <el-form-item label="允许访问路径"><el-input v-model="form.antUrl"></el-input></el-form-item>
            </@INIT.operationElementInit>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <@INIT.tableInit>
        <el-table-column sortable prop="id" label="主键ID/自增"></el-table-column>
        <el-table-column sortable prop="antUrl" label="允许访问路径"></el-table-column>
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
                'antUrl':'',
            </@INIT.searchInline>
            <@INIT.form>
                'id':'',
                'antUrl':'',
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