<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Web Store</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<table style="width: 100%">
    <tr>
        <th>Name</th>
        <th>Brand</th>
        <th>Description</th>
        <th>Price</th>
    </tr>
    <c:forEach items="${products}" var="product">
        <tr onclick="window.location='product?id=${product.id}'">
            <td>${product.name}</td>
            <td>${product.brand}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
        </tr>
    </c:forEach>
    <sec:authorize access="hasAuthority('ADMIN')">
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td><a href="/createProduct">Add new product</a></td>
        </tr>
    </sec:authorize>
</table>
</body>
</html>
