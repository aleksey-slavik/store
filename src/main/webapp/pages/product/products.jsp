<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/parts/meta.jsp" %>
    <title>Web Store</title>
    <%@ include file="/parts/header.jsp" %>
</head>
<body>

<script>
    var principal = "${pageContext.request.remoteUser}"
</script>

<%@ include file="/parts/top.jsp" %>

<sec:authorize access="hasAuthority('ADMIN')">
    <%@ include file="admin.jsp" %>
</sec:authorize>

<sec:authorize access="!hasAuthority('ADMIN')">
    <%@ include file="customer.jsp" %>
</sec:authorize>

<%@include file="/parts/bottom.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/product/products.js"></script>

</body>
</html>
