<!DOCTYPE html>
<html lang="en">
<head>
    <title>评论审核页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/static/jquery-easyui-1.5.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/static/jquery-easyui-1.5.3/themes/icon.css">
    <script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        window.top["reload_Abnormal_Monitor"]=function(){
            $("#dg").datagrid( "reload");
        }

        function formatBlogTitle(val,row) {
            if(val == null) {
                return "<font color=red>该博客已删除</font>";
            } else {
                return "<a href='${request.contextPath}/admin/blog/detail/"+val.id+".html' target='_blank'>"+val.title+"</a>";
            }
        }

        function commentReview(state) {
            var selectedRows = $("#dg").datagrid("getSelections");
            if(selectedRows.length == 0) {
                $.messager.alert("系统提示", "请选择要审核的评论");
                return;
            }
            var idsStr = [];
            for(var i = 0; i < selectedRows.length; i++) {
                idsStr.push(selectedRows[i].id);
            }
            var ids = idsStr.join(","); //1,2,3,4
            $.messager.confirm("系统提示", "<font color=red>您确定如此审核选中的"+selectedRows.length+"条数据吗？</font>", function(r) {
                if(r) {
                    $.post("${request.contextPath}/admin/comment/review",
                            {ids: ids, state: state},
                            function(result){
                                if(result.code == 0) {
                                    $.messager.alert("系统提示", "评论审核成功！");
                                    $("#dg").datagrid("reload");
                                } else {
                                    $.messager.alert("系统提示", "评论审核失败！");
                                }
                            }, "json");
                }
            });
        }

        function reload() {
            $("#dg").datagrid("reload");
        }
    </script>
</head>
<body style="margin: 1px; font-family: microsoft yahei">
<table id="dg" title="评论审核管理" class="easyui-datagrid" fitColumns="true" pagination="true"
       url="${request.contextPath}/admin/comment/commentReview" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="20" align="center">编号</th>
        <th field="blog" width="200" align="center" formatter="formatBlogTitle">博客标题</th>
        <th field="userName" width="50" align="center">用户的名称</th>
        <th field="content" width="200" align="center">评论内容</th>
        <th field="commentDate" width="50" align="center">评论日期</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:commentReview(1)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">审核通过</a>
        <a href="javascript:commentReview(2)" class="easyui-linkbutton" iconCls="icon-no" plain="true">审核不通过</a>
        <a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
    </div>
</div>

</body>
</html>