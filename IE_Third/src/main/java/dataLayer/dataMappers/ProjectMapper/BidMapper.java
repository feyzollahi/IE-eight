package dataLayer.dataMappers.ProjectMapper;

import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import model.Bid.Bid;
import model.Skill.UserSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidMapper extends Mapper<Bid, String> {
    public BidMapper() throws SQLException {
        Connection con1 = DBCPDBConnectionPool.getConnection();
        Statement st =
                con1.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(userId TEXT PRIMARY KEY, firstName TEXT," +
//                " firstname TEXT, gpa FLOAT)");
        st.executeUpdate("    Create Table IF NOT EXISTS\n" +
                "                bid (\n" +
                "                        userId VARCHAR(20) ,\n" +
                "                        projectId VARCHAR(20),\n"+
                "                        bidAmount INTEGER ,\n"+
                "                        foreign key (userId) references user (userId) ,\n"+
                "                        foreign key (projectId) references project (projectId),\n"+
                "                         primary key (userId, projectId)\n" +
                ")");


        st.close();
        con1.close();
    }
    @Override
    protected String getFindStatement() {
        return "SELECT userId, skillName FROM userSkill WHERE userId = ?";
    }

    protected  String getFindWithUserIdStatement(){
        return "Select userId, projectId, bidAmount FROM bid WHERE userId = ?";
    }

    protected  String getFindWithProjectIdStatement(){
        return "Select userId, projectId, bidAmount FROM bid WHERE projectId = ?";
    }

    @Override
    protected Bid convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    public List<Bid> findBidWithUserId(String userId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindWithUserIdStatement())
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

    public List<Bid> findBidWithProjectId(String projectId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindWithProjectIdStatement())
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
    protected List<Bid> convertResultSetToListDomainModel(ResultSet rs) throws SQLException{
        List<Bid> bids = new ArrayList<>();
        while(rs.next()){
            Bid bid = new Bid(rs.getString("userId"),
                    rs.getString("projectId"), rs.getInt("bidAmount"));

            bids.add(bid);
        }
        return bids;
    }

    @Override
    protected String getFindAllStatement() {
        return "SELECT userId, skillName FROM userSkill";    }

    @Override
    protected String getInsertStatement() {
        return "insert into bid (userId, projectId, bidAmount)\n" +
                " Select ?, ?, ? Where not exists(select * from bid where userId=? AND projectId=?)";
    }


    @Override
    public void insertObjectToDB(Bid object) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = getInsertStatement();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            String projectId, userId;
            if(object.getBiddingUser() != null) {
                st.setString(1, object.getBiddingUser().getId());
                userId = object.getBiddingUser().getId();
            }
            else {
                st.setString(1, object.getUserId());
                userId = object.getBiddingUser().getId();
            }
            if(object.getProject() != null){
                st.setString(2, object.getProject().getId());
                projectId = object.getProject().getId();
            }
            else {
                st.setString(2, object.getProjectId());
                projectId = object.getProjectId();
            }
            st.setInt(3, object.getBidAmount());
            st.setString(4, userId);
            st.setString(5, projectId);
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
//            st.executeUpdate();
//        }
//        con.close();
//    }
}
