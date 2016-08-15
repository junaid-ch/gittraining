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
import java.util.Scanner;

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

        System.out.print("Name: ");
        baseModel.setName(scan.next());

        return dao.insert(baseModel);
    }

    public int update() {
        baseModel = modelFactory.getModel(className);

        System.out.print("ID: ");
        baseModel.setId(scan.nextInt());
        System.out.print("Name: ");
        baseModel.setName(scan.next());

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
