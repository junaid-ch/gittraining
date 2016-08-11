/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.view;

import customorm.controller.BaseController;
import customorm.controller.ControllerFactory;
import customorm.model.Course;
import customorm.model.Student;
import customorm.model.Teacher;

/**
 *
 * @author junaid.ahmad
 */
public class TeacherView extends BaseView {

    private final ControllerFactory controllerFactory;
    private final BaseController baseController;

    public TeacherView() {
        controllerFactory = new ControllerFactory();
        baseController = controllerFactory.getController("teacherController");
    }

    @Override
    public void add() {
        baseController.add();
    }

    @Override
    public void delete() {
        baseController.delete();
    }

    @Override
    public void update() {
        baseController.update();
    }

    @Override
    public void print() {

        Teacher teacher = (Teacher) baseController.print();

        if (teacher.getId() != 0) {
            System.out.println("Teacher: ");
            System.out.println("ID: " + teacher.getId()
                    + "\tName: " + teacher.getName());

            System.out.println("Realted Students: ");
            if (teacher.getStudents().get(0).getId() != 0) {    //students exist or not
                for (Student student : teacher.getStudents()) {
                    System.out.println("ID: " + student.getId()
                            + "\tName: " + student.getName()
                            + "\tAddress: " + student.getAddress());
                }
            } else {
                System.out.println("No Student Found...");
            }

            System.out.println("Realted Courses: ");
            if (teacher.getCourses().get(0).getId() != 0) {    //courses exist or not
                for (Course course : teacher.getCourses()) {
                    System.out.println("ID: " + course.getId()
                            + "\tName: " + course.getName());
                }
            } else {
                System.out.println("No course Found...");
            }
        } else {
            System.out.println("No Record Found...");
        }
    }

}
