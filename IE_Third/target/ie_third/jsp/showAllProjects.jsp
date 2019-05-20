<%@ page import="model.Repo.GetRepo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Project.Project" %><%--
  Created by IntelliJ IDEA.
  User: mahdi
  Date: 3/1/19
  Time: 9:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%--%>
    <%--if(!GetRepo.isSetRepo){--%>
        <%--GetRepo.print("not setRepo showAllUsers.jsp");--%>
        <%--request.getRequestDispatcher("").forward(request, response);--%>
    <%--}--%>
<%--%>--%>
<c:choose>
<c:when test="${!GetRepo.isSetRepo}">
<c:out value="${GetRepo.print(\"not setRepo showAllProjects.jsp\")}" />
<c:out value="${request.getRequestDispatcher(\"\").forward(request, response)}" />
</c:when>
</c:choose>
<html>
<head>
    <title>مشاهده تمام پروژه ها</title>
</head>
<body>
<c:out value="${GetRepo.print(\"showAllProjects.jsp\")}"/>

<form action="showAllUsersCtrl" method="GET">
    <button>مشاهده تمام کاربران</button>
</form>
<form action="homeServlet" method="GET">
    <button>مشاهده صفحه اول</button>
</form>
<form action="jsp/userOwnPage.jsp" method="GET">
    <button>پروفایل</button>
</form>
<table>
    <tr>
        <th>id </th>
        <th>title</th>
        <th>budget</th>
        <th>مشاهده</th>
    </tr>
    <c:forEach var="project" items="${projects}">
    <%--<%for(Project project: matchingProjects){%>--%>
    <tr>
        <th><c:out value="${project.getId()}"/></th>
        <th><c:out value="${project.getTitle()}"/></th>
        <th><c:out value="${project.getBudget()}"/></th>
        <th>
            <form action="showSpecifiedProjectCtrl" method="GET">
                <input type="hidden" name="projectId" value="<c:out value="${project.getId()}"/>"/>
                <button>مشاهده</button>
            </form>
        </th>
    </tr>
    </c:forEach>
    <%--<%}%>--%>
</table>
</body>
</html>
