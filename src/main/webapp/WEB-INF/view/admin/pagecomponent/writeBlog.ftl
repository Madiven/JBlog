<!DOCTYPE html>
<html lang="en">
<head>
    <title>写博客页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css"
          href="${request.contextPath}/static/jquery-easyui-1.5.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${request.contextPath}/static/jquery-easyui-1.5.3/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/static/jquery-easyui-1.5.3/demo/demo.css">
    <script type="text/javascript"
            src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/static/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/static/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" charset="utf-8"
            src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${request.contextPath}/static/ueditor/ueditor.all.min.js">

    </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8"
            src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body style="margin: 10px; font-family: microsoft yahei">

    <table cellspacing="20px">
        <tr>
            <td width="85px">博客标题：</td>
            <td><input type="text" id="title" name="title" style="width:400px" /></td>
        </tr>
        <tr>
        <td>博客类型：</td>
        <td><select id="type" class="easyui-combobox"
                    name="type" style="width:154px" editable="false"
                    panelHeight="auto">
            <option value="">请选择博客类型...</option>
            <option value="1">原创</option>
            <option value="2">转载</option>
            <option value="3">翻译</option>
        </select></td>
        </tr>
        <tr>
            <td>所属类别：</td>
            <td><select id="blogTypeId" class="easyui-combobox"
                        name="blogType.id" style="width:154px" editable="false"
                        panelHeight="auto">
                <option value="">请选择博客类别...</option>
                <#list blogTypes as item>
                    <option value="${item.id }">${item.typeName }</option>
                </#list>
            </select></td>
        </tr>
        <tr>
            <td valign="top">博客内容：</td>
            <td><script id="editor" name="content" type="text/plain"
                        style="width:800px; height:500px;"></script></td>
        </tr>
        <tr>
            <td>关键字：</td>
            <td><input type="text" id="keyWord" name="keyWord"
                       style="width:400px" />&nbsp;&nbsp;&nbsp;多个关键字的话请用逗号隔开</td>
        </tr>
        <tr>
            <td>文章摘要：</td>
            <td><textarea id="summary" name="summary"
                          style="width:400px;height:80px;"></textarea></td>
        </tr>
        <tr>
            <td>是否显示博客：</td>
            <td><select id="show" class="easyui-combobox"
                        name="show" style="width:154px" editable="false"
                        panelHeight="auto">
                <option value="1" selected="true">是</option>
                <option value="0">否</option>
            </select></td>
        </tr>

        <tr>
        <td>是否可以评论：</td>
        <td><select id="reply" class="easyui-combobox"
                    name="reply" style="width:154px" editable="false"
                    panelHeight="auto">
            <option value="1" selected="true">是</option>
            <option value="0">否</option>
        </select></td>
        </tr>

        <tr>
            <td>是否推荐：</td>
            <td><select id="commend" class="easyui-combobox"
                        name="commend" style="width:154px" editable="false"
                        panelHeight="auto">
                <option value="1">是</option>
                <option value="0" selected="true">否</option>
            </select></td>
        </tr>
        <tr>
            <td></td>
            <td><a href="javascript:submitData()" class="easyui-linkbutton"
                   data-options="iconCls:'icon-submit'">发布博客</a></td>
        </tr>
    </table>

<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('editor');
</script>
<script type="text/javascript">
    function submitData() {
        var title = $("#title").val();
        var blogTypeId = $("#blogTypeId").combobox("getValue");
        var type = $("#type").combobox("getValue");
        var content = UE.getEditor('editor').getContent();
        var summary = $("#summary").val();
        var keyWord = $("#keyWord").val();
        var contentNoTag = UE.getEditor('editor').getContentTxt();
        var show = $("#show").combobox("getValue");
        var reply = $("#reply").combobox("getValue");
        var commend = $("#commend").combobox("getValue");
        if($.trim(summary) == "") {
            summary = UE.getEditor('editor').getContentTxt().substr(0, 120);
        }

        if (title == null || title == '') {
            $.messager.alert("系统提示", "请输入标题！");
        } else if (type == null || type == '') {
            $.messager.alert("系统提示", "请选择博客类型！");
        } else if (blogTypeId == null || blogTypeId == '') {
            $.messager.alert("系统提示", "请选择博客类型！");
        } else if (content == null || content == '') {
            $.messager.alert("系统提示", "请编辑博客内容！");
        } else {
            $.post("${request.contextPath}/admin/blog/save",
                {
                    'title' : title,
                    'blogType.id' : blogTypeId,
                    'type' : type,
                    'content' : content,
                    'summary' : summary,
                    'keyWord' : keyWord,
                    'contentNoTag' : contentNoTag,
                    'show' : show,
                    'reply' : reply,
                    'commend' : commend
                }, function(result) {
                    if (result.code == 0) {
                        clearValues();
                        $.messager.alert("系统提示", "博客保存成功！");
                    } else {
                        $.messager.alert("系统提示", "博客保存失败！");
                    }
                }, "json");
        }
    }
function clearValues() {
    $("#title").val("");
    $("#blogTypeId").combobox("setValue", "");
    $("#type").combobox("setValue", "");
    $("#show").combobox("setValue", "1");
    $("#reply").combobox("setValue", "1");
    $("#commend").combobox("setValue", "0");
    UE.getEditor("editor").setContent("");
    var summary = $("#summary").val("");
    $("#keyWord").val("");
}
</script>
</body>
</html>