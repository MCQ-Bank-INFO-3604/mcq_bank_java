package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the application without using CSVImporter.
 */
public class IntegrationTest {

    private static final String TEST_DB_URL = "jdbc:sqlite:test_mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    @BeforeEach
    void setUp() throws Exception {
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            // Create necessary tables for testing
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (courseID INTEGER PRIMARY KEY, courseCode TEXT, courseName TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS topics (topicID INTEGER PRIMARY KEY, topicName TEXT, courseID INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS subtopics (subtopicID INTEGER PRIMARY KEY, subtopicName TEXT, topicID INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS questions (questionID INTEGER PRIMARY KEY, question TEXT, courseID INTEGER, topicID INTEGER, subtopicID INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS exams (examID INTEGER PRIMARY KEY, examTitle TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS ExamQuestions (examID INTEGER, questionID INTEGER, PRIMARY KEY (examID, questionID))");

            // Insert test data
            stmt.execute("INSERT INTO courses (courseCode, courseName) VALUES ('CS101', 'Computer Science Basics')");
            stmt.execute("INSERT INTO topics (topicName, courseID) VALUES ('Introduction to CS', 1)");
            stmt.execute("INSERT INTO subtopics (subtopicName, topicID) VALUES ('History of Computers', 1)");
            for (int i = 1; i <= 10; i++) {
                stmt.execute("INSERT INTO questions (question, courseID, topicID, subtopicID) VALUES ('Question " + i + "', 1, 1, 1)");
            }
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            // Drop all tables after each test
            stmt.execute("DROP TABLE IF EXISTS ExamQuestions");
            stmt.execute("DROP TABLE IF EXISTS exams");
            stmt.execute("DROP TABLE IF EXISTS questions");
            stmt.execute("DROP TABLE IF EXISTS subtopics");
            stmt.execute("DROP TABLE IF EXISTS topics");
            stmt.execute("DROP TABLE IF EXISTS courses");
        }
    }

    @Test
    void testCreateTestExamWithQuestions() throws Exception {
        String examTitle = "Test Exam";

        try (Connection conn = DriverManager.getConnection(TEST_DB_URL)) {
            // Check if the test exam already exists
            if (!isExamInDatabase(conn, examTitle)) {
                // Insert the test exam
                int examID = insertExam(conn, examTitle);

                // Get the first 10 questions from the database
                String query = "SELECT questionID FROM questions LIMIT 10";
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        int questionID = rs.getInt("questionID");

                        // Check if the question is already in the exam
                        if (!isQuestionInExam(conn, examID, questionID)) {
                            // Add the question to the exam
                            addQuestionToExam(conn, examID, questionID);
                        }
                    }
                }

                // Verify the test exam and questions
                ResultSet examRs = conn.createStatement().executeQuery("SELECT * FROM exams WHERE examTitle = 'Test Exam'");
                assertTrue(examRs.next(), "Test exam should be created");
                int examIDFromDb = examRs.getInt("examID");

                ResultSet questionRs = conn.createStatement().executeQuery("SELECT COUNT(*) AS count FROM ExamQuestions WHERE examID = " + examIDFromDb);
                assertTrue(questionRs.next());
                int questionCount = questionRs.getInt("count");
                assertEquals(10, questionCount, "Test exam should have 10 questions");
            }
        }
    }

    private boolean isExamInDatabase(Connection conn, String examTitle) throws Exception {
        String query = "SELECT COUNT(*) FROM exams WHERE examTitle = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, examTitle);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private int insertExam(Connection conn, String examTitle) throws Exception {
        String insert = "INSERT INTO exams (examTitle) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, examTitle);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the generated exam ID
            }
        }
        throw new Exception("Failed to insert exam: " + examTitle);
    }

    private boolean isQuestionInExam(Connection conn, int examID, int questionID) throws Exception {
        String query = "SELECT COUNT(*) FROM ExamQuestions WHERE examID = ? AND questionID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examID);
            stmt.setInt(2, questionID);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private void addQuestionToExam(Connection conn, int examID, int questionID) throws Exception {
        String insert = "INSERT INTO ExamQuestions (examID, questionID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert)) {
            stmt.setInt(1, examID);
            stmt.setInt(2, questionID);
            stmt.executeUpdate();
        }
    }
}
