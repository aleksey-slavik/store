<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Product List</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<div id="example-table"></div>
<%@include file="/parts/scripts.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/admin-product-table.js"></script>
</body>
</html>
