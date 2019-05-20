package model.User;


import dataLayer.dataMappers.ProjectMapper.BidMapper;
import dataLayer.dataMappers.UserMapper.AdvancedUserMapper;
import dataLayer.dataMappers.UserMapper.EndorseInfo;
import dataLayer.dataMappers.UserMapper.EndorseMapper;
import dataLayer.dataMappers.UserMapper.UserSkillMapper;
import model.Bid.Bid;
import model.Exceptions.DupEndorse;
import model.Exceptions.SkillNotFound;
import model.Exceptions.UserSkillNotFound;
import model.Project.Project;
import model.Skill.ProjectSkill;
import model.Skill.UserSkill;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;

public class User {
    public User(JSONObject jsonObject) {
        this.isLogin = false;
        this.bio = (String) jsonObject.get("bio");
        this.firstName = (String) jsonObject.get("firstName");
        this.lastName = (String) jsonObject.get("lastName");
        this.id = (String) jsonObject.get("id");
        this.jobTitle = (String) jsonObject.get("jobTitle");
        this.skills = new HashMap<String, UserSkill>();
        this.bids = new HashMap<String, Bid>();
        this.userName = (String) jsonObject.get("userName");
        this.profilePictureURLText = (String) jsonObject.get("imageUrlText");
        JSONArray skills;
        skills = (JSONArray) jsonObject.get("skills");
        for (Object skill1 : skills) {
            UserSkill skill = new UserSkill((JSONObject) skill1);
            this.skills.put(skill.getName(), skill);
        }
    }
    public User(String userId, String firstName, String lastName, String userName,
                 String jobTitle, String bio, String imageUrlText, boolean isLogin){
        this.isLogin = isLogin;
        this.bio = bio;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = userId;
        this.userName = userName;
        this.jobTitle = jobTitle;
        this.profilePictureURLText = imageUrlText;
        this.skills = new HashMap<String, UserSkill>();
        this.bids = new HashMap<String, Bid>();
    }
    private boolean isLogin;
    private String bio;
    private String firstName;
    private String lastName;
    private String userName;
    private String id;
    private String jobTitle;
    private String profilePictureURLText;
    private HashMap<String, UserSkill> skills;
    private HashMap<String, Bid> bids;

    public boolean isUserApproprateForProject(Project project){
        for(ProjectSkill projectSkill:project.getSkills()){
            if(this.skills.get(projectSkill.getName()) == null
            || this.skills.get(projectSkill.getName()).getEndorsedCount() < projectSkill.getPoint()){
                return false;
            }
        }
        return true;
    }
    public boolean isLogin() {
        return isLogin;
    }
    public boolean hasSkill(String skillName){
        return this.skills.get(skillName) != null;
    }
    public void removeSkill(String skillName) throws SkillNotFound {
        if(this.skills.get(skillName) == null)
            throw new SkillNotFound();
        this.skills.remove(skillName);
    }
    public void setLogin(boolean login) {
        isLogin = login;
    }

    public void login(){
        isLogin = true;
    }
    public void logout(){isLogin = false;}
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, Bid> getBids() {
        return bids;
    }

    public void setBids(HashMap<String, Bid> bids) {
        this.bids = bids;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getProfilePictureURLText() {
        return profilePictureURLText;
    }

    public void setProfilePictureURLText(String profilePictureURLText) {
        this.profilePictureURLText = profilePictureURLText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public HashMap<String, UserSkill> getSkills() {
        return skills;
    }

    public void setSkills(HashMap<String, UserSkill> skills) {
        this.skills = skills;
    }
    public void addBid(Bid bid) throws SQLException {
        this.bids.put(bid.getProject().getId(), bid);
        BidMapper bidMapper = new BidMapper();
        bidMapper.insertObjectToDB(bid);

    }
    public void addSkill(UserSkill skill) throws SQLException {
        this.skills.put(skill.getName(), skill);
        UserSkillMapper userSkillMapper = new UserSkillMapper();
        userSkillMapper.insertObjectToDBWithId(skill, this.id);
    }
    public void addEndorserToSkills(String skillName, User endorser) throws UserSkillNotFound, DupEndorse, SQLException {
        UserSkill skill = this.skills.get(skillName);
        if(skill == null)
            throw new UserSkillNotFound();
        skill.addEndorser(endorser);
        EndorseMapper endorseMapper = new EndorseMapper();
        EndorseInfo endorseInfo = new EndorseInfo(endorser.getId(), this.id, skillName);
        endorseMapper.insertObjectToDB(endorseInfo);
    }
    public void deleteSkill(String skillName) throws SkillNotFound, SQLException {
        if(this.skills.get(skillName) == null)
            throw new SkillNotFound();
        this.skills.remove(skillName);
        AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
        advancedUserMapper.deleteSkill(this.id, skillName);
    }
}
