/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.DBDriver;
import customorm.controller.StudentController;
import customorm.model.BaseModel;
import customorm.model.ModelFactory;
import java.lang.reflect.Field;
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
            StringBuilder query = new StringBuilder();
            StringBuilder values = new StringBuilder();
            query.append("insert into ");
            query.append(tableName);
            query.append(" (");
            values.append("values(");

            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {
                    if (classField.getName().equalsIgnoreCase("id")) {
                        continue;
                    }
                    query.append(classField.getName()).append(",");
                    values.append("?,");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(") ");
            values.deleteCharAt(values.length() - 1).append(");");
            query.append(values);
            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query.toString());

            int i = 1;
            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {

                    try {
                        if (classField.getType().isAssignableFrom(java.lang.String.class)) {
                            preparedStmt.setString(i, (String) model.getField(model, classField));
                            i++;
                        } else if (classField.getType().isAssignableFrom(int.class)
                                && !classField.getName().equalsIgnoreCase("id")) {
                            preparedStmt.setInt(i, (int) model.getField(model, classField));
                            i++;
                        } else if (classField.getType().isAssignableFrom(double.class)) {
                            preparedStmt.setDouble(i, (double) model.getField(model, classField));
                            i++;
                        }
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
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
            StringBuilder query = new StringBuilder();
            query.append("delete from ")
                    .append(tableName)
                    .append(" where id = ?");

            // create the mysql delete preparedstatement
            preparedStmt = conn.prepareStatement(query.toString());
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

            StringBuilder query = new StringBuilder();
            query.append("update ")
                    .append(tableName)
                    .append(" set ");

            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {
                    if (classField.getName().equalsIgnoreCase("id")) {
                        continue;
                    }
                    query.append(classField.getName()).append(" = ? ,");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(" where id = ? ");

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query.toString());

            int i = 1;
            int id = 0;
            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {

                    try {
                        if (classField.getType().isAssignableFrom(java.lang.String.class)) {
                            preparedStmt.setString(i, (String) model.getField(model, classField));
                            i++;
                        } else if (classField.getType().isAssignableFrom(int.class)
                                && classField.getName().equalsIgnoreCase("id")) {
                            id = (int) model.getField(model, classField);
                        } else if (classField.getType().isAssignableFrom(int.class)) {
                            preparedStmt.setInt(i, (int) model.getField(model, classField));
                            i++;
                        } else if (classField.getType().isAssignableFrom(double.class)) {
                            preparedStmt.setDouble(i, (double) model.getField(model, classField));
                            i++;
                        }

                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            preparedStmt.setInt(i, id);
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
            StringBuilder query = new StringBuilder();
            query.append("Select * from ")
                    .append(tableName)
                    .append(" where id = ? ");

            // create the mysql insert preparedstatement
            preparedStmt = conn.prepareStatement(query.toString());
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            ResultSet rs = preparedStmt.executeQuery();
            conn.commit();
            if (!rs.next()) {
                return model;
            }

            for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field classField : fields) {

                    try {
                        if (classField.getType().isAssignableFrom(java.lang.String.class)) {
                            model.setField(model, classField, rs.getString(classField.getName()));
                        } else if (classField.getType().isAssignableFrom(int.class)) {
                            model.setField(model, classField, rs.getInt(classField.getName()));
                        } else if (classField.getType().isAssignableFrom(double.class)) {
                            model.setField(model, classField, rs.getDouble(classField.getName()));
                        }
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
