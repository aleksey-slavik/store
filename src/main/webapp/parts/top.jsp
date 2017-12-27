<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="top-panel">
    <sec:authorize access="hasAnyAuthority('ADMIN', 'CUSTOMER')">
        <sec:authentication var="user" property="principal.username"/>
        <a class="account-panel">${user}</a>
        <a class="account-panel" href="logout">(logout)</a>
    </sec:authorize>

    <sec:authorize access="hasAuthority('CUSTOMER')">
        <a class="account-panel" href="cart">(my cart)</a>
    </sec:authorize>

    <sec:authorize access="isAnonymous()">
        <a class="account-panel" href="login">Login</a>
        <a class="account-panel" href="register">Register</a>
    </sec:authorize>
</div>
