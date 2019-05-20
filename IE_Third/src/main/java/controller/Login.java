package controller;

import dataLayer.dataMappers.UserMapper.UserMapper;
import model.Repo.GetRepo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import webTools.JwtFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@WebServlet(name= "login",urlPatterns = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetRepo.print("Login Servlet");
        String userName = "", passWord = "";
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
            userName = (String) jsonObject.get("userName");
            passWord = (String) jsonObject.get("passWord");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UserMapper userMapper = null;
        try {
             userMapper = new UserMapper();
             if(userMapper.isUserExistWithUserNamePassWord(userName)){
                 boolean correctPassWord = userMapper.checkPassWord(userName, passWord);
                 if(correctPassWord){
                     JwtFactory jwtFactory = new JwtFactory();
                     String jwt = jwtFactory.createJWT(userName, TimeUnit.MINUTES.toMillis(20));
                     response.setStatus(200);
                     PrintWriter writer = response.getWriter();
                     writer.print(jwt);
                     writer.flush();
                 }
                 else{
                     response.setStatus(403);
                 }
             }
             else{
                 response.setStatus(404);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
