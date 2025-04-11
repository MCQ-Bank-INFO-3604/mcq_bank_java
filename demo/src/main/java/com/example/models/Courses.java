package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Courses {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    public Courses() {
        deleteCoursesTable();
        createCoursesTable();
    }

    public void deleteCoursesTable(){
        String sql = "DROP TABLE IF EXISTS courses;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCoursesTable(){
        String sql = "CREATE TABLE IF NOT EXISTS courses ("
                + "courseID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "courseCode TEXT NOT NULL,"
                + "courseName TEXT NOT NULL);";

        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
