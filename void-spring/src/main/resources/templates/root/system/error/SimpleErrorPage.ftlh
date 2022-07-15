<#assign projectPath=request.contextPath/>
<!DOCTYPE html>
<html lang="en" style="background-color: #dfdfdf">
<head>
    <meta charset="UTF-8">
    <title>错误页面</title>
    <style>
        .center{
            font-size: 100px;
            font-weight: lighter;
            text-align: center;
            -webkit-user-select: none;
            user-select: none;
        }
    </style>
</head>
<body>
<#if code??>
    <#if code=='404'>
        <div class="center" style="margin-top: 350px">${code}</div>
        <div class="center" style="font-size: 30px">- 资源不存在 -</div>
    <#elseif code =='500'>
        <div class="center" style="margin-top: 350px">${code}</div>
        <div class="center" style="font-size: 30px">- 内部错误 -</div>
    <#else >
        <div class="center" style="margin-top: 350px">ERROR</div>
        <div class="center" style="font-size: 30px">- 未知错误 -</div>
    </#if>
    <#else >
        <div class="center" style="margin-top: 350px">ERROR</div>
        <div class="center" style="font-size: 30px">- 未知错误 -</div>
</#if>


</body>
</html>