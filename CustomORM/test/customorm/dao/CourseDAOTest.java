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
public class CourseDAOTest {

    private static Course course;
    private static CourseDAO mockedCourseDAO;

    public CourseDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("...COURSE_DAO...");
        mockedCourseDAO = mock(CourseDAO.class);
        course = new Course();
        course.setId(1);
        course.setName("c++");

        Student s = new Student();
        s.setId(1);
        s.setName("ali");
        s.setAddress("lahore");
        List<Student> sList = new ArrayList<>();
        sList.add(s);
        course.setStudents(sList);

        Teacher t = new Teacher();
        t.setId(1);
        t.setName("hamza");
        List<Teacher> tList = new ArrayList<>();
        tList.add(t);
        course.setTeachers(tList);
        
        when(mockedCourseDAO.insert(course)).thenReturn(1);
        when(mockedCourseDAO.select(course.getId())).thenReturn(course);
        when(mockedCourseDAO.delete(course.getId())).thenReturn(1);
        when(mockedCourseDAO.update(course)).thenReturn(1);
    }

    /**
     * Test of insert method, of class CourseDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        int rowsAffected = mockedCourseDAO.insert(course);
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of delete method, of class CourseDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int rowsAffected = mockedCourseDAO.delete(course.getId());
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of update method, of class CourseDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        int rowsAffected = mockedCourseDAO.update(course);
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of select method, of class CourseDAO.
     */
    @Test
    public void testSelect() {
        System.out.println("select");
        Course result = (Course) mockedCourseDAO.select(course.getId());

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("c++", result.getName());
        assertEquals(1, result.getStudents().size());
        assertEquals(1, result.getTeachers().size());
    }

}
