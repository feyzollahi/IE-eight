package model.Project;

import dataLayer.dataMappers.ProjectMapper.BidMapper;
import model.Bid.Bid;
import model.Exceptions.BidNotFound;
import model.Skill.ProjectSkill;
import model.User.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Project {

    public Project(JSONObject project_data) {
        this.skills = new HashMap<String, ProjectSkill>();
        this.title = project_data.get("title").toString();
        JSONArray array_skill = (JSONArray) project_data.get("skills");
        for (Object anArray_skill : array_skill) {
            this.skills.put((((JSONObject) anArray_skill).get("name")).toString(), new ProjectSkill((JSONObject) anArray_skill));
        }
        this.imageUrlText = (String) project_data.get("imageUrl");
        this.budget = (Long) project_data.get("budget");
        this.id = (String) project_data.get("id");
        this.deadline = (Long) project_data.get("deadline");
        this.description = (String) project_data.get("description");
        this.creationDate = (Long) project_data.get("creationDate");
        this.bids = new HashMap<String, Bid>();
    }
    public Project(String projectId, String title, String imageUrlText, long budget,
                   long deadline, long creationDate, String description){
        this.id = projectId;
        this.title = title;
        this.imageUrlText = imageUrlText;
        this.budget = budget;
        this.deadline = deadline;
        this.creationDate = creationDate;
        this.description = description;
        this.bids = new HashMap<String, Bid>();
        this.skills = new HashMap<String, ProjectSkill>();
    }

    private HashMap<String, ProjectSkill> skills;
    private Long budget;
    private String title;
    private String id;
    private String description;
    private long deadline;
    private long creationDate;
    private HashMap<String, Bid> bids;
    private User winnerUser;
    private String imageUrlText;



    public boolean isUserAppropriateForProject(User user){

        for(ProjectSkill skill: skills.values()){
            if(user.getSkills().get(skill.getName()) == null
            || user.getSkills().get(skill.getName()).getEndorsedCount() < skill.getPoint()){
                return false;
            }
        }
        return true;
    }
    public HashMap<String, Bid> getBids() {
        return bids;
    }

    public void setBids(HashMap<String, Bid> bids) {
        this.bids = bids;
    }
    public String getImageUrlText() {
        return imageUrlText;
    }

    public void setImageUrlText(String imageUrlText) {
        this.imageUrlText = imageUrlText;
    }

    public void addBid(Bid bid) throws SQLException {
        this.bids.put(bid.getBiddingUser().getId(), bid);
        BidMapper bidMapper = new BidMapper();
        bidMapper.insertObjectToDB(bid);
    }

    public Bid getBid(String userId) throws BidNotFound {
        if(this.bids.get(userId) == null){
            throw new BidNotFound();
        }
        return this.bids.get(userId);
    }
    public boolean userHasBid(String userId){
        try {
            Bid bid = this.getBid(userId);
            return true;
        } catch (BidNotFound bidNotFound) {
            return false;
        }
    }
    public User getWinnerUser() {
        return winnerUser;
    }

    public void setWinnerUser(User winnerUser) {
        this.winnerUser = winnerUser;
    }

    public void setSkills(HashMap<String, ProjectSkill> skills) {
        this.skills = skills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addSkill(ProjectSkill skill) {
        this.skills.put(skill.getName(), skill);
    }

    public ProjectSkill getSkill(String skillName) {
        return this.skills.get(skillName);
    }

    public ArrayList<ProjectSkill> getSkills() {
        return new ArrayList<ProjectSkill>(skills.values());
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }
}
