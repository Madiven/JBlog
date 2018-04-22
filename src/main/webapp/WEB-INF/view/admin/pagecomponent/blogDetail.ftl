<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>${blogger.nickName}</title>
    <meta name="keywords" content="JAVA, Web, Spring" />
    <meta name="description" content="小麦的个人博客，一时兴起的产物。" />
    <link href="${request.contextPath}/static/css/admin/comment.css" rel="stylesheet">
    <link href="${request.contextPath}/static/css/admin/detail.css" rel="stylesheet">
    <script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(function() {
            //回复评论
            $(".user .reply").click(function () {
                var parent = $(this).next();
                $("#comment_replyId").val(parent.attr("commentid"));
                $("#comment_form .comment_content").val("[reply]" + parent.attr("username") + "[/reply]\n\r");
                $("#comment_form .comment_content").focus();
            });
            //文本域字数统计
            $("#comment_form .comment_content").keyup(function () {
                var text = $("#comment_form .comment_content").val();
                if(text.length >= 1000) {
                    $("#comment_form #tip_comment").html("评论字数已达上限！");
                    $("#comment_form .comment_content").val(text.substring(0, 1000));
                } else {
                    $("#comment_form #tip_comment").html("还能输入" + (1000 - text.length).toString() + "个字符");
                }
            });
            //文本域获得焦点事件
            $("#comment_form .comment_content").focus(function(){
                $("#comment_form .comment_content").animate({
                    height:"100px"
                });
            });
            //文本域失去焦点事件
            $("#comment_form .comment_content").blur(function(){
                var text = $("#comment_form .comment_content").val().trim();
                if(text.length > 0){
                    return;
                }
                $("#comment_form .comment_content").val("");
                $("#comment_form #tip_comment").html("");
                $("#comment_form .comment_content").animate({
                    height:"30px"
                })
            });
            //提示框关闭事件
            $(".result-poptit .close").click(function(){
                $(".result-popover-mask").fadeOut(100);
                $(".result-popover").slideUp(200);
            });
            //提交评论
            $("#commentForm").submit(function (e) {
                var flag = true;
                    $.ajax({
                        type:"post",
                        async:false,
                        url:"${request.contextPath}/admin/comment/submit",
                        data:$("#commentForm").serialize(),
                        dataType: "json",
                        success:function (result) {
                            if(result.code == 0) {
                                $(".rform span").html("回复成功！");

                            } else {
                                $(".rform span").html("评论失败，服务器错误，抱歉！");
                                flag = false;
                                $("#comment_content").val("");
                                $("#comment_content").blur();

                                $(".result-popover-mask").fadeIn(100);
                                $(".result-popover").slideDown(200);
                            }
                        }
                    });

                return flag;
            });
        });
    </script>
</head>

<body>
  <div class="ibody">
        <article>
            <div class="index_about">
                <h2 class="c_titile"><@typeImage type=detail.type/>${detail.title}</h2>
                <p class="box_c"><span class="d_time">发布时间：${detail.releaseDate?string('yyyy-MM-dd')}</span><span>浏览（${detail.readCount}）</span><span>评论（${detail.commentCount}）</span></p>
                <div class="keybq">
                    <p><span>关键字词</span>：${detail.keyWord}</p>
                </div>
                <ul class="infos">
                    ${detail.content}
                </ul>

                <div class="comment_class">
                    <a name="commentbox"></a>
                    <a name="reply"></a>
                    <a name="quote"></a>
                    <div id="comment_form">
                        <div id="report_title" class="panel_head">
                            <span class="see_comment">发表评论</span>
                        </div>
                        <form id="commentForm">
                            <div class="commentform" style="height: 145px;">
                                <textarea name="content" class="comment_content" id="comment_content"></textarea>
                                <input name="parentId" id="comment_replyId" type="hidden">
                                <input name="blog.id" id="blog_id" type="hidden" value="${detail.id}">
                                <input class="btn btn-redborder" type="submit" value="发表评论">
                                <span class="tip" id="tip_comment"></span>
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
                    <#if comments?? && comments?size gt 0>
                        <div id="comment_list">
                            <div id="comment_title" class="panel_head">
                                <span class="see_comment">查看评论</span>
                            </div>
                            <@buildComment parents=comments/>
                        </div>
                    </#if>

                </div>
            </div>
        </article>

    <div class="clear"></div>
    <!-- 清除浮动 -->
  </div>
</body>
<script src="${request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<script type="text/javascript">
    SyntaxHighlighter.all();
</script>
<link href="${request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css" rel="stylesheet">
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
                <a href="JavaScript:void(0)" name="comment_${child.comment.id}" onclick="return false;" style="cursor: default;">
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
                <a href="JavaScript:void(0)" name="comment_${parent.comment.id}" onclick="return false;" style="cursor: default;">
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

<#macro typeImage type>
    <#switch type>
        <#case 1><span class="ico ico_type_Original"></span><#break>
        <#case 2><span class="ico ico_type_Repost"></span><#break>
        <#case 3><span class="ico ico_type_Translated"></span><#break>
        <#default>
    </#switch>
</#macro>