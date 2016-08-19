/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.model.Course;
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
public class CourseController extends BaseController{

    @RequestMapping(value = "/courseView")
    public ModelAndView course() {
        return new ModelAndView("course", "command", new Course());
    }

    //@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    public ModelAndView addCourse(@ModelAttribute("SpringWeb") Course course,
            ModelMap model) {

       // BaseController b = new BaseController("Course");
       // b.add(course);
        return new ModelAndView("result", "model", course);
    }

    @RequestMapping(value = "/updateCourse", method = RequestMethod.POST)
    public ModelAndView updateCourse(@ModelAttribute("SpringWeb") Course course,
            ModelMap model) {

       // BaseController b = new BaseController("Course");
       // b.update(course);
        return new ModelAndView("result", "model", course);
    }

    @RequestMapping(value = "/deleteCourse", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@ModelAttribute("SpringWeb") Course course,
            ModelMap model) {

       // BaseController b = new BaseController("Course");
        //b.delete(course.getId());
        return new ModelAndView("result", "model", course);
    }

    @RequestMapping(value = "/viewCourse", method = RequestMethod.POST)
    public ModelAndView viewCourse(@ModelAttribute("SpringWeb") Course course,
            ModelMap model) {

        //BaseController b = new BaseController("Course");
        Course s = null;//(Course) b.print(course.getId());
        return new ModelAndView("result", "model", s);
    }

}
