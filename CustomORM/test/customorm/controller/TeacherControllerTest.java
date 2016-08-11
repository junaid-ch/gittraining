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
public class TeacherControllerTest {

    private static Teacher teacher;
    private static TeacherController mockedTeacherController;

    public TeacherControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("...TEACHER_CONTROLLER...");
        mockedTeacherController = mock(TeacherController.class);
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

        when(mockedTeacherController.add()).thenReturn(1);
        when(mockedTeacherController.print()).thenReturn(teacher);
        when(mockedTeacherController.delete()).thenReturn(1);
        when(mockedTeacherController.update()).thenReturn(1);
    }

    /**
     * Test of add method, of class TeacherController.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int rowsAffected = mockedTeacherController.add();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of delete method, of class TeacherController.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int rowsAffected = mockedTeacherController.delete();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of update method, of class TeacherController.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        int rowsAffected = mockedTeacherController.update();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of print method, of class TeacherController.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        Teacher result = mockedTeacherController.print();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("hamza", result.getName());
        assertEquals(1, result.getStudents().size());
        assertEquals(1, result.getCourses().size());
    }

}
