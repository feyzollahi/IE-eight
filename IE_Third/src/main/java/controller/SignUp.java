package controller;

import dataLayer.dataMappers.UserMapper.UserMapper;
import model.Repo.GetRepo;
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

@WebServlet("/signUp")
public class SignUp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer signUpInfoToken = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                signUpInfoToken.append(line);
            }
        } catch (Exception e) { /*report an error*/ }
        GetRepo.print(signUpInfoToken.toString());
        JSONParser jsonParser = new JSONParser();
        String firstName = "", lastName = "", userName = "", passWord = "", jobTitle = "", bio = "", imageUrlText = "";
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(signUpInfoToken.toString());
            firstName = (String) jsonObject.get("firstName");
            lastName = (String) jsonObject.get("lastName");
            userName = (String) jsonObject.get("userName");

            passWord = (String) jsonObject.get("passWord");
            jobTitle = (String) jsonObject.get("jobTitle");
            bio = (String) jsonObject.get("bio");
            imageUrlText = (String) jsonObject.get("imageUrlText");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            UserMapper um = new UserMapper();
            if(!um.isUserExistWithUserNamePassWord(userName)){
                System.out.println("signingUp");
                int userCount = um.getUserTableSize();
                User user = new User(String.valueOf(userCount + 1), firstName,
                        lastName, userName, jobTitle, bio, imageUrlText, false);
                um.insertObjectToDB(user);
                System.out.println("signingUp finished");

                response.setStatus(200);
            }
            else{
                response.setStatus(403);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
