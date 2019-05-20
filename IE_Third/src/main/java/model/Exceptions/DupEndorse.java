package model.Exceptions;

public class DupEndorse extends Throwable {
    public DupEndorse(String enodorserName, String skillName) {
        this.endorserName = enodorserName;
        this.skillName = skillName;
    }
    private String endorserName;
    private String skillName;
}
