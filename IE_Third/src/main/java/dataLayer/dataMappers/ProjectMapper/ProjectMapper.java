package dataLayer.dataMappers.ProjectMapper;

import dataLayer.ConnectionPool;
import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import dataLayer.dataMappers.UserMapper.AdvancedUserMapper;
import dataLayer.dataMappers.UserMapper.UserMapper;
import dataLayer.dbConnectionPool.BasicDBConnectionPool;
import model.Exceptions.DupEndorse;
import model.Project.Project;
import model.Repo.GetRepo;
import model.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectMapper extends Mapper<Project, java.lang.String>  {
    private static final java.lang.String COLUMNS = " p.projectId, p.title, p.imageUrlText," +
            " p.budget, p.deadline, p.creationDate, p.description, p.winnerUserId";
    private static final String CULUMNS_PROJECT_SKILL = " projectId, skillName, point ";
    private static final String CULUMNS_PROJECT_BID = " userId, projectId, bidAmount";


    public ProjectMapper() throws SQLException {
        Connection con1 = DBCPDBConnectionPool.getConnection();
        Statement st =
                con1.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(userId TEXT PRIMARY KEY, firstName TEXT," +
//                " firstname TEXT, gpa FLOAT)");
        st.executeUpdate("    Create Table IF NOT EXISTS\n" +
                "                project (\n" +
                "                        projectId VARCHAR(20) PRIMARY KEY,\n" +
                "                        title VARCHAR(100),\n" +
                "                        imageUrlText VARCHAR(100),\n" +
                "                        budget BIGINT,\n" +
                "                        deadline BIGINT,\n" +
                "                        description VARCHAR(10000),\n" +
                "                        winnerUserId VARCHAR(20),\n" +
                "                        creationDate BIGINT,\n" +
                "                        foreign key (winnerUserId) references user (userId) \n" +
                "                )");

        st.close();
        con1.close();

    }
    protected String getSearchStatement(String searchVal){
        return "SELECT * FROM project WHERE description LIKE '%" +searchVal + "%' or title LIKE '%" + searchVal +"%'" ;
    }
    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM project p" +
                " WHERE p.projectId = ? ";
    }
    protected String getFindSkillsStatement(){
        return "SELECT " + CULUMNS_PROJECT_SKILL +
                " FROM projectSkill" +
                " WHERE projectId = ? ";
    }

    protected String getFindProjectTitleIncludeStatement(String word){
        return "SELECT " + COLUMNS +
                " FROM project p" +
                " WHERE p.title LIKE \"%" + word + "%\"";
    }
    protected String getFindProjectDescriptionIncludeStatement(String word){
        return "SELECT " + COLUMNS +
                " FROM project p" +
                " WHERE p.description LIKE \"%" + word + "%\"";
    }

    @Override
    public Project find(String projectId) throws SQLException {
        Project result = loadedMap.get(projectId);
        if (result != null)
            return result;

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            st.setString(1, projectId.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                return null;
            }
        }
    }

    @Override
    protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        if(rs.getString("projectId") == null)
            return null;
        Project project = new Project(rs.getString("projectId"), rs.getString("title"),
                rs.getString("imageUrlText"), rs.getLong("budget"),
                rs.getLong("deadline"), rs.getLong("creationDate"),
                rs.getString("description"));
        if(rs.getString("winnerUserId") != null) {
            AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
            advancedUserMapper.setUserSkillContain(true);
            advancedUserMapper.setBidContain(true);
            try {
                project.setWinnerUser(advancedUserMapper.getUserWithId(rs.getString("winnerUserId")));
            } catch (DupEndorse dupEndorse) {
                dupEndorse.printStackTrace();
            }

        }
        return project;
    }

    protected List<Project> convertResultSetToListDomainModel(ResultSet rs) throws SQLException{
        List<Project> projects = new ArrayList<>();
        while(rs.next()){
            Project project = new Project(rs.getString("projectId"), rs.getString("title"),
                    rs.getString("imageUrlText"), rs.getLong("budget"),
                    rs.getLong("deadline"), rs.getLong("creationDate"),
                    rs.getString("description"));
            if(rs.getString("winnerUserId") != null){
                AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
                advancedUserMapper.setUserSkillContain(true);
                advancedUserMapper.setBidContain(true);
                try {
                    project.setWinnerUser(advancedUserMapper.getUserWithId(rs.getString("winnerUserId")));
                } catch (DupEndorse dupEndorse) {
                    dupEndorse.printStackTrace();
                }
            }

            projects.add(project);

        }
        return projects;
    }


    @Override
    protected String getFindAllStatement() {
        return "SELECT " + COLUMNS +
                " FROM project p";
    }

    @Override
    protected String getInsertStatement() {

        return "insert into project (projectId, title, imageUrlText, budget, deadline, creationDate, description, winnerUserId)\n" +
                " Select ?, ?, ?, ?, ?, ?, ?, ? Where not exists(select * from project where projectId=?)";
    }
    protected String getUpdateWinnerUserStatement(){
        return "update project SET project.winnerUserId = ? where projectId = ?  ";
    }

    public void setWinnerUser(String userId, String projectId) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getUpdateWinnerUserStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, userId);
            st.setString(2, projectId);
            st.executeUpdate();
        }
        con.close();
    }
    @Override
    public void insertObjectToDB(Project object) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, object.getId());
            st.setString(2, object.getTitle());
            st.setString(3, object.getImageUrlText());
            st.setLong(4, object.getBudget());
            st.setLong(5, object.getDeadline());
            st.setLong(6, object.getCreationDate());
            st.setString(7, object.getDescription());
            if(object.getWinnerUser() == null)
                st.setString(8, null);
            else
                st.setString(8, object.getWinnerUser().getId());
            st.setString(9, object.getId());
            st.executeUpdate();
        }
        con.close();

    }

    public List<Project> findWithSearchVal(java.lang.String searchVal) throws SQLException {


        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getSearchStatement(searchVal))
        ) {
            GetRepo.print(getSearchStatement(searchVal));
            GetRepo.print("22");
            ResultSet resultSet;
            try {
                GetRepo.print("23");
                resultSet = st.executeQuery();
//                resultSet.next();
                GetRepo.print("24");

                return convertResultSetToListDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
    public List<Project> findWithTitle(java.lang.String prefixTitle) throws SQLException {


        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindProjectTitleIncludeStatement(prefixTitle))
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



    public List<Project> findWithDescription(java.lang.String word) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindProjectDescriptionIncludeStatement(word))
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