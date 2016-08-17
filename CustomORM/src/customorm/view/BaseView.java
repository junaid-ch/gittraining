/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.view;

import customorm.controller.BaseController;
import customorm.controller.ControllerFactory;
import customorm.controller.StudentController;
import customorm.model.BaseModel;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.Validator;

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
        int option;
        scan = new Scanner(System.in);
        List<String> functionNames = new ArrayList<>();
        Class<?> clazz = this.getClass().getSuperclass();
        try {
            int i = 1;
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equalsIgnoreCase("menu")) {
                    continue;
                }
                functionNames.add(method.getName());
                System.out.println(i + ". " + method.getName());
                i++;
            }
            option = scan.nextInt();
            String name = functionNames.get(option - 1);
            Method m = clazz.getDeclaredMethod(name);
            m.invoke(this);
        } catch (SecurityException | IllegalArgumentException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(BaseView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add() {
        int add = baseController.add();
        System.out.println("rows affected: " + add);
    }

    public void delete() {
        int delete = baseController.delete();
        System.out.println("rows affected: " + delete);
    }

    public void update() {
        int update = baseController.update();
        System.out.println("rows affected: " + update);
    }

    public void print() {
        BaseModel model = baseController.print();

        if (model.getId() != 0) {
            System.out.println(model.getClass().getSimpleName());
            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {

                    try {

                        System.out.println(classField.getName()
                                + ": "
                                + model.getField(model, classField));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else{
            System.out.println("no record found...");
        }
    }
}
