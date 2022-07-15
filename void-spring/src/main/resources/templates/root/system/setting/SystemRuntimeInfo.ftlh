<#assign projectPath=request.contextPath/>
<#import "/root/component/element/FILE-ELEMENT.ftlh" as FILE/>
<!--创建日期:2020-07-09-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>系统运行状况</title>
    <script src="${projectPath}/webjars/vue/2.6.11/dist/vue.min.js"></script>
<#--    <script src="${projectPath}/static/js/flexible/flexible.js"></script>-->
<#--    <script src="${projectPath}/static/js/flexible/flexible_css.js"></script>-->
    <link rel="stylesheet" href="${projectPath}/static/ethereal-ui/style/theme/index.css">
    <script src="${projectPath}/webjars/element-ui/2.13.0/lib/index.js"></script>
    <link type="text/css" rel="stylesheet" href="${projectPath}/static/ethereal-ui/fonts/iconfont-index.css">
</head>
<style>
    [v-cloak] {
        display: none !important;
    }
</style>
<body>
<div id="vueInstance" v-cloak>
    <#--<@FILE.imageUploadElement/>-->
</div>
</body>
<script>
    new Vue({
        el: '#vueInstance',
        data: {
            form:{
                name:"aaaa"
            },
            fileList:[]
        },
        created: function () {
        },
        methods:{
            <@FILE.imageUploadMethod>
                console.log(response);
            </@FILE.imageUploadMethod>
        }
    })
</script>
</html>