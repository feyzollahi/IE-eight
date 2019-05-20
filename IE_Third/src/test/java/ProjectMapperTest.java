import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.ProjectMapper.ProjectMapper;
import dataLayer.dataMappers.SkillMapper.SkillMapper;
import dataLayer.dataMappers.UserMapper.IUserMapper;
import dataLayer.dataMappers.UserMapper.UserMapper;
import model.Project.Project;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataLayer.DBCPDBConnectionPool;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectMapperTest {

    private Project testProject;
    private Project testProject2;
    private ProjectMapper projectMapper;
    private Connection con;

    @Before
    public void setUp() throws SQLException {
        SkillMapper skillMapper = new SkillMapper();
        projectMapper = new ProjectMapper();
        testProject = new Project("123123", "programming", "https://google.com"
                , 25000, 123123414
                , 421312132, "salam aleykom");
        testProject2 = new Project("125125", "programming", "https://google.com"
                , 25000, 123123414
                , 421312132, "salam aleykom");
        con = DBCPDBConnectionPool.getConnection();
        insertProject(testProject);
        insertProject(testProject2);
    }

    private void insertProject(Project testProject)  throws SQLException {
        String sql = "INSERT INTO project (projectId, title, imageUrlText, budget, deadline, creationDate, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, testProject.getId());
            st.setString(2, testProject.getTitle());
            st.setString(3, testProject.getImageUrlText());
            st.setLong(4, testProject.getBudget());
            st.setLong(5, testProject.getDeadline());
            st.setLong(6, testProject.getCreationDate());
            st.setString(7, testProject.getDescription());
            st.executeUpdate();
        }

    }


        @Test
        public void whenGetStudentWithExistID_StudentLoaded() throws SQLException {
            ProjectMapper projectMapper = new ProjectMapper();
            Project u = projectMapper.find("123123");
            Project u2 = projectMapper.find("125125");

            System.out.println("loaded student: " + u);
            Assert.assertTrue("loaded student is not correct", assertStudentsEqual(testProject, u));
            Assert.assertTrue("loaded student is not correct2", assertStudentsEqual(testProject2, u2));
        }


        @After
        public void terminate() throws SQLException {
            deleteStudent();
            con.close();
        }

        private void deleteStudent() throws SQLException {
            PreparedStatement st = con.prepareStatement("DELETE FROM project WHERE projectId = ?");
            st.setString(1, testProject.getId());
            st.executeUpdate();
            st.close();
            PreparedStatement st2 = con.prepareStatement("DELETE FROM project WHERE projectId = ?");
            st2.setString(1, testProject2.getId());
            st2.executeUpdate();
            st2.close();
        }


        public static boolean assertStudentsEqual(Project p1, Project p2) {
            return (p1.getId().equals(p2.getId()));
        }



    }


