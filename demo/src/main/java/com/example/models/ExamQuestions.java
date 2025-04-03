package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ExamQuestions {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    
    public ExamQuestions(){
        deleteExamQuestionsTable();
        createExamQuestionsTable();
    }

    private void deleteExamQuestionsTable() {
        String sql = "DROP TABLE IF EXISTS ExamQuestions;";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }        

    private void createExamQuestionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ExamQuestions ("
                   + "examID INTEGER,"
                   + "questionID INTEGER,"        
                   + "PRIMARY KEY (examID, questionID),"
                   + "FOREIGN KEY (examID) REFERENCES exams(examID),"
                   + "FOREIGN KEY (questionID) REFERENCES questions(questionID));";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }        
}
