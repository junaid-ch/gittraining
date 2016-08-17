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
public class DBDriver {

    Connection conn = null;

    private DBDriver() {
    }

    public static DBDriver getInstance() {
        return DBDriverHolder.INSTANCE;
    }

    public Connection getConnection() {

        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }

            //STEP 2: Register JDBC driver
            Class.forName(Configurations.getJDBC_DRIVER());

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(Configurations.getDB_URL(), Configurations.getUSER_NAME(), Configurations.getPASSWORD());

        } catch (SQLException | ClassNotFoundException se) {
            //Handle errors for JDBC
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, se);
        }

        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null || !conn.isClosed()) {
                this.conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class DBDriverHolder {

        private static final DBDriver INSTANCE = new DBDriver();
    }
}
