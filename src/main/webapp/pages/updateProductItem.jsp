<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Update product</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<form id="regForm" action="updateProductProcess?id=${product.id}" method="post">
    <table align="center">
        <tr>
            <td><label for="name">Name</label></td>
            <td><input type="text" name="name" id="name" value="${product.name}"/></td>
        </tr>
        <tr>
            <td><label for="brand">Brand</label></td>
            <td><input type="text" name="brand" id="brand" value="${product.brand}"/></td>
        </tr>
        <tr>
            <td><label for="description">Description</label></td>
            <td><input type="text" name="description" id="description" value="${product.description}"/></td>
        </tr>
        <tr>
            <td><label for="price">Price</label></td>
            <td><input type="text" name="price" id="price" value="${product.price}"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button id="change" name="change">Change</button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>
            <td><a href="home">Home</a></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
