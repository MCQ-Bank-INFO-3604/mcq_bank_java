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
import java.util.ArrayList;
import java.util.Date;


public class QuestionController {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    public boolean insertQuestion(String question, String correctAnswer, String wrong1, String wrong2, String wrong3, String course, String topic, String subTopic, String difficulty) {
        String sql = "INSERT INTO questions (question, correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, course, topic, subTopic, difficulty, dateCreated, lastEdited, timesUsed, hasImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        // java.util.Date today=new Date();
        // java.sql.Date date=new java.sql.Date(today.getTime()); //your SQL date object
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question);
            pstmt.setString(2, correctAnswer);
            pstmt.setString(3, wrong1);
            pstmt.setString(4, wrong2);
            pstmt.setString(5, wrong3);
            pstmt.setString(6, course);
            pstmt.setString(7, topic);
            pstmt.setString(8, subTopic);
            pstmt.setString(9, difficulty);
            pstmt.setString(10, date);
            pstmt.setString(11, date);
            pstmt.setInt(12, 0);
            pstmt.setInt(13, 0);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }


    public void insertQuestionsFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true; // Flag to track header row
            
            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] values = line.split(","); // Assuming the CSV is comma-separated
                
                // Skip empty lines or lines with insufficient data
                if (values.length < 9) {
                    System.err.println("Skipping incomplete line: " + line);
                    continue;
                }
                
                try {
                    String question = values[0].trim();
                    String correctAnswer = values[1].trim();
                    String wrong1 = values[2].trim();
                    String wrong2 = values[3].trim();
                    String wrong3 = values[4].trim();
                    String course = values[5].trim();
                    String topic = values[6].trim();
                    String subTopic = values[7].trim();
                    String difficulty = values[8].trim();
    
                    // Pass data to the next step
                    insertQuestion(question, correctAnswer, wrong1, wrong2, wrong3, 
                                 course, topic, subTopic, difficulty);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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


    public boolean editQuestion(int questionID, String question, String correctAnswer, String wrong1, String wrong2, String wrong3, String course, String topic, String subTopic, String difficulty) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String sql = "UPDATE questions " +
                    "SET question = '" + question + "' ,"+
                    "correctAnswer = '" + correctAnswer + "' ,"+
                    "wrongAnswer1 = '" + wrong1 + "' ,"+
                    "wrongAnswer2 = '" + wrong2 + "' ,"+
                    "wrongAnswer3= '" + wrong3 + "' ,"+
                    "course = '" + course + "' ,"+
                    "topic = '" + topic + "' ,"+
                    "subTopic = '" + subTopic + "' ,"+
                    "difficulty = '" + difficulty + "' ,"+
                    "lastEdited = '" + date + "' " +
                    "WHERE questionID = '" + questionID + "';";


        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }        
    }

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