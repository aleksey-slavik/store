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
        <td>${item.name}</td>
    </tr>
    <tr>
        <td style="width: 10%">Brand:</td>
        <td>${item.brand}</td>
    </tr>
    <tr>
        <td style="width: 10%">Description:</td>
        <td>${item.description}</td>
    </tr>
    <tr>
        <td style="width: 10%">Price:</td>
        <td>${item.price}</td>
    </tr>
</table>
<sec:authorize access="hasAuthority('CUSTOMER')">
    <form style="text-align: center;" action="${pageContext.request.contextPath}/cart" method="post">
        <label>
            <input type="number" name="count" min="1" value="1"/>
        </label>
        <button type="submit">Add item to my shopping cart</button>
    </form>
</sec:authorize>
<sec:authorize access="hasAuthority('ADMIN')">
    <div>
        <a href="${pageContext.request.contextPath}/product/update?id=<%= request.getParameter("id") %>">Update
            product</a>
    </div>
    <div style="text-align: center;">
        <a href="${pageContext.request.contextPath}/product/delete?id=<%= request.getParameter("id") %>">Delete
            product</a>
    </div>
    <div style="text-align: center;">
        <a href="${pageContext.request.contextPath}/product/create">Create product</a>
    </div>
</sec:authorize>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>