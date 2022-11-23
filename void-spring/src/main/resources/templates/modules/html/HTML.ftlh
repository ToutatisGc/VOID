<#--
ElementPlus Icon 图标
param: name 图标名称
-->
<#macro ICON name>
    <el-icon><${name}></${name}></el-icon>
</#macro>

<#macro VUE_INIT var="app" element="app">
    ${var}.use(ElementPlus);
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
        ${var}.component(key, component);
    }
    ${var}.mount('#${element}');
</#macro>