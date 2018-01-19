<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="top-panel">
    <div id="top-panel-search">
        <div class="header" align="center">
            <input type="text" id="searchKey"/>
            <a id="buttonSearch" class="top-panel-icon" href="#">
                <img class="icon" src="${pageContext.request.contextPath}/images/search.png" alt="Search">
            </a>
        </div>
    </div>

    <div id="top-panel-account">
        <sec:authorize access="isAuthenticated()">
            <sec:authentication var="principal" property="principal.username"/>
            <a class="top-panel-user">
                ${principal}
            </a>
            <a class="top-panel-icon" href="${pageContext.request.contextPath}/j_spring_security_logout">
                <img class="icon" alt="Logout" src="${pageContext.request.contextPath}/images/logout.png">
            </a>
        </sec:authorize>

        <sec:authorize access="hasAuthority('CUSTOMER')">
            <a class="top-panel-icon" href="cart">
                <img class="icon" src="${pageContext.request.contextPath}/images/cart.png" alt="Cart">
            </a>
        </sec:authorize>

        <sec:authorize access="isAnonymous()">
            <a class="top-panel-icon" href="login">
                <img class="icon" src="${pageContext.request.contextPath}/images/login.png" alt="Login">
            </a>
        </sec:authorize>
    </div>
</div>
