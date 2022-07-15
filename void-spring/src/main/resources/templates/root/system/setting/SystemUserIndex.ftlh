<#-- @ftlroot "../../.." -->
<!--创建日期:2020-08-13-->
<#import "/root/component/form/VUE-METHODS.ftlh" as VUE>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#import "/root/component/element/ICON-ELEMENT.ftlh" as ELEMENT>
<#assign tableName='必要用户表' controllerPrefix="/api/systemUser" pageSize='15'>
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
            <el-form-item label="上级用户Id"><el-input v-model="searchInline.belongToId" placeholder="请填写上级用户Id" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="账号"><el-input v-model="searchInline.account" placeholder="请填写账号" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="用户名"><el-input v-model="searchInline.userName" placeholder="请填写用户名" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="电话"><el-input v-model="searchInline.tel" placeholder="请填写电话" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="openId"><el-input v-model="searchInline.openId" placeholder="请填写openId" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="邮箱"><el-input v-model="searchInline.email" placeholder="请填写邮箱" size="mini" class="search-item"></el-input></el-form-item>
            <el-form-item label="密文"><el-input v-model="searchInline.secret" placeholder="请填写密文" size="mini" class="search-item"></el-input></el-form-item>
    </@INIT.searchElementInit>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
            <el-button type="success" @click="location.reload()"><i class="el-icon-refresh"></i></el-button>
            <el-button type="warning" @click="dataBus">传递数据</el-button>
            <el-button type="warning" @click="logData">测试</el-button>
        </el-col>
        <el-col :span="12">
            <@INIT.operationElementInit allowAccessRealDelete=false rules="rules" isSelect=isSelect>
                <el-form-item label="账号" prop="account"><el-input v-model="form.account"></el-input></el-form-item>
                <el-form-item label="用户名"><el-input v-model="form.userName"></el-input></el-form-item>
                <el-form-item label="电话"><el-input v-model="form.tel"></el-input></el-form-item>
                <el-form-item label="openId"><el-input v-model="form.openId"></el-input></el-form-item>
                <el-form-item label="邮箱">
<#--                    <el-input v-model="form.email"></el-input>-->
                    <@VUE.selectOtherTableElement dataName="role" controllerPrefix="${projectPath}/api/systemUserRole"  demandForField="uuid" toFormField="email" displayName="roleName"/>
<#--                    <el-input placeholder="请点击右侧选择" v-model="roleSelect.displayString('roleName','uuid','email')" disabled>-->
<#--                        <el-button slot="append" icon="el-icon-search" @click="roleSelect.dialogDisplay = true"></el-button>-->
<#--                    </el-input>-->
<#--                    <el-dialog title="提示" :visible.sync="roleSelect.dialogDisplay" width="70%" :modal="false" destroy-on-close append-to-body>-->
<#--                        <iframe src="/api/systemUserRole/index?select=true&selectField=roleSelect&from=uuid&toField=email" class="full-div" style="border: none;height: 700px"></iframe>-->
<#--                    </el-dialog>-->
                </el-form-item>
                <el-form-item label="密文"><el-input v-model="form.secret"></el-input></el-form-item>
            </@INIT.operationElementInit>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <@INIT.tableInit idName="uuid" deleteDisplayField="account">
        <el-table-column sortable prop="uuid" label="主键id/UUID"></el-table-column>
        <el-table-column sortable prop="belongToId" label="上级用户Id"></el-table-column>
        <el-table-column sortable prop="account" label="账号"></el-table-column>
        <el-table-column sortable prop="userName" label="用户名"></el-table-column>
        <el-table-column sortable prop="tel" label="电话"></el-table-column>
        <el-table-column sortable prop="openId" label="openId"></el-table-column>
        <el-table-column sortable prop="email" label="邮箱"></el-table-column>
        <el-table-column sortable prop="secret" label="密文"></el-table-column>
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
                'account':'',
                'userName':'',
                'tel':'',
                'openId':'',
                'email':'',
                'secret':'',
            </@INIT.searchInline>
            <@INIT.form>
                'uuid':'',
                'belongToId':'',
                'account':'',
                'userName':'',
                'tel':'',
                'openId':'',
                'email':'',
                'secret':'',
            </@INIT.form>
            rules:{
                account:[
                    {required:true,message:"请填写账户",trigger:'blur'}
                ]
            },
            roleCustomSelect:{
                dialogDisplay:false,
                selectData:[],
                displayString:function(display){
                    if (display == null){
                        console.error("请填写显示字段名");
                        return ;
                    }
                    let data = this.selectData;
                    let str = '[已选择'+data.length+'项] ';
                    for (let i = 0; i < data.length; i++) {
                        if (i === data.length-1){
                            str+= data[i][display];
                        }else {
                            str+= data[i][display]+',';
                        }
                    }
                    return str;
                },
            }
        },
        created: function () {
            //初始化方法
            <@INIT.createdInit/>
        },
        methods:{
            //方法初始化
            <@INIT.methodInit controllerPrefix=controllerPrefix idName="uuid" allowAccessRealDelete=false/>
            init:function(){
                this.getList();
            },
            logData:function (dev) {
                console.log(this.roleCustomSelect.displayString('roleName'));
                console.log(this.form.email);
            },
        }
    })
</script>
</html>