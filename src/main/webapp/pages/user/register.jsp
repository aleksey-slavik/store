<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Register</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body onload='document.regForm.firstname.focus();'>
<%@include file="/parts/top.jsp" %>
<form id="regForm" action="${pageContext.request.contextPath}/register" method="post">
    <table align="center">
        <tr>
            <td><label for="firstname">First Name</label></td>
            <td><input type="text" name="firstname" id="firstname" value="${firstname}"/></td>
        </tr>
        <tr>
            <td><label for="lastname">Last Name</label></td>
            <td><input type="text" name="lastname" id="lastname" value="${lastname}"/></td>
        </tr>
        <tr>
            <td><label for="email">Email</label></td>
            <td><input type="text" name="email" id="email" value="${email}"/></td>
        </tr>
        <tr>
            <td><label for="username">Username</label></td>
            <td><input type="text" name="username" id="username" value="${username}"/></td>
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
