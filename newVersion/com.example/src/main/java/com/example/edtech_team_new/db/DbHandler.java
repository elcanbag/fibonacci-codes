package com.example.edtech_team_new.db;

import com.example.edtech_team_new.config.DbConfig;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Slf4j
@RequiredArgsConstructor
public class DbHandler {
    private final DbConfig dbConfig;
    private Connection connection;

    @PostConstruct
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(
                    dbConfig.getDb_url(),
                    dbConfig.getDb_username(),
                    dbConfig.getDb_password()
            );
            log.info("Database connection is successful");
        } catch (ClassNotFoundException | SQLException e) {
            log.error("Error establishing database connection", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
