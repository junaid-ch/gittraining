/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.DBDriver;
import customorm.model.BaseModel;
import customorm.model.ModelFactory;
import customorm.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class StudentDAO extends BaseDAO {

    private Connection conn = null;
    private final DBDriver dBConfig;
    private final ModelFactory modelFactory;

    public StudentDAO() {
        super("Student");
        dBConfig = DBDriver.getInstance();
        modelFactory = new ModelFactory();
    }

    @Override
    public int insert(BaseModel obj) {
        PreparedStatement preparedStmt = null;
        Student student = (Student) obj;
        int rowsAffected = 0;

        try {
            if (conn == null || conn.isClosed()) {
                conn = dBConfig.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " insert into student (name, address)"
                    + " values (?, ?)";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, student.getName());
            preparedStmt.setString(2, student.getAddress());
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
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, excep);
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
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, se);
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
                conn = dBConfig.getConnection();
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
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, excep);
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
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try
        }
        return rowsAffected;
    }

    @Override
    public BaseModel select(int id) {
        Student student = (Student) modelFactory.getModel("Student");
        PreparedStatement preparedStmt = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = dBConfig.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql select statement
            String query = " select * from student "
                    + "where id = ?";

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

            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, excep);
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
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try
        }

        return student;
    }
}
