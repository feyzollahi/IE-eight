package model.Repo;

import dataLayer.dataMappers.ProjectMapper.AdvancedProjectMapper;
import model.Exceptions.ProjectNotFound;
import model.Project.Project;
import model.User.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProjectsRepo {
    private static final String projectRepoUrlText = "http://142.93.134.194:8000/joboonja/project";
    private static ProjectsRepo singleProjectsRepo = null;
    private ProjectsRepo() {

    }
    public static ProjectsRepo getInstance(){
        if(singleProjectsRepo == null)
            singleProjectsRepo = new ProjectsRepo();
        return singleProjectsRepo;
    }
    public Project getProjectById(String id) throws ProjectNotFound, SQLException {
        Project project;
        AdvancedProjectMapper advancedProjectMapper = new AdvancedProjectMapper();
        project = advancedProjectMapper.getProjectById(id);
        if(project == null){
            throw new ProjectNotFound();
        }
        return project;
    }
//    public ArrayList<Project> getProjectFilteredByUserSkills(User user){
//        ArrayList<Project> projects = new ArrayList<Project>();
//        for(Project project:this.projects.values()){
//            if(project.isUserAppropriateForProject(user)){
//                projects.add(project);
//            }
//        }
//        return projects;
//    }
    public ArrayList<Project> getAllProjects() throws SQLException {
        AdvancedProjectMapper advancedProjectMapper = new AdvancedProjectMapper();
        advancedProjectMapper.setProjectSkillContain(true);
        return new ArrayList<Project>(advancedProjectMapper.getAllProject());
    }
    public void addProject(Project project) throws SQLException {
        AdvancedProjectMapper advancedProjectMapper = new AdvancedProjectMapper();
        advancedProjectMapper.setProject(project);
    }
    public void setRepo() throws Exception {
        Object prjObj = GetRepo.getHTML(projectRepoUrlText);
        JSONArray jsonPrjArr = (JSONArray) prjObj;
//        JSONObject jo = new JSONObject();
//        org.json.simple.parser.JSONParser jp = new JSONParser();
//        jo = (JSONObject) jp.parse("{\"id\":\"c29444de-7dbb-41c1-a664-765473f6c1c7\",\"title\":\"سایت شرکت شیرزادیان \",\"description\":\"ایجاد یک وب سایت جهت معرفی بخشی از خدمات شرکت با استفاده از سیستم wordpress، این شرکت قصد داره تا در یک معرفی اجمالی به شرح خدماتی که می تونه به افراد...\",\"imageUrl\":\"https://pureelementwater.com/wp-content/uploads/2017/07/proba.png\",\"budget\":1500000,\"deadline\":1556458488000,\"skills\":[{\"name\":\"CSS\",\"point\":1}]}");
//        Project project1 = new Project(jo);
//        this.addProject(project1);
        for(Object prj: jsonPrjArr){
            JSONObject jsonPrj = (JSONObject) prj;
            Project project = new Project(jsonPrj);
            AdvancedProjectMapper advancedProjectMapper = new AdvancedProjectMapper();
            advancedProjectMapper.setProject(project);
        }
    }
}
