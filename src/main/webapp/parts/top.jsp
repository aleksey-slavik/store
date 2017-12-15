<%@ page import="com.globallogic.store.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="top-panel">
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
    %>
    <a class="account-panel" href="login">Login</a>
    <a class="account-panel" href="register">Register</a>
    <%
    } else {
        String firstname = user.getFirstname();
    %>
    <a class="account-panel"><%=firstname%></a>
    <a class="account-panel" href="cart">(my cart)</a>
    <a class="account-panel" href="logout">(logout)</a>
    <%
        }
    %>
</div>
