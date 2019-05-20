package dataLayer.dataMappers.UserMapper;

public class EndorseInfo {
    private String endorserId;
    private String endorsedId;
    private String skillName;

    public EndorseInfo(String endorserId, String endorsedId, String skillName) {
        this.endorserId = endorserId;
        this.endorsedId = endorsedId;
        this.skillName = skillName;
    }

    public String getEndorserId() {
        return endorserId;
    }

    public String getEndorsedId() {
        return endorsedId;
    }

    public String getSkillName() {
        return skillName;
    }
}
