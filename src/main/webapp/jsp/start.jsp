<%--
  Created by IntelliJ IDEA.
  User: ao
  Date: 2017/10/18
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>开始</title>
</head>
<body>
<table width="1100" class="tab_inquiry m10_0" id="baseInfo" bgcolor="#FFFFFF" border="0" cellpadding="3"
       cellspacing="1">
    <tr>
        <td width="10%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>表名：</label>
        </td>
        <td width="90%" align="left" bgcolor="#FFFFFF"><input type="text" id="tableName" maxlength="120">
        </td>
    </tr>
    <tr>
        <td width="10%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>原型地址：</label>
        </td>
        <td width="90%" align="left" bgcolor="#FFFFFF">
            <input type="text" id="htmlPath" maxlength="120">
        </td>
    </tr>
    <tr>
        <td width="10%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>是否使用缓存：</label>
        </td>
        <td width="90%" align="left" bgcolor="#FFFFFF">
            <select id="useCache">
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </td>
    </tr>
</table>
    <input type="button" onclick="toPage()" value="确定">
</body>
<script>
    function toPage(){
        var tableName = ifBlankReturnNull(document.getElementById("tableName").value);
        if(tableName == null || tableName.trim() == ""){
            alert("请输入设计的表名称！");
            return;
        }
        window.open("http://localhost:8080/codeGenerator/page/init.do" ,'_blank','height=800, width=1250, top=150, left=300,status=yes,toolbar=no,menubar=no,location=yes,scrollbars=yes,resizable=no');
    }
    function ifBlankReturnNull(str) {
        return str == null || str.trim().length == 0 ? null : str.trim();
    }

</script>
</html>
