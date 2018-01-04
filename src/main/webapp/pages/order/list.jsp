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
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <c:forEach items="${order.items}" var="item">
                        <tr>
                            <td>${item.product.name}</td>
                            <td>${item.quantity}</td>
                            <td>${item.price}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>${order.totalCost}</td>
            <td>${order.status}</td>
            <td><a href="${pageContext.request.contextPath}/order/update?id=${order.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/order/delete?id=${order.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/order/create">Create user</a>
</div>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
