<#list tableInfo as map>
    <#list map?keys as itemKey>
        ${itemKey} : ${map[itemKey]}
    </#list>
    ==================
</#list>