<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Order List</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<table style="width: 100%">
    <tr>
        <th>User</th>
        <th>Products</th>
        <th>Total Cost</th>
        <th>Status</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${orders}" var="order">
        <tr onclick="window.location='order?id=${order.id}'">
            <td><a href="${pageContext.request.contextPath}/user?id=${order.user.id}">${order.user.username}</a></td>
            <td>
                <table width="100%">
                    <c:forEach items="${order.items}" var="product">
                        <tr>
                            <td>${product.name}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>${order.totalCost}</td>
            <td>${order.status}</td>
            <td><a href="${pageContext.request.contextPath}/user/update?id=${order.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/user/delete?id=${order.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/create">Create user</a>
</div>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
