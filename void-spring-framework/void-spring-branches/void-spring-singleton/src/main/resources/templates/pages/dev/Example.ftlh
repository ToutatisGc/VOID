<#assign projectPath=request.contextPath/>

<#import "/templates/modules/init/INIT.ftlh" as INIT >

<@INIT.DOC_INIT title=title > <#--不可删除-->
<#--填充head头-->
</@INIT.DOC_INIT> <#--不可删除-->
<body>
<#--HTML body起始-->
<div v-cloak id="app" class="xv-base-background">
    <br style="line-height:0;"/>
    <div class="xv-main-text">{{message}}</div>
</div>
</body>
    <#--临时样式-->
<style>

</style>
    <#--VUE 实例-->
<script>
    const { createApp } = Vue

    createApp({
        data() {
            return {
                message: "Hello, world!"
            }
        }
    }).mount('#app')
</script>

<@INIT.DOC_SIGN_END/> <#--不可删除-->