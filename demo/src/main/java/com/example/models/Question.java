package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Question{
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    public Question(){
        // deleteQuestionTable();
        createQuestionTable();

    }

    public void deleteQuestionTable() {
        String sql = "DROP TABLE IF EXISTS questions;";
        
        try (Connection conn = getConnection();
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
                   + "course INTEGER NOT NULL,"
                   + "topic INTEGER NOT NULL,"
                   + "subTopic INTEGER NOT NULL,"
                   + "difficulty FLOAT,"
                   + "dateCreated DATETIME,"
                   + "lastUsed DATETIME,"
                   + "lastEdited DATETIME,"
                   + "timesUsed INTEGER,"
                   + "performance FLOAT,"
                   + "discrimination FLOAT,"
                   + "hasImage BOOLEAN DEFAULT 0,"
                   + "questionImagePath TEXT,"
                   + "correctAnswerImagePath TEXT,"
                   + "wrongAnswer1ImagePath TEXT,"
                   + "wrongAnswer2ImagePath TEXT,"
                   + "wrongAnswer3ImagePath TEXT,"
                   + "comment TEXT,"
                   + "FOREIGN KEY(course) REFERENCES courses(courseID),"
                   + "FOREIGN KEY(topic) REFERENCES topics(topicID),"
                   + "FOREIGN KEY(subtopic) REFERENCES subtopics(subtopicID));";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

}