<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="container">
    <div class="leftArea">
        <div class="header" align="center">
            <%@ include file="/parts/search.jsp" %>
        </div>
    </div>

    <div class="centralArea">
        <div id="modal-form-container">
            <form class="modal-form">
                <span id="buttonCancel" class="close" title="Close Modal">&times;</span>
                <table class="modal-table">
                    <tr>
                        <td width="30%">
                            <input id="productId" name="id" type="hidden"/>
                            <input id="merchantId" name="merchantId" type="hidden"/>

                            <table class="modal-table-item">
                                <tr>
                                    <td width="50%"><label for="name">Name:</label></td>
                                    <td width="50%"><input id="name" name="name" type="text" disabled/></td>
                                </tr>
                                <tr>
                                    <td><label for="brand">Brand:</label></td>
                                    <td><input id="brand" name="brand" type="text" disabled/></td>
                                </tr>
                                <tr>
                                    <td><label for="price">Price:</label></td>
                                    <td><input id="price" name="price" type="text" disabled/></td>
                                </tr>
                                <tr>
                                    <td><label for="merchant">Merchant:</label></td>
                                    <td><input id="merchant" name="merchant" type="text" disabled/></td>
                                </tr>
                                <sec:authorize access="hasAuthority('CUSTOMER')">
                                    <tr>
                                        <td><label for="quantity">Quantity:</label></td>
                                        <td><input id="quantity" name="quantity" type="number" min="1" value="1"/></td>
                                    </tr>
                                </sec:authorize>
                            </table>
                            <sec:authorize access="hasAuthority('CUSTOMER')">
                                <button id="buttonBuy">Buy</button>
                            </sec:authorize>
                        </td>
                        <td width="70%">
                            <label for="description">Description:</label>
                            <textarea id="description" name="description" disabled></textarea>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div id="wrapper">
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/product/products.js"></script>