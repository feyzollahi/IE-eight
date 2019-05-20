package controller;

import model.Exceptions.DupEndorse;
import model.Exceptions.UserNotFound;
import model.Exceptions.UserSkillNotFound;
import model.Repo.GetRepo;
import model.Repo.UsersRepo;
import model.User.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "endorseCtrl", urlPatterns = "/endorseCtrl")
public class EndorseCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String skillName = "", userId = "";
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) { /*report an error*/ }
        GetRepo.print(jb.toString());
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jb.toString());
            skillName = (String) jsonObject.get("skillName");
            userId = (String) jsonObject.get("userId");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User loginUser = null;
        loginUser = (User) request.getAttribute("user");
        User user = null;
        try {
            user = UsersRepo.getInstance().getUserById(userId);
            user.addEndorserToSkills(skillName, loginUser);
            response.setStatus(200);
        } catch (UserNotFound | UserSkillNotFound | DupEndorse userNotFound) {
            userNotFound.printStackTrace();
            GetRepo.print("Exception EndorserCtrl");
            response.setStatus(404);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String skillName = request.getParameter("skillName");
//        String userId = request.getParameter("userId");
//        User loginUser = UsersRepo.getInstance().getLoginUser();
//        User user = null;
//        try {
//            user = UsersRepo.getInstance().getUserById(userId);
//            user.addEndorserToSkills(skillName, loginUser);
//        } catch (UserNotFound | UserSkillNotFound | DupEndorse userNotFound) {
//            userNotFound.printStackTrace();
//            GetRepo.print("Exception EndorserCtrl");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        request.setAttribute("endorseMsg","skill " + "\"" + skillName + "\"" + " of user " + user.getFirstName()
//                + " " + user.getLastName() + " was endoresed by user " + loginUser.getFirstName()
//                + " " + loginUser.getLastName());
//        request.getRequestDispatcher("jsp/userGuestPage.jsp").forward(request, response);
    }
}
