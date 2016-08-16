/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.DBDriver;
import customorm.model.BaseModel;
import customorm.model.ModelFactory;
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
public abstract class BaseDAO {

    private Connection conn = null;
    private final DBDriver dBDriver;
    private final ModelFactory modelFactory;
    private final String tableName;
    private final String modelName;

    public BaseDAO(String className) {
        modelName = className;
        tableName = className.toLowerCase();
        dBDriver = DBDriver.getInstance();
        modelFactory = new ModelFactory();
    }

    public int insert(BaseModel model) {
        PreparedStatement preparedStmt = null;
        int rowsAffected = 0;

        try {
            if (conn == null) {
                conn = dBDriver.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql insert statement
            String query = " insert into "
                    + tableName
                    + " (name) values (?)";

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, model.getName());
            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();

            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            rowsAffected = 0;
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (conn != null) {
                    conn.close();
                    dBDriver.closeConnection();
                }
            } catch (SQLException se) {
                Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try
        }
        return rowsAffected;
    }

    public int delete(int id) {
        PreparedStatement preparedStmt = null;
        int rowsAffected = 0;
        try {
            if (conn == null) {
                conn = dBDriver.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql delete statement
            String query = " delete from "
                    + tableName
                    + " where id = ?";

            // create the mysql delete preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            rowsAffected = 0;
            Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (conn != null) {
                    conn.close();
                    dBDriver.closeConnection();
                }
            } catch (SQLException se) {
                Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try
        }
        return rowsAffected;
    }

    public int update(BaseModel model) {
        PreparedStatement preparedStmt = null;
        int rowsAffected = 0;

        try {
            if (conn == null) {
                conn = dBDriver.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql update statement
            String query = " update "
                    + tableName
                    + " set name = ? where id = ?";

            // create the mysql update preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, model.getName());
            preparedStmt.setInt(2, model.getId());

            // execute the preparedstatement
            rowsAffected = preparedStmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            rowsAffected = 0;
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }

                if (conn != null) {
                    conn.close();
                    dBDriver.closeConnection();
                }
            } catch (SQLException se) {
                Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try
        }
        return rowsAffected;

    }

    public BaseModel select(int id) {
        BaseModel model = modelFactory.getModel(modelName);
        PreparedStatement preparedStmt = null;
        try {
            if (conn == null) {
                conn = dBDriver.getConnection();
            }
            conn.setAutoCommit(false);
            // the mysql select statement
            String query = " select * from "
                    + tableName
                    + " where id = ?";

            // create the mysql select preparedstatement
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            conn.commit();
            if (rs.next()) {
                model.setId(rs.getInt(1));
                model.setName(rs.getString(2));

            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (conn != null) {
                    conn.close();
                    dBDriver.closeConnection();
                }
            } catch (SQLException se) {
                Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, se);
            }//end finally try
        }
        return model;
    }
}
