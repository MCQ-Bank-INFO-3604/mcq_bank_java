package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Exam {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    
    public Exam(){
        deleteExamTable();
        createExamTable();
    }

    private void deleteExamTable() {
        String sql = "DROP TABLE IF EXISTS exams;";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    private void createExamTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exams ("
                   + "examID INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + "examTitle TEXT NOT NULL,"
                   + "numQuestions INTEGER,"
                   + "course TEXT,"
                   + "topic TEXT,"
                   + "subTopic TEXT,"
                   + "dateCreated DATETIME,"
                   + "lastUsed DATETIME,"
                   + "lastEdited DATETIME,"
                   + "performanceMetric FLOAT);";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }        

}
