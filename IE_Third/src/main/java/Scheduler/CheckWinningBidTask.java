package Scheduler;

import dataLayer.dataMappers.ProjectMapper.AdvancedProjectMapper;
import dataLayer.dataMappers.ProjectMapper.ProjectMapper;
import dataLayer.dataMappers.UserMapper.AdvancedUserMapper;
import model.Bid.Bid;
import model.Project.Project;
import model.Repo.GetRepo;
import model.Skill.UserSkill;
import model.User.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class CheckWinningBidTask extends TimerTask {

    public CheckWinningBidTask(){

        //Constructor

    }
    private int getScoreOfUserInBid(Project project, User user){
        int score = 0;
        for(UserSkill userSkill: user.getSkills().values()){
            long x = userSkill.getEndorsedCount() - project.getSkill(userSkill.getName()).getPoint();
            score += 10000 * x * x;
        }
        score += (project.getBudget() - user.getBids().get(project.getId()).getBidAmount());
        return score;
    }
    public int getIndexOfLargest( ArrayList<Integer> array )
    {
        if ( array == null || array.size() == 0 ) return -1; // null or empty

        int largest = 0;
        for ( int i = 1; i < array.size(); i++ )
        {
            if ( array.get(i) > array.get(largest) ) largest = i;
        }
        return largest; // position of the first largest found
    }
    public void run() {
        try {
            GetRepo.print("1");
            AdvancedProjectMapper advancedProjectMapper = new AdvancedProjectMapper();
            advancedProjectMapper.setProjectSkillContain(true);
            advancedProjectMapper.setBidContain(true);
            AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
            advancedUserMapper.setUserSkillContain(true);
            advancedUserMapper.setBidContain(true);
            GetRepo.print("1");

            ArrayList<Project> projects = (ArrayList<Project>) advancedProjectMapper.getAllProject();
            GetRepo.print("2");

            for(Project project: projects){
                if(project.getWinnerUser() != null || project.getDeadline() > new Date().getTime())
                    continue;
                ArrayList<User> users = new ArrayList<>();
                ArrayList<Integer> scores = new ArrayList<>();
                GetRepo.print("3");

                for(Bid bid: project.getBids().values()){
                    users.add(bid.getBiddingUser());
                }
                GetRepo.print("4");

                for(int i = 0; i < users.size(); i++){
                    scores.add(getScoreOfUserInBid(project, users.get(i)));
                }
                GetRepo.print("5");

                int maxIndex = getIndexOfLargest(scores);
                ProjectMapper projectMapper = new ProjectMapper();
                if(users.size() != 0)
                    projectMapper.setWinnerUser(users.get(maxIndex).getId(), project.getId());
            }

            GetRepo.print("Bid check run!!!!!!!");

        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}
