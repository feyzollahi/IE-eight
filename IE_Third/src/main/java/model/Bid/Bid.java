package model.Bid;

import dataLayer.dataMappers.ProjectMapper.ProjectMapper;
import dataLayer.dataMappers.UserMapper.UserMapper;
import dataLayer.dataMappers.UserMapper.UserSkillMapper;
import model.Exceptions.DupEndorse;
import model.Exceptions.ProjectNotFound;
import model.Exceptions.UserNotFound;
import model.Project.Project;
import model.Repo.ProjectsRepo;
import model.Repo.UsersRepo;
import model.User.User;
import org.json.simple.JSONObject;

import java.sql.SQLException;

public class Bid {
    public Bid(JSONObject jsonObject) throws Exception, DupEndorse {
        String userId = (String) jsonObject.get("userId");
        UsersRepo usersRepo = UsersRepo.getInstance();
        User user = usersRepo.getUserById(userId);
        if(user == null){
            throw new UserNotFound();
        }
        ProjectsRepo projectsRepo = ProjectsRepo.getInstance();
        String projectId = (String) jsonObject.get("projectId");
        Project project = projectsRepo.getProjectById(projectId);
        if(project == null){
            throw new ProjectNotFound();
        }
        this.bidAmount = (Integer) jsonObject.get("bidAmount");
        this.biddingUser = user;
        this.project = project;
    }
    public Bid(User user, Project project, int bidAmount){
        this.biddingUser = user;
        this.project = project;
        this.bidAmount = bidAmount;
    }
    public Bid(String userId, String projectId, int bidAmount){
        this.userId = userId;
        this.projectId = projectId;
        this.bidAmount = bidAmount;
    }
    public void setUserAndProjectFromDB() throws SQLException {
        UserMapper userMapper = new UserMapper();
        ProjectMapper projectMapper = new ProjectMapper();
        this.biddingUser = userMapper.find(this.userId);
        this.project = projectMapper.find(this.projectId);
    }
    public boolean isValid(){
        if(project.getBudget() < bidAmount)
            return false;
        return true;
    }
    private User biddingUser;
    private int bidAmount;
    private Project project;
    private String userId;
    private String projectId;

    public String getUserId() {
        return userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public User getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(User biddingUser) {
        this.biddingUser = biddingUser;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
