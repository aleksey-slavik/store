<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>User List</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<table style="width: 100%">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
        <th>Password</th>
        <th>E-mail</th>
        <th>Permissions</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${users}" var="users">
        <tr onclick="window.location='user?id=${users.id}'">
            <td>${users.firstname}</td>
            <td>${users.lastname}</td>
            <td>${users.username}</td>
            <td>${users.password}</td>
            <td>${users.email}</td>
            <td>${users.role}</td>
            <td><a href="${pageContext.request.contextPath}/user/update?id=${users.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/user/delete?id=${users.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/user/create">Create user</a>
</div>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
