package fr.perrot54u.restaurationdb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String serverName = "charlemagne.iutnc.univ-lorraine.fr";
    private static String portNumber = "1521";
    private static String dbName = "infodb";
    private static String username;
    private static String password;
    private static final String JDBC_URL = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + dbName;

    public static void initializeDatabase(String usr, String pwd) throws ClassNotFoundException {
        username = usr;
        password = pwd;
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }

    /**
     * @return Une session de DBConnection
     */
    public static Connection createSession() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, username, password);
    }

}