package dataLayer.dataMappers.ProjectMapper;

import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import model.Skill.ProjectSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectSkillMapper extends Mapper<ProjectSkill, String> {
    public ProjectSkillMapper() throws SQLException {
        Connection con1 = DBCPDBConnectionPool.getConnection();
        Statement st =
                con1.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(userId TEXT PRIMARY KEY, firstName TEXT," +
//                " firstname TEXT, gpa FLOAT)");
        st.executeUpdate("    Create Table IF NOT EXISTS\n" +
                "                projectSkill (\n" +
                "                        projectId VARCHAR(20) ,\n" +
                "                        skillName VARCHAR(100),\n"+
                "                        point INT ,\n"+
                "                         primary key (projectId, skillName),\n" +

                "                        foreign key (skillName) references skill (skillName),\n"+
                "                        foreign key (projectId) references project (projectId)\n"+

                ")");


        st.close();
        con1.close();
    }

    @Override
    protected String getFindStatement() {
        return "SELECT projectId, skillName, point FROM projectSkill WHERE projectId = ?";
    }

    @Override
    protected ProjectSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    public List<ProjectSkill> findProjectSkills(String projectId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            st.setString(1, projectId.toString());

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
    protected List<ProjectSkill> convertResultSetToListDomainModel(ResultSet rs) throws SQLException{
        List<ProjectSkill> projectSkills = new ArrayList<>();
        while(rs.next()){
            ProjectSkill projectSkill = new ProjectSkill(rs.getString("skillName"), rs.getLong("point"));

            projectSkills.add(projectSkill);
        }
        return projectSkills;
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT projectId, skillName, point FROM projectSkill";
    }

    @Override
    protected String getInsertStatement() {
        return "insert into projectSkill (projectId, skillName, point)\n" +
                " Select ?, ?, ? Where not exists(select * from projectSkill where projectId=? AND skillName = ?)";
    }

    @Override
    public void insertObjectToDB(ProjectSkill object) throws SQLException {
        return;
    }

    public void insertObjectToDBWithId(ProjectSkill projectSkill, String projectId) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, projectId);
            st.setString(2, projectSkill.getName());
            st.setLong(3, projectSkill.getPoint());
            st.setString(4, projectId);
            st.setString(5, projectSkill.getName());
            st.executeUpdate();
        }
        con.close();
    }

}
