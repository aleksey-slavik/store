<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Order List</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>
<h2 align="center">Page at development stage!</h2>
<%--

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

            <label>User:</label>
            <input id="username" name="username" type="text"/>

            <label>Total Cost:</label>
            <input id="totalCost" name="totalCost" type="text"/>

            <label>Status:</label>
            <select id="status" name="status">
                <option>PAID</option>
                <option selected>OPENED</option>
            </select>

            <button id="buttonSave">Save</button>
            <button id="buttonDelete">Delete</button>
        </div>

        <div class="rightArea">
            <label>Items:</label>
            <ul id="productList"></ul>
        </div>

        <div class="additionalArea">

            <label>Id:</label>
            <input id="itemId" name="itemId" type="text" disabled/>

            <label>Product:</label>
            <input id="itemName" name="itemName" type="text"/>

            <label>Brand:</label>
            <input id="itemBrand" name="itemBrand" type="text"/>

            <label>Price:</label>
            <input id="itemPrice" name="itemPrice" type="text"/>

            <label>Quantity:</label>
            <input id="itemQuantity" name="itemQuantity" type="text"/>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/tableOrder.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/restCore.js"></script>
--%>

<%--<table style="width: 100%">
    <tr>
        <th>User</th>
        <th>Products</th>
        <th>Total Cost</th>
        <th>Status</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${orders}" var="order">
        <tr onclick="window.location='order?id=${order.id}'">
            <td><a href="${pageContext.request.contextPath}/user?id=${order.user.id}">${order.user.username}</a></td>
            <td>
                <table width="100%">
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <c:forEach items="${order.items}" var="item">
                        <tr>
                            <td>${item.product.name}</td>
                            <td>${item.quantity}</td>
                            <td>${item.price}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>${order.totalCost}</td>
            <td>${order.status}</td>
            <td><a href="${pageContext.request.contextPath}/order/update?id=${order.id}">Edit</a></td>
            <td><a href="${pageContext.request.contextPath}/order/delete?id=${order.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}/order/create">Create order</a>
</div>--%>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
