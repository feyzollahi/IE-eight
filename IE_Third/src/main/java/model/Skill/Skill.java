package model.Skill;

import org.json.simple.JSONObject;

public class Skill {
    public Skill(JSONObject jsonObj){
        this.name = (String) jsonObj.get("name");
    }
    public Skill(){

    }
    public Skill(String name){
        this.name = name;
    }
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
