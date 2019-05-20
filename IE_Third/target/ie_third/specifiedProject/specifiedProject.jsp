<%@ page import="model.Project.Project" %>
<%@ page import="model.Repo.GetRepo" %>
<%@ page import="model.Repo.UsersRepo" %><%--
  Created by IntelliJ IDEA.
  User: mahdi
  Date: 3/1/19
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Project</title>
</head>
<body>
<form action="showAllUsersCtrl" method="GET">
    <button>مشاهده تمام کاربران</button>
</form>
<form action="showAllProjects" method="GET">
    <button>مشاهده تمام پروژه ها</button>
</form>
<form action="homeServlet" method="GET">
    <button>مشاهده صفحه اول</button>
</form>
<form action="jsp/userOwnPage.jsp" method="GET">
    <button>پروفایل</button>
</form>
<c:set var="projectId" value = "${request.getParameter(\"projectId\")}"/>
<c:out value = "${GetRepo.print(\"specifiedProject.jsp\")}"/>
<%--<%String projectId = request.getParameter("projectId");--%>
    <%--GetRepo.print("specifiedProject.jsp");--%>
<c:choose>
<c:when test = "${not empty forbiddenMsg}">
<%--if(request.getAttribute("forbiddenMsg") != null){%>--%>
    <h3 style="color:red"><c:out value="${forbiddenMsg}"/></h3>
</c:when>
<c:otherwise>
    <%--userId and project attribute is defined--%>
    <c:choose>
    <c:when test = "${not empty BidMsg}">
    <h3 style="color:blue"><c:out value ="${BidMsg}"/></h3>
    </c:when>
    </c:choose>
    <c:choose>
    <c:when test = "${not empty BidErrorMsg}">
    <h3 style="color:red"><c:out value = "${BidErrorMsg}"/></h3>
    </c:when>
    </c:choose>
    <c:choose>
    <c:when test="${not empty BidInvalidMsg}">
    <h3 style="color:red"><c:out value="${BidInvalidMsg}"/></h3>
    </c:when>
    </c:choose>
    <ul>
        <li>id: <c:out value="${project.getId()}"/></li>
        <li>title: <c:out value="${project.getTitle()}"/></li>
        <li>description: <c:out value="${project.getDescription()}"/></li>
        <li>imageUrl: <img src="<c:out value="${project.getImageUrlText()}"/>" style="width: 50px; height: 50px;"></li>
        <li>budget: <c:out value ="${project.getBudget()}"/></li>
    </ul>
    <!-- display form if user has not bidded before -->
    <c:choose>
    <c:when test = "${!project.userHasBid(userId)}">
    <form action="userBidProjectCtrl" method="GET">
        <label for="bidAmount">Bid Amount:</label>
        <input type="number" id="bidAmount" name="bidAmount"/>
        <input type="hidden" name="projectId" value="<c:out value="${project.getId()}"/>"/>
        <button>Submit</button>
    </form>
    </c:when>
    </c:choose>
</c:otherwise>
</c:choose>
</body>
</html>
