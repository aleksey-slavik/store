<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
            <table>
                <tr>
                    <td><label for="productId">Id:</label></td>
                    <td><input id="productId" name="productId" type="text" disabled/></td>
                </tr>
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
                <tr>
                    <td><label for="merchantId">Merchant id:</label></td>
                    <td><input id="merchantId" name="merchantId" type="text" disabled/></td>
                </tr>
                <tr>
                    <td><label for="merchant">Merchant:</label></td>
                    <td><input id="merchant" name="merchant" type="text" disabled/></td>
                </tr>
            </table>

            <label>Description:</label>
            <textarea id="description" name="description" rows="10"></textarea>

            <button id="buttonCreate">Clear</button>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/script/product/secured.js"></script>