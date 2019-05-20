package model.Repo;

import dataLayer.dataMappers.SkillMapper.SkillMapper;
import model.Skill.Skill;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class SkillsRepo {
    private static final String skillRepoUrlText = "http://142.93.134.194:8000/joboonja/skill";

    private static SkillsRepo skillsRepo = null;
    private SkillsRepo(){
    }
    public static SkillsRepo getInstance(){
        if(skillsRepo == null){
            skillsRepo = new SkillsRepo();
        }
        return skillsRepo;
    }


    public ArrayList<Skill> getSkills() throws SQLException {
        SkillMapper skillMapper = new SkillMapper();

        return new ArrayList<Skill>(skillMapper.findAll());
    }

//    public void setSkills(ArrayList<Skill> skills) {
//
//    }

    public void addSkill(Skill skill) throws SQLException {
        SkillMapper skillMapper = new SkillMapper();
        skillMapper.insertObjectToDB(skill);
    }
    public void setRepo() throws Exception {
        Object obj = GetRepo.getHTML(skillRepoUrlText);
        JSONArray skillJsonArr = (JSONArray) obj;
        for(Object skillObj: skillJsonArr){
            Skill skill = new Skill((JSONObject)skillObj);
            SkillMapper skillMapper = new SkillMapper();
            skillMapper.insertObjectToDB(skill);
        }
    }
}
