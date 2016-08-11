/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.model.Course;
import customorm.model.Student;
import customorm.model.Teacher;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 *
 * @author junaid.ahmad
 */
public class TeacherDAOTest {

    private static Teacher teacher;
    private static TeacherDAO mockedTeacherDAO;

    public TeacherDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("...TEACHER_DAO...");
        mockedTeacherDAO = mock(TeacherDAO.class);
        teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("hamza");

        Student s = new Student();
        s.setId(1);
        s.setName("ali");
        s.setAddress("lahore");
        List<Student> sList = new ArrayList<>();
        sList.add(s);
        teacher.setStudents(sList);

        Course c = new Course();
        c.setId(2);
        c.setName("oop");
        List<Course> cList = new ArrayList<>();
        cList.add(c);
        teacher.setCourses(cList);

        when(mockedTeacherDAO.insert(teacher)).thenReturn(1);
        when(mockedTeacherDAO.select(teacher.getId())).thenReturn(teacher);
        when(mockedTeacherDAO.delete(teacher.getId())).thenReturn(1);
        when(mockedTeacherDAO.update(teacher)).thenReturn(1);
    }

    /**
     * Test of insert method, of class TeacherDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        int rowsAffected = mockedTeacherDAO.insert(teacher);
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of delete method, of class TeacherDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int rowsAffected = mockedTeacherDAO.delete(teacher.getId());
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of update method, of class TeacherDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        int rowsAffected = mockedTeacherDAO.update(teacher);
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of select method, of class TeacherDAO.
     */
    @Test
    public void testSelect() {
        System.out.println("select");
        Teacher result = (Teacher) mockedTeacherDAO.select(teacher.getId());

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("hamza", result.getName());
        assertEquals(1, result.getStudents().size());
        assertEquals(1, result.getCourses().size());
    }

}
