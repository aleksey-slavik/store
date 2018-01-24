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

<div class="container">
    <table id="orderTable"></table>

    <table>
        <tr>
            <td>
                <label>Total cost:</label>
            </td>
            <td>
                <input id="totalCost" name="totalCost" type="text" disabled/>
            </td>
        </tr>
    </table>

    <button id="buttonBuy">Buy items</button>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/rest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/order/cart.js"></script>

<%@include file="/parts/bottom.jsp" %>
</body>
</html>
