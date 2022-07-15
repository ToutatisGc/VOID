<#-- @ftlroot "../../.." -->
<!--创建日期:2020-07-09-->
<#import "/root/component/form/FORM-FIELD.ftlh" as field>
<#import "/root/component/form/VUE-METHODS.ftlh" as vue>
<#import "/root/component/init/INDEX-INIT.ftlh" as init>
<#assign tableName='系统静态字段'>
<#assign projectPath=request.contextPath/>
<#assign isSelect = (RequestParameters.select?? && RequestParameters.select =='true'),selectField=RequestParameters.selectField!,
from=RequestParameters.from!'id',toField = RequestParameters.toField!>
<!DOCTYPE html>
<html lang="en" xmlns="">
<head>
    <meta charset="UTF-8">
    <title>${tableName}</title>
<#--    引入必要js和css-->
    <@init.importScript hiddenVersion=true/>
</head>
<style>
    [v-cloak] {
        display: none !important;
    }
</style>
<body>
<div id="vueInstance" v-cloak>
    <el-divider content-position="left">查 找</el-divider>
    <el-form :inline="true" :model="searchInline">
        <@field.status model="searchInline.status" size="mini" label="状态" class="search-item"/>
        <el-form-item label="期间选择">
            <el-date-picker v-model="searchInline.createStart" type="datetime" placeholder="选择日期时间" align="right" :picker-options="pickerOptions" size="mini" class="search-item"></el-date-picker> -
            <el-date-picker v-model="searchInline.createEnd" type="datetime" placeholder="选择日期时间" align="right" :picker-options="pickerOptions" size="mini" class="search-item"></el-date-picker>
        </el-form-item>
        <el-form-item label="名称">
            <el-input v-model="searchInline.name" placeholder="名称" size="mini" class="search-item"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="search" size="mini" >查询</el-button>
        </el-form-item>
    </el-form>
    <el-divider content-position="left">操 作</el-divider>
    <el-row>
        <el-col :span="12" style="min-height: 1px">
<#--            选择部分-->
            <el-button type="success" @click="location.reload()"><i class="el-icon-refresh"></i></el-button>
            <el-button type="warning" @click="dataBus">传递数据</el-button>
        </el-col>
        <el-col :span="12">
<#--            操作部分-->
<#--            【危】将从库中删除-->
<#--            <el-button type="danger" class="rightButton" @click="realDelete">批量删除</el-button>-->
            <el-button type="danger" class="rightButton" @click="tombstone">批量删除</el-button>
            <el-button type="success" class="rightButton" @click="addition">添 加</el-button>
            <el-dialog :title="(addOrView?'添加':'查看')+tableName" :visible.sync="dialogFormVisible">
                <el-form label-position="left" :model="form" label-width="80px">
                    <el-form-item label="上级依赖">
                        <el-select v-model="form.parentId" clearable filterable placeholder="请选择" popper-class="select-width">
                            <el-option v-for="item in selectTable" :key="item.id" :label="item.name" :value="item.id">
                                <span style="float: left">{{ item.name }}</span>
                                <span class="select-grey-text">{{ item.code }}</span>
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="编 号"><el-input v-model="form.code"></el-input></el-form-item>
                    <el-form-item label="名 称"><el-input v-model="form.name"></el-input></el-form-item>
                    <el-form-item label="分 类">
                        <@field.dropDown isFormDrop=false data="classifyTable" model="form.classify" key="name" value="code" ></@field.dropDown>
                    </el-form-item>
                    <el-form-item label="权 重">
                        <el-input-number v-model="form.weight" :min="1" :max="100" label="权重"></el-input-number>
                    </el-form-item>
                    <el-form-item label="级 别"><el-input v-model="form.level"></el-input></el-form-item>
                    <@field.status model="form.status"/>
                    <el-form-item label="操 作">
                        <el-button type="success" @click="submitForm" >{{addOrView ? '提 交':'更 新'}}</el-button>
                        <el-button v-show="addOrView" type="warning" @click="resetForm">重 置</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </el-col>
    </el-row>
    <el-divider content-position="left">数 据</el-divider>
    <el-table v-loading="loading" :data="treeData" style="width: 100%;min-height: 75%" row-key="id" border lazy :load="loadChild" @selection-change="multiSelectHandle" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column type="selection" prop="id" label="多选" ></el-table-column>
        <el-table-column sortable prop="code" label="编码" width="300"></el-table-column>
        <el-table-column sortable prop="name" label="名称" ></el-table-column>
        <el-table-column sortable prop="classify" label="分类">
            <@vue.slotSwitch prop="classify" data="classify" class="statusBack"/>
        </el-table-column>
        <el-table-column sortable prop="weight" label="权重"></el-table-column>
        <el-table-column sortable prop="createTimeStr" label="创建日期"></el-table-column>
        <el-table-column sortable prop="status" label="状态">
            <@vue.slotSwitch prop="status" data="switchData" class="statusBack"/>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
            <template slot-scope="scope">
                <el-button type="success" @click="viewDetail(scope.row.id)" size="small">查看</el-button>
                <el-button type="danger" @click="tombstoneOne(scope.row.id,scope.row.name)" size="small">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination style="float: right" @size-change="handleSizeChange" @current-change="handleCurrentChange" background="true"
                   :current-page="currentPage" :page-sizes="[5, 15, 30, 50, 100]" :page-size="pageSize"
                   layout="prev,pager,next,total,sizes,jumper" :total="totalCount"></el-pagination>
</div>
</body>

<script>
    <@init.outterMethod/>
    let vueInstance = new Vue({
        el: '#vueInstance',
        data: {
            <@vue.switchData name='switchData' data=status/>
            <@vue.switchData name='classify' data=classify/>
            tableName:'${tableName}',
            currentPage:1,
            pageSize:15,
            totalCount:0,
            treeData:[],
            loading:false,
            selectTable:[],
            multiSelectTable:[],
            classifyTable:[],
            dialogFormVisible:false,
            addOrView:true,
            searchSign:false,
            searchInline:{
                'code':'',
                'name':'',
                'classify':'',
                'weight':'',
                'level':'',
                'status':'${normalSign}',
                'createStart':'',
                'createEnd':'',
                'currentPage':1,
                'pageSize':15
            },
            form:{
                'id':'',
                'parentId':'',
                'code':'',
                'name':'',
                'classify':'',
                'weight':'50',
                'level':'',
                'status':'${normalSign}'
            },
            pickerOptions: {
                shortcuts: [
                    {text: '今天', onClick(picker) {picker.$emit('pick', new Date());}},
                    {text: '昨天', onClick(picker) {const date = new Date();date.setTime(date.getTime() - 3600 * 1000 * 24);picker.$emit('pick', date);}},
                    {text: '一周前', onClick(picker) {const date = new Date();date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);picker.$emit('pick', date);}}
                ]
            }
        },
        created: function () {
            this.init();
        },
        methods:{
            init:function(){
                this.getTree(0);
                let _this = this;
                $.post("${projectPath}/root/systemDictionary/getSelectList",{code:'0'},function (response) {
                    _this.selectTable = response.content;
                });
                $.post("${projectPath}/root/systemDictionary/getClassifyTable",function (response) {
                    _this.classifyTable = response.content;
                });
            },
            addition:function(){
                this.addOrView = true;
                this.resetForm();
                this.dialogFormVisible = true;
            },
            resetForm:function(){
                for (let formKey in this.form) {
                    switch (formKey) {
                        case 'id':
                            if (this.addOrView){
                                this.form[formKey] = '';
                            }
                            break;
                        case 'status':
                            this.form[formKey] = '${normalSign}';
                            break;
                        case 'weight':
                            this.form[formKey] = '50';
                            break;
                        default:
                            this.form[formKey] = '';
                            break;
                    }
                }
            },
            viewDetail:function(id){
                let _this = this;
                $.post("${projectPath}/root/systemDictionary/getById",{'id':id},function (response) {
                    <@vue.mappingContentKey/>
                    _this.addOrView = false;
                    _this.dialogFormVisible = true;
                    <@vue.notice isVariable=true title='response.message' message='response.resultTime'/>
                })
            },
            tombstone:function(){
                let _this = this;
                let multiSelectTable = _this.multiSelectTable;
                $.ajax({
                    url:"${projectPath}/root/systemDictionary/tombstone",
                    type: "POST",
                    data: JSON.stringify(multiSelectTable),
                    dataType:"json",
                    contentType : 'application/json;charset=utf-8',
                    success: function (response) {
                        <@vue.notice isVariable=true title='response.message' message='response.resultTime'/>
                        _this.getTree(0)
                    }
                });
            },
            //真实删除[慎用]
            realDelete:function(){
                let _this = this;
                let multiSelectTable = _this.multiSelectTable;
                $.ajax({
                    url:"${projectPath}/root/systemDictionary/batchDeleteReal",
                    type: "POST",
                    data: JSON.stringify(multiSelectTable),
                    dataType:"json",
                    contentType : 'application/json;charset=utf-8',
                    success: function (response) {
                        <@vue.notice isVariable=true title='response.message' message='response.resultTime'/>
                        _this.getTree(0)
                    }
                });
            },
            tombstoneOne:function(id,display){
                display = display === undefined ? '此纪录' : display;
                let _this = this;
                this.$confirm('确认删除 [ '+display+' ]?', '删除操作', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    $.post("${projectPath}/root/systemDictionary/tombstoneOne",{'id':id},function (response) {
                        <@vue.notice isVariable=true title='response.message' message='response.resultTime'/>
                        _this.getTree(0);
                    });
                }).catch(() => {});
            },
            search:function(){
                let searchInline = this.searchInline;
                let _this = this;
                $.ajax({
                    url:"${projectPath}/root/systemDictionary/search",
                    type: "POST",
                    data: JSON.stringify(searchInline),
                    dataType:"json",
                    contentType : 'application/json;charset=utf-8',
                    success: function (response) {
                        if (response.sign === true){
                            _this.searchSign = response.sign;
                            _this.treeData = response.content.list;
                            _this.totalCount = response.content.totalCount;
                            <@vue.notice isVariable=true title='response.message' message='response.resultTime'/>
                        }else {
                            <@vue.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                        }
                    }
                });
            },
            multiSelectHandle:function(list){
                this.multiSelectTable = list;
            },
            loadChild:function (tree, treeNode, resolve) {
                $.post("${projectPath}/root/systemDictionary/getTree",{'parentId':tree.id},function (response) {
                    resolve(response.content.tree);
                });
            },
            submitForm:function () {
                let _this = this;
                if (this.addOrView){
                    $.post("${projectPath}/root/systemDictionary/saveRecord",_this.form,function (response) {
                        if (response.sign === true){
                            _this.getTree(0);
                            _this.dialogFormVisible = false;
                            <@vue.notice isVariable=true title='response.message' message='response.resultTime'/>
                        }else {
                            <@vue.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                        }
                    })
                }else {
                    $.post("${projectPath}/root/systemDictionary/updateStatus",_this.form,function (response) {
                        if (response.sign === true){
                            _this.getTree(0);
                            _this.dialogFormVisible = false;
                            <@vue.notice isVariable=true title='response.message' message='response.resultTime'/>
                        }else {
                            <@vue.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                        }
                    })
                }
            },
            getTree(parentId){
                let _this = this;
                let currentPage = this.currentPage;
                let pageSize = this.pageSize;
                _this.loading = true;
                $.post("${projectPath}/root/systemDictionary/getTree",{'parentId':parentId,'currentPage':currentPage,'pageSize':pageSize},function (response) {
                    _this.treeData = response.content.tree;
                    window.parent.notice(response.message,response.resultTime,'success');
                    _this.loading = false;
                    if (parentId === 0){
                        _this.totalCount = response.content.total;
                    }
                    return response.content.tree;
                });
            },
            handleSizeChange:function(value){
                this.searchInline.pageSize = value;
                this.pageSize = value;
                if (this.searchSign){
                    this.search();
                }else {
                    this.getTree(0);
                }
            },
            handleCurrentChange:function(value){
                this.searchInline.currentPage = value;
                this.currentPage = value;
                if (this.searchSign){
                    this.search();
                }else {
                    this.getTree(0);
                }
            },
            dataBus:function (field,value) {
                parent.dataBus(field,value);
            }
        }
    })
</script>
</html>