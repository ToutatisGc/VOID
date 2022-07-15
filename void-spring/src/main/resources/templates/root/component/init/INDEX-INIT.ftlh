<#assign projectPath=request.contextPath/>

<#import '/root/component/form/VUE-METHODS.ftlh' as VUE>
<#import "/root/component/form/FORM-FIELD.ftlh" as FIELD>

<#--初始化页面必要加载的js和css-->
<#--参数hiddenVersion为webjars-locator的功能 添加jar包后可开启-->
<#--开启后更改pom文件对应webjar包即可更新版本-->
<#macro importScript openFlex=false hiddenVersion = false>
    <script src="${projectPath}/webjars/vue/<#if hiddenVersion>2.6.11/</#if>dist/vue.min.js"></script>
    <#if openFlex>
    <script src="${projectPath}/static/js/flexible/flexible.js"></script>
    <script src="${projectPath}/static/js/flexible/flexible_css.js"></script>
    </#if>
    <link rel="stylesheet" href="${projectPath}/static/ethereal-ui/style/theme/index.css">
    <script src="${projectPath}/webjars/element-ui/<#if hiddenVersion>2.13.0/</#if>lib/index.js"></script>
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/ethereal-ui/fonts/iconfont-index.css">
    <script type="application/javascript" src="${projectPath}/webjars/jquery/<#if hiddenVersion>3.5.1/</#if>jquery.min.js"></script>
    <link rel="icon" href="${projectPath}/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="${projectPath}/favicon.ico" type="image/x-icon">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/component-common.css">
    <style>
        @font-face {
            font-family: 'iconfont';  /* project id 1892839 */
            src: url('${projectPath}/static/ethereal-ui/fonts/iconfont.eot');
            src: url('${projectPath}/static/ethereal-ui/fonts/iconfont.eot?#iefix') format('embedded-opentype'),
            url('${projectPath}/static/ethereal-ui/fonts/iconfont.woff2') format('woff2'),
            url('${projectPath}/static/ethereal-ui/fonts/iconfont.woff') format('woff'),
            url('${projectPath}/static/ethereal-ui/fonts/iconfont.ttf') format('truetype'),
            url('${projectPath}/static/ethereal-ui/fonts/iconfont.svg#iconfont') format('svg');
        }
        .formHeight{
            height: inherit;
            overflow-y: auto;
            scrollbar-width: none; /* Firefox */
            -ms-overflow-style: none; /* IE 10+ */
        }
        ::-webkit-scrollbar {
            display: none; /* Chrome Safari */
        }

        html,body{
            height: 100%;
            /*overflow-y: hidden;*/
        }

    </style>
</#macro>

<#macro outterMethod>
    const VOID_MCH = localStorage.getItem("Void-mchId");
    function dataBus(field,from,toField,value) {
        vueInstance.$data[field]['selectData'] = value;
        vueInstance.$data[field]['dialogDisplay'] = false;
        let str = '';
        for (let i = 0; i < value.length; i++) {
            if (i === value.length-1){
                str+= value[i][from];
            }else {
                str+= value[i][from]+',';
            }
        }
        vueInstance.$data['form'][toField] = str;
    }

    function notice(title,msg,type) {
        vueInstance.$notify({
            title: title,
            message: msg,
            type: type,
            duration:2000,
            position:'bottom-right'
        });
    }
</#macro>

<#macro dataInit pageSize="15" tableName="[未填充]">
    tableName:'${tableName}',
    tableHeight:0,
    currentPage:1,
    pageSize:${pageSize},
    totalCount:0,
    tableData:[],
    loading:false,
    multiSelectTable:[],
    dialogFormVisible:false,
    addOrView:true,
    searchSign:false,
    pickerOptions: {
    shortcuts: [
        {text: '今  天', onClick(picker) {picker.$emit('pick', new Date());}},
        {text: '昨  天', onClick(picker) {const date = new Date();date.setTime(date.getTime() - 3600 * 1000 * 24);picker.$emit('pick', date);}},
        {text: '一周前', onClick(picker) {const date = new Date();date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);picker.$emit('pick', date);}}
    ]},
</#macro>

<#macro createdInit thisDirect="this" initFunction="init()">
    ${thisDirect}.${initFunction};
</#macro>

<#macro searchInline pageSize="15">
    searchInline:{
        'status':'${normalSign}',
        'createStart':'',
        'createEnd':'',
        'currentPage':1,
        'pageSize':${pageSize},
        <#nested>
    },
</#macro>

<#macro form>
    form:{
        'status':'${normalSign}',
        <#nested>
    },
</#macro>

<#macro methodInit controllerPrefix allowAccessRealDelete=false idName="id" >
<#--    //获取列表-->
    getList:function(){
        let _this = this;
        let currentPage = this.currentPage;
        let pageSize = this.pageSize;
        _this.loading = true;
        $.ajax({
            headers: {
                'Void-mchId': VOID_MCH
            },
            url : "${projectPath}${controllerPrefix}/getList",
            type : "post",
            data:{
                currentPage:currentPage,
                pageSize:pageSize
            },
            success:function(response){
                if (response.sign){
                    _this.tableData = response.content.list;
                    _this.totalCount = response.content.totalCount;
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                }else {
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                }
                _this.loading = false;
                _this.tableHeight = $(window).height()-$('.initTable').offset().top-40;
            }
        });
    },
<#--    //添加按钮响应-->
    addition:function(){
        this.addOrView = true;
        this.resetForm();
        this.dialogFormVisible = true;
    },
<#--    //重置表单-->
    resetForm:function(){
        for (let formKey in this.form) {
            switch (formKey) {
                case '${idName}':
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
<#--    //查看详情-->
    viewDetail:function(id){
        let _this = this;
        _this.loading = true;
        $.ajax({
            headers: {'Void-mchId': VOID_MCH},
            url : "${projectPath}${controllerPrefix}/getById",
            type : "post",
            data:{
                '${idName}':id
            },
            dataType:"json",
            contentType : 'application/x-www-form-urlencoded;charset=utf-8',
            success:function(response){
                if (response.sign){
                    <@VUE.mappingContentKey/>
                    _this.addOrView = false;
                    _this.dialogFormVisible = true;
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                }else {
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                }
                _this.loading = false;
            }
        });
    },
<#--    //多选删除-->
    tombstone:function(){
        let _this = this;
        let multiSelectTable = _this.multiSelectTable;
        $.ajax({
            headers: {
                'Void-mchId': VOID_MCH
            },
            url:"${projectPath}${controllerPrefix}/tombstone",
            type: "POST",
            data: multiSelectTable,
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(response){
                if (response.sign){
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                    _this.getList();
                }else {
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                }
            }
        });
    },
<#--    //多选真实删除[慎用]-->
    <#if allowAccessRealDelete>
    realDelete:function(){
        let _this = this;
        let multiSelectTable = _this.multiSelectTable;
        $.ajax({
            headers: {
                'Void-mchId': VOID_MCH
            },
            url:"${projectPath}${controllerPrefix}/batchDeleteReal",
            type: "POST",
            data: JSON.stringify(multiSelectTable),
            dataType:"json",
            contentType : 'application/x-www-form-urlencoded;charset=utf-8',
            success:function(response){
                if (response.sign){
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                    _this.getList();
                }else {
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                }
            }
        });
    },
    <#else >
    realDelete:function(){
        <@VUE.notice isVariable=false title='删除失败' message='请联系技术人员开启' type="warning"/>
    },
    </#if>
<#--    //右侧操作删除-->
    tombstoneOne:function(id,display){
        display = display === undefined ? '此纪录' : display;
        let _this = this;
        this.$confirm('确认删除 [ '+display+' ]?', '删除操作', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            $.ajax({
                headers: {
                    'Void-mchId': VOID_MCH
                },
                url:"${projectPath}${controllerPrefix}/tombstoneOne",
                type: "POST",
                data: {'${idName}':id},
                dataType:"json",
                contentType : 'application/x-www-form-urlencoded;charset=utf-8',
                success:function(response){
                    if (response.sign){
                        <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                        _this.getList();
                    }else {
                        <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                    }
                }
            });
        }).catch(() => {});
    },
<#--    //搜索-->
    search:function(){
        let searchInline = this.searchInline;
        let _this = this;
        _this.loading = true;
        $.ajax({
            headers: {
                'Void-mchId': VOID_MCH
            },
            url:"${projectPath}${controllerPrefix}/search",
            type: "POST",
            data: JSON.stringify(searchInline),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(response){
                if (response.sign === true){
                    _this.searchSign = response.sign;
                    _this.tableData = response.content.list;
                    _this.totalCount = response.content.totalCount;
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                }else {
                    <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                }
                _this.loading = false;
            }
        });
    },
<#--    //多选-->
    multiSelectHandle:function(list){
        this.multiSelectTable = list;
    },
<#--    //提交-->
    submitForm:function () {
        let _this = this;
        if (this.addOrView){
            $.ajax({
                headers: {
                    'Void-mchId': VOID_MCH
                },
                url:"${projectPath}${controllerPrefix}/saveRecord",
                type: "POST",
                data: _this.form,
                dataType:"json",
                contentType : 'application/x-www-form-urlencoded;charset=utf-8',
                success:function(response){
                    if (response.sign === true){
                        _this.getList();
                        _this.dialogFormVisible = false;
                        <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                    }else {
                        <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                    }
                }
            });
        }else {
            this.$confirm('确认修改此纪录?', '修改操作', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                $.ajax({
                    headers: {
                        'Void-mchId': VOID_MCH
                    },
                    url:"${projectPath}${controllerPrefix}/updateStatus",
                    type: "POST",
                    data: _this.form,
                    dataType:"json",
                    contentType : 'application/x-www-form-urlencoded;charset=utf-8',
                    success:function(response){
                        if (response.sign === true){
                            _this.getList();
                            _this.dialogFormVisible = false;
                            <@VUE.notice isVariable=true title='response.message' message='response.resultTime'/>
                        }else {
                            <@VUE.notice isVariable=true title='response.message' message='response.resultTime' type="warning"/>
                        }
                    }
                });
            }).catch(() => {});
        }
    },
<#--    分页大小更改当前页-->
    handleSizeChange:function(value){
        this.searchInline.pageSize = value;
        this.pageSize = value;
        if (this.searchSign){
            this.search();
        }else {
            this.getList();
        }
    },
<#--    当前页更改方法-->
    handleCurrentChange:function(value){
        this.searchInline.currentPage = value;
        this.currentPage = value;
        if (this.searchSign){
            this.search();
        }else {
            this.getList();
        }
    },
<#--    父子页面传输-->
    dataBus:function (field,from,toField,value) {
        parent.dataBus(field,from,toField,value);
    },
</#macro>

<#macro searchElementInit clickSearchMethod="search" buttonSize="mini" showStatus=true showTime=true>
    <el-form :inline="true" :model="searchInline">
        <#if showStatus>
            <@FIELD.status model="searchInline.status" size="mini" label="数据状态" class="search-item"/>
        </#if>
        <#if showTime>
            <el-form-item label="期间选择">
                <div class="block">
                    <el-date-picker
                            v-model="searchInline.createStart"
                            type="datetimerange"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            :picker-options="pickerOptions"
                            size="${buttonSize}" class="search-item"
                            :default-time="['12:00:00']">
                    </el-date-picker>
                </div>
                <#--<el-date-picker v-model="searchInline.createStart" type="datetime" placeholder="选择日期时间" align="right" :picker-options="pickerOptions" size="${buttonSize}" class="search-item"></el-date-picker> -
                <el-date-picker v-model="searchInline.createEnd" type="datetime" placeholder="选择日期时间" align="right" :picker-options="pickerOptions" size="${buttonSize}" class="search-item"></el-date-picker>-->
            </el-form-item>
        </#if>
        <#nested >
        <el-form-item>
            <el-button type="primary" @click="${clickSearchMethod}" size="${buttonSize}">查询</el-button>
        </el-form-item>
    </el-form>
</#macro>

<#macro leftOperationElementInit isSelect selectField from toField>
    <el-button class="reflash" @click="location.reload()"><i class="el-icon-refresh"></i>刷新</el-button>
    <#if isSelect>
    <el-button type="warning" @click="dataBus('${selectField}','${from}','${toField}',multiSelectTable)">选 择</el-button>
    </#if>
</#macro>

<#macro operationElementInit isSelect showStatus=true customSubmitMethod="submitForm" customResetForm="resetForm" allowAccessRealDelete=false rules="">
    <#if !isSelect>
    <#--            【危】将从库中删除-->
    <#if allowAccessRealDelete>
        <el-button type="danger" class="rightButton" @click="realDelete">[!]真实删除</el-button>
    </#if>
    <el-button class="rightButton delete" @click="tombstone"><i class="el-icon-remove-outline"></i>批量删除</el-button>
    <el-button class="rightButton add" @click="addition"><i class="el-icon-circle-plus-outline"></i>添 加</el-button>

    <el-dialog class="abow_dialog" :title="(addOrView?'添加':'查看')+tableName" width="60%" center :visible.sync="dialogFormVisible">
        <el-form label-position="right" class="formHeight" :model="form" ref="form" label-width="120px" <#if rules!= "">:rules="${rules}"</#if> status-icon="true">
            <#nested >
                <#if showStatus>
                    <@FIELD.status model="form.status"/>
                </#if>
                <el-form-item label="操 作">
                <el-button type="success" @click="${customSubmitMethod}" >{{addOrView ? '提 交':'更 新'}}</el-button>
                <el-button v-show="addOrView" type="warning" @click="${customResetForm}">重 置</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    </#if>
</#macro>

<#macro tableInit idName="id" customOperation=false customPagination=false showCreateTime=true showStatus=true deleteDisplayField="name">
    <el-table class="initTable" v-loading="loading" :height="tableHeight" :data="tableData" style="width: 100%;height: 75%" :row-style="{height: '60px'}" row-key="${idName}" border @selection-change="multiSelectHandle">
        <el-table-column type="selection" align="center" prop="${idName}" label="多选" ></el-table-column>
        <#nested >
        <#if showCreateTime>
            <el-table-column sortable align="center" prop="createTimeStr" label="创建日期" show-overflow-tooltip></el-table-column>
        </#if>
        <#if showStatus>
        <el-table-column sortable prop="status" align="center" label="数据状态">
            <template slot-scope="scope">
                <el-tag @VUE.slotSwitch prop="status"
                        data="switchData" class="statusBack"
                        :type="scope.row.status === 'SYS_OPEN_0000' ? 'primary' : 'success'"
                        disable-transitions>
                    {{scope.row.status == 'SYS_OPEN_0000' ? '开启' : '关闭'}}
                </el-tag>
            </template>
            <#--<@VUE.slotSwitch prop="status" data="switchData" class="statusBack"/>-->
        </el-table-column>
        </#if>
        <#if !customOperation>
        <el-table-column fixed="right" label="操作" align="center" width="150">
            <template slot-scope="scope">
                <el-button class="lookInfo" round @click="viewDetail(scope.row.${idName})" size="small">查看</el-button><#--idName-->
                <el-button class="delete" round @click="tombstoneOne(scope.row.${idName},scope.row.${deleteDisplayField})" size="small">删除</el-button>
            </template>
        </el-table-column>
        </#if>
    </el-table>
    <#if !customPagination>
    <el-pagination style="float: right" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   background="true" :current-page="currentPage" :page-sizes="[5, 15, 30, 50, 100]"
                   :page-size="pageSize" layout="prev,pager,next,total,sizes,jumper" :total="totalCount">
    </el-pagination>
    </#if>
</#macro>