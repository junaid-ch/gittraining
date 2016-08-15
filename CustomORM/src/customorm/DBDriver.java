/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author junaid.ahmad
 */
public class DBDriver {

    // JDBC driver name and database URL
    private static String JDBC_DRIVER;
    private static String DB_URL;

    //  Database credentials
    private static String USER_NAME;
    private static String PASSWORD;

    Connection conn = null;

    private DBDriver() {
    }

    public static DBDriver getInstance() {

        try {
            File fXmlFile = new File("DBConfig.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            JDBC_DRIVER = doc
                    .getElementsByTagName("JDBC_DRIVER")
                    .item(0)
                    .getTextContent().trim();
            DB_URL = doc
                    .getElementsByTagName("DB_URL")
                    .item(0)
                    .getTextContent().trim();
            USER_NAME = doc
                    .getElementsByTagName("USER_NAME")
                    .item(0)
                    .getTextContent().trim();
            PASSWORD = doc
                    .getElementsByTagName("PASSWORD")
                    .item(0)
                    .getTextContent().trim();

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DBDriverHolder.INSTANCE;
    }

    public Connection getConnection() {

        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }

            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

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
            Logger.getLogger(DBDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class DBDriverHolder {

        private static final DBDriver INSTANCE = new DBDriver();
    }
}
