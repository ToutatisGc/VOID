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
<#if message??>
    <div class="center" style="margin-top: 350px">403</div>
    <div class="center" style="font-size: 30px">- ${message} -</div>
    <#else >
    <div class="center" style="margin-top: 350px">ERROR</div>
    <div class="center" style="font-size: 30px">- 未认证 -</div>
</#if>


</body>
</html>