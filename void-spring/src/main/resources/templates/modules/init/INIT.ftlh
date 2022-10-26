<#assign projectPath=request.contextPath/>

<#--HTML模板起始-->
<#macro DOC_INIT title>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="${projectPath}/static/css/index.css">
    <#nested>
</head>
</#macro>

<#--HTML模板结束-->
<#macro DOC_INIT_END>
</html>
</#macro>