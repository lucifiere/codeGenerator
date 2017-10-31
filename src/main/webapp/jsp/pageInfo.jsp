<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="page"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/common.css"/>
    <title>页面配置</title>
</head>
<body>
<c:forEach items="${pageInfo.pageTableList}" var="table">
    <table width="1100" class="table-1" border="0" cellpadding="3"
           cellspacing="1">
        <tr>
            <td>表格名称</td>
            <td><input type="text" value="${table.tableName}" name="tableName"></td>
            <td>是否列表</td>
            <td>
                <select name="isList">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}">${whether.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select name="isList">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}">${whether.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>每行列数</td>
            <td><input type="text" value="${table.tdNum}" name="tdNum"></td>
        </tr>
    </table>
    <c:forEach items="${table.elementList}" var="element">

        <table width="1100" class="table_1" border="0" cellpadding="3"
        cellspacing="1">
        <thead>
        <th>页面字段名</th>
        <th>来源表名</th>
        <th>数据库字段名</th>
        <th>数据库字段说明</th>
        <th>来源bean名称</th>
        <th>页面输入类型</th>
        <th>值类型限制</th>
        <th>是否允许为空</th>
        <th>长度限制</th>
        <th>是否表格标题</th>
        </thead>
        <tbody>
        <tr>
            <td><input type="text" value="${element.eleName}" name="eleName"></td>
            <td><input type="text" value="${element.dbTable}" name="dbTable"></td>
            <td><input type="text" value="${element.dbColName}" name="dbColName"></td>
            <td><input type="text" value="${element.dbComment}" name="dbComment"></td>
            <td><input type="text" value="${element.beanName}" name="beanName"></td>
            <td>
                <select name="inputType">
                    <c:forEach items="${inputTypeList }" var="inputType">
                        <option value="${inputType.value}"
                                <c:if test="${inputType.value eq  element.inputType}">selected</c:if>>${inputType.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select name="isNullable">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}">${whether.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select name="valueType">
                    <c:forEach items="${valueTypeList }" var="valueType">
                        <option value="${valueType.value}">${valueType.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td><input type="text" value="${element.lengthLimit}" name="lengthLimit"></td>
            <td>
                <select name="isTableTitle" onchange="createNewTable(this)">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}">${whether.desc}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    </table>
</c:forEach>
</body>
<script type="text/javascript">
    function getPageInfo() {
        var tableName = ifBlankReturnNull(document.getElementById("tableName").value);
        var htmlPath = ifBlankReturnNull(document.getElementById("htmlPath").value);
        if (tableName == null || tableName.trim() == "") {
            alert("请输入设计的表名称！");
            return;
        }
        $.ajax({
            type: "post",
            url: CAR_PATH + "/repay/core/bill/controller/repayingBillController/toReissue?",
            dataType: "json",
            data: {"tableName": tableName, "htmlPath": htmlPath},
            async: false,
            error: function (xhr, status, err) {
                alert(err);
            },
            success: function (data) {
                alert(data.messages);
            }
        });
    }
</script>
</html>
