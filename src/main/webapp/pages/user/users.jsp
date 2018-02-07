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

            <label>Id:</label>
            <input id="id" name="id" type="text" disabled/>

            <label>Username:</label>
            <input id="username" name="username" type="text"/>

            <label>Password:</label>
            <input id="password" name="password" type="text"/>

            <label>E-mail:</label>
            <input id="email" name="email" type="text"/>

            <button id="buttonCreate">Clear</button>
            <button id="buttonSave">Save</button>
            <button id="buttonDelete">Delete</button>
        </div>

        <div class="rightArea">
            <label>First Name:</label>
            <input id="firstName" name="firstName" type="text"/>

            <label>Last Name:</label>
            <input id="lastName" name="lastName" type="text"/>

            <label>Role:</label>
            <select name="role" id="role">
                <option>ADMIN</option>
                <option selected>CUSTOMER</option>
            </select>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/search.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/secured.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
