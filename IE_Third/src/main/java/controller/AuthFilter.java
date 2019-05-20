package controller;

import dataLayer.dataMappers.UserMapper.AdvancedUserMapper;
import model.User.User;
import webTools.JwtFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//@WebFilter(filterName = "authFilter" , servletNames = {"addSkill",
//        "deleteSkill", "endorseCtrl", "home", "projectlist", "searchProject",
//        "searchUser", "showAllUsersCtrl", "showSpecifiedProjectCtrl",
//        "showSpecifiedUserCtrl", "skillList", "userBidProjectCtrl"})
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("AuthFilter.......");
        HttpServletRequest request = (HttpServletRequest) req;
        String jwt = request.getHeader("Authorization");
        JwtFactory jwtFactory = new JwtFactory();
        if(jwt == null || jwt.equals("")){
            System.out.println("not Authorized");
            System.out.print("jwt = ");
            System.out.println(jwt);
//            chain.doFilter(req, resp);
            ((HttpServletResponse)resp).setStatus(401);
        }
        else{

            boolean isValid = jwtFactory.validateJwt(jwt);
            if(!isValid){
                System.out.println("not valid jwt" + jwt);
                ((HttpServletResponse)resp).setStatus(403);
            }
            else{
                User user = null;
                try {
                    AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
                    advancedUserMapper.setUserSkillContain(true);
                    user = advancedUserMapper.getUserWithUserName(jwtFactory.decodeJwt(jwt).get("userName").toString());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("finalUser: " + user.getId());
                req.setAttribute("user", user);
                chain.doFilter(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
