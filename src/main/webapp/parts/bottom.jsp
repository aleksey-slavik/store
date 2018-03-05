<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="top-panel">
    <div class="bottom-panel-link">
        <table id="bottom-panel">
            <tr>
                <sec:authorize access="!hasAuthority('ADMIN')">
                    <td>
                        <a class="bottom-link" href="${pageContext.request.contextPath}/home">Home</a>
                    </td>
                </sec:authorize>
                <sec:authorize access="hasAuthority('ADMIN')">
                    <td><a class="bottom-link" href="${pageContext.request.contextPath}/product">Products</a></td>
                    <td><a class="bottom-link" href="${pageContext.request.contextPath}/user">Users</a></td>
                    <td><a class="bottom-link" href="${pageContext.request.contextPath}/order">Orders</a></td>
                </sec:authorize>
            </tr>
        </table>
    </div>
</div>
