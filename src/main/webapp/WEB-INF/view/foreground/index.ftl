<#include "common/common.ftl">
<!DOCTYPE HTML>
<html>

<head>
  <meta charset="UTF-8">
  <title>${blogger.nickName}</title>
  <meta name="keywords" content="JAVA, Web, Spring" />
  <meta name="description" content="小麦的个人博客，一时兴起的产物。" />
  <link href="${request.contextPath}/static/css/foreground/base.css" rel="stylesheet">
  <link href="${request.contextPath}/static/css/foreground/index.css" rel="stylesheet">
  <link href="${request.contextPath}/static/css/foreground/media.css" rel="stylesheet">
  <link href="${request.contextPath}/static/css/foreground/aside.css" rel="stylesheet">
  <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
  <!--[if lt IE 9]>
  <script src="${request.contextPath}/static/js/modernizr.js"></script>
  <![endif]-->
</head>

<body>
  <div class="ibody">
  <#include "common/head.ftl">
    <article>
      <div class="banner">
        <ul class="texts">
          <p>The best life is use of willing attitude, a happy-go-lucky life. </p>
          <p>最好的生活是用心甘情愿的态度，过随遇而安的生活。</p>
        </ul>
      </div>
      <div class="bloglist">
        <h2>
          <p>
            <span>推荐</span>文章
          </p>
        </h2>
        <#if recommendArticles.list?? && recommendArticles.list?size gt 0>
          <#list recommendArticles.list as recommendArticle>
              <div class="blogs">
                  <h3>
                      <@typeImage type=recommendArticle.type />
                      <a href="${request.contextPath}/article/detail/${recommendArticle.id}">${recommendArticle.title}</a><br>
                      <span class="blog_date">时间 : ${recommendArticle.releaseDate?string('yyyy-MM-dd hh:mm:ss')}</span>
                  </h3>
                  <ul>
                      <p>${recommendArticle.summary} </p>
                      <a href="${request.contextPath}/article/detail/${recommendArticle.id}" target="_blank" class="readmore">阅读全文&gt;&gt;</a>
                  </ul>
                  <p class="autor">
                      <span> 分类：【<a href="${request.contextPath}/blogType/${recommendArticle.blogType.id}">${recommendArticle.blogType.typeName}</a>】</span>
                      <span> 浏览（${recommendArticle.readCount}）</span>
                      <span> 评论（${recommendArticle.commentCount}）</span>
                      <span class="blog_date">时间（${recommendArticle.releaseDate?string('yyyy-MM-dd hh:mm:ss')}）</span>
                  </p>
                  <div class="dateview">${recommendArticle.releaseDate?string('yyyy-MM-dd')}</div>
              </div>
          </#list>
        </#if>
      </div>
      <@buildPageList pageInfo = recommendArticles  strUrl="${request.contextPath}/article/recommend" />
    </article>
  <#include "common/sidebar.ftl">

    <div class="clear"></div>
    <!-- 清除浮动 -->
  </div>
</body>
<script src="${request.contextPath}/static/js/jquery-3.2.1.min.js"></script>
<script src="${request.contextPath}/static/js/silder.js"></script>
<script src="${request.contextPath}/static/js/snow.js"></script>
<script src="${request.contextPath}/static/js/main.js"></script>
<style type="text/css">.snow-container{position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:100001;}</style>
<div class="snow-container"></div>
</html>