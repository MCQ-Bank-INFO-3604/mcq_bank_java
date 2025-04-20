package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Exam {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public Exam(){
        // deleteExamTable();
        createExamTable();
    }

    public void deleteExamTable() {
        String sql = "DROP TABLE IF EXISTS exams;";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public void createExamTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exams ("
                   + "examID INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + "examTitle TEXT NOT NULL,"
                   + "numQuestions INTEGER,"
                   + "course INTEGER,"
                   + "dateCreated DATETIME,"
                   + "lastUsed DATETIME,"
                   + "lastEdited DATETIME,"
                   + "performance FLOAT,"
                   + "FOREIGN KEY(course) REFERENCES courses(courseID));";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }        

}
