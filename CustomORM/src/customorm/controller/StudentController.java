/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.dao.BaseDAO;
import customorm.dao.DAOFactory;
import customorm.model.BaseModel;
import customorm.model.Course;
import customorm.model.ModelFactory;
import customorm.model.Student;
import customorm.model.Teacher;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author junaid.ahmad
 */
public class StudentController implements BaseController {

    private final Scanner scan;
    private final BaseDAO dao;
    private final DAOFactory dAOFactory;
    private final ModelFactory modelFactory;
    private BaseModel baseModel;

    public StudentController() {
        scan = new Scanner(System.in);
        dAOFactory = new DAOFactory();
        dao = dAOFactory.getDAO("studentDAO");
        modelFactory = new ModelFactory();
    }

    @Override
    public int add() {
        baseModel = modelFactory.getModel("studentModel");
        Student student = (Student) baseModel;
        List<Teacher> tlist = new ArrayList<>();
        List<Course> clist = new ArrayList<>();

        System.out.print("Name: ");
        student.setName(scan.next());
        System.out.print("Address: ");
        student.setAddress(scan.next());
        System.out.print("TeacherID's(comma seperated): ");
        String[] tId = scan.next().split(",");
        System.out.print("CourseID's(comma seperated): ");
        String[] cId = scan.next().split(",");

        for (String str1 : tId) {
            Teacher t1 = new Teacher();
            t1.setId(Integer.parseInt(str1));
            tlist.add(t1);
        }

        for (String str1 : cId) {
            Course c = new Course();
            c.setId(Integer.parseInt(str1));
            clist.add(c);
        }

        student.setTeachers(tlist);
        student.setCourses(clist);

        return dao.insert(student);

    }

    @Override
    public int delete() {
        System.out.print("ID: ");
        return dao.delete(scan.nextInt());
    }

    @Override
    public int update() {
        Student t = (Student) modelFactory.getModel("studentModel");

        System.out.print("ID: ");
        t.setId(scan.nextInt());
        System.out.print("Name: ");
        t.setName(scan.next());
        System.out.print("Address: ");
        t.setAddress(scan.next());

        return dao.update(t);
    }

    @Override
    public Student print() {
        System.out.print("ID: ");
        int id = scan.nextInt();
        Student s = (Student) dao.select(id);
        return s;
    }
}
