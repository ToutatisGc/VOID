<#--表单状态下拉框-->
<#macro status model="form.status" label="数据状态" size="medium" class="">
    <el-form-item label="${label}">
        <el-select v-model="${model}" size="${size}" class="${class}">
            <#list dataStatus as status>
                <el-option key="${status.id}" label="${status.name}" value="${status.code}"></el-option>
            </#list>
        </el-select>
    </el-form-item>
</#macro>

<#--    通用下拉框-->
<#macro dropDown data="" model="" label="" key="" value="" isFormDrop=false >
    <#if isFormDrop>
    <el-form-item label="${label}">
    </#if>
        <el-select v-model="${model}">
            <el-option :key="index" :label="item.${key}" :value="item.${value}" v-for="(item,index) in ${data}"></el-option>
        </el-select>
    <#if isFormDrop>
    </el-form-item>
    </#if>
</#macro>