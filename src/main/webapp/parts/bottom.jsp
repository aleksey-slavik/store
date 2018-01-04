<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div style="text-align: center;">
    <sec:authorize access="hasAuthority('ADMIN')">
        <a href="${pageContext.request.contextPath}/product">Products</a>
        <a href="${pageContext.request.contextPath}/user">Users</a>
        <a href="${pageContext.request.contextPath}/order">Orders</a>
    </sec:authorize>

    <sec:authorize access="!hasAuthority('ADMIN')">
        <a href="${pageContext.request.contextPath}/home">Home</a>
    </sec:authorize>
</div>
