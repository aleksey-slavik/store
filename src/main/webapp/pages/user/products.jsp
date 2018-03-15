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
<%@include file="/parts/top.jsp" %>

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
        <div class="header" align="center">
            <button id="addProduct" type="button">Add product</button>
        </div>
    </div>

    <form id="itemForm">
        <div class="mainArea">
            <input id="id" name="id" type="text" hidden/>
            <input id="ownerId" name="ownerId" type="text" hidden/>
            <input id="owner" name="owner" type="text" hidden/>
            <table>
                <tr>
                    <td><label for="name">Name:</label></td>
                    <td><input id="name" name="name" type="text"/></td>
                </tr>
                <tr>
                    <td><label for="brand">Brand:</label></td>
                    <td><input id="brand" name="brand" type="text"/></td>
                </tr>
                <tr>
                    <td><label for="price">Price:</label></td>
                    <td><input id="price" name="price" type="text"/></td>
                </tr>
            </table>

            <label>Description:</label>
            <textarea id="description" name="description" rows="10"></textarea>

            <button id="buttonSave">Save</button>
            <button id="buttonDelete">Delete</button>
        </div>

        <div class="rightArea">
            <label>Share permissions:</label>
            <table>
                <tr>
                    <td><input id="share" name="share" type="text"/></td>
                    <td><button id="buttonShare">Share</button></td>
                </tr>
            </table>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/user/products.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
