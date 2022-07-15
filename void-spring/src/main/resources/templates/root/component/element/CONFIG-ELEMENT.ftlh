<#assign projectPath=request.contextPath/>


<#macro mixinStyle>
    <style>
        .gc-accordion-title{
            margin-left: 10px;
            margin-top: 12px;
            font-size: 24px;
            font-weight: lighter;
        }

        .gc-accordion-title span{
            margin-left: 10px;
            font-size: 12px;
            color: grey;
        }

        .gc-setting-content{
            margin: 10px;
        }

        .el-card__body{
            padding-top: 0;
        }
    </style>
</#macro>

<#macro configItem itemName title subHeading>
    <el-collapse-item name="${itemName}">
        <template slot="title">
            <div class="gc-accordion-title">${title}<span>${subHeading}</span></div>
        </template>
        <div class="gc-setting-content">
            <#nested>
        </div>
    </el-collapse-item>
</#macro>

<#macro configBlock name formName refName hint="" rules="" shadow="hover" labelPosition="top">
    <el-tooltip effect="dark" content="${hint}" placement="top-start">
    <el-card shadow="${shadow}" style="margin-top: 15px">
        <el-divider content-position="left">${name}</el-divider>
        <el-form ref="${refName}" :model="${formName}" <#if  rules !="">:rules="${rules}"</#if>  label-width="125px" label-position="${labelPosition}" status-icon="true" size="mini">
            <#nested>
        </el-form>
    </el-card>
    </el-tooltip>
</#macro>

<#macro field labelName type model placeHolder="" hint="" saveButton=true  width="500px"
        stringIsTextArea=false
        numberMin="0" numberMax="100000"
        switchActiveText="启用" switchInactiveText="关闭">
    <el-form-item label="${labelName}">
        <#if type == "string">
            <el-input style="width: ${width}" <#if stringIsTextArea>type="textarea"</#if> v-model="${model}" placeholder="${placeHolder}"></el-input>
        <#elseif type == "stepper">
            <el-input-number v-model="${model}" :min="${numberMin}" :max="${numberMax}" label="${labelName}"></el-input-number>
        <#elseif type == "switch">
            <el-switch v-model="${model}" active-text="${switchActiveText}" inactive-text="${switchInactiveText}"></el-switch>
        </#if>
        <#if type == "custom">
            <#nested>
        </#if>
        <#if saveButton>
        <el-tooltip effect="dark" content="${hint}" placement="top-start">
            <el-button type="warning" @click="updateValue(<#list model?split(".") as key>'${key}'<#if key_has_next>,</#if> </#list>)">保存</el-button>
        </el-tooltip>
        </#if>
    </el-form-item>
</#macro>