package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Subtopics {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    public Subtopics() {
        deleteSubtopicsTable();
        createSubtopicsTable();
    }

    public void deleteSubtopicsTable(){
        String sql = "DROP TABLE IF EXISTS subtopics;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSubtopicsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS subtopics ("
                + "subtopicID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "subtopicName TEXT NOT NULL,"
                + "topicID INTEGER NOT NULL,"
                + "FOREIGN KEY(topicID) REFERENCES topics(topicID));";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

