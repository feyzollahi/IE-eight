package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Exceptions.DupEndorse;
import model.Project.Project;
import model.Repo.GetRepo;
import model.Repo.ProjectsRepo;
import model.Repo.UsersRepo;
import model.User.User;
import springController.ProjectSummaryData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "projectlist", urlPatterns = "/projectlist")
public class ProjectList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetRepo.print("projectList");
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Project> projects = null;
        try {
            GetRepo.print("2");

            projects = ProjectsRepo.getInstance().getAllProjects();
            GetRepo.print("3");

        } catch (Exception e) {
            e.printStackTrace();
        }
        User loginUser = null;
        GetRepo.print("4");

        loginUser = (User) request.getAttribute("user");
        if(loginUser == null){
            System.out.println("loginUserNull");
        }
        GetRepo.print(loginUser.getUserName());
        GetRepo.print("5");

        ArrayList<Project> filterProjects = new ArrayList<>();
        for(Project project: projects){
            if(project.isUserAppropriateForProject(loginUser)){
                filterProjects.add(project);
            }
        }
        GetRepo.print("6");

        ArrayList<ProjectSummaryData> projectSummaryDataCollection = new ArrayList<>();
		for(Project filterProject: filterProjects){
			projectSummaryDataCollection.add(new ProjectSummaryData(filterProject.getId(), filterProject.getTitle(), filterProject.getDescription()
                                                                ,filterProject.getImageUrlText(), filterProject.getDeadline()
                                                            , filterProject.getCreationDate() , filterProject.getBudget(), filterProject.getSkills()));
		}
        GetRepo.print("7");

        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        String json = mapper.writeValueAsString(projectSummaryDataCollection);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
