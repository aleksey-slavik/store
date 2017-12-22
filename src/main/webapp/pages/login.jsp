<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Login</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<form id="loginForm" action="<c:url value='/j_spring_security_check' />" method="post">
    <table align="center">
        <tr>
            <td><label for="username">Username:</label></td>
            <td><input type="text" name="username" id="username"/>
            </td>
        </tr>
        <tr>
            <td><label for="password">Password:</label></td>
            <td><input type="password" name="password" id="password"/></td>
        </tr>
        <tr>
            <td></td>
            <td align="left">
                <button id="login" name="login">Login</button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>
            <td><a href="home">Home</a></td>
        </tr>
    </table>
</form>
<table align="center">
    <tr>
        <td id="notification">${message}</td>
    </tr>
</table>
</body>
</html>
