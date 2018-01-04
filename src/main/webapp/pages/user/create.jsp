<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Create new user</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body onload='document.createForm.name.focus();'>
<%@include file="/parts/top.jsp" %>
<form id="createForm" action="${pageContext.request.contextPath}/user/create" method="post">
    <table align="center">
        <tr>
            <td><label for="firstname">First Name</label></td>
            <td><input type="text" name="firstname" id="firstname" value="${user.firstname}"/></td>
        </tr>
        <tr>
            <td><label for="lastname">Last Name</label></td>
            <td><input type="text" name="lastname" id="lastname" value="${user.lastname}"/></td>
        </tr>
        <tr>
            <td><label for="username">Username</label></td>
            <td><input type="text" name="username" id="username" value="${user.username}"/></td>
        </tr>
        <tr>
            <td><label for="password">Password</label></td>
            <td><input type="text" name="password" id="password" value="${user.password}"/></td>
        </tr>
        <tr>
            <td><label for="email">E-mail</label></td>
            <td><input type="text" name="email" id="email" value="${user.email}"/></td>
        </tr>
        <tr>
            <td><label for="role">Permissions</label></td>
            <td><select name="role" id="role">
                <option>ADMIN</option>
                <option selected>CUSTOMER</option>
            </select></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button id="create" name="create">Create</button>
            </td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
