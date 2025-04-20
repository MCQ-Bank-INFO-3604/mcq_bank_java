package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Topics {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public Topics() {
        // deleteTopicsTable();
        createTopicsTable();
    }

    public void deleteTopicsTable(){
        String sql = "DROP TABLE IF EXISTS topics;";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTopicsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS topics ("
                + "topicID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "topicName TEXT NOT NULL,"
                + "courseID INTEGER NOT NULL,"
                + "FOREIGN KEY(courseID) REFERENCES courses(courseID));";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

