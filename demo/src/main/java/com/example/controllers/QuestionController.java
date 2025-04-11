package com.example.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionController {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    public boolean insertQuestion(String question, String correctAnswer, String wrong1, String wrong2, String wrong3, Integer course, Integer topic, Integer subTopic, Float difficulty, Float performance, Float discrimination, Boolean hasImage, String questionImagePath, String correctAnswerImagePath, String wrongAnswer1ImagePath, String wrongAnswer2ImagePath, String wrongAnswer3ImagePath, String comment) {
        String sql = "INSERT INTO questions (question, correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, course, topic, subTopic, difficulty, performance, discrimination, hasImage, questionImagePath, correctAnswerImagePath, wrongAnswer1ImagePath, wrongAnswer2ImagePath, wrongAnswer3ImagePath, comment, dateCreated, lastEdited, timesUsed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question);
            pstmt.setString(2, correctAnswer);
            pstmt.setString(3, wrong1);
            pstmt.setString(4, wrong2);
            pstmt.setString(5, wrong3);
            pstmt.setInt(6, course);
            pstmt.setInt(7, topic);
            pstmt.setInt(8, subTopic);
            pstmt.setFloat(9, difficulty);
            pstmt.setFloat(10, performance);
            pstmt.setFloat(11, discrimination);
            pstmt.setBoolean(12, hasImage);
            pstmt.setString(13, questionImagePath);
            pstmt.setString(14, correctAnswerImagePath);
            pstmt.setString(15, wrongAnswer1ImagePath);
            pstmt.setString(16, wrongAnswer2ImagePath);
            pstmt.setString(17, wrongAnswer3ImagePath);
            pstmt.setString(18, comment);
            pstmt.setString(19, date);
            pstmt.setString(20, date);
            pstmt.setInt(21, 0); // timesUsed
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public ResultSet getQuestion(int questionID){
        
        String sql = "SELECT * FROM questions WHERE questionID = '" + questionID + "';";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        
    }

    public boolean deleteQuestion(int questionID){
        
        String sql = "DELETE FROM questions WHERE questionID = '" + questionID + "';";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0; // Return true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }        
    }

    public boolean editQuestion(int questionID, String question, String correctAnswer, String wrong1, String wrong2, String wrong3, Integer course, Integer topic, Integer subTopic, Float difficulty, Float performance, Float discrimination, Boolean hasImage, String questionImagePath, String correctAnswerImagePath, String wrongAnswer1ImagePath, String wrongAnswer2ImagePath, String wrongAnswer3ImagePath, String comment) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String sql = "UPDATE questions " +
                    "SET question = ?, " +
                    "correctAnswer = ?, " +
                    "wrongAnswer1 = ?, " +
                    "wrongAnswer2 = ?, " +
                    "wrongAnswer3 = ?, " +
                    "course = ?, " +
                    "topic = ?, " +
                    "subTopic = ?, " +
                    "difficulty = ?, " +
                    "performance = ?, " +
                    "discrimination = ?, " +
                    "hasImage = ?, " +
                    "questionImagePath = ?, " +
                    "correctAnswerImagePath = ?, " +
                    "wrongAnswer1ImagePath = ?, " +
                    "wrongAnswer2ImagePath = ?, " +
                    "wrongAnswer3ImagePath = ?, " +
                    "comment = ?, " +
                    "lastEdited = ? " +
                    "WHERE questionID = ?;";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question);
            pstmt.setString(2, correctAnswer);
            pstmt.setString(3, wrong1);
            pstmt.setString(4, wrong2);
            pstmt.setString(5, wrong3);
            pstmt.setInt(6, course);
            pstmt.setInt(7, topic);
            pstmt.setInt(8, subTopic);
            pstmt.setFloat(9, difficulty);
            pstmt.setFloat(10, performance);
            pstmt.setFloat(11, discrimination);
            pstmt.setBoolean(12, hasImage);
            pstmt.setString(13, questionImagePath);
            pstmt.setString(14, correctAnswerImagePath);
            pstmt.setString(15, wrongAnswer1ImagePath);
            pstmt.setString(16, wrongAnswer2ImagePath);
            pstmt.setString(17, wrongAnswer3ImagePath);
            pstmt.setString(18, comment);
            pstmt.setString(19, date);
            pstmt.setInt(20, questionID);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public void updateTimesUsed(int questionID) {
        String countExamsSql = "SELECT COUNT(*) AS examCount " +
                               "FROM ExamQuestions eq " +
                               "JOIN exams e ON eq.examID = e.examID " +
                               "WHERE eq.questionID = ? AND e.lastUsed IS NOT NULL AND e.lastUsed != ''";

        String updateQuestionSql = "UPDATE questions SET timesUsed = ? WHERE questionID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement countStmt = conn.prepareStatement(countExamsSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuestionSql)) {

            // Count the number of exams where the question is used and lastUsed is not null or empty
            countStmt.setInt(1, questionID);
            ResultSet rs = countStmt.executeQuery();
            int examCount = rs.next() ? rs.getInt("examCount") : 0;

            // Update the timesUsed attribute of the question
            updateStmt.setInt(1, examCount);
            updateStmt.setInt(2, questionID);
            updateStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*
    public ArrayList<String> getDistinctCourses() {
        ArrayList<String> courses = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT course FROM questions");
            while (rs.next()) {
                String course = rs.getString("course");
                if (course != null && !course.isEmpty()) {
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public ArrayList<String> getDistinctTopics() {
        ArrayList<String> topics = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT topic FROM questions");
            while (rs.next()) {
                String topic = rs.getString("topic");
                if (topic != null && !topic.isEmpty()) {
                    topics.add(topic);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    public ArrayList<String> getDistinctSubtopics() {
        ArrayList<String> subtopics = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT subTopic FROM questions");
            while (rs.next()) {
                String subtopic = rs.getString("subTopic");
                if (subtopic != null && !subtopic.isEmpty()) {
                    subtopics.add(subtopic);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subtopics;
    }
*/
    public ResultSet getQuestionsWithFilter(String sqlQuery) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getQuestionsWithFilter() {
        return getQuestionsWithFilter("SELECT * FROM questions ORDER BY questionID");
    }
}