//package dataLayer.dataMappers.student;
//
//import dataLayer.ConnectionPool;
//import dataLayer.DBCPDBConnectionPool;
//import dataLayer.dataMappers.Mapper;
//import dataLayer.dbConnectionPool.BasicDBConnectionPool;
//import domain.Student;
//import model.User.User;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//
//public class StudentMapper extends Mapper<Student, Integer> implements IStudentMapper {
//
//    private static final String COLUMNS = " id, lastname, firstname, gpa ";
//
//
//    public StudentMapper() throws SQLException {
//        Connection con1 = DBCPDBConnectionPool.getConnection();
//        BasicDBConnectionPool conPool = ConnectionPool.getInstance();
//        Connection con = conPool.get();
//        Statement st =
//                con.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "student" + " " + "(id TEXT PRIMARY KEY, lastname TEXT," +
//                " firstname TEXT, gpa FLOAT)");
//
//        st.close();
//        con.close();
//
//    }
//
//    @Override
//    protected String getFindStatement() {
//        return "SELECT " + COLUMNS +
//                " FROM student" +
//                " WHERE id = ?";
//    }
//
//    @Override
//    public T find(I userId){
//
//    }
//
//    @Override
//    protected Student convertResultSetToDomainModel(ResultSet rs) throws SQLException {
//        return  new Student(
//                rs.getInt(1),
//                rs.getString(3),
//                rs.getString(2),
//                rs.getFloat(4)
//        );
//    }
//
//    @Override
//    public List<Student> findWithGPA(float minGPA, float maxGPA) {
//        //todo: implement
//        return null;
//    }
//}