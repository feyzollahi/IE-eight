<%@ page import="model.Repo.UsersRepo" %>
<%@ page import="model.Exceptions.UserNotFound" %>
<%@ page import="model.User.User" %>
<%@ page import="model.Skill.UserSkill" %>
<%@ page import="model.Repo.GetRepo" %><%--
  Created by IntelliJ IDEA.
  User: mahdi
  Date: 3/1/19
  Time: 10:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<c:set  var= "userId"  value = "${param [\"userId\"]}"/>
<%GetRepo.print(request.getParameter("userId"));%>
<c:catch var="e">
    <c:set var = "user" value = "${UsersRepo.getInstance().getUserById(userId)}"/>
</c:catch>
<c:if test = "${not empty e}" >
    ${e}
</c:if>
<c:if test = "${not empty endorseMsg}">
    <h3 style = "color:blue"><c:out value = "${endorseMsg}"/></h3>
</c:if>
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
<ul>
    <li>id: <c:out value="${user.getId()}"/></li>
    <li>first name: <c:out value="${user.getFirstName()}"/></li>
    <li>last name: <c:out value="${user.getLastName()}"/></li>
    <li>jobTitle: <c:out value="${user.getJobTitle()}"/></li>
    <li>bio: <c:out value="${user.getBio()}"/></li>
    <li>
        skills:
        <ul>
            <c:forEach var = "userSkill" items = "${user.getSkills().values()}">
                <li>
                    <c:out value="${userSkill.getName()}"/> : <c:out value="${userSkill.getEndorsedCount()}"/>
                    <c:if test = "${!userSkill.isEndorser(UsersRepo.getInstance().getLoginUser().getId())}">
                        <form action="endorseCtrl" method="GET">
                            <input type="hidden" name="userId" value="<c:out value="${user.getId()}"/>"/>
                            <input type="hidden" name="skillName" value="<c:out value="${userSkill.getName()}"/>"/>
                            <button>Endorse</button>
                        </form>
                    </c:if>
                </li>
            </c:forEach>
            </li>
        </ul>
    </li>
</ul>
</body>
</html>
