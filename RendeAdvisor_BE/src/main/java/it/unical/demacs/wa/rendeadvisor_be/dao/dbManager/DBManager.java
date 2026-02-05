package it.unical.demacs.wa.rendeadvisor_be.dao.dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static DBManager instance = null;
    private Connection connection = null;

    private DBManager() throws SQLException {

        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "12345"
        );
    }

    public static DBManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

}