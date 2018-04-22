<#macro titleFormat title>
    <#if title?length gt 15>
    ${title?substring(0,14)}...
    <#else>
    ${title}
    </#if>
</#macro>
