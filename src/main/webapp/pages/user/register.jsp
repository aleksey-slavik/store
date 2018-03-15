<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Register</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<div class="container">
    <form id="regForm">
        <table align="center">
            <tr>
                <td><label for="firstName">First Name</label></td>
                <td><input type="text" name="firstName" id="firstName"/></td>
            </tr>
            <tr>
                <td><label for="lastName">Last Name</label></td>
                <td><input type="text" name="lastName" id="lastName"/></td>
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
        </table>
        <div class="header" align="center">
            <button id="register" name="register">Create account</button>
        </div>
    </form>

    <table align="center">
        <tr>
            <td>
                Already registered? <a href="${pageContext.request.contextPath}/login">Sign In</a>.
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/register.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
