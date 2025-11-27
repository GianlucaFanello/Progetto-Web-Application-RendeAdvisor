package it.unical.demacs.wa.rendeadvisor_be.dao.dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static Connection connection;
    private DBManager() throws SQLException {}

    public static Connection testConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            System.out.println("Connected");
        }
        else {
            String url = "jdbc:";
            connection = DriverManager.getConnection(url);
            System.out.println("Connected");
        }
        return connection;
    }
}
