package controller;

import model.Exceptions.DupEndorse;
import model.Exceptions.SkillNotFound;
import model.Repo.GetRepo;
import model.Repo.UsersRepo;
import model.Skill.UserSkill;
import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "deleteSkill",urlPatterns = {"/deleteSkill"})
public class DeleteSkillCtrl extends HttpServlet {
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        user = (User) request.getAttribute("user");
        String skillName = request.getParameter("userSkill");
        System.out.println("skillName = " + skillName);
        ArrayList<UserSkill> uskills = new ArrayList<>(user.getSkills().values());
        for(UserSkill userSkill: uskills){
            System.out.println(userSkill.getName());
        }
        try {
            user.deleteSkill(skillName);
            response.setStatus(200);
        } catch (SkillNotFound skillNotFound) {
            skillNotFound.printStackTrace();
            response.setStatus(404);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        GetRepo.print("deleteSkill");
//        User user = UsersRepo.getInstance().getLoginUser();
//        String skillName = request.getParameter("userSkill");
//        try {
//            user.deleteSkill(skillName);
//        } catch (SkillNotFound skillNotFound) {
//            skillNotFound.printStackTrace();
//        }
//        request.setAttribute("deleteMsg","skill " + "\"" + skillName + "\"" + " was removed from user " + user.getFirstName()
//        + " " + user.getLastName() + " skills list");
//        request.getRequestDispatcher("jsp/userOwnPage.jsp").forward(request, response);

    }
}
