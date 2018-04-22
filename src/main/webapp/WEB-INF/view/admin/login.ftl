<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>账号登陆</title>

    <#--<script src="${request.contextPath}/static/js/jquery-3.2.1.min.js"></script>-->
    <link href="${request.contextPath}/static/bootstrap_3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${request.contextPath}/static/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script src="${request.contextPath}/static/bootstrap_3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${request.contextPath}/static/css/admin/style.css">
    <script type="application/javascript">
        function createErrordiv(msg) {
            if ($("#errordiv").length > 0) {
                var node = $("#errordiv").find("span").eq(1);
                node.text(msg);
            } else {
                $("#inputpwd").after("<div id=\"errordiv\" class=\"form-group error-mess\">" +
                    "<span class=\"error-icon\"></span>" +
                    "<span>"+ msg +"</span></div>");
            }
        }
        $.fn.validate = function(tips){
            if($(this).val() == "" || $.trim($(this).val()).length == 0) {
                createErrordiv("请输入" + tips);
                return false;
            }
            return true;
        }

        function submit(){
            //调用validate()
            if(!$("#userName").validate("账号")) {
                return;
            }
            if(!$("#password").validate("密码")) {
                return;
            }
            var userName = $("#userName").val();
            var password = $("#password").val();
            $.ajax({
                type:"POST",
                url:"${request.contextPath}/admin/login",
                data: {
                    userName: userName,
                    password: password
                },
                dataType: "json",
                success: function(result) {
                    if(result.code == 0){//登录成功
                        parent.location.href ='${request.contextPath}/admin/index.html';
                    }else{
                        createErrordiv(result.msg);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <div class="form row">
        <div class="form-horizontal col-md-offset-3" id="login_form" action="${request.contextPath}/blogger/main" method="post">
            <h3 class="form-title">登陆</h3>
            <div class="col-md-9">
                <div class="form-group">
                    <i class="fa fa-user fa-lg"></i>
                    <input class="form-control required" type="text" placeholder="Username" id="userName" name="userName" autofocus="autofocus" maxlength="20"/>
                </div>
                <div id="inputpwd" class="form-group">
                    <i class="fa fa-lock fa-lg"></i>
                    <input class="form-control required" type="password" placeholder="Password" id="password" name="password" maxlength="8"/>
                </div>
                <div class="form-group col-md-offset-9">
                    <button type="button" class="btn btn-success pull-right" name="submit" onclick="submit();">登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
