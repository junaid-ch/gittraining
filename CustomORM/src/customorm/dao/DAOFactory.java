/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

/**
 *
 * @author junaid.ahmad
 */
public class DAOFactory {

    //returns the required controller object
    public BaseDAO getDAO(String DAOName) {

        if (DAOName == null) {
            return null;
        } else if (DAOName.equalsIgnoreCase("teacherDAO")) {
            return new TeacherDAO();
        } else if (DAOName.equalsIgnoreCase("studentDAO")) {
            return new StudentDAO();
        } else if (DAOName.equalsIgnoreCase("courseDAO")) {
            return new CourseDAO();
        }
        return null;
    }
}
