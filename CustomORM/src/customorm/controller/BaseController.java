/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.dao.BaseDAO;
import customorm.dao.DAOFactory;
import customorm.model.BaseModel;
import customorm.model.ModelFactory;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public abstract class BaseController {

    private final Scanner scan;
    private final BaseDAO dao;
    private final DAOFactory dAOFactory;
    private final ModelFactory modelFactory;
    private final String className;
    private BaseModel baseModel;

    public BaseController(String name) {
        className = name;
        scan = new Scanner(System.in);
        dAOFactory = new DAOFactory();
        dao = dAOFactory.getDAO(className + "DAO");
        modelFactory = new ModelFactory();
    }

    public int add() {
        baseModel = modelFactory.getModel(className);

        for (Class<?> c = baseModel.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields) {

                try {
                    if (!classField.getType().isAssignableFrom(int.class)) {
                        System.out.print(classField.getName() + ": ");
                        baseModel.setField(baseModel, classField, scan.next());
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return dao.insert(baseModel);
    }

    public int update() {
        baseModel = modelFactory.getModel(className);

        for (Class<?> c = baseModel.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields) {

                try {
                    System.out.print(classField.getName() + ": ");
                    if (classField.getType().isAssignableFrom(int.class)) {
                        baseModel.setField(baseModel, classField, scan.nextInt());
                    } else {
                        baseModel.setField(baseModel, classField, scan.next());
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return dao.update(baseModel);
    }

    public int delete() {
        System.out.print("ID: ");
        return dao.delete(scan.nextInt());
    }

    public BaseModel print() {
        System.out.print("ID: ");
        int id = scan.nextInt();
        return dao.select(id);
    }
}
