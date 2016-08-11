/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.view;

import java.util.Scanner;

/**
 *
 * @author junaid.ahmad
 */
public abstract class BaseView {
    
    Scanner scan = null;
    
    abstract void add();
    abstract void delete();
    abstract void update();
    abstract void print();  
    
    public void menu(){
        int option = 0;
        scan = new Scanner(System.in);
        
        System.out.println("1. add");
        System.out.println("2. delete");
        System.out.println("3. update");
        System.out.println("4. view");
        
        option = scan.nextInt();

        switch (option) {
            case 1:
                add();
                break;
            case 2:
                delete();
                break;
            case 3:
                update();
                break;
            case 4:
                print();
                break;
            default:
                break;
        }
        
    } 
}
