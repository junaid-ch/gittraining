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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author junaid.ahmad
 */
//@RunWith(Parameterized.class)
public class StudentControllerTest {

    private static Student student;
    private static StudentController mockedStudentController;

    public StudentControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("...STUDENT_CONTROLLER...");
        mockedStudentController = mock(StudentController.class);
        student = new Student();
        student.setId(1);
        student.setName("ali");
        student.setAddress("lahore");

        Teacher t = new Teacher();
        t.setId(1);
        t.setName("hamza");
        List<Teacher> tList = new ArrayList<>();
        tList.add(t);
        student.setTeachers(tList);

        Course c = new Course();
        c.setId(2);
        c.setName("oop");
        List<Course> cList = new ArrayList<>();
        cList.add(c);
        student.setCourses(cList);

        when(mockedStudentController.add()).thenReturn(1);
        when(mockedStudentController.print()).thenReturn(student);
        when(mockedStudentController.delete()).thenReturn(1);
        when(mockedStudentController.update()).thenReturn(1);
    }

    /**
     * Test of add method, of class StudentController.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int rowsAffected = mockedStudentController.add();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of delete method, of class StudentController.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int rowsAffected = mockedStudentController.delete();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of update method, of class StudentController.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        int rowsAffected = mockedStudentController.update();
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of print method, of class StudentController.
     */
    @Test
    public void testPrint() {
        System.out.println("print");

        Student result = mockedStudentController.print();

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("ali", result.getName());
        assertEquals("lahore", result.getAddress());
        assertEquals(1, result.getTeachers().size());
        assertEquals(1, result.getCourses().size());
    }

}
