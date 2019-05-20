package springController;

public class UserSummaryData {
    private String id, firstName, lastName, jobTitle;

    public UserSummaryData(String id, String firstName, String lastName, String jobTitle){
        this.id = id;
        this.jobTitle = jobTitle;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
