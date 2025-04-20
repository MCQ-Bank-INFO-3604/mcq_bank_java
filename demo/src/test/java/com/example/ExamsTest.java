package com.example;

import com.example.controllers.ExamController;
import com.example.models.Exam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExamsTest {
    private ExamController controller;
    private Exam model;
    private static final String TEST_DB_URL = "jdbc:sqlite:test_mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    private int testCourseId;
    private int testQuestionId;

    @BeforeEach
    void setUp() throws Exception {
        // Configure test database connections
        controller = new ExamController() {
            @Override
            protected java.sql.Connection getConnection() throws SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };

        model = new Exam() {
            @Override
            protected java.sql.Connection getConnection() throws SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };

        // Setup prerequisite tables
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Create courses table
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "courseCode TEXT NOT NULL," +
                    "courseName TEXT NOT NULL);");
            
            // Create questions table
            stmt.execute("CREATE TABLE IF NOT EXISTS questions (" +
                    "questionID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "question TEXT NOT NULL," +
                    "correctAnswer TEXT NOT NULL," +
                    "wrongAnswer1 TEXT NOT NULL," +
                    "wrongAnswer2 TEXT NOT NULL," +
                    "wrongAnswer3 TEXT NOT NULL," +
                    "lastUsed TEXT);");

            // Create exam-questions relationship table
            stmt.execute("CREATE TABLE IF NOT EXISTS ExamQuestions (" +
                    "examID INTEGER NOT NULL," +
                    "questionID INTEGER NOT NULL," +
                    "PRIMARY KEY (examID, questionID));");

            // Enable foreign keys
            stmt.execute("PRAGMA foreign_keys = ON");

            // Insert test data
            stmt.executeUpdate("INSERT INTO courses (courseCode, courseName) VALUES ('TEST101', 'Test Course')");
            var rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testCourseId = rs.getInt(1);
            
            stmt.executeUpdate("INSERT INTO questions (question, correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3) VALUES " +
                    "('Sample question', 'Correct', 'Wrong1', 'Wrong2', 'Wrong3')");
            rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testQuestionId = rs.getInt(1);
        }

        // Initialize exams table
        model.deleteExamTable();
        model.createExamTable();
    }

    @AfterEach
    void tearDown() {
        // Cleanup test database
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS exams");
            stmt.execute("DROP TABLE IF EXISTS ExamQuestions");
            stmt.execute("DROP TABLE IF EXISTS questions");
            stmt.execute("DROP TABLE IF EXISTS courses");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertExam() throws Exception {
        assertTrue(controller.insertExam("Midterm Exam", testCourseId, "2023-01-01"),
                "Should insert new exam successfully");
        
        ResultSet exams = controller.getExamsWithFilter();
        assertTrue(exams.next(), "Should retrieve inserted exam");
        assertEquals("Midterm Exam", exams.getString("examTitle"));
    }

    @Test
    void testDeleteExam() throws Exception {
        // Create test exam
        controller.insertExam("Final Exam", testCourseId, "2023-01-01");
        ResultSet rs = controller.getExamsWithFilter();
        rs.next();
        int examId = rs.getInt("examID");
        
        assertTrue(controller.deleteExam(examId), "Should delete existing exam");
        
        ResultSet deleted = controller.getExam(examId);
        assertFalse(deleted.next(), "Exam should no longer exist");
    }

    @Test
    void testAddRemoveQuestion() throws Exception {
        // Create exam
        controller.insertExam("Quiz", testCourseId, "2023-01-01");
        ResultSet rs = controller.getExamsWithFilter();
        rs.next();
        int examId = rs.getInt("examID");
        
        // Test adding question
        controller.addQuestionToExam(examId, testQuestionId);
        ResultSet examQuestions = controller.getQuestionsFromExam(examId);
        assertTrue(examQuestions.next(), "Should have added question to exam");
        
        // Verify question count update
        ResultSet exam = controller.getExam(examId);
        assertEquals(1, exam.getInt("numQuestions"));
        
        // Test removing question
        controller.removeQuestionFromExam(examId, testQuestionId);
        examQuestions = controller.getQuestionsFromExam(examId);
        assertFalse(examQuestions.next(), "Should have removed question from exam");
    }

    @Test
    void testExamEditing() throws Exception {
        // Create test exam
        controller.insertExam("Old Title", testCourseId, "2023-01-01");
        ResultSet rs = controller.getExamsWithFilter();
        rs.next();
        int examId = rs.getInt("examID");
        
        // Edit exam
        assertTrue(controller.editExam(examId, "New Title", testCourseId, "2023-02-01"), 
                "Should update exam successfully");
        
        ResultSet updatedExam = controller.getExam(examId);
        assertTrue(updatedExam.next());
        assertEquals("New Title", updatedExam.getString("examTitle"));
        assertEquals("2023-02-01", updatedExam.getString("lastUsed"));
    }

    @Test
    void testGetExamsWithFilter() throws Exception {
        controller.insertExam("Exam 1", testCourseId, "2023-01-01");
        controller.insertExam("Exam 2", testCourseId, "2023-02-01");
        
        // Test default filter
        ResultSet rs = controller.getExamsWithFilter();
        ArrayList<String> examTitles = new ArrayList<>();
        while(rs.next()) {
            examTitles.add(rs.getString("examTitle"));
        }
        assertEquals(2, examTitles.size(), "Should return all exams");
        
        // Test custom filter
        rs = controller.getExamsWithFilter("SELECT * FROM exams WHERE examTitle LIKE '%1'");
        assertTrue(rs.next(), "Should find filtered exam");
        assertEquals("Exam 1", rs.getString("examTitle"));
    }

    @Test
    void testQuestionLastUsedUpdate() throws Exception {
        // Create exam and add question
        controller.insertExam("Test Exam", testCourseId, "2023-01-01");
        ResultSet rs = controller.getExamsWithFilter();
        rs.next();
        int examId = rs.getInt("examID");
        controller.addQuestionToExam(examId, testQuestionId);
        
        // Edit exam with new lastUsed date
        controller.editExam(examId, "Updated Exam", testCourseId, "2023-12-31");
        
        // Verify question lastUsed update
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            ResultSet question = stmt.executeQuery("SELECT lastUsed FROM questions WHERE questionID = " + testQuestionId);
            assertTrue(question.next());
            assertEquals("2023-12-31", question.getString("lastUsed"));
        }
    }
}