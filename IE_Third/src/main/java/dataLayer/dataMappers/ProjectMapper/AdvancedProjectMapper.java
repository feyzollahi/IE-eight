package dataLayer.dataMappers.ProjectMapper;

import dataLayer.dataMappers.UserMapper.UserMapper;
import model.Bid.Bid;
import model.Project.Project;
import model.Skill.ProjectSkill;
import model.User.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedProjectMapper {

    private boolean bidContain;
    private boolean projectSkillContain;
    private UserMapper userMapper;
    private ProjectMapper projectMapper;
    private ProjectSkillMapper projectSkillMapper;
    private BidMapper bidMapper;

    public AdvancedProjectMapper() throws SQLException {
        userMapper = new UserMapper();
        projectMapper = new ProjectMapper();
        projectSkillMapper = new ProjectSkillMapper();
        bidMapper = new BidMapper();
    }



    public boolean isBidContain() {
        return bidContain;
    }

    public void setBidContain(boolean bidContain) {
        this.bidContain = bidContain;
    }

    public boolean isProjectSkillContain() {
        return projectSkillContain;
    }

    public void setProjectSkillContain(boolean projectSkillContain) {
        this.projectSkillContain = projectSkillContain;
    }

    public List<Project> getAllProject() throws SQLException {
        ProjectMapper projectMapper = new ProjectMapper();
        List<Project> projects = projectMapper.findAll();
        for(int i = 0; i < projects.size(); i++){
            projects.set(i, getProjectById(projects.get(i).getId()));
        }
        return projects;
    }
    public Project getProjectById(String projectId) throws SQLException {
        Project project = projectMapper.find(projectId);
        if(bidContain){
            List<Bid> bids = new ArrayList<>();
            bids = bidMapper.findBidWithProjectId(projectId);
            for(Bid bid: bids){
                User user = userMapper.find(bid.getUserId());
                bid.setBiddingUser(user);
                bid.setProject(project);
                project.addBid(bid);
            }
        }
        if(projectSkillContain){
            List<ProjectSkill> projectSkills = new ArrayList<>();
            projectSkills = projectSkillMapper.findProjectSkills(projectId);
            for(ProjectSkill projectSkill: projectSkills){
                project.addSkill(projectSkill);
            }
        }

        return project;
    }

    public void setProject(Project project) throws SQLException {
        projectMapper.insertObjectToDB(project);
        if(!project.getSkills().isEmpty()){
            for(ProjectSkill projectSkill: project.getSkills()){
                projectSkillMapper.insertObjectToDBWithId(projectSkill, project.getId());
            }
        }
        if(!project.getBids().isEmpty()){
            for(Bid bid:project.getBids().values()){
                bidMapper.insertObjectToDB(bid);
            }
        }
    }
}
