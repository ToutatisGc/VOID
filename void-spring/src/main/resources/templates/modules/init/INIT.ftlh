<#assign projectPath=request.contextPath/>

<#--HTML模板起始-->
<#macro DOC_INIT title="" appname='XVOID | 春野'>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
    <title>${appname}<#if title != ""> - </#if>${title}</title>
    <#--Vue库-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue/3.2.41/vue.global.prod.min.js"></script>
    <#--Vant UI库-->
<#--    <script src="https://fastly.jsdelivr.net/npm/vant@3/lib/vant.min.js"></script>-->
<#--    <link rel="stylesheet" href="https://fastly.jsdelivr.net/npm/vant@3/lib/index.css"/>-->
    <#--Element Plus库-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/element-plus/dist/index.css"/>
    <script src="https://cdn.jsdelivr.net/npm/element-plus"></script>
    <#--自定义样式库-->
    <link rel="stylesheet" href="${projectPath}/static/css/index.css">
    <#nested>
</head>
</#macro>

<#--HTML模板结束-->
<#macro DOC_SIGN_END>
</html>
</#macro>