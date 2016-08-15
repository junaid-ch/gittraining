/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.view;

import customorm.controller.BaseController;
import customorm.controller.ControllerFactory;
import customorm.model.BaseModel;
import java.util.Scanner;

/**
 *
 * @author junaid.ahmad
 */
public abstract class BaseView {

    private final ControllerFactory controllerFactory;
    private final BaseController baseController;
    Scanner scan = null;

    public BaseView(String controllerName) {
        controllerFactory = new ControllerFactory();
        baseController = controllerFactory.getController(controllerName + "Controller");
    }

    public void menu() {
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

    public void add() {
        baseController.add();
    }

    public void delete() {
        baseController.delete();
    }

    public void update() {
        baseController.update();
    }

    public void print() {
        BaseModel model = baseController.print();
        if (model.getId() != 0) {
            System.out.println(model.getClass().getSimpleName());
            System.out.println("ID: " + model.getId()
                    + "\tName: " + model.getName());
        } else {
            System.out.println("No Record Found...");
        }
    }
}
