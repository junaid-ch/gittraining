/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

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
public class CourseControllerTest {
    
    private static Course course;
    private static CourseController mockedCourseController;
    
    public CourseControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("...COURSE_CONTROLLER...");
        mockedCourseController = mock(CourseController.class);
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

        when(mockedCourseController.add()).thenReturn(1);
        when(mockedCourseController.print()).thenReturn(course);
        when(mockedCourseController.delete()).thenReturn(1);
        when(mockedCourseController.update()).thenReturn(1);
    }

    /**
     * Test of add method, of class TeacherController.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int rowsAffected = mockedCourseController.add();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of delete method, of class TeacherController.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int rowsAffected = mockedCourseController.delete();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of update method, of class TeacherController.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        int rowsAffected = mockedCourseController.update();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of print method, of class TeacherController.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        Course result = mockedCourseController.print();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("c++", result.getName());
        assertEquals(1, result.getStudents().size());
        assertEquals(1, result.getTeachers().size());
    }
    
}
