<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Account</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>

<script>
    var principal = "${pageContext.request.remoteUser}"
</script>

<div class="container">

    <sec:authorize access="!hasAuthority('ADMIN')">
        <div class="leftArea">
            <div class="leftArea-list">
                <ul id="itemList">
                    <li><a href="${pageContext.request.contextPath}/account/products">My products</a></li>
                    <li><a href="${pageContext.request.contextPath}/account/orders">Order history</a></li>
                </ul>
            </div>
        </div>
    </sec:authorize>

    <form id="itemForm">
        <div class="mainArea">
            <input id="id" name="id" type="text" hidden/>
            <input id="enabled" name="enabled" type="text" hidden/>
            <table>
                <tr>
                    <td><label for="firstName">First name:</label></td>
                    <td><input id="firstName" name="firstName" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="lastName">Last name:</label></td>
                    <td><input id="lastName" name="lastName" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="username">Username:</label></td>
                    <td><input id="username" name="username" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input id="password" name="password" type="password" disabled/></td>
                </tr>
                <tr>
                    <td><label for="email">E-mail:</label></td>
                    <td><input id="email" name="email" type="email" disabled/></td>
                </tr>
            </table>

            <button id="buttonChange">Change</button>
            <button id="buttonSave">Save</button>
            <button id="buttonCancel">Cancel</button>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/account.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
