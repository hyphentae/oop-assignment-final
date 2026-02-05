package com.hyphentae.lms.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try {
            Properties props = new Properties();
            props.load(Files.newInputStream(Paths.get(".env")));

            URL = props.getProperty("DB_URL");
            USER = props.getProperty("DB_USER");
            PASSWORD = props.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            throw new RuntimeException("Cannot load .env file", e);
        }
    }

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
