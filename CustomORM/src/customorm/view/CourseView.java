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
public class CourseView extends BaseView{
    
    private final ControllerFactory controllerFactory;
    private final BaseController baseController;
    
    public CourseView() {
        controllerFactory = new ControllerFactory();
        baseController = controllerFactory.getController("courseController");
    }
    
    @Override
    public void add(){
        baseController.add();
    }
    
    @Override
    public void delete(){
        baseController.delete();
    }
    
    @Override
    public void update(){
        baseController.update();
    }
    
    @Override
    public void print(){
        
        Course course = (Course)baseController.print();
        if(course.getId() != 0){
            System.out.println("Course: ");
            System.out.println("ID: " + course.getId() 
                    + "\tName: " + course.getName());
            
            System.out.println("Realted Teachers: ");
            if(course.getTeachers().get(0).getId() != 0){
                for (Teacher teacher : course.getTeachers()) {
                    System.out.println("ID: " + teacher.getId() 
                            + "\tName: " + teacher.getName());
                }
            }else{
                System.out.println("No Teacher Found...");
            } 
            
            System.out.println("Realted Students: ");
            if(course.getStudents().get(0).getId() != 0){
                for (Student student : course.getStudents()) {
                    System.out.println("ID: " + student.getId() 
                            + "\tName: " + student.getName()
                            + "\tAddress: " + student.getAddress());
                }
            }else{
                System.out.println("No student Found...");
            }    
            
        }else{
            System.out.println("No Record Found...");
        }
   }
}
