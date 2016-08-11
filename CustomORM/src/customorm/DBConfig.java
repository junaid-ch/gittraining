/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class DBConfig {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Practice";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "123";

    Connection conn = null;

    private DBConfig() {
    }

    public static DBConfig getInstance() {
        return DBConfigHolder.INSTANCE;
    }

    public Connection configureDB() {

        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException | ClassNotFoundException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }

        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                this.conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class DBConfigHolder {

        private static final DBConfig INSTANCE = new DBConfig();
    }
}
