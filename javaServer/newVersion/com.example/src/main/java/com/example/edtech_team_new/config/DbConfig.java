package com.example.edtech_team_new.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    @Value("${db.url:jdbc:postgresql://localhost:5432/cubesat}")
    private String db_url;

    @Value("${db.username:postgres}")
    private String db_username;

    @Value("${db.password:admin}")
    private String db_password;

    public String getDb_url() {
        return db_url;
    }

    public String getDb_username() {
        return db_username;
    }

    public String getDb_password() {
        return db_password;
    }
}
