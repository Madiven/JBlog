<#macro titleFormat title >
    <#if title?length gt 15>
    ${title?substring(0,14)}...
    <#else>
    ${title}
    </#if>
</#macro>

<#macro typeImage type>
    <#switch type>
        <#case 1><span class="ico ico_type_Original"></span><#break>
        <#case 2><span class="ico ico_type_Repost"></span><#break>
        <#case 3><span class="ico ico_type_Translated"></span><#break>
        <#default>
    </#switch>
</#macro>

<#macro buildPageList pageInfo strUrl>
    <#assign index = strUrl?last_index_of("list")>
    <#if index gte 0>
        <#assign url = strUrl?substring(0,index-1) + "/list">
    <#else>
        <#assign url = strUrl + "/list">
    </#if>
    <#if pageInfo??>
        <#if pageInfo.list?? && pageInfo.list?size gt 0>
        <div class="wrap clearfix">
            <div class="pagelist" id="pagelist">
                <ul class="page">
                    <li><span> ${pageInfo.total}条  共${pageInfo.pages}页</span>
                    <#if !pageInfo.isFirstPage>
                        <li><a href="${url}/1">首页</a></li>
                        <li><a href="${url}/${pageInfo.pageNum - 1}">上一页</a></li>
                    </#if>
                    <#if pageInfo.navigateFirstPage gt 5>
                        <li><a href="${url}/${navigateFirstPage - 1}">...</a></li>
                    </#if>
                    <#list pageInfo.navigatepageNums as navigatepageNum>
                        <#if navigatepageNum == pageInfo.pageNum>
                            <li><strong>${navigatepageNum}</strong></li>
                        <#else>
                            <li><a href="${url}/${navigatepageNum}">${navigatepageNum}</a></li>
                        </#if>
                    </#list>
                    <#if pageInfo.navigateLastPage < pageInfo.pages>
                        <li><a href="${url}/${endPage + 1}">...</a></li>
                    </#if>
                    <#if !pageInfo.isLastPage>
                        <li><a href="${url}/${pageInfo.pageNum + 1}">下一页</a></li>
                        <#if pageInfo.navigatepageNums?size < 5>
                            <li><a href="${url}/${pageInfo.pages}">尾页</a></li>
                        </#if>
                    </#if>
                </ul>
            </div>
        </div>
        <#else>
        <div class="wrap clearfix">
            <div class="pagelist" id="pagelist">
                <ul class="page" >
                    <li><span> 0条  共0页</span></li>
                </ul>
            </div>
        </div>
        </#if>

    </#if>
</#macro>
