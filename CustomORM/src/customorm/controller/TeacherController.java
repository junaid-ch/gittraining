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
public class TeacherController implements BaseController {

    private final Scanner scan;
    private final BaseDAO dao;
    private final DAOFactory dAOFactory;
    private final ModelFactory modelFactory;
    private BaseModel baseModel;

    public TeacherController() {
        scan = new Scanner(System.in);
        dAOFactory = new DAOFactory();
        dao = dAOFactory.getDAO("teacherDAO");
        modelFactory = new ModelFactory();
    }

    @Override
    public int add() {
        baseModel = modelFactory.getModel("teacherModel");
        Teacher teacher = (Teacher) baseModel;
        List<Student> slist = new ArrayList<>();
        List<Course> clist = new ArrayList<>();

        System.out.print("Name: ");
        teacher.setName(scan.next());
        System.out.print("StudentID's(comma seperated): ");
        String[] sId = scan.next().split(",");
        System.out.print("CourseID's(comma seperated): ");
        String[] cId = scan.next().split(",");

        for (String str1 : sId) {
            Student s = new Student();
            s.setId(Integer.parseInt(str1));
            slist.add(s);
        }

        for (String str1 : cId) {
            Course c = new Course();
            c.setId(Integer.parseInt(str1));
            clist.add(c);
        }

        teacher.setStudents(slist);
        teacher.setCourses(clist);

        return dao.insert(teacher);

    }

    @Override
    public int delete() {
        System.out.print("ID: ");
        return dao.delete(scan.nextInt());
    }

    @Override
    public int update() {
        Teacher t = (Teacher) modelFactory.getModel("teacherModel");

        System.out.print("ID: ");
        t.setId(scan.nextInt());
        System.out.print("Name: ");
        t.setName(scan.next());

        return dao.update(t);
    }

    @Override
    public Teacher print() {
        System.out.print("ID: ");
        int id = scan.nextInt();
        Teacher s = (Teacher) dao.select(id);
        return s;
    }
}
