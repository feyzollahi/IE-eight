//import dataLayer.DBCPDBConnectionPool;
//import dataLayer.dataMappers.UserMapper.IUserMapper;
//import dataLayer.dataMappers.UserMapper.UserMapper;
//import model.User.User;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
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
//public class UserMapperListTest {
//
//    private User testUser;
//    private User testUser2;
//    private IUserMapper userMapper;
//    private Connection con;
//
//    @Before
//    public void setUp() throws SQLException {
//        userMapper = new UserMapper();
//        testUser = new User("10", "mahdi", "feyzollahi", "programmer", "bad slave for god", false);
//        testUser2 = new User("11", "mahdieh", "feyzollahi", "programmer", "bad slave for god", false);
//
//        con = DBCPDBConnectionPool.getConnection();
//        insertUser(testUser);
//        insertUser(testUser2);
//
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
//    public void whenGetStudentWithExistID_StudentLoaded() throws SQLException {
//        UserMapper um = new UserMapper();
//        List<User> u = um.findWithFirstName("mah");
//
//        Assert.assertTrue("loaded student is not correct", assertStudentsEqual(testUser, u.get(0)));
//        Assert.assertTrue("loaded student is not correct2", assertStudentsEqual(testUser2, u.get(1)));
//
//    }
//
//
//    @After
//    public void terminate() throws SQLException {
//        delete();
//        con.close();
//    }
//
//    private void delete() throws SQLException {
//        PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE userId=\"10\"");
//        PreparedStatement st1 = con.prepareStatement("DELETE FROM user WHERE userId=\"10\"");
//        PreparedStatement st2 = con.prepareStatement("DELETE FROM user WHERE userId=\"10\"");
//
//        st.executeUpdate();
//        st.close();
//    }
//
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
//                u1.getBio().equals(u2.getBio()));
//    }
//
//
//
//}
