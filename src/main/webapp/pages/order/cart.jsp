<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>My Cart</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<table style="width: 80%" align="center">
    <tr>
        <td style="width: 10%">User:</td>
        <td>${order.user.username}</td>
    </tr>
    <tr>
        <td style="width: 10%">Items size:</td>
        <td>${order.items.size()}</td>
    </tr>
    <tr>
        <td style="width: 10%">Status:</td>
        <td>${order.status}</td>
    </tr>
    <tr>
        <td style="width: 10%">Total Cost:</td>
        <td>${order.totalCost}</td>
    </tr>
</table>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
