package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Exceptions.DupEndorse;
import model.Exceptions.ProjectNotFound;
import model.Project.Project;
import model.Repo.GetRepo;
import model.Repo.ProjectsRepo;
import model.Repo.UsersRepo;
import model.User.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import springController.ProjectCompleteData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "showSpecifiedProjectCtrl",urlPatterns = "/showSpecifiedProjectCtrl")
public class ShowSpecifiedProjectCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetRepo.print("showSpecifiedProjectCtrl");
        String projectId = request.getParameter("projectId");
        boolean isLegal = false;
        boolean exist = true;
        try {
            exist = ProjectsRepo.getInstance().getProjectById(projectId) != null;
            isLegal = ProjectsRepo.getInstance().getProjectById(projectId).isUserAppropriateForProject((User) request.getAttribute("user"));
        } catch (ProjectNotFound projectNotFound) {
            projectNotFound.printStackTrace();
            exist = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.removeAttribute("projects");
        if(!exist){
            response.setStatus(404);
        }
        else {
            if (isLegal) {
                try {

                    response.setHeader("Content-Type", "application/json; charset=UTF-8");
                    Project project = ProjectsRepo.getInstance().getProjectById(projectId);

                    ProjectCompleteData projectCompleteData = new ProjectCompleteData(project.getId(), project.getTitle()
                            , project.getDescription(), project.getImageUrlText(), project.getBudget());
                    response.setStatus(200);
                    ObjectMapper om = new ObjectMapper();
                    String json = om.writeValueAsString(project);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject projectJson = null;
                    try {
                        projectJson = (JSONObject) jsonParser.parse(json);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    boolean isLoginUserBid = project.userHasBid(((User) request.getAttribute("user")).getId());
                    projectJson.put("userBid", isLoginUserBid);
                    GetRepo.print("bid : " + isLoginUserBid);
                    String finalJson = projectJson.toString();
                    PrintWriter writer = response.getWriter();
                    writer.print(finalJson);
                    writer.flush();
//                    request.getRequestDispatcher("specifiedProject/specifiedProject.jsp").forward(request, response);

                } catch (ProjectNotFound projectNotFound) {
                    projectNotFound.printStackTrace();
                    response.setStatus(404);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                response.setStatus(403);

            }

        }
    }
}
