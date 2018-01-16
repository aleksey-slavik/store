<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Web Store</title>
    <%@include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>

<div class="header" align="center">
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

            <label>Name:</label>
            <input id="name" name="name" type="text"/>

            <label>Brand:</label>
            <input id="brand" name="brand" type="text"/>

            <label>Price:</label>
            <input id="price" name="price" type="text"/>

            <button id="buttonSave">Save</button>
            <button id="buttonDelete">Delete</button>
        </div>

        <div class="rightArea">
            <label>Description:</label>
            <textarea id="description" name="description"></textarea>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/tableProduct.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/tableCore.js"></script>

<%--<table style="width: 100%">
    <tr>
        <th>Name</th>
        <th>Brand</th>
        <th>Description</th>
        <th>Price</th>
        <sec:authorize access="hasAuthority('ADMIN')">
            <th>Edit</th>
            <th>Delete</th>
        </sec:authorize>
    </tr>
    <c:forEach items="${items}" var="order">
        <tr onclick="window.location='product?id=${order.id}'">
            <td>${order.name}</td>
            <td>${order.brand}</td>
            <td>${order.description}</td>
            <td>${order.price}</td>
            <sec:authorize access="hasAuthority('ADMIN')">
                <td><a href="${pageContext.request.contextPath}/product/update?id=${order.id}">Edit</a></td>
                <td><a href="${pageContext.request.contextPath}/product/delete?id=${order.id}">Delete</a></td>
            </sec:authorize>
        </tr>
    </c:forEach>
</table>
<sec:authorize access="hasAuthority('ADMIN')">
    <div style="text-align: center;">
        <a href="${pageContext.request.contextPath}/product/create">Create product</a>
    </div>
</sec:authorize>--%>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
