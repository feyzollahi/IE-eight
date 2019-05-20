package dataLayer.dataMappers.UserMapper;

import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import javafx.util.Pair;
import model.Skill.UserSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EndorseMapper extends Mapper<EndorseInfo, String> {
    public EndorseMapper() throws SQLException {
        Connection con1 = DBCPDBConnectionPool.getConnection();
        Statement st =
                con1.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(userId TEXT PRIMARY KEY, firstName TEXT," +
//                " firstname TEXT, gpa FLOAT)");
        st.executeUpdate("    Create Table IF NOT EXISTS\n" +
                "                endorse (\n" +
                "                        endorserId VARCHAR(20) ,\n" +
                "                        endorsedId VARCHAR(20),\n"+
                "                        skillName VARCHAR(100),\n"+
                "                        foreign key (endorserId) references user (userId) ,\n"+
                "                        foreign key (endorsedId) references user (userId) ,\n"+
                "                        foreign key (skillName) references skill (skillName),\n"+
                "                         primary key (endorserId, endorsedId, skillName)\n" +
                ")");


        st.close();
        con1.close();
    }
    @Override
    protected String getFindStatement() {
        return "SELECT endorserId, endorsedId, skillName FROM endorse WHERE endorsedId = ?";
    }
    protected String getDeleteStatement(){
        return "Delete From endorse Where endorsedId = ? AND skillName = ?";
    }
    @Override
    protected EndorseInfo convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    public void deleteEndorseWithSkill(String endorsedId, String skillName) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getDeleteStatement())
        ) {
            st.setString(1, endorsedId.toString());
            st.setString(2, skillName);
            ResultSet resultSet;
            try {
                st.executeUpdate();
//                resultSet.next();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }
    public List<EndorseInfo> findEndorsers(String userId) throws SQLException {
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

    protected List<EndorseInfo> convertResultSetToListDomainModel(ResultSet rs) throws SQLException{
        List<EndorseInfo> endorseInfos = new ArrayList<EndorseInfo>();

        while(rs.next()){
            EndorseInfo endorseInfo = new EndorseInfo(rs.getString("endorserId")
                    ,rs.getString("endorsedId"),rs.getString("skillName"));

            endorseInfos.add(endorseInfo);
        }
        return endorseInfos;
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT endorserId, endorsedId, skillName FROM endorse";
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO endorse (endorserId, endorsedId, skillName) VALUES (?, ?, ?)";
    }

    @Override
    public void insertObjectToDB(EndorseInfo object) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, object.getEndorserId());
            st.setString(2, object.getEndorsedId());
            st.setString(3, object.getSkillName());
            st.executeUpdate();
        }
        con.close();
    }





//    public void insertObjectToDBWithId(UserSkill userSkill, String userId) throws SQLException {
//        Connection con = DBCPDBConnectionPool.getConnection();
//        String sql = getInsertStatement();
//        try (PreparedStatement st = con.prepareStatement(sql)) {
//            st.setString(1, userId);
//            st.setString(2, userSkill.getName());
//            st.setString(3, userSkill.getName());
//            st.executeUpdate();
//        }
//        con.close();
//    }
}
