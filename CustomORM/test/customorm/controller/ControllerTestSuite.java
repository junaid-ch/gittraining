/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author junaid.ahmad
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    customorm.controller.TeacherControllerTest.class,
    customorm.controller.StudentControllerTest.class,
    customorm.controller.CourseControllerTest.class
})
public class ControllerTestSuite {

}
