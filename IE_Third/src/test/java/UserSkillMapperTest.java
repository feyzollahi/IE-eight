//import dataLayer.DBCPDBConnectionPool;
//import dataLayer.dataMappers.UserMapper.IUserMapper;
//import dataLayer.dataMappers.UserMapper.UserMapper;
//import dataLayer.dataMappers.UserMapper.UserSkillMapper;
//import model.Skill.UserSkill;
//import model.User.User;
//import org.junit.*;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import dataLayer.DBCPDBConnectionPool;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//
//public class UserSkillMapperTest {
//
//    private UserSkill u1;
//    private UserSkill u2;
//    private User u;
//    private Connection con;
//    private UserSkillMapper usm;
//
//    @Before
//    public void setUp() throws SQLException {
//        usm = new UserSkillMapper();
//        u = new User("10", "mahdi", "feyzollahi", "programmer", "bad slave for god", false);
//        u1 = new UserSkill("CSS");
//        u2 = new UserSkill("HTML");
//        u.addSkill(u1);
//        u.addSkill(u2);
//
//        con = DBCPDBConnectionPool.getConnection();
//        insertUser(u);
//        insertUserSkill(u1, u.getId());
//        insertUserSkill(u2, u.getId());
//
//    }
//
//    private void insertUserSkill(UserSkill u2, String userId) throws SQLException {
//        String sql = "INSERT INTO userSkill (userId, skillName) VALUES (?, ?)";
//
//        try (PreparedStatement st = con.prepareStatement(sql)) {
//            st.setString(1, userId);
//            st.setString(2, u2.getName());
//
//            st.executeUpdate();
//        }
//    }
//
//
//    // not implemented in mapper for this sample.
//    private void insertUser(User testUser) throws SQLException {
//        String sql = "INSERT INTO user (userId, firstName, lastName, jobTitle, bio, isLogin) VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (PreparedStatement st = con.prepareStatement(sql)) {
//            st.setString(1, testUser.getId());
//            st.setString(2, testUser.getFirstName());
//            st.setString(3, testUser.getLastName());
//            st.setString(4, testUser.getJobTitle());
//            st.setString(5, testUser.getBio());
//            st.setBoolean(6, testUser.isLogin());
//            st.executeUpdate();
//        }
//
//    }
//
//    @Test
//    @Ignore
//    public void whenGetStudentWithExistID_StudentLoaded() throws SQLException {
//        UserSkillMapper usm = new UserSkillMapper();
//        UserMapper um = new UserMapper();
//        User user = um.find(u.getId());
//        List<UserSkill> us = usm.findUserSkills(user.getId());
//        user.addSkill(us.get(0));
//        user.addSkill(us.get(1));
//        Assert.assertTrue("loaded student is not correct", assertStudentsEqual(user, u));
//
//    }
//
//
//    @After
//    public void terminate() throws SQLException {
//        delete();
//        con.close();
//    }
//    private void delete() throws SQLException {
//        PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE userId=\"10\"");
//        PreparedStatement st1 = con.prepareStatement("DELETE FROM userSkill WHERE userId=\"10\"");
//        PreparedStatement st2 = con.prepareStatement("DELETE FROM userSkill WHERE userId=\"10\"");
//
//        st.executeUpdate();
//        st1.executeUpdate();
//        st2.executeUpdate();
//        st.close();
//        st1.close();
//        st2.close();
//    }
//    private void deleteStudent() throws SQLException {
//        PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE firstName Like \"mah%\"");
//        st.executeUpdate();
//        st.close();
//    }
//
//
//    public static boolean assertStudentsEqual(User u1, User u2) {
//        return (u1.getFirstName().equals(u2.getFirstName()) &&
//                u1.getLastName().equals(u2.getLastName()) &&
//                u1.getId().equals(u2.getId())&&
//                u1.getBio().equals(u2.getBio()) &&
//                u1.getSkills().get("CSS") != null &&
//                u2.getSkills().get("CSS") != null &&
//                u1.getSkills().get("HTML") != null &&
//                u2.getSkills().get("HTML") != null);
//    }
//
//
//
//}
