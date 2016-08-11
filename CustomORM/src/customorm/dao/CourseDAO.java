/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.DBConfig;
import customorm.model.BaseModel;
import customorm.model.Course;
import customorm.model.ModelFactory;
import customorm.model.Student;
import customorm.model.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class CourseDAO implements BaseDAO {

    private Connection conn = null;
    private final DBConfig dBConfig;
    private final ModelFactory modelFactory;

    public CourseDAO() {
        dBConfig = DBConfig.getInstance();
        modelFactory = new ModelFactory();
    }

    @Override
    public int insert(BaseModel obj) {
        PreparedStatement preparedStmt = null;
        Course course = (Course) obj;
        StringBuilder query1 = new StringBuilder();
        StringBuilder query2 = new StringBuilder();
        int rowsAffected = 0;

        try {
            if (conn == null) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " insert into course (name)"
                    + " values (?)";
            query1.append("select * from teacher where id in (");
            query2.append("select * from student where id in (");
            String query3 = " insert into teacher_course "
                    + "(TEACHER_ID, COURSE_ID) values (?, ?)";
            String query4 = " insert into student_course "
                    + "(STUDENT_ID, COURSE_ID) values (?, ?)";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, course.getName());
            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            //getting id of last iserted record
            ResultSet r = preparedStmt.getGeneratedKeys();
            r.next();

            if (course.getTeachers().get(0).getId() != 0) {    //if teacher exist
                //check whether teacher exist or not
                for (int i = 0; i < course.getTeachers().size(); i++) {
                    query1.append(course.getTeachers()
                            .get(i).getId()).append(",");
                }
                query1.deleteCharAt(query1.length() - 1);
                query1.append(")");
                // create the mysql select preparedstatement
                preparedStmt = conn.prepareStatement(query1.toString());
                // execute the preparedstatement
                ResultSet rs = preparedStmt.executeQuery();

                //Adding data relation in teacher_course table
                preparedStmt = conn.prepareStatement(query3);
                while (rs.next()) {
                    preparedStmt.setInt(1, rs.getInt("id"));
                    preparedStmt.setInt(2, r.getInt(1));
                    preparedStmt.execute();
                }
            }
            if (course.getStudents().get(0).getId() != 0) {    //if students exist
                //check whether studeny exist or not
                for (int i = 0; i < course.getStudents().size(); i++) {
                    query2.append(course.getStudents()
                            .get(i).getId()).append(",");
                }
                query2.deleteCharAt(query2.length() - 1);
                query2.append(")");
                // create the mysql select preparedstatement
                preparedStmt = conn.prepareStatement(query2.toString());
                // execute the preparedstatement
                ResultSet rs = preparedStmt.executeQuery();

                //Adding data relation in teacher_course table
                preparedStmt = conn.prepareStatement(query4);
                while (rs.next()) {
                    preparedStmt.setInt(1, rs.getInt("id"));
                    preparedStmt.setInt(2, r.getInt(1));
                    preparedStmt.execute();
                }
            }
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            rowsAffected = 0;
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (conn != null) {
                    conn.close();
                    dBConfig.closeConnection();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
        return rowsAffected;
    }

    @Override
    public int delete(int id) {
        PreparedStatement preparedStmt = null;
        int rowsAffected = 0;
        try {
            if (conn == null) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql delete statement
            String query = " delete from course where id = ?";

            // create the mysql delete preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            rowsAffected = 0;
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (conn != null) {
                    conn.close();
                    dBConfig.closeConnection();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
        return rowsAffected;
    }

    @Override
    public int update(BaseModel obj) {
        PreparedStatement preparedStmt = null;
        Course course = (Course) obj;
        int rowsAffected = 0;
        try {
            if (conn == null) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql update statement
            String query = " update course set name = ? where id = ?";

            // create the mysql update preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, course.getName());
            preparedStmt.setInt(2, course.getId());

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            rowsAffected = 0;
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }

                if (conn != null) {
                    conn.close();
                    dBConfig.closeConnection();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
        return rowsAffected;
    }

    @Override
    public BaseModel select(int id) {
        Course course = (Course) modelFactory.getModel("courseModel");
        PreparedStatement preparedStmt = null;
        try {
            if (conn == null) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql select statement
            String query = " select c.*, t.*, s.* from course c "
                    + "left join teacher_course ts on c.id = ts.course_id "
                    + "left join teacher t on ts.teacher_id = t.id "
                    + "left join student_course sc on c.id = sc.course_id "
                    + "left join student s on sc.student_id = s.id "
                    + "where c.id = ?";

            // create the mysql select preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            conn.commit();
            if (rs.next()) {
                course.setId(rs.getInt(1));
                course.setName(rs.getString(2));
                List<Teacher> tList = new ArrayList<>();
                List<Student> sList = new ArrayList<>();
                do {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt(3));
                    t.setName(rs.getString(4));
                    if (!tList.contains(t)) {     //ignore repeating objects
                        tList.add(t);
                    }

                    Student s = new Student();
                    s.setId(rs.getInt(5));
                    s.setName(rs.getString(6));
                    s.setAddress(rs.getString(7));
                    if (!sList.contains(s)) {     //ignore repeating objects
                        sList.add(s);
                    }
                } while (rs.next());
                course.setTeachers(tList);
                course.setStudents(sList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (conn != null) {
                    conn.close();
                    dBConfig.closeConnection();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
        return course;
    }
}
