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
<div class="container">
    <div class="leftArea">
        <div class="header" align="center">
            <h1>Orders List:</h1>
        </div>

        <div class="leftArea-list">
            <ul id="itemList"></ul>
        </div>
    </div>

    <form id="itemForm">
        <div class="mainArea">
            <div class="header" align="center">
                <h1>Order Info:</h1>
            </div>

            <label for="id">Id:</label>
            <input id="id" name="id" type="text" disabled/>

            <label for="user">User:</label>
            <input id="user" name="user" type="text" disabled/>

            <label for="totalCost">Total Cost:</label>
            <input id="totalCost" name="totalCost" type="text" disabled/>

            <label for="status">Status:</label>
            <select id="status" name="status">
                <option>PAID</option>
                <option selected>OPENED</option>
            </select>

            <button id="buttonCreate">Clear</button>
            <button id="buttonSave">Update</button>
            <button id="buttonDelete">Delete</button>
        </div>

        <div class="rightArea">
            <div class="header" align="center">
                <h1>Order Items:</h1>
            </div>

            <div class="leftArea-list">
                <ul id="orderItemList"></ul>
            </div>
        </div>
    </form>
</div>

<div id="modal-form-container">
    <form id="modal-form" style="width: 330px;">
        <div class="mainArea">
            <label for="name">Name:</label>
            <input id="name" name="name" type="text" disabled/>

            <label for="brand">Brand:</label>
            <input id="brand" name="brand" type="text" disabled/>

            <label for="price">Price:</label>
            <input id="price" name="price" type="text" disabled/>


            <label for="quantity">Quantity:</label>
            <input id="quantity" name="quantity" type="number" min="1" value="1"/>

            <button id="buttonSaveItem">Update</button>
            <button id="buttonDeleteItem">Delete</button>
            <button id="buttonCancel">Cancel</button>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/order/orders.js"></script>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
