<%@ page contentType="text/html;charset=UTF-8" %>
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
        <td id="name"></td>
    </tr>
    <tr>
        <td style="width: 10%">Brand:</td>
        <td id="brand"></td>
    </tr>
    <tr>
        <td style="width: 10%">Description:</td>
        <td id="description"></td>
    </tr>
    <tr>
        <td style="width: 10%">Price:</td>
        <td id="price"></td>
    </tr>
</table>
<div style="text-align: center;"><a href="addToCart">Add item to my shopping cart</a></div>
<div style="text-align: center;"><a href="home">Home</a></div>
<%@include file="/parts/scripts.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/product-item.js"></script>
</body>
</html>