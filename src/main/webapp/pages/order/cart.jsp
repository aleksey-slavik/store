<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>My Cart</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>
<%@include file="/parts/top.jsp" %>

<script>
    var principal = "${pageContext.request.remoteUser}"
</script>

<div style="width: 90%" class="container">
    <table width="100%" id="orderTable"></table>

    <table>
        <tr>
            <td>
                <label>Total cost:</label>
            </td>
            <td>
                <input style="margin: 0" id="totalCost" name="totalCost" type="text" disabled/>
            </td>
        </tr>
    </table>

    <button id="buttonBuy">Buy items</button>
</div>

<div id="modal-form-container">
    <form id="modal-form">
        <div class="mainArea">
            <input id="id" name="id" type="hidden"/>
            <input id="id" name="id" type="hidden"/>

            <label>Name:</label>
            <input id="productName" name="productName" type="text" disabled/>

            <label>Brand:</label>
            <input id="productBrand" name="productBrand" type="text" disabled/>

            <label>Price:</label>
            <input id="price" name="price" type="text" disabled/>

            <label>Quantity:</label>
            <input id="quantity" name="quantity" type="number" min="1" value="1"/>

            <button id="buttonChange">Change</button>
            <button id="buttonDelete">Delete</button>
            <button id="buttonCancel">Cancel</button>
        </div>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/order/cart.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
