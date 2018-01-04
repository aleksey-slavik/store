<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Order Item</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<table style="width: 80%" align="center">
    <tr>
        <td style="width: 10%">User:</td>
        <td><a href="${pageContext.request.contextPath}/user?id=${order.user.id}">${order.user.username}</a></td>
    </tr>
    <tr>
        <td style="width: 10%">Items:</td>
        <td>
            <table width="100%">
                <th>Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <c:forEach items="${order.items}" var="item">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/product?id=${item.product.id}">${item.product.name}</a></td>
                        <td>${item.quantity}</td>
                        <td>${item.price}</td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
    <tr>
        <td style="width: 10%">Total Cost:</td>
        <td>${order.totalCost}</td>
    </tr>
    <tr>
        <td style="width: 10%">Status:</td>
        <td>${order.status}</td>
    </tr>
</table>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/order/update?id=<%= request.getParameter("id") %>">Update order</a>
</div>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/order/delete?id=<%= request.getParameter("id") %>">Delete order</a>
</div>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/order/create">Create order</a>
</div>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>