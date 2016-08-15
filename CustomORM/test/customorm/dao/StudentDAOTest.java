/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.model.Student;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 *
 * @author junaid.ahmad
 */
public class StudentDAOTest {

    private static Student student;
    private static StudentDAO mockedStudentDAO;

    public StudentDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("...STUDENT_DAO...");
        mockedStudentDAO = mock(StudentDAO.class);
        student = new Student();
        student.setId(1);
        student.setName("ali");
        student.setAddress("lahore");

        when(mockedStudentDAO.insert(student)).thenReturn(1);
        when(mockedStudentDAO.select(student.getId())).thenReturn(student);
        when(mockedStudentDAO.delete(student.getId())).thenReturn(1);
        when(mockedStudentDAO.update(student)).thenReturn(1);
    }

    /**
     * Test of insert method, of class StudentDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        int rowsAffected = mockedStudentDAO.insert(student);
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of delete method, of class StudentDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int rowsAffected = mockedStudentDAO.delete(student.getId());
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of update method, of class StudentDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        int rowsAffected = mockedStudentDAO.update(student);
        assertEquals(true, rowsAffected > 0);
    }

    /**
     * Test of select method, of class StudentDAO.
     */
    @Test
    public void testSelect() {
        System.out.println("select");

        Student result = (Student) mockedStudentDAO.select(student.getId());

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("ali", result.getName());
        assertEquals("lahore", result.getAddress());

    }
}
