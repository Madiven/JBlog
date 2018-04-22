var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

$(function() {
    //侧边栏的归档的下拉按钮
    $("#archive_list_button").click(function() {
        var className = String($(this).prop("className"));
        if(className == "list_closed") {
            $(this).removeClass().addClass("list_opended");
            $(this).html("收起");
        } else {
            $(this).removeClass().addClass("list_closed");
            $(this).html("展开");
        }
        $(".hidelist").toggle();
    });
    //提示框关闭事件
    $(".result-poptit .close").click(function(){
        $(".result-popover-mask").fadeOut(100);
        $(".result-popover").slideUp(200);
    });
    //回复评论
    $(".user .reply").click(function () {
        var parent = $(this).next();
        $("#comment_replyId").val(parent.attr("commentid"));
        $("#comment_form .comment_content").val("[reply]" + parent.attr("username") + "[/reply]\n\r");
        $("#comment_form .comment_content").focus();
    });
    //引用评论
    $(".user .quote").click(function () {
        var userName = $(this).parent().attr("username");
        var quote = $(this).parents(".comment_item").find("dd").eq(1).text();
        $("#comment_form .comment_content").focus();
        $("#comment_form .comment_content").html("[quote=" + userName + "]" + quote + "[/quote]");
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
        var text = $("#comment_form .comment_content").val();
        if(text.length > 0){
            return;
        }
        $("#comment_form #tip_comment").html("");
        $("#comment_form .comment_content").animate({
            height:"30px"
        })
    });
    //弹出验证框
    $("#comment_form .btn-redborder").click(function() {
        var text = $("#comment_form .comment_content").val();
        if(text.trim().length < 1) {
            $(".rform span").html("请填写评论!");
            $(".result-popover-mask").fadeIn(100);
            $(".result-popover").slideDown(200);
            return false;
        }
        $(".theme-popover-mask").fadeIn(100);
        $(".theme-popover").slideDown(200);
    });
    //关闭验证框
    $(".theme-poptit .close").click(function(){
        $(".theme-popover-mask").fadeOut(100);
        $(".theme-popover").slideUp(200);
    });
    //提交评论
    $("#commentForm").submit(function (e) {
        var isSubmit = checkCaptcha();
        if(isSubmit) {
            $.ajax({
                type:"post",
                async:false,
                url: projectName + "/comment/submit",
                data:$("#commentForm").serialize(),
                dataType: "json",
                success:function (result) {
                    if(result.status) {
                        $(".rform span").html("评论成功，等待博主审核！");

                    } else {
                        $(".rform span").html("评论失败，服务器错误，抱歉！");
                    }
                }
            });
            changeCaptcha();
            $("#captchaCode").val("");
            $("#comment_content").val("");
            $("#comment_content").blur();
            $(".theme-popover-mask").fadeOut(100);
            $(".theme-popover").slideUp(200);

            $(".result-popover-mask").fadeIn(100);
            $(".result-popover").slideDown(200);
        }
        return false;
    });
});
function check() {
    var input = $("#inputSearch").val();
    if(input == null || input.trim() == "") {
        return false;
    }
    return true;
}

function changeCaptcha() {
    $("#changeCaptcha").attr("src", projectName + "/captcha/getCaptchaCode");
    $(".dform ol span").html("");
}

function checkCaptcha() {
    var flag = false;
    var code = $("#captchaCode").val();
    if(code == null || code == "") {
        $(".dform ol span").html("请输入验证码");
        return flag;
    }
    $.ajax({
        type:"post",
        async:false,
        url: projectName + "/captcha/checkCaptchaCode",
        data:{"captchaCode": code},
        dataType: "json",
        success:function (result) {
            if(result.status) {
                flag = true;
            } else {
                $(".dform ol span").html("验证码输入有误");
            }
        }
    });
    return flag;
}