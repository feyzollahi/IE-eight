package model.Repo;

import dataLayer.dataMappers.UserMapper.AdvancedUserMapper;
import dataLayer.dataMappers.UserMapper.UserMapper;
import model.Exceptions.DupEndorse;
import model.Exceptions.UserNotFound;
import model.User.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersRepo {
    private static final String usersRepoUrlText3 = "https://api.jsonbin.io/b/5cdd4f09dbffad51f8aa9d06";
    private static final String usersRepoUrlText2 = "http://api.myjson.com/bins/16xj3u";
    private static final String usersRepoUrlText = "https://api.myjson.com/bins/171ppi";
    private static UsersRepo singleUsersRepo = null;
    private UsersRepo(){

        this.users = new HashMap<String, User>();
        loginUser = null;
    }
    public static UsersRepo getInstance(){
        if(singleUsersRepo == null)
            singleUsersRepo = new UsersRepo();
        return singleUsersRepo;
    }
    private HashMap<String, User> users;
    private User loginUser;
    public boolean isUserLogin(String userId){
        if(users.get(userId) == null){
            return false;
        }
        else
            return users.get(userId).isLogin();
    }
    public ArrayList<User> getAllUsers() throws SQLException, DupEndorse {
        AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
        return new ArrayList<>(advancedUserMapper.getAllUser());
    }
    public void setLoginUser(User user){
        this.loginUser = user;
    }
    public void setLoginUser(String userId) throws UserNotFound, SQLException, DupEndorse {
        User user = this.getUserById(userId);
        user.login();
        UserMapper um = new UserMapper();
        um.setLogin(user.getId());
    }
    public void logOutAlUsers(){
        if(loginUser != null) {
            loginUser.logout();
            loginUser = null;
        }
    }
    public User getLoginUser() throws SQLException, DupEndorse {
        UserMapper um = new UserMapper();
        AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
        advancedUserMapper.setUserSkillContain(true);
        User user = advancedUserMapper.getUserWithId(um.findLoginUsers().get(0).getId());
        return user;
    }
    public User tempGetLoginUser() throws UserNotFound, SQLException, DupEndorse {
        return getUserById("1");
    }
    public void addUser(User user){
        users.put(user.getId(), user);
    }
    public User getUserById(String id) throws UserNotFound, SQLException, DupEndorse {
        AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
        advancedUserMapper.setBidContain(true);
        advancedUserMapper.setUserSkillContain(true);
        System.out.println("id = " + id);
        User user = advancedUserMapper.getUserWithId(id);
        if(user == null)
            throw new UserNotFound();
        return user;
    }
    public void setRepoInDataBase() throws Exception, DupEndorse {
        UsersRepo usersRepo = UsersRepo.getInstance();

        JSONArray arr = (JSONArray) GetRepo.getHTML(usersRepoUrlText2);
        for(Object obj: arr){
            User user = new User((JSONObject) obj);
            AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
            advancedUserMapper.setUserSkillContain(true);
            advancedUserMapper.setUser(user, (Long)((JSONObject) obj).get("passWordHash"));
        }
    }
}
