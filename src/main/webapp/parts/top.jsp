<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="top-panel">
    <div class="top-panel-account">
        <table>
            <tr>
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication var="principal" property="principal.username"/>
                    <td>
                        <div class="top-panel-user">
                            <a class="bottom-link" href="${pageContext.request.contextPath}/account">${principal}</a>
                        </div>
                    </td>
                    <td>
                        <div class="top-panel-icon">
                            <a href="${pageContext.request.contextPath}/j_spring_security_logout">
                                <img class="icon" alt="Logout"
                                     src="${pageContext.request.contextPath}/images/logout.png" title="Logout">
                            </a>
                        </div>
                    </td>
                </sec:authorize>

                <sec:authorize access="hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')">
                    <td>
                        <div class="top-panel-icon">
                            <a href="cart">
                                <img class="icon" src="${pageContext.request.contextPath}/images/cart.png" alt="Cart" title="Cart">
                            </a>
                        </div>
                    </td>
                </sec:authorize>

                <sec:authorize access="isAnonymous()">
                    <td>
                        <div class="top-panel-icon">
                            <a href="login">
                                <img class="icon" src="${pageContext.request.contextPath}/images/login.png" alt="Login" title="Login">
                            </a>
                        </div>
                    </td>
                </sec:authorize>
            </tr>
        </table>
    </div>
</div>
