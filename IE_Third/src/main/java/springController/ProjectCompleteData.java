package springController;

public class ProjectCompleteData {
    String id, title, description, imageUrlText;
    long budget;

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public ProjectCompleteData(String id, String title, String description, String imageUrlText, long budget) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrlText = imageUrlText;
        this.budget = budget;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrlText() {
        return imageUrlText;
    }

    public void setImageUrlText(String imageUrlText) {
        this.imageUrlText = imageUrlText;
    }
}
