<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Product List</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@ include file="/parts/top.jsp" %>
<script>
    var principal = "${pageContext.request.remoteUser}"
</script>

<div class="container">
    <div class="leftArea">
        <div class="header" align="center">
            <%@ include file="/parts/search.jsp" %>
        </div>
        <div class="leftArea-list">
            <ul id="itemList"></ul>
        </div>
    </div>

    <div class="centralArea">
        <form id="itemForm">
            <label>Id:</label>
            <input id="id" name="id" type="text" disabled/>

            <input id="ownerId" name="ownerId" type="hidden"/>
            <input id="owner" name="price" type="hidden"/>

            <label>Name:</label>
            <input id="name" name="name" type="text"/>

            <label>Brand:</label>
            <input id="brand" name="brand" type="text"/>

            <label>Price:</label>
            <input id="price" name="price" type="text"/>

            <label>Description:</label>
            <textarea id="description" name="description"></textarea>

            <label>Share permissions:</label>
            <input id="share" name="share" type="text"/>
            <button id="buttonShare">Share Permissions</button>
            <button id="buttonRemove">Remove Permissions</button>
            <button id="buttonCreate">Clear</button>
            <button id="buttonSave">Save</button>
            <button id="buttonDelete">Delete</button>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/search.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/products.js"></script>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
