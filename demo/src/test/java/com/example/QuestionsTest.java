package com.example;

import com.example.controllers.QuestionController;
import com.example.models.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuestionsTest {
    private QuestionController controller;
    private Question model;
    private static final String TEST_DB_URL = "jdbc:sqlite:test_mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    private int testCourseId;
    private int testTopicId;
    private int testSubtopicId;

    @BeforeEach
    void setUp() throws Exception {
        // Configure test database connections
        controller = new QuestionController() {
            @Override
            protected java.sql.Connection getConnection() throws java.sql.SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };

        model = new Question() {
            @Override
            protected java.sql.Connection getConnection() throws java.sql.SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };

        // Setup prerequisite tables
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Enable foreign keys
            stmt.execute("PRAGMA foreign_keys = ON");
            
            // Create courses table
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "courseCode TEXT NOT NULL," +
                    "courseName TEXT NOT NULL);");
            
            // Create topics table
            stmt.execute("CREATE TABLE IF NOT EXISTS topics (" +
                    "topicID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "topicName TEXT NOT NULL," +
                    "courseID INTEGER NOT NULL);");

            // Create subtopics table
            stmt.execute("CREATE TABLE IF NOT EXISTS subtopics (" +
                    "subtopicID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "subtopicName TEXT NOT NULL," +
                    "topicID INTEGER NOT NULL);");

            // Insert test data
            stmt.executeUpdate("INSERT INTO courses (courseCode, courseName) VALUES ('TEST101', 'Test Course')");
            var rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testCourseId = rs.getInt(1);
            
            stmt.executeUpdate("INSERT INTO topics (topicName, courseID) VALUES ('Test Topic', " + testCourseId + ")");
            rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testTopicId = rs.getInt(1);
            
            stmt.executeUpdate("INSERT INTO subtopics (subtopicName, topicID) VALUES ('Test Subtopic', " + testTopicId + ")");
            rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testSubtopicId = rs.getInt(1);
        }

        // Initialize questions table
        model.deleteQuestionTable();
        model.createQuestionTable();
    }

    @AfterEach
    void tearDown() {
        // Cleanup test database
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS questions");
            stmt.execute("DROP TABLE IF EXISTS subtopics");
            stmt.execute("DROP TABLE IF EXISTS topics");
            stmt.execute("DROP TABLE IF EXISTS courses");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertQuestion() throws Exception {
        boolean result = controller.insertQuestion(
            "What is 2+2?", 
            "4", 
            "3", 
            "5", 
            "6", 
            testCourseId,
            testTopicId,
            testSubtopicId,
            1.0f,
            0.8f,
            0.5f,
            false,
            null,
            null,
            null,
            null,
            null,
            "Basic math question"
        );
        
        assertTrue(result, "Should insert question successfully");
        
        ResultSet questions = controller.getQuestionsWithFilter();
        assertTrue(questions.next(), "Should retrieve inserted question");
        assertEquals("What is 2+2?", questions.getString("question"));
        assertEquals("4", questions.getString("correctAnswer"));
        assertEquals(testCourseId, questions.getInt("course"));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        // Insert test question
        controller.insertQuestion(
            "Sample question", 
            "Correct", 
            "Wrong1", 
            "Wrong2", 
            "Wrong3", 
            testCourseId,
            testTopicId,
            testSubtopicId,
            1.0f,
            0.8f,
            0.5f,
            false,
            null,
            null,
            null,
            null,
            null,
            null
        );
        
        ResultSet rs = controller.getQuestionsWithFilter();
        rs.next();
        int questionId = rs.getInt("questionID");
        
        // Test valid deletion
        assertTrue(controller.deleteQuestion(questionId), "Should delete existing question");
        
        // Test invalid deletion
        assertFalse(controller.deleteQuestion(999), "Should return false for non-existent question");
    }

    @Test
    void testEditQuestion() throws Exception {
        // Insert test question
        controller.insertQuestion(
            "Original question", 
            "Correct", 
            "Wrong1", 
            "Wrong2", 
            "Wrong3", 
            testCourseId,
            testTopicId,
            testSubtopicId,
            1.0f,
            0.8f,
            0.5f,
            false,
            null,
            null,
            null,
            null,
            null,
            null
        );
        
        ResultSet rs = controller.getQuestionsWithFilter();
        rs.next();
        int questionId = rs.getInt("questionID");
        
        // Edit question
        boolean result = controller.editQuestion(
            questionId,
            "Updated question",
            "New correct",
            "New wrong1",
            "New wrong2",
            "New wrong3",
            testCourseId,
            testTopicId,
            testSubtopicId,
            2.0f,
            0.9f,
            0.6f,
            true,
            "/path/image.jpg",
            "/path/correct.jpg",
            "/path/wrong1.jpg",
            "/path/wrong2.jpg",
            "/path/wrong3.jpg",
            "Updated comment"
        );
        
        assertTrue(result, "Should update question successfully");
        
        ResultSet updated = controller.getQuestion(questionId);
        assertTrue(updated.next());
        assertEquals("Updated question", updated.getString("question"));
        assertEquals("New correct", updated.getString("correctAnswer"));
        assertEquals(2.0f, updated.getFloat("difficulty"));
        assertTrue(updated.getBoolean("hasImage"));
    }

    @Test
    void testGetQuestionsWithFilter() throws Exception {
        // Insert test questions
        controller.insertQuestion("Question 1", "C1", "W1", "W2", "W3", testCourseId, testTopicId, testSubtopicId, 1.0f, 0.8f, 0.5f, false, null, null, null, null, null, null);
        controller.insertQuestion("Question 2", "C2", "W1", "W2", "W3", testCourseId, testTopicId, testSubtopicId, 2.0f, 0.9f, 0.6f, true, "/path", null, null, null, null, null);
        
        // Test default filter
        ResultSet rs = controller.getQuestionsWithFilter();
        ArrayList<String> questions = new ArrayList<>();
        while(rs.next()) {
            questions.add(rs.getString("question"));
        }
        assertEquals(2, questions.size(), "Should return all questions");
        
        // Test custom filter
        rs = controller.getQuestionsWithFilter("SELECT * FROM questions WHERE difficulty > 1.5");
        assertTrue(rs.next(), "Should find filtered question");
        assertEquals("Question 2", rs.getString("question"));
    }

    @Test
    void testQuestionLifecycle() throws Exception {
        // Test full CRUD lifecycle
        // Create
        boolean insertResult = controller.insertQuestion(
            "Lifecycle question", 
            "Correct", 
            "W1", 
            "W2", 
            "W3", 
            testCourseId,
            testTopicId,
            testSubtopicId,
            1.0f,
            0.8f,
            0.5f,
            false,
            null,
            null,
            null,
            null,
            null,
            null
        );
        assertTrue(insertResult);
        
        // Read
        ResultSet rs = controller.getQuestionsWithFilter();
        assertTrue(rs.next());
        int questionId = rs.getInt("questionID");
        
        // Update
        boolean updateResult = controller.editQuestion(
            questionId,
            "Updated lifecycle question",
            "New correct",
            "New W1",
            "New W2",
            "New W3",
            testCourseId,
            testTopicId,
            testSubtopicId,
            2.0f,
            0.9f,
            0.6f,
            true,
            "/path",
            null,
            null,
            null,
            null,
            "Updated comment"
        );
        assertTrue(updateResult);
        
        // Delete
        boolean deleteResult = controller.deleteQuestion(questionId);
        assertTrue(deleteResult);
        
        // Verify deletion
        ResultSet deleted = controller.getQuestion(questionId);
        assertFalse(deleted.next());
    }
}