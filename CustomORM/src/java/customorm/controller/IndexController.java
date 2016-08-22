/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.Configurations;
import customorm.Request;
import customorm.model.BaseModel;
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
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {

        Configurations.configure();
        return new ModelAndView("index");
    }

    @RequestMapping("/student")
    public ModelAndView student() {

        Request.setController("StudentController");
        return new ModelAndView("student", "command", new Request());
    }

    @RequestMapping("/teacher")
    public ModelAndView teacher() {

        Request.setController("TeacherController");
        return new ModelAndView("teacher", "command", new Request());
    }

    @RequestMapping("/course")
    public ModelAndView course() {

        Request.setController("CourseController");
        return new ModelAndView("course", "command", new Request());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(Request request) {

        baseController = controllerFactory
                .getController(Request.getController());
        int rowsAffected = baseController.add(request);
        return new ModelAndView("result", "model", rowsAffected);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Request request) {

        baseController = controllerFactory
                .getController(Request.getController());
        int rowsAffected = baseController.update(request);
        return new ModelAndView("result", "model", rowsAffected);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(Request request) {

        baseController = controllerFactory
                .getController(Request.getController());
        int rowsAffected = baseController.delete(request);
        return new ModelAndView("result", "model", rowsAffected);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelAndView view(Request request) {

        baseController = controllerFactory
                .getController(Request.getController());
        BaseModel baseModel = baseController.print(request);
        return new ModelAndView("result", "model", baseModel);
    }

}
