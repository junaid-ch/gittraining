/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.Request;
import customorm.dao.BaseDAO;
import customorm.model.BaseModel;
import customorm.dao.DAOFactory;
import customorm.model.ModelFactory;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public abstract class BaseController {

    private final BaseDAO dao;
    private final DAOFactory dAOFactory;
    private final String className;

    public BaseController(String name) {
        className = name;
        dAOFactory = new DAOFactory();
        dao = dAOFactory.getDAO(className + "DAO");
    }

    public int add(Request request) {
        
        BaseModel baseModel = convertObject(request);
        return dao.insert(baseModel);
    }

    public int update(Request request) {

        BaseModel baseModel = convertObject(request);
        return dao.update(baseModel);
    }

    public int delete(Request request) {
        BaseModel baseModel = convertObject(request);
        return dao.delete(baseModel.getId());
    }

    public BaseModel print(Request request) {
        BaseModel baseModel = convertObject(request);
        return dao.select(baseModel.getId());
    }

    public BaseModel convertObject(Request request) {
        BaseModel baseModel = new ModelFactory()
                .getModel(className);

        for (Class<?> c = baseModel.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields) {

                try {
                    if (classField.getType().isAssignableFrom(java.lang.String.class)) {

                        String val = (String) request.getField(request, classField);
                        baseModel.setField(baseModel, classField, val);
                    } else if (classField.getType().isAssignableFrom(int.class)) {

                        int val = (int) request.getField(request, classField);
                        baseModel.setField(baseModel, classField, val);
                    }
                } catch (IllegalArgumentException | SecurityException ex) {
                    Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return baseModel;
    }
}
