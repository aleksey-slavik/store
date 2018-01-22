<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<table>
    <tr>
        <td>
            <div class="top-panel-search">
                <input class="search" type="search" id="searchKey" placeholder="Enter search key here...">
            </div>
        </td>
        <td>
            <div class="top-panel-icon">
                <a id="buttonSearch" href="#">
                    <img class="icon" src="${pageContext.request.contextPath}/images/search.png" alt="Search">
                </a>
            </div>
        </td>
    </tr>
</table>