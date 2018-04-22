<!DOCTYPE html>
<html>
<head>
	<title>博客类别管理页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/jquery-easyui-1.5.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/jquery-easyui-1.5.3/themes/icon.css">
	<script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.min.js"></script>
	<script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${request.contextPath}/static/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>

	<script type="text/javascript">

        function formatBool2Text(val, row) {
            return val? "是":"否";
        }

        var url;

        var type;

        var BlogTypeObj =
            {
                id:"",
                typeName:""
            }

        function openBlogTypeAddDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "添加博客类别信息");
            type = "保存";
            $("#hidden").val($("#isShow").combobox("getValue"));
            url = "${request.contextPath}/admin/blogType/newBlogType";

        }

        function openBlogTypeModifyDialog() {
            var selectedRows = $("#dg").datagrid("getSelections");
            if(selectedRows.length != 1) {
                $.messager.alert("系统提示", "请选择一个要修改的博客类别");
                return;
            }
            var row = selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle", "修改博客类别信息");
            $("#fm").form("load", row);//会自动识别name属性，将row中对应的数据，填充到form表单对应的name属性中
            type = "修改";
            $("#hidden").val($("#isShow").combobox("getValue"));
            url = "${request.contextPath}/admin/blogType/updateBlogType?id=" + row.id;
        }

        function saveBlogType() {
            $("#fm").form("submit",{
                url: url,
                onSubmit: function() {
                    return $(this).form("validate");
                }, //进行验证，通过才让提交
                success: function(result) {
                    var result = eval("(" + result + ")"); //将json格式的result转换成js对象
                    if(result.code == 0) {
                        $.messager.alert("系统提示", "博客类别"+type+"成功");
                        $("typeName").val(""); //保存成功后将内容置空
                        $("typeNum").val("");
                        $("#dlg").dialog("close"); //关闭对话框
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示", "博客类别保存失败");
                        return;
                    }
                }
            });
        }

        function closeBlogTypeDialog() {
            $("typeName").val(""); //保存成功后将内容置空
            $("typeNum").val("");
            $("#dlg").dialog("close"); //关闭对话框
        }


        function deleteBlogType() {
            var selectedRows = $("#dg").datagrid("getSelections");
            if(selectedRows.length == 0) {
                $.messager.alert("系统提示", "请选择要删除的数据");
                return;
            }
            var blogTypes = JSON.parse("[]");
            for(var i = 0; i < selectedRows.length; i++) {
                var BlogTypeObj = new Object();
                BlogTypeObj.id =selectedRows[i].id;
                BlogTypeObj.typeName = selectedRows[i].typeName;
                blogTypes.push(BlogTypeObj);
            }
            //格式化数据
           var data=JSON.stringify(blogTypes);
            $.messager.confirm("系统提示", "<font color=red>您确定要删除选中的"+selectedRows.length+"条数据么？</font>", function(r) {
                if(r) {
                    $.post("${request.contextPath}/admin/blogType/deleteBlogType",
                        {blogTypes: data},
						function(result){
                            if(result.data.trim() != "") {
                                $.messager.alert("系统提示", "博客类别" + result.data + "下有博客，不能删除！");
                                $("#dg").datagrid("reload");
							}
							else if(result.code == 0) {
                                $.messager.alert("系统提示", "成功删除" + result.data + "条数据！");
                                $("#dg").datagrid("reload");
                            } else {
                                $.messager.alert("系统提示", "数据删除失败！");
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
<table id="dg" title="博客类别管理" class="easyui-datagrid" fitColumns="true" pagination="true"
	   url="${request.contextPath}/admin/blogType/listBlogType" toolbar="#tb">
	<thead>
	<tr>
		<th field="cb" checkbox="true" align="center"></th>
		<th field="id" width="20" align="center" hidden="hidden">编号</th>
		<th field="typeName" width="100" align="center">博客分类名称</th>
		<th field="orderNum" width="100" align="center">类别排序</th>
		<th field="show" width="20" align="center" formatter="formatBool2Text">是否显示</th>
	</tr>
	</thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openBlogTypeAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:deleteBlogType()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openBlogTypeModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
	</div>
</div>
<div id="dlg" class="easyui-dialog" style="width:500px; height:200px; padding:10px 20px"
	 closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<table cellspacing="8px">
			<tr>
				<td>博客类别名称</td>
				<td>
					<input type="text" id="typeName" name="typeName" class="easyui-validatebox" required="true">
				</td>
			</tr>
			<tr>
				<td>博客类别排序</td>
				<td>
					<input type="text" id="orderNum" name="orderNum" class="easyui-numberbox" required="true"
						   style="width:60px">&nbsp;(博客类别会根据序号从小到大排列)
				</td>
			</tr>
			<tr>
				<td>是否显示该博客类别</td>
				<td>
                    <input type="hidden" id="hidden" name="show"/>
					<select type="text" id="isShow" name="isShow" class="easyui-combobox" required="true"
						   style="width:60px">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="dlg-buttons">
	<div>
		<a href="javascript:saveBlogType()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
		<a href="javascript:closeBlogTypeDialog()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
	</div>
</div>
</body>
</html>
