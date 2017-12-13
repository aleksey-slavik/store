<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Title</title>
    <%@include file="/parts/header.jsp" %>
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
        <td>${product.price} UAH</td>
    </tr>
</table>
<div style="text-align: center;"><a href="home">Home</a></div>
</body>
</html>
