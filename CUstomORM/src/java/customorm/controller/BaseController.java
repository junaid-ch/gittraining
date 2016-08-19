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
import customorm.model.Teacher;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junaid.ahmad
 */
@Controller
public abstract class BaseController {

    private final BaseDAO dao;
    private final DAOFactory dAOFactory;

    private final String className;

    public BaseController() {
        className = Request.getModel();
        dAOFactory = new DAOFactory();
        this.dao = dAOFactory.getDAO(className + "DAO");
    }

    public BaseController(String name) {
        className = name;
        dAOFactory = new DAOFactory();
        dao = dAOFactory.getDAO(className + "DAO");
    }

    public int add(Request model) {
        BaseModel baseModel = new ModelFactory()
                .getModel(Request.getModel());
        for (Class<?> c = baseModel.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields) {

                try {
                    if (classField.getType().isAssignableFrom(java.lang.String.class)) {

                        String val = (String) model.getField(model, classField);
                        baseModel.setField(baseModel, classField, val);
                    }
                } catch (IllegalArgumentException | SecurityException ex) {
                    Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return dao.insert(baseModel);
    }

    public int update(BaseModel baseModel) {
        return dao.update(baseModel);
    }

    public int delete(int id) {
        return dao.delete(id);
    }

    public BaseModel print(int id) {
        return dao.select(id);
    }
}
