<#include "common/common.ftl">
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${blogger.nickName}</title>
    <meta name="keywords" content="JAVA, Web, Spring" />
    <meta name="description" content="小麦的个人博客，一时兴起的产物。" />
    <link href="${request.contextPath}/static/css/foreground/base.css" rel="stylesheet">
    <link href="${request.contextPath}/static/css/foreground/about.css" rel="stylesheet">
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
    <h3 class="about_h"></h3>
    <div class="about">
      <h2>Just about me</h2>
      <ul>
        ${blogger.profile}
      </ul>
    </div>
  </article>
  <#include "common/sidebar.ftl">
  <div class="clear"></div>
  <!-- 清除浮动 --> 
</div>
</body>
<script src="${request.contextPath}/static/js/jquery-3.2.1.min.js"></script>
<script src="${request.contextPath}/static/js/silder.js"></script>
<script src="${request.contextPath}/static/js/snow.js"></script>
<style type="text/css">.snow-container{position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:100001;}</style>
<div class="snow-container"></div>
</html>
