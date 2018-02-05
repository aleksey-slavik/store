<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Web Store</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@ include file="/parts/top.jsp" %>
<script>
    var principal = "${pageContext.request.remoteUser}"
</script>


<sec:authorize access="hasAuthority('ADMIN')">
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

                <label>Name:</label>
                <input id="name" name="name" type="text"/>

                <label>Brand:</label>
                <input id="brand" name="brand" type="text"/>

                <label>Price:</label>
                <input id="price" name="price" type="text"/>

                <button id="buttonCreate">Clear</button>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/search.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/script/product/public.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/script/product/secured.js"></script>
</sec:authorize>

<sec:authorize access="!hasAuthority('ADMIN')">
    <div class="header" align="center">
        <%@ include file="/parts/search.jsp" %>
    </div>

    <div id="wrapper">
    </div>

    <div id="modal-form-container">
        <form id="modal-form">
            <div class="mainArea">
                <input id="id" name="id" type="hidden"/>

                <label>Name:</label>
                <input id="name" name="name" type="text" disabled/>

                <label>Brand:</label>
                <input id="brand" name="brand" type="text" disabled/>

                <label>Price:</label>
                <input id="price" name="price" type="text" disabled/>

                <sec:authorize access="isAuthenticated()">
                    <label>Quantity:</label>
                    <input id="quantity" name="quantity" type="number" min="1" value="1"/>

                    <button id="buttonBuy">Add to cart</button>
                </sec:authorize>
                <button id="buttonCancel">Cancel</button>
            </div>

            <div class="rightArea">
                <label>Description:</label>
                <textarea id="description" name="description" disabled></textarea>
            </div>
        </form>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.session.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/search.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/script/product/public.js"></script>
</sec:authorize>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
