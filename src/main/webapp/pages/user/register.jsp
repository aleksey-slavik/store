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
            <td><input type="text" name="firstname" id="firstname"/></td>
        </tr>
        <tr>
            <td><label for="lastname">Last Name</label></td>
            <td><input type="text" name="lastname" id="lastname"/></td>
        </tr>
        <tr>
            <td><label for="email">Email</label></td>
            <td><input type="text" name="email" id="email"/></td>
        </tr>
        <tr>
            <td><label for="username">Username</label></td>
            <td><input type="text" name="username" id="username"/></td>
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
    </table>
</form>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
