package dataLayer.dataMappers.UserMapper;

import dataLayer.ConnectionPool;
import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import dataLayer.dbConnectionPool.BasicDBConnectionPool;
import model.Repo.GetRepo;
import model.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserMapper extends Mapper<User, java.lang.String> implements IUserMapper {
    private static final java.lang.String COLUMNS = " u.userId, u.firstName, u.lastName, u.userName," +
            " u.jobTitle, u.bio, u.isLogin, u.imageUrlText";
    private static final String CULUMNS_USER_SKILL = " name, userId";
    private static final String CULUMNS_USER_ENDORSE = " name, endorserUserId, endorsedUserId";
    private static final String CULUMNS_USER_BID = " userId, projectId, bidAmount";


    public UserMapper() throws SQLException {
        Connection con1 = DBCPDBConnectionPool.getConnection();
        Statement st =
                con1.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(userId TEXT PRIMARY KEY, firstName TEXT," +
//                " firstname TEXT, gpa FLOAT)");
        st.executeUpdate("    Create Table IF NOT EXISTS\n" +
                "                user (\n" +
                "                        userId VARCHAR(20) PRIMARY KEY,\n" +
                "                        firstName VARCHAR(100),\n" +
                "                        lastName VARCHAR(100),\n" +
                "                        userName VARCHAR(100) UNIQUE ,\n" +
                "                        passWordHash INT,\n" +
                "                        isLogin BOOLEAN,\n" +
                "                        bio VARCHAR(10000),\n" +
                "                        jobTitle VARCHAR(300),\n" +
                "                        imageUrlText VARCHAR(300)\n" +
                "                )");

        st.close();
        con1.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM user u" +
                " WHERE u.userId = ? ";
    }
    protected String getPassWordHashStatement(){
        return "SELECT u.passWordHash" +
                " FROM user u" +
                " WHERE u.userName = ? ";
    }
    protected String getFindWithUserNameStatement(){
        return "SELECT " + COLUMNS +
                " FROM user u" +
                " WHERE u.userName = ? ";
    }
    protected String getFindWithUserNamePassWordStatement(){
        return "SELECT " + COLUMNS +
                " FROM user u" +
                " WHERE u.userName = ? AND u.passWordHash = ? ";
    }
    private String getFindWithUserNameCountStatement() {
        return "SELECT count(*) As total" +
                " FROM user u" +
                " WHERE u.userName = ? ";
    }
    protected String getFindSkillsStatement(){
        return "SELECT " + CULUMNS_USER_SKILL +
                " FROM userSkill" +
                " WHERE u.userId = ? ";
    }

    protected String getFindََUserWithPrefixFirstName(String prefix){
        return "SELECT " + COLUMNS +
                " FROM user u" +
                " WHERE u.firstName LIKE \"" + prefix + "%\"";
    }
    protected String getFindََUserWithPrefixLastName(String prefix){
        return "SELECT " + COLUMNS +
                " FROM user u" +
                " WHERE u.lastName LIKE \"" + prefix + "%\"";
    }
    protected String getFindََLoginUserStatement(){
        return "SELECT " + COLUMNS +
                " FROM user u" +
                " WHERE u.isLogin = 1 ";
    }
    protected String getUserCountStatement(){
        return "SELECT " + "Count(*) As total" +
                " FROM user u";
    }
    @Override
    public User find(String userId) throws SQLException {
        User result = loadedMap.get(userId);
        if (result != null)
            return result;

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            st.setString(1, userId.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
    public User findWithUserName(String userName) throws SQLException {

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindWithUserNameStatement())
        ) {
            st.setString(1, userName);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
    public int getPassWordHash(String userName) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getPassWordHashStatement())
        ) {
            st.setString(1, userName);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return resultSet.getInt("passWordHash");
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
    public boolean checkPassWord(String userName, String passWord) throws SQLException {
        int passWordHash = getPassWordHash(userName);
        System.out.println("passWordHashInDB: " + passWordHash);
        System.out.println("passWordHashUserSend: " + passWord.hashCode());
        System.out.println("passWordUserSend: " + passWord);
        return passWordHash == passWord.hashCode();
    }
    public int getUserTableSize() throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getUserCountStatement())
        ) {

            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return resultSet.getInt("total");
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
    public User findWithUserNamePassWord(String userName, String passWord) throws SQLException {

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindWithUserNamePassWordStatement())
        ) {
            st.setString(1, userName);
            st.setInt(2, passWord.hashCode());
            ResultSet resultSet;
            GetRepo.print("now!!");
            try {
                resultSet = st.executeQuery();
                System.out.println(resultSet.getString(1));
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
    public boolean isUserExistWithUserNamePassWord(String userName) throws SQLException {

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindWithUserNameCountStatement())
        ) {
            st.setString(1, userName);
            ResultSet resultSet;
            GetRepo.print("now!!");
            try {
                resultSet = st.executeQuery();
                GetRepo.print(resultSet.getInt("total") + " total");
                return resultSet.getInt("total") != 0;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }



    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        if(rs.getString("userId") == null){
            return null;
        }
        return  new User(rs.getString("userId"), rs.getString("firstName"),
                rs.getString("lastName"),rs.getString("userName"),
                 rs.getString("jobTitle"),
                rs.getString("bio"), rs.getString("imageUrlText"),
                rs.getBoolean("isLogin"));
    }

    protected List<User> convertResultSetToListDomainModel(ResultSet rs) throws SQLException{
        List<User> users = new ArrayList<>();
        while(rs.next()){
            User user = new User(rs.getString("userId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("userName"),
                    rs.getString("jobTitle"),
                    rs.getString("bio"),
                    rs.getString("imageUrlText")
                    , rs.getBoolean("isLogin"));
            users.add(user);

        }
        return users;
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT " + COLUMNS +
                " FROM user u";
    }

    @Override
    protected String getInsertStatement() {

        return "insert into user (userId, firstName, lastName, userName, passWordHash, jobTitle, bio, isLogin, imageUrlText)\n" +
                " Select ?, ?, ?, ?, ?, ?, ?, ?, ? Where not exists(select * from user where userId=?)";
    }

    protected String getInsertPassWordStatement(){
        return "update user SET passWordHash = ? where userId = ?";
    }
    protected  String getSetLoginStatement(){
        return "update user SET isLogin = 1 Where userId = ?";
    }
    public void setLogin(String userId) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getSetLoginStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, userId);

            st.executeUpdate();
        }
        con.close();
    }
    @Override
    public void insertObjectToDB(User object) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertStatement();
        System.out.println("id = " + object.getId());
        System.out.println("userName2 = " + object.getUserName());
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, object.getId());
            st.setString(2, object.getFirstName());
            st.setString(3, object.getLastName());
            st.setString(4, object.getUserName());
            st.setInt(5, new Random().nextInt());
            st.setString(6, object.getJobTitle());
            st.setString(7, object.getBio());
            st.setBoolean(8, object.isLogin());
            st.setString(9, object.getProfilePictureURLText());
            st.setString(10, object.getId());
            st.executeUpdate();
        }
        con.close();
        System.out.println(this.find("1").getBio());

        System.out.println(this.find("1").getUserName());
    }

    public void setPassWordHash(String userId, int passWordHash) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertPassWordStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, passWordHash);
            st.setString(2, userId);
            st.executeUpdate();
        }
        con.close();
    }


    @Override
    public List<model.User.User> findWithFirstName(java.lang.String prefixUserName) throws SQLException {


        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindََUserWithPrefixFirstName(prefixUserName))
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
//                resultSet.next();
                return convertResultSetToListDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }


    @Override
    public List<model.User.User> findWithLastName(java.lang.String prefixLastName) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindََUserWithPrefixLastName(prefixLastName))
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
//                resultSet.next();
                return convertResultSetToListDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    @Override
    public List<model.User.User> findWithJobTitle(java.lang.String prefixJobTitle) throws  SQLException {
        return null;
    }

    @Override
    public List<model.User.User> findLoginUsers() throws SQLException{
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindََLoginUserStatement())
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
//                resultSet.next();
                return convertResultSetToListDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }



}