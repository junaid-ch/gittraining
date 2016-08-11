/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;


import customorm.view.BaseView;
import customorm.view.CourseView;
import customorm.view.StudentView;
import customorm.view.TeacherView;
import java.util.Scanner;

/**
 *
 * @author junaid.ahmad
 */
public class CustomORM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        BaseView bv;
        Scanner scan = new Scanner(System.in);
        int option = 0;
        
        System.out.println("1. Teacher");
        System.out.println("2. Student");
        System.out.println("3. Course");
        
        option = scan.nextInt();
        
        switch(option){
            case 1:
                bv = new TeacherView();
                break;
            case 2:
                bv = new StudentView();
                break;
            case 3:
                bv = new CourseView();
                break;
            default:
                System.out.println("Wrong input....");
                return;
        }
        bv.menu();
        
    }
    
}
