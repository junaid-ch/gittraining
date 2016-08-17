/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;

import customorm.view.BaseView;
import customorm.view.StudentView;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Configurations.configure();
        BaseView bv;
        Scanner scan = new Scanner(System.in);
        String[] views = {"Teacher", "Student", "Course"};
        int option;
        
        System.out.println("1. " + views[0]);
        System.out.println("2. " + views[1]);
        System.out.println("3. " + views[2]);

        option = scan.nextInt();

        if (option >= 1 || option <= 3) {
            try {
                bv = (BaseView) Class
                        .forName("customorm.view."
                                + views[option - 1]
                                + "View")
                        .newInstance();
                bv.menu();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(CustomORM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }     
}
