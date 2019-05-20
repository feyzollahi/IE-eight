package springController;

import model.Repo.GetRepo;
import model.Skill.ProjectSkill;
import model.Skill.Skill;

import java.util.ArrayList;

public class ProjectSummaryData {
    String id, title, description, imageUrlText;
    long budget, deadline, creationDate;
    ArrayList<Skill> skills = new ArrayList<Skill>();
    public ProjectSummaryData(String id, String title, String description, String imageUrlText, long deadline,
                              long creationDate, long budget, ArrayList<ProjectSkill> pSkills){
        this.id = id;
        this.title = title;
        this.budget = budget;
        this.deadline = deadline;
        this.creationDate = creationDate;
        this.description = description;
        this.imageUrlText = imageUrlText;
        GetRepo.print(imageUrlText);
        for(int i = 0; i < pSkills.size(); i++)
            this.skills.add(new Skill(pSkills.get(i).getName()));
    }

    public String getImageUrlText() {
        return imageUrlText;
    }

    public void setImageUrlText(String imageUrlText) {
        this.imageUrlText = imageUrlText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}
