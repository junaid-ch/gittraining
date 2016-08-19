/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.Configurations;
import customorm.Request;
import customorm.model.BaseModel;
import customorm.model.ModelFactory;
import customorm.model.Teacher;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junaid.ahmad
 */
@Controller
public class IndexController {

    private final ControllerFactory controllerFactory;
    private BaseController baseController;

    public IndexController() {
        controllerFactory = new ControllerFactory();
        //baseController = controllerFactory.getController(controllerName + "Controller");
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        Configurations.configure();
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addTeacher(Request model) {
        String s = model.getClass().getSimpleName();
        baseController = controllerFactory
                .getController(Request.getController());
        baseController.add(model);
        return new ModelAndView("result", "model", model);
    }

    @RequestMapping("/student")
    public void student(HttpServletResponse response) {
        Request.setModel("Student");
        Request.setController("StudentController");
        try {
            response.sendRedirect("/CustomORM/studentView");
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return new ModelAndView("student");
    }

    @RequestMapping("/teacher")
    public void teacher(HttpServletResponse response) {
        Request.setModel("Teacher");
        Request.setController("TeacherController");
        try {
            response.sendRedirect("/CustomORM/teacherView");
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return new ModelAndView("student");
    }

    @RequestMapping("/course")
    public void course(HttpServletResponse response) {
        Request.setModel("Course");
        Request.setController("CourseController");
        try {
            response.sendRedirect("/CustomORM/courseView");
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return new ModelAndView("student");
    }

}
