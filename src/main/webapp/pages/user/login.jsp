<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Login</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>

<div class="container">
    <table align="center">
        <tr>
            <td id="notification">${message}</td>
        </tr>
    </table>

    <form id="loginForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="POST">
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
        </table>

        <div class="header" align="center">
            <button>Login</button>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <table align="center">
        <tr>
            <td>
                Not registered? <a href="${pageContext.request.contextPath}/register">Create an account</a>.
            </td>
        </tr>
    </table>
</div>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
