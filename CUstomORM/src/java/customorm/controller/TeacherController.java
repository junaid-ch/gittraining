/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.model.ModelFactory;
import customorm.model.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junaid.ahmad
 */
@Controller
public class TeacherController extends BaseController{

    @RequestMapping(value = "/teacherView")
    public ModelAndView teacher() {
        return new ModelAndView("teacher", "command", new ModelFactory().getModel("Teacher"));
    }

    //@RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    public ModelAndView addTeacher(@ModelAttribute("SpringWeb") Teacher teacher,
            ModelMap model) {

        //BaseController b = new BaseController("Teacher");
       // b.add(teacher);
        return new ModelAndView("result", "model", teacher);
    }

    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    public ModelAndView updateTeacher(@ModelAttribute("SpringWeb") Teacher teacher,
            ModelMap model) {

       // BaseController b = new BaseController("Teacher");
       // b.update(teacher);
        return new ModelAndView("result", "model", teacher);
    }

    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
    public ModelAndView deleteTeacher(@ModelAttribute("SpringWeb") Teacher teacher,
            ModelMap model) {

        //BaseController b = new BaseController("Teacher");
       // b.delete(teacher.getId());
        return new ModelAndView("result", "model", teacher);
    }

    @RequestMapping(value = "/viewTeacher", method = RequestMethod.POST)
    public ModelAndView viewTeacher(@ModelAttribute("SpringWeb") Teacher teacher,
            ModelMap model) {

       // BaseController b = new BaseController("Teacher");
        Teacher s =null; //(Teacher) b.print(teacher.getId());
        return new ModelAndView("result", "model", s);
    }

}
