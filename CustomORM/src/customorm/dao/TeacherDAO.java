/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.DBDriver;
import customorm.model.BaseModel;
import customorm.model.ModelFactory;
import customorm.model.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class TeacherDAO implements BaseDAO {

    private Connection conn = null;
    private final DBDriver dBConfig;
    private final ModelFactory modelFactory;

    public TeacherDAO() {
        dBConfig = DBDriver.getInstance();
        modelFactory = new ModelFactory();
    }

    @Override
    public int insert(BaseModel obj) {
        PreparedStatement preparedStmt = null;
        Teacher teacher = (Teacher) obj;
        int rowsAffected = 0;

        try {
            if (conn == null) {
                conn = dBConfig.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " insert into teacher (name)"
                    + " values (?)";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, teacher.getName());
            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();

            ResultSet r = preparedStmt.getGeneratedKeys();
            r.next();

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
                conn = dBConfig.getConnection();
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
                conn = dBConfig.getConnection();
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
        Teacher teacher = (Teacher) modelFactory.getModel("Teacher");
        PreparedStatement preparedStmt = null;
        try {
            if (conn == null) {
                conn = dBConfig.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " select t.* from teacher t "
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
