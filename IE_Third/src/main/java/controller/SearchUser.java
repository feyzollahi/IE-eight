package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataLayer.dataMappers.UserMapper.UserMapper;
import model.Repo.GetRepo;
import model.User.User;
import springController.UserSummaryData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchUser", urlPatterns = "/searchUser")
public class SearchUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchVal = request.getParameter("searchVal");
        GetRepo.print("searchUser");
        UserMapper userMapper = null;
        try {
             userMapper = new UserMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<User> users = null;
        GetRepo.print("2");
        try {
            users = userMapper.findWithFirstName(searchVal);
            User loginUser = (User) request.getAttribute("user");
            GetRepo.print("2.2");
            for(User user:userMapper.findWithLastName(searchVal)){
                if(!users.contains(user) && !user.getId().equals(loginUser.getId())){
                    users.add(user);
                }
            }
            GetRepo.print("2.5");
//            for(User user:userMapper.findWithJobTitle(searchVal)){
//                if(!users.contains(user)){
//                    users.add(user);
//                }
//            }
            GetRepo.print("2.7");
        } catch (SQLException e) {
            GetRepo.print("2.8");

            e.printStackTrace();
        }
        GetRepo.print("3");
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        List<UserSummaryData> usds = new ArrayList<>();
        GetRepo.print("4");
        for(User user:users){
            usds.add(new UserSummaryData(user.getId(), user.getFirstName(), user.getLastName(), user.getJobTitle()));
        }
        System.out.println("searchUser");
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(usds);
        GetRepo.print(json);
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();

    }
}
