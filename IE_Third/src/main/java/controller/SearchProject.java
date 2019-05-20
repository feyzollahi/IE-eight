package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataLayer.dataMappers.ProjectMapper.ProjectMapper;
import model.Project.Project;
import model.Repo.GetRepo;
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
import java.util.List;

@WebServlet(name = "searchProject", urlPatterns = "/searchProject")
public class SearchProject extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchVal = request.getParameter("searchVal");
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        ProjectMapper projectMapper;
        GetRepo.print("searchProject");
        try {
            projectMapper = new ProjectMapper();
            List<Project> projects = projectMapper.findWithSearchVal(searchVal);
            GetRepo.print("2");

            List<ProjectSummaryData> projectSummaryDataCollection = new ArrayList<>();
            for(Project project:projects){
                projectSummaryDataCollection.add(new ProjectSummaryData(project.getId(), project.getTitle(), project.getDescription(),
                        project.getImageUrlText(), project.getDeadline(), project.getCreationDate(), project.getBudget(), project.getSkills()));
            }
            GetRepo.print("3");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(projectSummaryDataCollection);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            GetRepo.print("5");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
