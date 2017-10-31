<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>开始</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv ="X-UA-Compatible" content ="IE=edge">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
    <script src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body>
<table width="1100" class="table-1" bgcolor="#FFFFFF" border="0" cellpadding="3"
       cellspacing="1">
    <tr>
        <td width="20%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>表名：</label>
        </td>
        <td width="80%" align="left" bgcolor="#FFFFFF"><input type="text" id="tableName" maxlength="120" size="120">
        </td>
    </tr>
    <tr>
        <td width="20%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>原型地址：</label>
        </td>
        <td width="80%" align="left" bgcolor="#FFFFFF">
            <input type="text" id="htmlPath" maxlength="120" size="120">
        </td>
    </tr>
    <tr>
        <td width="20%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>是否使用缓存：</label>
        </td>
        <td width="80%" align="left" bgcolor="#FFFFFF">
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
            alert("请输入涉及到的数据库表名称！");
            return;
        }
        window.open("http://localhost:8080/codeGenerator/page/init.do" ,'_blank','height=800, width=1250, top=150, left=300,status=yes,toolbar=no,menubar=no,location=yes,scrollbars=yes,resizable=no');
    }
    function ifBlankReturnNull(str) {
        return str == null || str.trim().length == 0 ? null : str.trim();
    }

</script>
</html>
