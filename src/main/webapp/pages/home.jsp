<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Web Store</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<div class="wrapper">
    <c:forEach items="${products}" var="product">
        <div class="box">
            <p>${product.name}</p>
            <p>Brand: ${product.brand}</p>
            <p>Price: ${product.price}</p></div>
    </c:forEach>
</div>
</body>
</html>
