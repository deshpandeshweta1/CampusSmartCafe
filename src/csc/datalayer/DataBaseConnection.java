package csc.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

public Connection openConnection() throws SQLException, ClassNotFoundException {
    // Load the Oracle database driver 
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

    /* 
       Here is the information needed when connecting to a database 
       server. These values are now hard-coded in the program.
     */
    String host = "localhost";
    String port = "1521";
    String dbName = "XE";
    String userName = "shweta";
    String password = "shweta";

    // Construct the JDBC URL 
    String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
    return DriverManager.getConnection(dbURL, userName, password);
}

/**
 * Close the database connection
 *
 * @param con
 */
public void closeConnection(Connection con) {
    try {
        con.close();
    } catch (SQLException e) {
        System.err.println("Cannot close connection: " + e.getMessage());
    }
}
}
