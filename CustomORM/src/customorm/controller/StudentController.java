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
import customorm.model.Student;
import java.util.Scanner;

/**
 *
 * @author junaid.ahmad
 */
public class StudentController extends BaseController {

    private final Scanner scan;
    private final BaseDAO dao;
    private final DAOFactory dAOFactory;
    private final ModelFactory modelFactory;
    private BaseModel baseModel;

    public StudentController() {
        super("Student");
        scan = new Scanner(System.in);
        dAOFactory = new DAOFactory();
        dao = dAOFactory.getDAO("StudentDAO");
        modelFactory = new ModelFactory();
    }

    @Override
    public int add() {
        baseModel = modelFactory.getModel("Student");
        Student student = (Student) baseModel;

        System.out.print("Name: ");
        student.setName(scan.next());
        System.out.print("Address: ");
        student.setAddress(scan.next());

        return dao.insert(student);

    }

    @Override
    public int update() {
        Student t = (Student) modelFactory.getModel("Student");

        System.out.print("ID: ");
        t.setId(scan.nextInt());
        System.out.print("Name: ");
        t.setName(scan.next());
        System.out.print("Address: ");
        t.setAddress(scan.next());

        return dao.update(t);
    }
}
