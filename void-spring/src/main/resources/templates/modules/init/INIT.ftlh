<#assign projectPath=request.contextPath/>

<#--HTML模板起始-->
<#macro DOC_INIT title="" appname='XVOID | 春野'>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${appname}<#if title != ""> - </#if>${title}</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue/3.2.41/vue.global.prod.min.js"></script>
    <link rel="stylesheet" href="${projectPath}/static/css/index.css">
    <#nested>
</head>
</#macro>

<#--HTML模板结束-->
<#macro DOC_SIGN_END>
</html>
</#macro>