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
        <div class="rightArea">
            <div class="header" align="center">
                <h1>Order Info:</h1>
            </div>

            <input id="userId" name="userId" type="hidden"/>

            <table>
                <tr>
                    <td><label for="id">Id:</label></td>
                    <td><input id="id" name="id" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="user">Customer:</label></td>
                    <td><input id="user" name="user" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="created">Created:</label></td>
                    <td><input id="created" name="created" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="totalCost">Total Cost:</label></td>
                    <td><input id="totalCost" name="totalCost" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="status">Status:</label></td>
                    <td>
                        <select id="status" name="status">
                        <option>PAID</option>
                        <option selected>OPENED</option>
                    </select>
                    </td>
                </tr>
            </table>

            <button id="buttonCreate">Clear</button>
            <button id="buttonSave">Update</button>
            <button id="buttonDelete">Delete</button>
        </div>

        <div class="mainArea">
            <div class="header" align="center">
                <h1>Order Items:</h1>
            </div>

            <div class="leftArea-list">
                <table width="100%" id="orderTable"></table>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/order/orders.js"></script>
<%@include file="/parts/bottom.jsp" %>
</body>
</html>
