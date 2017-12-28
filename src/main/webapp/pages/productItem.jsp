<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Product Item</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<table style="width: 80%" align="center">
    <tr>
        <td style="width: 10%">Name:</td>
        <td>${product.name}</td>
    </tr>
    <tr>
        <td style="width: 10%">Brand:</td>
        <td>${product.brand}</td>
    </tr>
    <tr>
        <td style="width: 10%">Description:</td>
        <td>${product.description}</td>
    </tr>
    <tr>
        <td style="width: 10%">Price:</td>
        <td>${product.price}</td>
    </tr>
</table>
<sec:authorize access="hasAuthority('CUSTOMER')">
    <div style="text-align: center;"><a href="addToCart">Add item to my shopping cart</a></div>
</sec:authorize>
<sec:authorize access="hasAuthority('ADMIN')">
    <div style="text-align: center;">
        <a href="${pageContext.request.contextPath}/updateProduct?id=<%= request.getParameter("id") %>">Change product</a>
    </div>
    <div style="text-align: center;">
        <a href="${pageContext.request.contextPath}/deleteProduct?id=<%= request.getParameter("id") %>">Remove product</a>
    </div>
    <div style="text-align: center;">
        <a href="${pageContext.request.contextPath}/createProduct">Add new product</a>
    </div>
</sec:authorize>
<div style="text-align: center;"><a href="home">Home</a></div>
</body>
</html>