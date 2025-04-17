package com.example.controllers;

import java.io.*;
import java.sql.*;

public class CSVImporter {

    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    private QuestionController questionController = new QuestionController();

    public void importCourseTopicSubtopicFromCSV(String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] values = line.split(",", -1);
                if (values.length < 4) {
                    System.err.println("Skipping incomplete line: " + line);
                    continue;
                }

                String courseCode = values[0].trim();
                String courseName = values[1].trim();
                String topicName = values[2].trim();
                String subtopicName = values[3].trim();

                try (Connection conn = DriverManager.getConnection(DB_URL)) {    
                    int courseID = getOrInsertCourse(conn, courseCode, courseName);
                    int topicID = getOrInsertTopic(conn, topicName, courseID);
                    int subtopicID = getOrInsertSubtopic(conn, subtopicName, topicID);

                    System.out.println("Course=" + courseName + 
                                       ", ID= " + courseID + 
                                       ", Topic=" + topicName +
                                       ", ID= " + topicID + 
                                       ", Subtopic=" + subtopicName +
                                       ", ID= " + subtopicID);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private int getOrInsertCourse(Connection conn, String code, String name) throws SQLException {
        String select = "SELECT courseID FROM courses WHERE courseCode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("courseID");
        }

        String insert = "INSERT INTO courses (courseCode, courseName) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("Failed to insert course: " + code);
    }

    private int getOrInsertTopic(Connection conn, String name, int courseID) throws SQLException {
        String select = "SELECT topicID FROM topics WHERE topicName = ? AND courseID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, name);
            stmt.setInt(2, courseID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("topicID");
        }

        String insert = "INSERT INTO topics (topicName, courseID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setInt(2, courseID);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("Failed to insert topic: " + name);
    }

    private int getOrInsertSubtopic(Connection conn, String name, int topicID) throws SQLException {
        String select = "SELECT subtopicID FROM subtopics WHERE subtopicName = ? AND topicID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, name);
            stmt.setInt(2, topicID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("subtopicID");
        }

        String insert = "INSERT INTO subtopics (subtopicName, topicID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setInt(2, topicID);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("Failed to insert subtopic: " + name);
    }

    public void importQuestionsFromCSV(String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            
            if (is == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }
            
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] values = line.split(",", -1);
                if (values.length < 18) {
                    System.err.println("Skipping incomplete line: " + line);
                    continue;
                }

                // Extract question details
                String question = values[0].trim();
                String correctAnswer = values[1].trim();
                String wrong1 = values[2].trim();
                String wrong2 = values[3].trim();
                String wrong3 = values[4].trim();
                int course = Integer.parseInt(values[5].trim());
                int topic = Integer.parseInt(values[6].trim());
                int subTopic = Integer.parseInt(values[7].trim());
                float difficulty = Float.parseFloat(values[8].trim());
                float performance = Float.parseFloat(values[9].trim());
                float discrimination = Float.parseFloat(values[10].trim());
                boolean hasImage = Boolean.parseBoolean(values[11].trim());
                String questionImagePath = values[12].trim();
                String correctAnswerImagePath = values[13].trim();
                String wrongAnswer1ImagePath = values[14].trim();
                String wrongAnswer2ImagePath = values[15].trim();
                String wrongAnswer3ImagePath = values[16].trim();
                String comment = values[17].trim();

                convertToEmptyStringIfNull(question);
                convertToEmptyStringIfNull(correctAnswer);
                convertToEmptyStringIfNull(wrong1);
                convertToEmptyStringIfNull(wrong2);
                convertToEmptyStringIfNull(wrong3);
                
                convertToEmptyStringIfNull(questionImagePath);
                convertToEmptyStringIfNull(correctAnswerImagePath);
                convertToEmptyStringIfNull(wrongAnswer1ImagePath);
                convertToEmptyStringIfNull(wrongAnswer2ImagePath);
                convertToEmptyStringIfNull(wrongAnswer3ImagePath);
                
                // Insert question using QuestionController
                questionController.insertQuestion(
                    question, correctAnswer, wrong1, wrong2, wrong3, course, topic, subTopic,
                    difficulty, performance, discrimination, hasImage, questionImagePath,
                    correctAnswerImagePath, wrongAnswer1ImagePath, wrongAnswer2ImagePath,
                    wrongAnswer3ImagePath, comment
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertToEmptyStringIfNull(String str) {
        if (str == null || str.equals("null")) {
            return ""; // Convert null to empty string
        } else {
            return str; // Return the original string if not null
        }
    }
}
