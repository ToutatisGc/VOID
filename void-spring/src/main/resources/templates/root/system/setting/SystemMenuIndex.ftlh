<#-- @ftlroot "../../.." -->
<!--创建日期:2020-07-09-->
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign tableName='系统菜单设置' controllerPrefix="/root/systemMenu" pageSize='15' >
<#assign isSelect = (RequestParameters.select?? && RequestParameters.select =='true'),selectField=RequestParameters.selectField!,
from=RequestParameters.from!'id',toField = RequestParameters.toField!>
<#assign projectPath=request.contextPath/>
<!DOCTYPE html>
<html lang="en" xmlns="">
<head>
    <meta charset="UTF-8">
    <title>${tableName}</title>
    <#--    引入必要js和css-->
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
        <el-form-item label="名称">
            <el-input v-model="searchInline.name" placeholder="名称" size="mini" class="search-item"></el-input>
        </el-form-item>
    </@INIT.searchElementInit>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
            <#--            选择部分-->
            <el-button class="reflash" @click="location.reload()"><i class="el-icon-refresh"></i>刷新</el-button>
            <el-button type="warning" @click="dataBus">传递数据</el-button>
        </el-col>
        <el-col :span="12">
            <#--            操作部分-->
            <@INIT.operationElementInit allowAccessRealDelete=false isSelect=isSelect>
                <el-form-item label="菜单名称">
                    <el-input v-model="form.name" placeholder="请填写菜单的名称"></el-input>
                </el-form-item>
                <el-form-item label="菜单图标">
                    <el-input v-model="form.label" placeholder="请填写菜单图标的class类名"></el-input>
                </el-form-item>
                <el-form-item label="跳转地址">
                    <el-input v-model="form.url" placeholder="请填写菜单跳转地址"></el-input>
                </el-form-item>
                <el-form-item label="顶级菜单Id"><el-input v-model="form.parentId" placeholder="如果为顶级菜单则为0，否则填写对应Id"></el-input></el-form-item>
            </@INIT.operationElementInit>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <@INIT.tableInit idName="id">
        <el-table-column sortable prop="name" align="center" label="名称"></el-table-column>
        <el-table-column sortable prop="label" align="center" label="图标" >
            <@ELEMENT.elementIconShow icon='label'/>
        </el-table-column>
        <el-table-column sortable prop="url" align="center" label="地址"></el-table-column>
        <el-table-column sortable prop="weight" align="center" label="权重"></el-table-column>
    </@INIT.tableInit>
</div>
</body>

<script>
    <@INIT.outterMethod/>
    let vueInstance = new Vue({
        el: '#vueInstance',
        data: {
<#--        必要字段初始化-->
            <@INIT.dataInit tableName=tableName/>
<#--        开启关闭字段初始化-->
            <@VUE.switchData name='switchData' data=status/>
<#--        行内搜索表单-->
            <@INIT.searchInline>
            'name':'',
            </@INIT.searchInline>
<#--         提交表单字段-->
            <@INIT.form>
            'id':'',
            'name':'',
            'label':'',
            'url':'',
            'parentId':'',
            </@INIT.form>
        },
        created: function () {
            //初始化方法
            <@INIT.createdInit/>
        },
        methods:{
            //方法初始化
            <@INIT.methodInit controllerPrefix=controllerPrefix allowAccessRealDelete=false idName="id"/>
            init:function(){
                this.getList();
            }
        }
    })
</script>
</html>