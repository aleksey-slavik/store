<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="top-panel">
    <sec:authentication var="user" property="principal.username"/>
    <sec:authorize var="isAdmin" access="hasAuthority('ADMIN')"/>
    <sec:authorize var="isCustomer" access="hasAuthority('CUSTOMER')"/>

    <c:if test="${isAdmin or isCustomer}">
        <a class="account-panel">${user}</a>
        <a class="account-panel" href="logout">(logout)</a>
    </c:if>

    <c:if test="${isCustomer}">
        <a class="account-panel" href="cart">(my cart)</a>
    </c:if>

    <c:if test="${!isAdmin and !isCustomer}">
        <a class="account-panel" href="login">Login</a>
        <a class="account-panel" href="register">Register</a>
    </c:if>
</div>
