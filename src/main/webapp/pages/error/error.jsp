<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Error page</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<div style="text-align: center;">
    <h2>Error during request process!</h2>
    <h4>Error message: ${message}</h4>
</div>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
