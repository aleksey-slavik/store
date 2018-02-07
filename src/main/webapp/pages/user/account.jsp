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
    <form id="itemForm">
        <div class="mainArea">
            <input id="id" name="id" type="hidden" disabled/>

            <label>First Name:</label>
            <input id="firstName" name="firstName" type="text" disabled/>

            <label>Last Name:</label>
            <input id="lastName" name="lastName" type="text" disabled/>

            <label>Username:</label>
            <input id="username" name="username" type="text" disabled/>

            <label>Password:</label>
            <input id="password" name="password" type="password" disabled/>

            <label>E-mail:</label>
            <input id="email" name="email" type="text" disabled/>

            <label>Role:</label>
            <input id="role" name="role" type="text" disabled/>

            <button id="buttonChange">Change</button>
            <button id="buttonSave">Save</button>
            <button id="buttonCancel">Cancel</button>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/public.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
