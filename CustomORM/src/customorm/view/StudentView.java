/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.view;

import customorm.controller.BaseController;
import customorm.controller.ControllerFactory;
import customorm.model.Student;

/**
 *
 * @author junaid.ahmad
 */
public class StudentView extends BaseView {

    private final ControllerFactory controllerFactory;
    private final BaseController baseController;

    public StudentView() {
        super("Student");
        controllerFactory = new ControllerFactory();
        baseController = controllerFactory.getController("StudentController");
    }

    @Override
    public void print() {

        Student student = (Student) baseController.print();
        if (student.getId() != 0) {
            System.out.println("Student: ");
            System.out.println("ID: " + student.getId()
                    + "\tName: " + student.getName()
                    + "\tAddress: " + student.getAddress());

        } else {
            System.out.println("No Record Found...");
        }
    }

}
