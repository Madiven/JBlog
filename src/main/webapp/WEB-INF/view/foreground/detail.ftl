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
    <link href="${request.contextPath}/static/css/foreground/aside.css" rel="stylesheet">
    <link href="${request.contextPath}/static/css/foreground/comment.css" rel="stylesheet">
    <link href="${request.contextPath}/static/css/foreground/media.css" rel="stylesheet">
    <link href="${request.contextPath}/static/css/foreground/validatebox.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
    <!--[if lt IE 9]>
  <script src="${request.contextPath}/static/js/modernizr.js"></script>
    <![endif]-->
</head>

<body>
  <div class="ibody">
    <#include "common/head.ftl">
        <article>
            <h2 class="about_h"></h2>
            <div class="index_about">
                <h2 class="c_titile"><@typeImage type=detail.type/>${detail.title}</h2>
                <p class="box_c"><span class="d_time">发布时间：${detail.releaseDate?string('yyyy-MM-dd')}</span><span>浏览（${detail.readCount}）</span><span>评论（${detail.commentCount}）</span></p>
                <div class="keybq">
                    <p><span>关键字词</span>：${detail.keyWord}</p>
                </div>
                <ul class="infos">
                    ${detail.content}
                </ul>

                <div class="nextinfo">
                    <#if prevBlog??><p>上一篇：<a href="${request.contextPath}/article/detail/${prevBlog.id}">${prevBlog.title}</a></p></#if>
                    <#if nextBlog??><p>下一篇：<a href="${request.contextPath}/article/detail/${nextBlog.id}">${nextBlog.title}</a></p></#if>
                </div>
                <div class="comment_class">
                    <a name="commentbox"></a>
                    <a name="reply"></a>
                    <a name="quote"></a>
                    <div id="comment_form">
                        <div id="report_title" class="panel_head">
                            <span class="see_comment">发表评论</span>
                        </div>
                        <form id="commentForm" action="/comment/submit">
                            <div class="commentform" style="height: 145px;">
                                <textarea name="content" class="comment_content" id="comment_content"
                                                  placeholder="请不要讨论涉及中国之军/政相关话题，谢谢合作！"></textarea>
                                <input name="parentId" id="comment_replyId" type="hidden">
                                <input name="blog.id" id="blog_id" type="hidden" value="${detail.id}">
                                <input class="btn btn-redborder" type="button" value="发表评论">
                                <span class="tip" id="tip_comment"></span>
                                <div class="theme-popover" style="display: none;">
                                    <div class="theme-poptit">
                                        <a title="关闭" class="close" href="javascript:;">×</a>
                                        <h3>发表评论需要验证，如果给您带来麻烦，抱歉！</h3>
                                    </div>
                                    <div class="theme-popbod dform">
                                        <ol>
                                            <!-- <li><h4>请填写验证码！</h4></li> -->
                                            <li><input id="captchaCode" name="captchaCode" class="ipt" type="text" size="20"></li>
                                            <li><a href="javascript:changeCaptcha()"><img id="changeCaptcha" src="${request.contextPath}/captcha/getCaptchaCode"></a></li>
                                            <li><input name="submit" class="btn-primary" type="submit" value=" 确 认 "></li>
                                            <span></span>
                                        </ol>
                                    </div>
                                </div>
                                <div class="theme-popover-mask" style="display: none;"></div>

                                <div class="result-popover" style="display: none;">
                                    <div class="result-poptit">
                                        <a title="关闭" class="close" href="javascript:;">×</a>
                                        <h3>提示</h3>
                                    </div>
                                    <div class="result-popbod rform">
                                        <span></span>
                                    </div>
                                </div>
                                <div class="result-popover-mask" style="display: none;"></div>
                        </div>
                    </form>
                    <div id="comment_list">
                        <div id="comment_title" class="panel_head">
                            <span class="see_comment">查看评论</span>
                        </div>
                    <#if comments?? && comments?size gt 0>
                        <@buildComment parents=comments/>
                    <#else>
                        <span class="no_data">暂无评论
                        </span>
                    </#if>
                    </div>
                </div>
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
<script src="${request.contextPath}/static/js/main.js"></script>
<script src="${request.contextPath}/static/js/shCore.js"></script>
<script type="text/javascript">
    SyntaxHighlighter.all();
</script>
<link href="${request.contextPath}/static/css/foreground/shCoreDefault.css" rel="stylesheet">
<style type="text/css">.snow-container{position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:100001;}</style>
<div class="snow-container"></div>
</html>

<#macro buildNode children parent>
    <#if children?? && children?size gt 0>
        <#list children as child>
        <dl class="comment_item comment_reply" id="${"comment_item_, ${child.comment.id}"}">
            <dt class="comment_head" floor="${child.floor}">Re:
                <span class="user">
            		<span class="username">${child.comment.userName}</span>
            		<span class="ptime">${child.comment.commentDate?string("yyyy-MM-dd HH:mm")}发表</span>
            		<a href="#reply" class="cmt_btn reply" title="回复">[回复]</a>
                    <span class="comment_manage" commentid="${child.comment.id}" username="${child.comment.userName}">
                        <#--<a href="#quote" class="cmt_btn quote" title="引用">[引用]</a>-->
                        <#--<a href="#report" class="cmt_btn report" title="举报">[举报]</a>-->
                    </span>
                </span>
            </dt>
            <dd class="comment_userface">
                <a href ="javascript:return false;" onclick="return false;" style="cursor: default;">
                    <img src="${request.contextPath}/static/images/timg.jpg" width="40" height="40">
                </a>
            </dd>
            <dd class="comment_body">${child.comment.content}</dd>
            <#if child.comments?? && child.comments?size gt 0>
                <@buildNode children=child.comments parent=child/>
            </#if>
        </dl>
        </#list>
    </#if>
</#macro>
<#macro buildComment parents>
    <#if parents?? && parents?size gt 0>
        <#list parents as parent>
        <dl class="comment_item comment_topic" id="${"comment_item_${parent.comment.id}"}">
            <dt class="comment_head" floor="${parent.floor}">${parent.floor}楼
                <span class="user">
                    	<span class="username">${parent.comment.userName}</span>
                    	<span class="ptime">${parent.comment.commentDate?string("yyyy-MM-dd HH:mm")}发表</span>
                   		<a href="#reply" class="cmt_btn reply" title="回复">[回复]</a>
                    	<span class="comment_manage" commentid="${parent.comment.id}" username="${parent.comment.userName}">
                    		<#--<a href="#quote" class="cmt_btn quote" title="引用">[引用]</a>-->
                    		<#--<a href="#report" class="cmt_btn report" title="举报">[举报]</a>-->
                    	</span>
                    </span>
            </dt>
            <dd class="comment_userface">
                <a href ="javascript:return false;" onclick="return false;" style="cursor: default;">
                    <img src="${request.contextPath}/static/images/timg.jpg" width="40" height="40">
                </a>
            </dd>
            <dd class="comment_body">${parent.comment.content}</dd>
            <#if parent.comments?? && parent.comments?size gt 0>
                <@buildNode children=parent.comments parent=parent/>
            </#if>
        </dl>
        </#list>
    </#if>
</#macro>
