package model.Exceptions;

public class BidNotValid extends Exception {
    String errorMessage;
    public BidNotValid(String projectTitle, String userName, long budget){
        errorMessage = "user " + userName + "cannot bid for project " + projectTitle + "with budget " + String.valueOf(budget);
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
