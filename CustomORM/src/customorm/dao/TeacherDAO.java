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
public class TeacherDAO implements BaseDAO {

    private Connection conn = null;
    private final DBConfig dBConfig;
    private final ModelFactory modelFactory;

    public TeacherDAO() {
        dBConfig = DBConfig.getInstance();
        modelFactory = new ModelFactory();
    }

    @Override
    public int insert(BaseModel obj) {
        PreparedStatement preparedStmt = null;
        Teacher teacher = (Teacher) obj;
        StringBuilder query1 = new StringBuilder();
        StringBuilder query2 = new StringBuilder();
        int rowsAffected = 0;

        try {
            if (conn == null) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " insert into teacher (name)"
                    + " values (?)";
            query1.append("select * from student where id in (");
            query2.append("select * from course where id in (");
            String query3 = " insert into teacher_student "
                    + "(TEACHER_ID, STUDENT_ID) values (?, ?)";
            String query4 = " insert into teacher_course "
                    + "(TEACHER_ID, COURSE_ID) values (?, ?)";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, teacher.getName());
            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();

            ResultSet r = preparedStmt.getGeneratedKeys();
            r.next();

            if (teacher.getStudents().get(0).getId() != 0) {
                //check whether students exist or not
                for (int i = 0; i < teacher.getStudents().size(); i++) {
                    query1.append(teacher.getStudents()
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
                    //id of teacher adding in teacher_student
                    preparedStmt.setInt(1, r.getInt(1));
                    //id of student adding in teacher_table
                    preparedStmt.setInt(2, rs.getInt(1));
                    preparedStmt.execute();
                }

            }
            if (teacher.getCourses().get(0).getId() != 0) {
                //check whether students exist or not
                for (int i = 0; i < teacher.getCourses().size(); i++) {
                    query2.append(teacher.getCourses()
                            .get(i).getId()).append(",");
                }
                query2.deleteCharAt(query2.length() - 1);
                query2.append(")");
                // create the mysql select preparedstatement
                preparedStmt = conn.prepareStatement(query2.toString());
                // execute the preparedstatement
                ResultSet rs = preparedStmt.executeQuery();

                //Adding data relation in teacher_student table
                preparedStmt = conn.prepareStatement(query4);
                while (rs.next()) {
                    //id of teacher adding in teacher_student
                    preparedStmt.setInt(1, r.getInt(1));
                    //id of student adding in teacher_table
                    preparedStmt.setInt(2, rs.getInt(1));
                    preparedStmt.execute();
                }

            }

            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            // the mysql insert statement
            String query = " delete from teacher where id = ?";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        Teacher teacher = (Teacher) obj;
        int rowsAffected = 0;
        try {
            if (conn == null) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " update teacher set name = ? where id = ?";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, teacher.getName());
            preparedStmt.setInt(2, teacher.getId());

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        Teacher teacher = (Teacher) modelFactory.getModel("teacherModel");
        PreparedStatement preparedStmt = null;
        try {
            if (conn == null) {
                conn = dBConfig.configureDB();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " select t.*, s.*,c.* from teacher t "
                    + "left join teacher_student ts on t.id = ts.teacher_id "
                    + "left join student s on ts.student_id = s.id "
                    + "left join teacher_course tc on t.id = tc.teacher_id "
                    + "left join course c on tc.course_id = c.id "
                    + "where t.id = ?";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            conn.commit();
            if (rs.next()) {
                teacher.setId(rs.getInt(1));
                teacher.setName(rs.getString(2));

                List<Student> sList = new ArrayList<>();
                ArrayList<Course> cList = new ArrayList<>();
                do {
                    Student s = new Student();
                    s.setId(rs.getInt(3));
                    s.setName(rs.getString(4));
                    s.setAddress(rs.getString(5));
                    if (!sList.contains(s)) {     //ignore repeating objects
                        sList.add(s);
                    }

                    Course c = new Course();
                    c.setId(rs.getInt(6));
                    c.setName(rs.getString(7));

                    if (!cList.contains(c)) {     //ignore repeating objects
                        cList.add(c);
                    }

                } while (rs.next());

                teacher.setStudents(sList);
                teacher.setCourses(cList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return teacher;
    }
}
