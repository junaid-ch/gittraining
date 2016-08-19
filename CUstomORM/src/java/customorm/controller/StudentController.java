/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.model.ModelFactory;
import customorm.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

/**
 *
 * @author junaid.ahmad
 */
@Controller
public class StudentController extends BaseController{

    @RequestMapping(value = "/studentView")
    public ModelAndView student() {
        return new ModelAndView("student", "command", new ModelFactory().getModel("Student"));
    }

    //@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public ModelAndView addStudent(@ModelAttribute("SpringWeb") Student student,
            ModelMap model) {

        //BaseController b = new BaseController("Student");
       // b.add(student);
        return new ModelAndView("result", "model", student);
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public ModelAndView updateStudent(@ModelAttribute("SpringWeb") Student student,
            ModelMap model) {

        //BaseController b = new BaseController("Student");
        //b.update(student);
        return new ModelAndView("result", "model", student);
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.POST)
    public ModelAndView deleteStudent(@ModelAttribute("SpringWeb") Student student,
            ModelMap model) {

        //BaseController b = new BaseController("Student");
        //b.delete(student.getId());
        return new ModelAndView("result", "model", student);
    }

    @RequestMapping(value = "/viewStudent", method = RequestMethod.POST)
    public ModelAndView viewStudent(@ModelAttribute("SpringWeb") Student student,
            ModelMap model) {

        //BaseController b = new BaseController("Student");
        Student s = null;//(Student) b.print(student.getId());
        return new ModelAndView("result", "model", s);
    }

}
