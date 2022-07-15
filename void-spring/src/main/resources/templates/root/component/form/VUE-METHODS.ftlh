<#assign projectPath=request.contextPath/>

<#--比较数据 放入data的最前方-->
<#macro switchData name data>
${name}:eval('${data}'.replace(/&quot;/g,"'")),
</#macro>

<#--    嵌入字典值-->
<#macro slotSwitch prop data class="">
<template slot-scope="scope">
    <div class="${class}" v-for="(item,index) in ${data}" :key="index">
        <div v-if="item.code === scope.row.${prop}">
            {{item.name}}
        </div>
    </div>
</template>
</#macro>

<#--    右下角消息提示-->
<#macro notice isVariable=false code="000000" title="请添加TITLE变量" message="" type="success">
<#--    　　alert(window.frames.length);-->
<#--    　　alert(parent.frames.length);-->
<#--    if (window.frames.length != parent.frames.length) {-->
<#--    }-->
    window.parent.notice(<#if !isVariable>'</#if>${title}<#if !isVariable>'</#if>,<#if !isVariable>'</#if>${message}<#if !isVariable>'</#if>,'${type}');
</#macro>

<#macro mappingContentKey thisDirect="_this">
    let content = response.content;
    for (let formKey in _this.form) {
        ${thisDirect}.form[formKey] = content[formKey];
    }
</#macro>

<#--   选择其他表数据-->
<#macro selectOtherTableElement dataName controllerPrefix demandForField="id" toFormField="" displayName="name" demandForField="id" icon="el-icon-search">
    <el-input placeholder="请点击右侧选择" v-model="${dataName}CustomSelect.displayString('${displayName}','${demandForField}','<#if toFormField == "">${dataName}<#else>${toFormField}</#if>')" disabled>
    <el-button slot="append" icon="${icon}" @click="${dataName}CustomSelect.dialogDisplay = true"></el-button>
    </el-input>
    <el-dialog title="选择" :visible.sync="${dataName}CustomSelect.dialogDisplay" width="70%" :modal="false" destroy-on-close append-to-body>
        <iframe src="${controllerPrefix}/index?select=true&selectField=${dataName}CustomSelect&from=${demandForField}&toField=${toFormField}" class="full-div" style="border: none;height: 700px"></iframe>
    </el-dialog>
</#macro>