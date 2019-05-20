package dataLayer.dataMappers.SkillMapper;

import dataLayer.ConnectionPool;
import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import dataLayer.dbConnectionPool.BasicDBConnectionPool;
import model.Project.Project;
import model.Skill.Skill;
import model.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillMapper extends Mapper<Skill, String> {


    public SkillMapper() throws SQLException {
        Connection con1 = DBCPDBConnectionPool.getConnection();
        Statement st =
                con1.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(userId TEXT PRIMARY KEY, firstName TEXT," +
//                " firstname TEXT, gpa FLOAT)");
        st.executeUpdate("    Create Table IF NOT EXISTS\n" +
                "                skill (\n" +
                "                        skillName VARCHAR(200) PRIMARY KEY\n" +
                "                )");

        st.close();
        con1.close();

    }


    protected String getFindAllStatement() {
        return "SELECT " + "skillName" +
                " FROM skill";
    }

    @Override
    protected String getInsertStatement() {

        return "insert into skill (skillName)\n" +
                " Select ? Where not exists(select * from skill where skillName=?)";

    }

    @Override
    public void insertObjectToDB(Skill object) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, object.getName());
            st.setString(2, object.getName());

            st.executeUpdate();
        }
        con.close();
    }


    @Override
    protected String getFindStatement() {
        return null;
    }

    @Override
    protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Skill(rs.getString("skillName"));
    }

    protected List<Skill> convertResultSetToListDomainModel(ResultSet rs) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        while (rs.next()) {
            Skill skill = new Skill(rs.getString("skillName"));

            skills.add(skill);

        }
        return skills;
    }


}