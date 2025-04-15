package com.example.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ExamController {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    private QuestionController questionController = new QuestionController();

    public void insertExam(String examTitle,int numQuestions, String course, String topic, String subTopic) {
        String sql = "INSERT INTO exams (examTitle, numQuestions, course, topic, subTopic, dateCreated) VALUES ( ?, ?, ?, ?, ?, ? );";
        
        //java.util.Date today=new Date();
        //java.sql.Date date=new java.sql.Date(today.getTime()); //your SQL date object
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, examTitle);
                pstmt.setInt(2, numQuestions);
                pstmt.setString(3, course);
                pstmt.setString(4, topic);
                pstmt.setString(5, subTopic);
                pstmt.setString(6, date);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteExam(int examId) throws SQLException {
        // First delete all exam-question relationships
        String deleteRelationsSql = "DELETE FROM ExamQuestions WHERE examID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteRelationsSql)) {
            pstmt.setInt(1, examId);
            pstmt.executeUpdate();
        }
        
        // Then delete the exam itself
        String deleteExamSql = "DELETE FROM exams WHERE examID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteExamSql)) {
            pstmt.setInt(1, examId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public ResultSet getExam(int examID){
        
        String sql = "SELECT * FROM exams WHERE examID = '" + examID + "';";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }        
    }

    public ResultSet getQuestionsFromExam(int examID){

        String sql = "SELECT * FROM ExamQuestions WHERE examID = '" + examID + "' ;";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public void addQuestionToExam(int examID, int questionID) throws SQLException {
        // Get current exam data
        ResultSet rs2 = getExam(examID);
        
        // Prepare the current date/time
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    
        // Update exam with new lastEdited and numQuestions
        String sql = "UPDATE exams " +
                    "SET lastEdited = ?, numQuestions = ? " +
                    "WHERE examID = ?";
    
        try (Connection conn1 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt1 = conn1.prepareStatement(sql)) {
            pstmt1.setString(1, date);
            pstmt1.setInt(2, rs2.getInt("numQuestions") + 1);
            pstmt1.setInt(3, examID);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }        
                   
        // Update question with lastUsed and increment timesUsed
        sql = "UPDATE questions " +
            "SET lastUsed = ?, timesUsed = timesUsed + 1 " +
            "WHERE questionID = ?";
    
        try (Connection conn2 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt2 = conn2.prepareStatement(sql)) {
            pstmt2.setString(1, date);
            pstmt2.setInt(2, questionID);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }        
    
        // Add the question to the exam
        sql = "INSERT INTO ExamQuestions (examID, questionID) VALUES (?, ?)"; 
    
        try (Connection conn3 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt3 = conn3.prepareStatement(sql)) {
            pstmt3.setInt(1, examID);
            pstmt3.setInt(2, questionID);
            pstmt3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void removeQuestionFromExam(int examID, int questionID) throws SQLException {
        // Get current exam data
        ResultSet rs2 = getExam(examID);
        
        // Prepare the current date/time
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    
        // Update exam with new lastEdited and numQuestions
        String sql = "UPDATE exams " +
                    "SET lastEdited = ?, numQuestions = ? " +
                    "WHERE examID = ?";
    
        try (Connection conn1 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt1 = conn1.prepareStatement(sql)) {
            pstmt1.setString(1, date);
            pstmt1.setInt(2, rs2.getInt("numQuestions") - 1);
            pstmt1.setInt(3, examID);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }        
    
        // Update question to decrement timesUsed
        sql = "UPDATE questions " +
            "SET timesUsed = timesUsed - 1 " +
            "WHERE questionID = ?";
    
        try (Connection conn2 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt2 = conn2.prepareStatement(sql)) {
            pstmt2.setInt(1, questionID);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }        
    
        // Remove the question from the exam
        sql = "DELETE FROM ExamQuestions WHERE questionID = ? AND examID = ?";
    
        try (Connection conn3 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt3 = conn3.prepareStatement(sql)) {
            pstmt3.setInt(1, questionID);
            pstmt3.setInt(2, examID);
            pstmt3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResultSet getExamsWithFilter(String sqlQuery) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getExamsWithFilter() {
        return getExamsWithFilter("SELECT * FROM exams ORDER BY examID");
    }


public void generateExamPDF(int examID, String filePath) throws SQLException, DocumentException, IOException {
    ResultSet rs = getQuestionsFromExam(examID);
    ResultSet rs2 = getExam(examID);
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    int autoNum = 1;

    // Update last used date
    String sql = "UPDATE exams SET lastUsed = ? WHERE examID = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, date);
        pstmt.setInt(2, examID);
        pstmt.executeUpdate();
    }
    
    Document document = new Document();
    try {
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        
        // Add exam header
        document.add(new Paragraph("Exam ID: " + examID, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        document.add(new Paragraph("Exam Title: " + rs2.getString("examTitle"), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        document.add(new Paragraph("Course: " + rs2.getString("course")));
        document.add(new Paragraph("Number of Questions: " + rs2.getInt("numQuestions")));
        document.add(new Paragraph("\n"));

        // Add questions
        while (rs != null && rs.next()) {
            ResultSet question = questionController.getQuestion(rs.getInt("questionID"));   
            List<String> li = new ArrayList<>();
            li.add(question.getString("correctAnswer"));
            li.add(question.getString("wrongAnswer1"));
            li.add(question.getString("wrongAnswer2"));
            li.add(question.getString("wrongAnswer3"));
            Collections.shuffle(li);              

            // Format question
            Paragraph questionPara = new Paragraph();
            questionPara.add(new Chunk(autoNum + ". ", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            questionPara.add(question.getString("question"));
            document.add(questionPara);
            
            // Format answers
            document.add(new Paragraph("A. " + li.get(0)));
            document.add(new Paragraph("B. " + li.get(1)));
            document.add(new Paragraph("C. " + li.get(2)));
            document.add(new Paragraph("D. " + li.get(3)));
            document.add(new Paragraph("\n"));
            
            autoNum++;
        }
        
        document.add(new Paragraph("End of Exam", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
    } finally {
        if (document != null && document.isOpen()) {
            document.close();
        }
    }
    }

}
