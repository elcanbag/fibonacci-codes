package com.example.edtech_team_new.config;

public class DbConfig {
    private String db_url = "jdbc:postgresql://localhost:5432/cubesat";

    private String db_username = "postgres";
    private String db_password = "admin";

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }
}
