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

<div class="header">
    <input type="text" id="searchKey"/>
    <button id="buttonSearch">Search</button>
    <button id="buttonCreate">Create</button>
</div>

<div class="container">
    <div class="leftArea">
        <ul id="itemList"></ul>
    </div>

    <form id="itemForm">
        <div class="mainArea">

            <label>Id:</label>
            <input id="id" name="id" type="text" disabled/>

            <label>First Name:</label>
            <input id="firstName" name="firstName" type="text"/>

            <label>Last Name:</label>
            <input id="lastName" name="lastName" type="text"/>

            <label>Username:</label>
            <input id="username" name="username" type="text"/>

            <label>Password:</label>
            <input id="password" name="password" type="text"/>

            <label>E-mail:</label>
            <input id="email" name="email" type="text"/>

            <label>Role:</label>
            <select name="role" id="role">
                <option>ADMIN</option>
                <option selected>CUSTOMER</option>
            </select>

            <button id="buttonSave">Save</button>
            <button id="buttonDelete">Delete</button>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/tableUser.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/tableCore.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
