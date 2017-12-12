<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="${pageContext.request.contextPath}/style/store.css" />">
    <title>Register</title>
</head>
<body>
<div id="top-panel">
    <a id="account-panel" href="login">Login</a>
</div>
<form id="regForm" action="registerProcess" method="post">
    <table align="center">
        <tr>
            <td><label for="firstname">First Name</label></td>
            <td><input type="text" name="firstname" id="firstname"/>
            </td>
        </tr>
        <tr>
            <td><label for="lastname">Last Name</label></td>
            <td><input type="text" name="lastname" id="lastname"/>
            </td>
        </tr>
        <tr>
            <td><label for="email">Email</label></td>
            <td><input type="text" name="email" id="email"/></td>
        </tr>
        <tr>
            <td><label for="username">Username</label></td>
            <td><input type="text" name="username" id="username"/>
            </td>
        </tr>
        <tr>
            <td><label for="password">Password</label></td>
            <td><input type="password" name="password" id="password"/></td>
        </tr>
        <tr>
            <td><label for="confirm-password">Confirm Password</label></td>
            <td><input type="password" name="confirm-password" id="confirm-password"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button id="register" name="register">Register</button>
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
