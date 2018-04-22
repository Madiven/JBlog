<#include "common/common.ftl">
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="UTF-8">
  <title>${blogger.nickName}</title>
  <meta name="keywords" content="JAVA, Web, Spring" />
  <meta name="description" content="小麦的个人博客，一时兴起的产物。" />
  <link href="${request.contextPath}/static/css/foreground/base.css" rel="stylesheet">
  <link href="${request.contextPath}/static/css/foreground/style.css" rel="stylesheet">
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
      <h2 class="about_h"><#if aboutPage??>${aboutPage}</#if></h2>
    <div class="bloglist">
    <#if articles.list?? && articles.list?size gt 0>
      <#list articles.list as article>
          <div class="newblog">
              <ul>
                <h3>
                    <@typeImage type=article.type />
                    <a href="${request.contextPath}/article/detail/${article.id}">${article.title}</a><br>
                    <span class="blog_date">时间 : ${article.releaseDate?string('yyyy-MM-dd hh:mm:ss')}</span>
                </h3>
                <div class="autor">
                    <span> 分类：【<a href="${request.contextPath}/article/category/${article.blogType.id}">${article.blogType.typeName}</a>】</span>
                    <span> 浏览（${article.readCount}）</span>
                    <span> 评论（${article.commentCount}）</span>
                    <span class="blog_date">时间（${article.releaseDate?string('yyyy-MM-dd hh:mm:ss')}）</span>
                </div>
                <p>${article.summary} <a href="${request.contextPath}/article/detail/${article.id}" target="_blank" class="readmore">全文</a></p>
              </ul>
              <div class="dateview">${article.releaseDate?string('yyyy-MM-dd')}</div>
          </div>
      </#list>
    </#if>
    <@buildPageList pageInfo = articles strUrl = "${request.requestUri}"/>
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
