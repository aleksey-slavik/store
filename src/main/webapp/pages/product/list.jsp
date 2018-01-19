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

<sec:authorize access="hasAuthority('ADMIN')">
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/product/restProduct.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/restCore.js"></script>
</sec:authorize>

<sec:authorize access="!hasAuthority('ADMIN')">
    <div id="wrapper">
    </div>

    <div id="modal-form-container">
        <form id="modal-form">
            <div class="mainArea">
                <label>Name:</label>
                <input id="name" name="name" type="text" disabled/>

                <label>Brand:</label>
                <input id="brand" name="brand" type="text" disabled/>

                <label>Price:</label>
                <input id="price" name="price" type="text" disabled/>

                <label>Description:</label>
                <textarea id="description" name="description" disabled></textarea>

                <label>Quantity:</label>
                <label>
                    <input type="number" name="count" min="1" value="1"/>
                </label>

                <button id="buttonSend">Save</button>
                <button id="buttonCancel">Cancel</button>
            </div>
        </form>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/product/customer.js"></script>
</sec:authorize>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
