package com.example.edtech_team_new.db;

import com.example.edtech_team_new.config.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHandler {
    private DbConfig dbConfig = new DbConfig();
    private Connection connection;

    public DbHandler() {
        try {

            Class.forName("org.postgresql.Driver");

            this.connection = DriverManager.getConnection(
                    dbConfig.getDb_url(),
                    dbConfig.getDb_username(),
                    dbConfig.getDb_password()
            );
            System.out.println("connection is saccessfull");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
