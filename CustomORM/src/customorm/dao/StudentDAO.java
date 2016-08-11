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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 *
 * @author junaid.ahmad
 */
public class StudentDAO implements BaseDAO {

    private Connection conn = null;
    private final DBConfig dBConfig;
    private final ModelFactory modelFactory;

    public StudentDAO() {
        dBConfig = DBConfig.getInstance();
        modelFactory = new ModelFactory();
    }

    @Override
    public int insert(BaseModel obj) {
        PreparedStatement preparedStmt = null;
        Student student = (Student) obj;
        StringBuilder query1 = new StringBuilder();
        StringBuilder query2 = new StringBuilder();
        int rowsAffected = 0;

        try {
            if (conn == null || conn.isClosed()) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " insert into student (name, address)"
                    + " values (?, ?)";
            query1.append("select * from teacher where id in (");
            query2.append("select * from course where id in (");
            String query3 = " insert into teacher_student "
                    + "(TEACHER_ID, STUDENT_ID) values (?, ?)";
            String query4 = " insert into student_course "
                    + "(STUDENT_ID, COURSE_ID) values (?, ?)";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, student.getName());
            preparedStmt.setString(2, student.getAddress());
            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            //getting id of last iserted record
            ResultSet r = preparedStmt.getGeneratedKeys();
            r.next();
            student.setId(r.getInt(1));

            if (student.getTeachers().get(0).getId() != 0) {
                //check whether teacher exist or not
                for (int i = 0; i < student.getTeachers().size(); i++) {
                    query1.append(student.getTeachers()
                            .get(i).getId()).append(",");
                }
                query1.deleteCharAt(query1.length() - 1);
                query1.append(")");
                // create the mysql select preparedstatement
                preparedStmt = conn.prepareStatement(query1.toString());
                // execute the preparedstatement
                ResultSet rs = preparedStmt.executeQuery();

                //Adding data relation in teacher_student table
                preparedStmt = conn.prepareStatement(query3);
                while (rs.next()) {
                    preparedStmt.setInt(1, rs.getInt("id"));
                    preparedStmt.setInt(2, student.getId());
                    preparedStmt.execute();
                }
            }
            if (student.getCourses().get(0).getId() != 0) {
                //check whether course exist or not
                for (int i = 0; i < student.getCourses().size(); i++) {
                    query2.append(student.getCourses()
                            .get(i).getId()).append(",");
                }
                query2.deleteCharAt(query2.length() - 1);
                query2.append(")");
                // create the mysql select preparedstatement
                preparedStmt = conn.prepareStatement(query2.toString());
                // execute the preparedstatement
                ResultSet rs = preparedStmt.executeQuery();

                //Adding data relation in student_course table
                preparedStmt = conn.prepareStatement(query4);
                while (rs.next()) {
                    preparedStmt.setInt(1, student.getId());
                    preparedStmt.setInt(2, rs.getInt(1));
                    preparedStmt.execute();
                }
            }
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            if (conn == null || conn.isClosed()) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql delete statement
            String query = " delete from student where id = ?";

            // create the mysql delete preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public int update(BaseModel obj) {
        PreparedStatement preparedStmt = null;
        Student student = (Student) obj;
        int rowsAffected = 0;
        try {
            if (conn == null || conn.isClosed()) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql update statement
            String query = " update student set name = ?, "
                    + "address = ? where id = ?";

            // create the mysql update preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, student.getName());
            preparedStmt.setString(2, student.getAddress());
            preparedStmt.setInt(3, student.getId());

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        Student student = (Student) modelFactory.getModel("studentModel");
        PreparedStatement preparedStmt = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql select statement
            String query = " select s.*, t.*, c.* from student s "
                    + "left join teacher_student ts on s.id = ts.student_id "
                    + "left join teacher t on ts.teacher_id = t.id "
                    + "left join student_course sc on s.id = sc.student_id "
                    + "left join course c on sc.course_id = c.id "
                    + "where s.id = ?";

            // create the mysql select preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            conn.commit();
            if (rs.next()) {
                student.setId(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setAddress(rs.getString(3));
                List<Teacher> tList = new ArrayList<>();
                List<Course> cList = new ArrayList<>();

                do {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt(4));
                    t.setName(rs.getString(5));
                    if (!tList.contains(t)) {     //ignoring repeating values
                        tList.add(t);
                    }

                    Course c = new Course();
                    c.setId(rs.getInt(6));
                    c.setName(rs.getString(7));
                    if (!cList.contains(c)) {     //ignoring repeating values
                        cList.add(c);
                    }
                } while (rs.next());
                student.setTeachers(tList);
                student.setCourses(cList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
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

        return student;
    }
}
