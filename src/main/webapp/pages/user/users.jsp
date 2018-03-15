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

<div class="container">
    <div class="leftArea">
        <div class="header" align="center">
            <%@ include file="/parts/search.jsp" %>
        </div>

        <div class="leftArea-list">
            <ul id="itemList"></ul>
        </div>
    </div>

    <form id="itemForm">
        <div class="mainArea">
            <table>
                <tr>
                    <td><label for="id">Id:</label></td>
                    <td><input id="id" name="id" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="username">Username:</label></td>
                    <td><input id="username" name="username" type="text"/></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input id="password" name="password" type="password"/></td>
                </tr>
                <tr>
                    <td><label for="email">E-mail:</label></td>
                    <td><input id="email" name="email" type="text"/></td>
                </tr>
                <tr>
                    <td><label for="firstName">First name:</label></td>
                    <td><input id="firstName" name="firstName" type="text"/></td>
                </tr>
                <tr>
                    <td><label for="lastName">Last Name:</label></td>
                    <td><input id="lastName" name="lastName" type="text"/></td>
                </tr>
                <tr>
                    <td><label for="enabled">Active:</label></td>
                    <td><input id="enabled" name="enabled" type="checkbox"/></td>
                </tr>
            </table>

            <button id="buttonClear">Clear</button>
            <button id="buttonSave">Save</button>
            <button id="buttonDelete">Delete</button>
        </div>

        <div class="rightArea">

            <fieldset id="roles">
                <legend>Authorities:</legend>
                <table>
                    <tr>
                        <td><input id="role_customer" name="roles" value="CUSTOMER" type="checkbox"></td>
                        <td><label for="role_customer">CUSTOMER</label></td>
                    </tr>
                    <tr>
                        <td><input id="role_admin" name="roles" value="ADMIN" type="checkbox"></td>
                        <td><label for="role_admin">ADMIN</label></td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/users.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
