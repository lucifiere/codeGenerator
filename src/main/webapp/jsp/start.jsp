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
    <input type="text" id="tableName" maxlength="120">
    <input type="button" onclick="toPage()" value="确定">
</body>
<script>
    function toPage(){
        var tableName = document.getElementById("tableName").value;
        location.href = "http://localhost:8080/codeGenerator/page/init.do?tableName="+tableName
    }
</script>
</html>
