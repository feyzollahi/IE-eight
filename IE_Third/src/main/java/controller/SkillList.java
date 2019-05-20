package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Exceptions.DupEndorse;
import model.Repo.SkillsRepo;
import model.Repo.UsersRepo;
import model.Skill.Skill;
import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "skillList", urlPatterns = "/skillList")
public class SkillList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Skill> skills = null;
        try {
            skills = SkillsRepo.getInstance().getSkills();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = null;
        user = (User) request.getAttribute("user");
        ArrayList<Skill> loginUserHasNotSkills = new ArrayList<>();
        for(Skill skill: skills){
            if(!user.hasSkill(skill.getName())){
                loginUserHasNotSkills.add(skill);
            }
        }
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        String json = mapper.writeValueAsString(loginUserHasNotSkills);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
