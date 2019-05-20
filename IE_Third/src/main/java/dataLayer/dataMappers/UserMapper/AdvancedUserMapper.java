package dataLayer.dataMappers.UserMapper;

import dataLayer.dataMappers.ProjectMapper.BidMapper;
import dataLayer.dataMappers.ProjectMapper.ProjectMapper;
import model.Bid.Bid;
import model.Exceptions.DupEndorse;
import model.Project.Project;
import model.Skill.UserSkill;
import model.User.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedUserMapper {
    public AdvancedUserMapper() throws SQLException {
        userMapper = new UserMapper();
        projectMapper = new ProjectMapper();
        userSkillMapper = new UserSkillMapper();
        bidMapper = new BidMapper();
        endorseMapper = new EndorseMapper();
    }
    private boolean bidContain;
    private boolean userSkillContain;
    private UserMapper userMapper;
    private ProjectMapper projectMapper;
    private UserSkillMapper userSkillMapper;
    private EndorseMapper endorseMapper;
    private BidMapper bidMapper;

    public boolean isBidContain() {
        return bidContain;
    }

    public void setBidContain(boolean bidContain) {
        this.bidContain = bidContain;
    }

    public boolean isUserSkillContain() {
        return userSkillContain;
    }

    public void setUserSkillContain(boolean userSkillContain) {
        this.userSkillContain = userSkillContain;
    }

    public List<User> getAllUser() throws SQLException, DupEndorse {
        List<User> users = userMapper.findAll();
        for(int i = 0; i < users.size(); i++){
            users.set(i, getUserWithId(users.get(i).getId()));
        }
        return users;
    }
    public User getUserWithUserName(String userName) throws SQLException {
        User user = userMapper.findWithUserName(userName);
        System.out.println("getWithUserName: firstName: " + user.getFirstName());
        return userMapper.find(user.getId());
    }
    public User getUserWithId(String userId) throws SQLException, DupEndorse {
        User user = userMapper.find(userId);

        if(bidContain) {
            List<Bid> bids = new ArrayList<>();
            bids = bidMapper.findBidWithUserId(userId);
            for(Bid bid: bids){
                Project project = projectMapper.find(bid.getProjectId());
                bid.setBiddingUser(user);
                bid.setProject(project);
                user.addBid(bid);
            }
        }
        if(userSkillContain){
            List<UserSkill> userSkills = new ArrayList<>();
            userSkills = userSkillMapper.findUserSkills(userId);
            List<EndorseInfo> endorseInfos = new ArrayList<>();
            endorseInfos = endorseMapper.findEndorsers(userId);
            for(UserSkill userSkill: userSkills){
                for(EndorseInfo endorseInfo: endorseInfos){
                    if(userSkill.getName().equals(endorseInfo.getSkillName())){
                        User endorser = userMapper.find(endorseInfo.getEndorserId());
                        userSkill.addEndorser(endorser);
                    }
                }
                user.addSkill(userSkill);
            }
        }
        return user;

    }
    public void deleteSkill(String userId, String skillName) throws SQLException {
        UserSkillMapper usm = new UserSkillMapper();
        EndorseMapper em = new EndorseMapper();
        usm.deleteUserSkill(userId, skillName);
        em.deleteEndorseWithSkill(userId, skillName);
    }
    public void setUser(User user, long passWordHash) throws SQLException {
        System.out.println(user.getUserName());
        System.out.println("userName");
        userMapper.insertObjectToDB(user);
        userMapper.setPassWordHash(user.getId(), (int)passWordHash);
        if(!user.getBids().isEmpty()){
            for(Bid bid: user.getBids().values()){
                bidMapper.insertObjectToDB(bid);
            }
        }
        if(!user.getSkills().isEmpty()){
            for(UserSkill userSkill: user.getSkills().values()){
                userSkillMapper.insertObjectToDBWithId(userSkill, user.getId());
                for(User user1: userSkill.getEndorsers()){
                    endorseMapper.insertObjectToDB(new EndorseInfo(user1.getId(), user.getId(), userSkill.getName()));
                }
            }
        }
    }
}
