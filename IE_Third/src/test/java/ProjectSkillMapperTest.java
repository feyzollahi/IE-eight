import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.ProjectMapper.ProjectMapper;
import dataLayer.dataMappers.ProjectMapper.ProjectSkillMapper;
import model.Project.Project;
import model.Skill.ProjectSkill;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class ProjectSkillMapperTest {

    private Project testProject;
    private ProjectSkill projectSkill1;
    private ProjectSkill projectSkill2;

    private ProjectMapper projectMapper;
    private ProjectSkillMapper projectSkillMapper;
    private Connection con;

    @Before
    public void setUp() throws SQLException {
        projectMapper = new ProjectMapper();
        projectSkillMapper = new ProjectSkillMapper();
        testProject = new Project("123123", "programming", "https://google.com"
                , 25000, 123123414
                , 421312132, "salam aleykom");
        projectSkill1 = new ProjectSkill("CSS", 5);
        projectSkill2 = new ProjectSkill("HTML", 3);
        testProject.addSkill(projectSkill1);
        testProject.addSkill(projectSkill2);
        con = DBCPDBConnectionPool.getConnection();
        insertProject(testProject);
        insertProjectSkill(projectSkill1);
        insertProjectSkill(projectSkill2);

    }

    private void insertProjectSkill(ProjectSkill projectSkill1) throws SQLException {
        String sql = "INSERT INTO projectSkill (projectId, skillName, point) VALUES (?, ?, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, testProject.getId());
            st.setString(2, projectSkill1.getName());
            st.setLong(3, projectSkill1.getPoint());
            st.executeUpdate();
        }
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
        ProjectSkillMapper psm = new ProjectSkillMapper();
        List<ProjectSkill> projectSkillList = psm.findProjectSkills("123123");
        u.addSkill(projectSkillList.get(0));
        u.addSkill(projectSkillList.get(1));
        System.out.println("loaded student: " + u);
        Assert.assertTrue("loaded student is not correct", assertStudentsEqual(testProject, u));
    }


    @After
    public void terminate() throws SQLException {
        deleteStudent();
        deleteProjectSkill();
        con.close();
    }

    private void deleteProjectSkill() throws SQLException {
        PreparedStatement st = con.prepareStatement("DELETE FROM projectSkill WHERE projectId = ?");
        st.setString(1, testProject.getId());
        st.executeUpdate();
        st.close();
    }

    private void deleteStudent() throws SQLException {
        PreparedStatement st = con.prepareStatement("DELETE FROM project WHERE projectId = ?");
        st.setString(1, testProject.getId());
        st.executeUpdate();
        st.close();

    }


    public static boolean assertStudentsEqual(Project p1, Project p2) {
        return (p1.getId().equals(p2.getId()) && p1.getSkill("CSS") != null
        && p1.getSkill("CSS").getPoint() == p2.getSkill("CSS").getPoint() && p1.getSkill("HTML") != null
                && p1.getSkill("HTML").getPoint() == p2.getSkill("HTML").getPoint());
    }



}


