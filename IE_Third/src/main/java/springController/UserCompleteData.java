package springController;

import model.Skill.UserSkill;

import java.util.ArrayList;

public class UserCompleteData {
    private ArrayList<UserSkill> uSkills = new ArrayList<UserSkill>();
    private String id, bio, firstName, lastName, jobTitle;

    public UserCompleteData(String id, String bio, String firstName, String lastName, String jobTitle) {
        this.id = id;
        this.bio = bio;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
    }

    public ArrayList<UserSkill> getuSkills() {
        return uSkills;
    }

    public void setuSkills(ArrayList<UserSkill> uSkills) {
        this.uSkills = uSkills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
