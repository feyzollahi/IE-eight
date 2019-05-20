package dataLayer.dataMappers.UserMapper;

import dataLayer.ConnectionPool;
import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import dataLayer.dbConnectionPool.BasicDBConnectionPool;
import model.Skill.UserSkill;
import model.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSkillMapper extends Mapper<UserSkill, String> {
    public UserSkillMapper() throws SQLException {
        Connection con1 = DBCPDBConnectionPool.getConnection();
        Statement st =
                con1.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(userId TEXT PRIMARY KEY, firstName TEXT," +
//                " firstname TEXT, gpa FLOAT)");
        st.executeUpdate("    Create Table IF NOT EXISTS\n" +
                "                userSkill (\n" +
                "                        userId VARCHAR(20) ,\n" +
                "                        skillName VARCHAR(100),\n"+
                "                        foreign key (userId) references user (userId) ,\n"+
                "                        foreign key (skillName) references skill (skillName),\n"+
                "                         primary key (userId, skillName)\n" +
                ")");


        st.close();
        con1.close();
    }
    @Override
    protected String getFindStatement() {
        return "SELECT userId, skillName FROM userSkill WHERE userId = ?";
    }

    protected String getDeleteStatement() {return "Delete From userSkill Where userId = ? AND skillName = ?" ;}
    @Override
    protected UserSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    public List<UserSkill> findUserSkills(String userId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            st.setString(1, userId.toString());

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

    public void deleteUserSkill(String userId, String skillName) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getDeleteStatement())
        ) {
            st.setString(1, userId.toString());
            st.setString(2, skillName.toString());

            ResultSet resultSet;
            try {
                st.executeUpdate();
//                resultSet.next();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete userSkill query.");
                throw ex;
            }
        }
    }
    protected List<UserSkill> convertResultSetToListDomainModel(ResultSet rs) throws SQLException{
        List<UserSkill> userSkills = new ArrayList<>();
        while(rs.next()){
            UserSkill userskill = new UserSkill(rs.getString("skillName"));

            userSkills.add(userskill);
        }
        return userSkills;
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT userId, skillName FROM userSkill";    }

    @Override
    protected String getInsertStatement() {

        return "insert into userSkill (userId, skillName)\n" +
                " Select ?, ? Where not exists(select * from userSkill where userId=? AND skillName = ?)";
    }

    @Override
    public void insertObjectToDB(UserSkill object) throws SQLException {
        return;
    }

    public void insertObjectToDBWithId(UserSkill userSkill, String userId) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, userId);
            st.setString(2, userSkill.getName());
            st.setString(3, userId);
            st.setString(4, userSkill.getName());

            st.executeUpdate();
        }
        con.close();
    }
}
