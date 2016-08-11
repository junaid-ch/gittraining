/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author junaid.ahmad
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    customorm.dao.StudentDAOTest.class, 
    customorm.dao.TeacherDAOTest.class, 
    customorm.dao.CourseDAOTest.class
})
public class DAOTestSuite {
}
