package controller;

import model.Bid.Bid;
import model.Exceptions.DupEndorse;
import model.Exceptions.ProjectNotFound;
import model.Project.Project;
import model.Repo.GetRepo;
import model.Repo.ProjectsRepo;
import model.Repo.UsersRepo;
import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "userBidProjectCtrl",urlPatterns = "/userBidProjectCtrl")
public class UserBidProjectCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectId = request.getParameter("projectId");
        Project project = null;
        try {
            project = ProjectsRepo.getInstance().getProjectById(projectId);
        } catch (ProjectNotFound projectNotFound) {
            projectNotFound.printStackTrace();
            response.setStatus(404, "project not found");
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String bidAmount = request.getParameter("bidAmount");
        GetRepo.print("bidAmount = " + bidAmount);
        if(bidAmount == null || Integer.valueOf(bidAmount) < 0){
            request.setAttribute("BidErrorMsg", "Bid amount is not set since invalid value of bid amount.");
            response.setStatus(400);//bad request
            return;
        }

        Bid bid = null;
        bid = new Bid((User) request.getAttribute("user"), project, Integer.valueOf(bidAmount));
        if(!bid.isValid()){
                response.setStatus(400, "more than project budget");
            }
            else {
                GetRepo.print("bid user = " + bid.getBiddingUser().getLastName());
            try {
                project.addBid(bid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ((User)request.getAttribute("user")).addBid(bid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.setStatus(200);

            }
    }
}
