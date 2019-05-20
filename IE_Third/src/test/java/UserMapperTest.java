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
//    import dataLayer.DBCPDBConnectionPool;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class UserMapperTest {
//
//        private User testUser;
//        private IUserMapper userMapper;
//        private Connection con;
//
//        @Before
//        public void setUp() throws SQLException {
//            userMapper = new UserMapper();
//            testUser = new User("10", "mahdi", "feyzollahi", "programmer", "bad slave for god", false);
//            con = DBCPDBConnectionPool.getConnection();
//            insertUser();
//        }
//
//
//        // not implemented in mapper for this sample.
//        private void insertUser() throws SQLException {
//            String sql = "INSERT INTO user (userId, firstName, lastName, jobTitle, bio, isLogin) VALUES (?, ?, ?, ?, ?, ?)";
//
//            try (PreparedStatement st = con.prepareStatement(sql)) {
//                st.setString(1, testUser.getId());
//                st.setString(2, testUser.getFirstName());
//                st.setString(3, testUser.getLastName());
//                st.setString(4, testUser.getJobTitle());
//                st.setString(5, testUser.getBio());
//                st.setBoolean(6, testUser.isLogin());
//                st.executeUpdate();
//            }
//
//        }
//
//        @Test
//        public void whenGetStudentWithExistID_StudentLoaded() throws SQLException {
//            UserMapper um = new UserMapper();
//            User u = um.find("10");
//            System.out.println("loaded student: " + u);
//            Assert.assertTrue("loaded student is not correct", assertStudentsEqual(testUser, u));
//        }
//
//
//        @After
//        public void terminate() throws SQLException {
//            deleteStudent();
//            con.close();
//        }
//
//        private void deleteStudent() throws SQLException {
//            PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE userId = ?");
//            st.setString(1, testUser.getId());
//            st.executeUpdate();
//            st.close();
//        }
//
//
//        public static boolean assertStudentsEqual(User u1, User u2) {
//            return (u1.getFirstName().equals(u2.getFirstName()) &&
//                    u1.getLastName().equals(u2.getLastName()) &&
//                    u1.getId().equals(u2.getId())&&
//                    u1.getBio().equals(u2.getBio()));
//        }
//
//
//
//}
