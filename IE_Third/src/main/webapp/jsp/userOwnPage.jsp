<%@ page import="model.Repo.UsersRepo" %>
<%@ page import="model.Repo.GetRepo" %>
<%@ page import="model.Skill.UserSkill" %>
<%@ page import="model.Skill.Skill" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Repo.SkillsRepo" %><%--

  Created by IntelliJ IDEA.
  User: mahdi
  Date: 3/1/19
  Time: 8:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<c:choose>
    <c:when test="${!GetRepo.isSetRepo}">
        <c:out value="${GetRepo.print(\"not setRepo userOwnPage.jsp\")}" />
        <c:out value="${request.getRequestDispatcher(\"\").forward(request, response)}" />
    </c:when>
</c:choose>
<html>
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
</head>
<body>
<c:out value ="${System.out.println(\"userOwnPage\")}"/>
<c:choose>
    <c:when test = "${not empty deleteMsg}">
        <h3 style="color: blue"><c:out value="${deleteMsg}"/></h3>
    </c:when>
</c:choose>
<c:choose>
    <c:when test = "${not empty addMsg}">
        <h3 style="color: blue"><c:out value ="${addMsg}"/></h3>
    </c:when>
</c:choose>
    <form action="showAllUsersCtrl" method="GET">
        <button>مشاهده تمام کاربران</button>
    </form>
    <form action="showAllProjects" method="GET">
        <button>مشاهده تمام پروژه ها</button>
    </form>
    <form action="homeServlet" method="GET">
        <button>مشاهده صفحه اول</button>
    </form>
    <ul>

        <li>id: <c:out value ="${UsersRepo.getInstance().getLoginUser().getId()}"/></li>
        <li>first name: <c:out value="${UsersRepo.getInstance().getLoginUser().getFirstName()}"/></li>
        <li>last name: <c:out value="${UsersRepo.getInstance().getLoginUser().getLastName()}"/></li>
        <li>jobTitle: <c:out value="${UsersRepo.getInstance().getLoginUser().getJobTitle()}"/></li>
        <li>bio: <c:out value="${UsersRepo.getInstance().getLoginUser().getBio()}"/></li>
        <li>
            skills:
            <ul>
                <c:forEach var = "userSkill" items = "${UsersRepo.getInstance().getLoginUser().getSkills().values()}">
                    <li>
                        <c:out value="${userSkill.getName()}"/>: <c:out value="${userSkill.getEndorsedCount()}"/>
                        <form action="deleteSkill" method="GET">
                            <input type="hidden" name="userSkill" value="<c:out value="${userSkill.getName()}"/>">
                            <button>Delete</button>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </li>
    </ul>
    Add Skill:
    <%--<%ArrayList<Skill> skills = SkillsRepo.getInstance().getSkills();%>--%>
    <form action="addSkillCtrl" method="GET">
        <select name="skillName">
            <c:forEach var = "skill" items="${SkillsRepo.getInstance().getSkills()}">
                <c:choose>
                    <c:when test="${empty (UsersRepo.getInstance().getLoginUser().getSkills().get(skill.getName()))}">
                        <option value="<c:out value="${skill.getName()}"/>"><c:out value="${skill.getName()}"/></option>
                    </c:when>
                </c:choose>
            </c:forEach>
        </select>
        <button>Add</button>
    </form>
</body>
</html>
