<aside>
    <div class="avatar">
        <a href="about.html">
            <img src="${blogger.imageName}" />
            <span>关于我</span>
        </a>
    </div>
    <div class="topspaceinfo">
        <h1>执子之手，与子偕老</h1>
        <p>于千万人之中，我遇见了我所遇见的人....</p>
    </div>
    <div class="about_c">
    <#assign array = ["原创","转载","翻译"] >
    <#if countMap??>
        <#list 1..3 as key>
            <li>
            ${array[key - 1]}: <#if countMap[key?c]??>${countMap[key?c]} <#else> 0 </#if>篇
            </li>
        </#list>
    </#if>
    </div>
    <br> <br> <br> <br> <br> <br>
    <div class="tj_news">
        <div id="panel_Search">
            <h2>
                <p class="tj_t0">文章搜索</p>
            </h2>
            <ul class="panel_body">
                <form id="frmSearch" action="${request.contextPath}/article/search" method="post" onsubmit="return check()">
                    <span><input id="inputSearch" name="search" type="text" class="blogsearch" title="请输入关键字"></span>
                    <input id="btnSubmit" type="submit" value="搜索" title="search in blog">
                </form>
            </ul>
        </div>
        <h2>
            <p class="tj_t1">最新文章</p>
        </h2>
        <ul>
        <#if currentBlogs?? && currentBlogs?size gt 0>
            <#list currentBlogs as currentBlog>
                <li>
                    <a href="${request.contextPath}/article/detail/${currentBlog.id}"><@titleFormat title=currentBlog.title /></a>
                </li>
            </#list>
        </#if>
        </ul>
        <h2>
            <p class="tj_t2">文章分类</p>
        </h2>
        <ul>
        <#if blogTypes?? && blogTypes?size gt 0>
            <#list blogTypes as blogType>
                <li class="blog_type_li">
                    <a href="${request.contextPath}/article/category/${blogType.id}">${blogType.typeName}</a><span> (${blogType.blogCount})</span>
                </li>
            </#list>
        </#if>
        </ul>
        <h2>
            <p class="tj_t3">阅读排行</p>
        </h2>
        <ul>
        <#if topReadBlogs?? && topReadBlogs?size gt 0>
            <#list topReadBlogs as topReadBlog>
                <li>
                    <a href="${request.contextPath}/article/detail/${topReadBlog.id}"><@titleFormat title=topReadBlog.title /></a><span>(${topReadBlog.readCount})</span>
                </li>
            </#list>
        </#if>
        </ul>
        <h2>
            <p class="tj_t4">归档</p>
        </h2>
        <ul>
        <#if recordMap??&&recordMap?size gt 0>
            <#assign x=0 />
            <#list recordMap?keys as key>
                <#if x<=4 >
                    <#assign x=x+1 />
                    <li class="record_li">
                        <a href="${request.contextPath}/article/month/${(key)?substring(0,4)}/${(key)?substring(5,7)}">${key}</a><span> (${recordMap[key]})</span>
                    </li>
                </#if>
            </#list>
            <#if x gt 4>
                <span class="hidelist" style="display: none;">
                    <#list recordMap?keys as key>
                        <#assign x=x-1 />
                        <#if x<0 >
                            <li class="record_li">
                                <a href="${request.contextPath}/article/month/${(key)?substring(0,4)}/${(key)?substring(5,7)}">${key}</a><span> (${recordMap[key]})</span>
                            </li>
                        </#if>
                    </#list>
                </span>
                <div class="list_closed" id="archive_list_button">展开</div>
            </#if>
        </#if>
        </ul>
    </div>
    <div class="links">
        <h2>
            <p>友情链接</p>
        </h2>
        <ul>
        <#if links?? && links?size gt 0>
            <#list links as link>
                <li>
                    <a href="${link.linkUrl}">${link.linkName}</a>
                </li>
            </#list>
        </#if>
        </ul>
    </div>
</aside>