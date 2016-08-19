/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;

import customorm.model.BaseModel;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class Request {
    private static String model;
    private static String controller;
    private int id;
    private String name;
    private String address;

    /**
     * @return the model
     */
    public static String getModel() {
        return model;
    }

    /**
     * @param aModel the model to set
     */
    public static void setModel(String aModel) {
        model = aModel;
    }

    /**
     * @return the controller
     */
    public static String getController() {
        return controller;
    }

    /**
     * @param aController the controller to set
     */
    public static void setController(String aController) {
        controller = aController;
    }
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setField(Request object, Field field, Object fieldValue) {
        Class<?> clazz = object.getClass();
        // MZ: Find the correct method
        for (Method method : clazz.getMethods()) {
            if ((method.getName().startsWith("set")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        method.invoke(object, fieldValue);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
    }

    public <V> V getField(Request object, Field field) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {

                for (Method method : clazz.getMethods()) {
                    if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                        if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                            // MZ: Method found, run it
                            try {
                                return (V) method.invoke(object);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }

            } catch (SecurityException | IllegalArgumentException e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

}
