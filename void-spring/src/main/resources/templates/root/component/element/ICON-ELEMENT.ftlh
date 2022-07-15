<#assign projectPath=request.contextPath/>
<#macro iconfontShow icon>
    <template slot-scope="scope">
        <i :class="('iconfont ')+scope.row.${icon}"></i>
    </template>
</#macro>

<#macro elementIconShow icon>
<template slot-scope="scope">
    <i :class="scope.row.${icon}"></i>
</template>
</#macro>


<#macro materialChooseButton title="选择图标">
    <a class="void-icon-target" href="${projectPath}/mobile/icons" target="_blank">${title}</a>
</#macro>

<#macro materialInit>
    <link rel="stylesheet" href="//cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css">
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/css/material-system.css">
</#macro>

<#macro materialIcon className theme="light" size=40 appendClass="" click="">
    <i @click="${click}" aria-hidden="true" class="void-icon notranslate mdi void-theme--${theme} ${appendClass}" :class="${className}" style="font-size: ${size}px;"></i>
</#macro>

