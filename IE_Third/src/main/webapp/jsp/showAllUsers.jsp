<%@ page import="model.Repo.GetRepo" %>
<%@ page import="model.User.User" %>
<%@ page import="model.Repo.UsersRepo" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: mahdi
  Date: 3/1/19
  Time: 9:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%--<%--%>
    <%--if(!GetRepo.isSetRepo){--%>
        <%--GetRepo.print("not setRepo showAllUsers.jsp");--%>
        <%--request.getRequestDispatcher("").forward(request, response);--%>
    <%--}--%>
<%--%>--%>
<c:choose>
    <c:when test="${!GetRepo.isSetRepo}">
        <c:out value="${GetRepo.print(\"not setRepo showAllUsers.jsp\")}" />
        <c:out value="${request.getRequestDispatcher(\"\").forward(request, response)}" />
    </c:when>
</c:choose>
<html>
<head>
    <title>مشاهده تمام کاربران</title>
</head>
<body>
<%GetRepo.print("showAllUsers.jsp");
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
    User loginUser = (User) request.getAttribute("loginUser");
%>


<form action="showAllProjects" method="GET">
    <button>مشاهده تمام پروژه ها</button>
</form>
<form action="homeServlet" method="GET">
    <button>مشاهده صفحه اول</button>
</form>
<form action="jsp/userOwnPage.jsp" method="GET">
    <button>پروفایل</button>
</form>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>jobTitle</th>
        <th>مشاهده</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <c:choose>
            <c:when test = "${user.getId() != loginUser.getId()}" >
                <tr>
                    <th><c:out value="${user.getId()}"/></th>
                    <th><c:out value ="${user.getFirstName()} ${user.getLastName()}" /></th>
                    <th><c:out value="${user.getJobTitle()}"/></th>
                    <th>
                        <form action="showSpecifiedUserCtrl" method="GET">
                            <input type="hidden" name="userId" value="<c:out value= "${user.getId()}"/>"/>
                            <button>مشاهده</button>
                        </form>
                    </th>
                </tr>
            </c:when>
        </c:choose>
    </c:forEach>
</table>
</body>
</html>
