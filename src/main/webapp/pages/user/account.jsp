<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Account</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<sec:authentication var="username" property="principal.username"/>
<sec:authentication var="password" property="principal.password"/>
<form id="itemForm">
    <div class="mainArea">
        <input id="id" name="id" type="hidden" disabled/>

        <label>First Name:</label>
        <input id="firstName" name="firstName" type="text" disabled/>

        <label>Last Name:</label>
        <input id="lastName" name="lastName" type="text" disabled/>

        <label>Username:</label>
        <input id="username" name="username" type="text" value="${username}" disabled/>

        <label>Password:</label>
        <input id="password" name="password" type="password" value="${password}" disabled/>

        <label>E-mail:</label>
        <input id="email" name="email" type="text" disabled/>

        <label>Role:</label>
        <input id="role" name="role" type="text" disabled/>

        <button id="buttonChange">Change</button>
        <button id="buttonSave">Save</button>
    </div>
</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/usersPublicAccess.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
