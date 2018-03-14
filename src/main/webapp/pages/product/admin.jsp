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
            <sec:authorize access="hasAuthority('ADMIN')">
                <label>Share permissions:</label>
                <input id="share" name="share" type="text"/>
                <button id="buttonShare">Share</button>
            </sec:authorize>
        </div>
    </form>
</div>