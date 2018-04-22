<!DOCTYPE html>
<html lang="en">
<head>
    <title>博客管理页面</title>
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

        function formatTitle(val, row) {
            return "<a target='_blank' href='${request.contextPath}/admin/blog/detail/"+row.id + ".html'>"+val+"</a>";
        }

        function formatBlogType(val, row) {
            return val.typeName;
        }

        function formatType(val, row) {
            var type;
            switch (val)
            {
                case 1:
                    type="原创";
                    break;
                case 2:
                    type="转载";
                    break;
                case 3:
                    type="翻译";
                    break;
                default:
                    type="";
            }
            return type;
        }

        function formatBool2Text(val, row) {
            return val? "是":"否";
        }

        function searchBlog() {
            var title = $('#s_title').val();
            $.ajax({
                type:"POST",
                url:"${request.contextPath}/admin/blog/searchBlog",
                data: { title: title },
                dataType: "json",
                success: function(result) {
                    if(result.code == 0){
                        $('#dg').datagrid('loadData',result);
                    }else{
                        $.messager.alert("系统错误", "博客搜索出现异常！");
                    }
                }
            });
        }

        function deleteBlog() {
            var selectedRows = $("#dg").datagrid("getSelections");
            if(selectedRows.length == 0) {
                $.messager.alert("系统提示", "请选择要删除的数据");
                return;
            }
            var idsStr = [];
            for(var i = 0; i < selectedRows.length; i++) {
                idsStr.push(selectedRows[i].id);
            }
            var ids = idsStr.join(","); //1,2,3,4
            $.messager.confirm("系统提示", "<font color=red>您确定要删除选中的"+selectedRows.length+"条数据么？</font>", function(r) {
                if(r) {
                    $.post("${request.contextPath}/admin/blog/delete",
                        {ids: ids}, function(result){
                            if(result.code == 0) {
                                $.messager.alert("系统提示", "数据删除成功！");
                                $("#dg").datagrid("reload");
                            } else {
                                $.messager.alert("系统提示", "数据删除失败！");
                            }
                        }, "json");
                }
            });
        }

        function openBlogModifyTab() {
            var selectedRows = $("#dg").datagrid("getSelections");
            if(selectedRows.length != 1) {
                $.messager.alert("系统提示", "请选择一个要修改的博客");
                return;
            }
            var row = selectedRows[0];
            window.parent.openTab("修改博客","modifyBlog.html?id=" + row.id, "icon-writeblog");
        }

        function reload() {
            $("#dg").datagrid("reload");
        }
    </script>
</head>
<body style="margin: 1px; font-family: microsoft yahei">
<table id="dg" title="博客管理" class="easyui-datagrid" fitColumns="true" pagination="true"
       url="${request.contextPath}/admin/blog/listBlog" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="20" align="center">编号</th>
        <th field="title" width="180" formatter="formatTitle">标题</th>
        <th field="type" width="80" align="center" formatter="formatType">类型</th>
        <th field="show" width="80" align="center" formatter="formatBool2Text">是否显示博客</th>
        <th field="reply" width="80" align="center" formatter="formatBool2Text">是否可以评论</th>
        <th field="commend" width="80" align="center" formatter="formatBool2Text">是否推荐</th>
        <th field="releaseDate" width="100" align="center">发布日期</th>
        <th field="blogType" width="100" align="center" formatter="formatBlogType">博客分类</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        &nbsp;标题&nbsp;<input type="text" id="s_title" size="20" onkeydown="if(event.keyCode==13) searchBlog()">
        <a href="javascript:searchBlog()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
        <a href="javascript:deleteBlog()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        <a href="javascript:openBlogModifyTab()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
    </div>
</div>
</body>
</html>