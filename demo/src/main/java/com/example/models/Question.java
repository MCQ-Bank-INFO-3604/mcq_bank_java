package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Question{
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    
    public Question(){
        deleteQuestionTable();
        createQuestionTable();

    }


    public void deleteQuestionTable() {
        String sql = "DROP TABLE IF EXISTS questions;";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }        

    public void createQuestionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS questions ("
                   + "questionID INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + "question TEXT NOT NULL,"
                   + "correctAnswer TEXT NOT NULL,"
                   + "wrongAnswer1 TEXT NOT NULL,"
                   + "wrongAnswer2 TEXT NOT NULL,"
                   + "wrongAnswer3 TEXT NOT NULL,"
                   + "course TEXT,"
                   + "topic TEXT,"
                   + "subTopic TEXT,"
                   + "difficulty TEXT,"
                   + "dateCreated DATETIME,"
                   + "lastUsed DATETIME,"
                   + "lastEdited DATETIME,"
                   + "timesUsed INTEGER,"
                   + "performanceMetric FLOAT,"
                   + "hasImage BOOLEAN DEFAULT 0,"
                   + "imagePath TEXT);";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

}