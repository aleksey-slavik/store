<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>User data</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<table align="center">
    <tr>
        <td style="width: 10%">First Name:</td>
        <td>${user.firstname}</td>
    </tr>
    <tr>
        <td style="width: 10%">Last Name:</td>
        <td>${user.lastname}</td>
    </tr>
    <tr>
        <td style="width: 10%">Username:</td>
        <td>${user.username}</td>
    </tr>
    <tr>
        <td style="width: 10%">Password:</td>
        <td>${user.password}</td>
    </tr>
    <tr>
        <td style="width: 10%">E-mail:</td>
        <td>${user.email}</td>
    </tr>
    <tr>
        <td style="width: 10%">Permissions:</td>
        <td>${user.role}</td>
    </tr>
</table>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/update?id=<%= request.getParameter("id") %>">Update user data</a>
</div>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/delete?id=<%= request.getParameter("id") %>">Delete user</a>
</div>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/create">Create user</a>
</div>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
