package com.example;

import com.example.controllers.SubtopicsController;
import com.example.models.Subtopics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubtopicsTest {
    private SubtopicsController controller;
    private Subtopics model;
    private static final String TEST_DB_URL = "jdbc:sqlite:test_mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    private int testCourseId;
    private int testTopicId;

    @BeforeEach
    void setUp() throws Exception {
        // Configure test database connections
        controller = new SubtopicsController() {
            @Override
            protected Connection getConnection() throws SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };

        model = new Subtopics() {
            @Override
            protected Connection getConnection() throws SQLException {
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
            
            // Create topics table
            stmt.execute("CREATE TABLE IF NOT EXISTS topics (" +
                    "topicID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "topicName TEXT NOT NULL," +
                    "courseID INTEGER NOT NULL," +
                    "FOREIGN KEY(courseID) REFERENCES courses(courseID));");

            // Insert test data
            stmt.executeUpdate("INSERT INTO courses (courseCode, courseName) VALUES ('TEST101', 'Test Course')");
            var rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testCourseId = rs.getInt(1);
            
            stmt.executeUpdate("INSERT INTO topics (topicName, courseID) VALUES ('Test Topic', " + testCourseId + ")");
            rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testTopicId = rs.getInt(1);
        }

        // Initialize subtopics table
        model.deleteSubtopicsTable();
        model.createSubtopicsTable();
    }

    @AfterEach
    void tearDown() {
        // Cleanup test database
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS subtopics");
            stmt.execute("DROP TABLE IF EXISTS topics");
            stmt.execute("DROP TABLE IF EXISTS courses");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertSubtopic() {
        // Test valid insertion
        assertTrue(controller.insertSubtopic("Introduction", testTopicId),
                "Should insert new subtopic successfully");
        
        // Test duplicate prevention
        assertFalse(controller.insertSubtopic("Introduction", testTopicId),
                "Should prevent duplicate subtopic names per topic");
    }

    @Test
    void testGetAllSubtopics() {
        controller.insertSubtopic("Basics", testTopicId);
        controller.insertSubtopic("Advanced", testTopicId);
        
        ArrayList<String> subtopics = controller.getAllSubtopics();
        assertEquals(2, subtopics.size(), "Should retrieve all subtopics");
        assertTrue(subtopics.contains("Basics"), "Should contain Basics");
        assertTrue(subtopics.contains("Advanced"), "Should contain Advanced");
    }

    @Test
    void testDeleteSubtopic() {
        controller.insertSubtopic("Variables", testTopicId);
        Integer subtopicId = controller.getSubtopicIdByNameAndTopicId("Variables", testTopicId);
        
        // Test valid deletion
        assertTrue(controller.deleteSubtopic(subtopicId),
                "Should delete existing subtopic");
        
        // Test invalid deletion
        assertFalse(controller.deleteSubtopic(999),
                "Should return false for non-existent subtopic ID");
    }

    @Test
    void testUpdateSubtopic() {
        controller.insertSubtopic("Functions", testTopicId);
        Integer subtopicId = controller.getSubtopicIdByNameAndTopicId("Functions", testTopicId);
        
        // Test valid update
        assertTrue(controller.updateSubtopic(subtopicId, "Advanced Functions"),
                "Should update subtopic name");
        
        // Test invalid update
        assertFalse(controller.updateSubtopic(999, "Invalid Subtopic"),
                "Should return false for non-existent subtopic ID");
    }

    @Test
    void testGetSubtopicsByTopicId() {
        // Create second topic
        int otherTopicId = createTestTopic("Other Topic");
        
        // Insert subtopics
        controller.insertSubtopic("Data Types", testTopicId);
        controller.insertSubtopic("Algorithms", otherTopicId);
        
        ArrayList<String[]> subtopics = controller.getSubtopicsByTopicId(testTopicId);
        assertEquals(1, subtopics.size(), "Should only return subtopics for specified topic");
        assertEquals("Data Types", subtopics.get(0)[0], "Should return correct subtopic name");
    }

    @Test
    void testGetAllSubtopicsWithIDs() {
        controller.insertSubtopic("OOP", testTopicId);
        controller.insertSubtopic("Design Patterns", testTopicId);
        
        ArrayList<String[]> subtopics = controller.getAllSubtopicsWithIDs();
        assertEquals(2, subtopics.size(), "Should return all subtopics with IDs");
        assertEquals(2, subtopics.get(0).length, "Each entry should have name and ID");
        assertNotNull(subtopics.get(0)[1], "Subtopic ID should not be null");
    }

    @Test
    void testGetSubtopicName() {
        controller.insertSubtopic("Inheritance", testTopicId);
        Integer subtopicId = controller.getSubtopicIdByNameAndTopicId("Inheritance", testTopicId);
        
        assertEquals("Inheritance", controller.getSubtopicName(subtopicId),
                "Should return correct subtopic name");
        assertNull(controller.getSubtopicName(999),
                "Should return null for invalid subtopic ID");
    }

    @Test
    void testGetSubtopicIdByNameAndTopicId() {
        controller.insertSubtopic("Polymorphism", testTopicId);
        
        // Test valid lookup
        Integer subtopicId = controller.getSubtopicIdByNameAndTopicId("Polymorphism", testTopicId);
        assertNotNull(subtopicId, "Should find subtopic by name and topic ID");
        
        // Test case sensitivity
        Integer caseSensitiveId = controller.getSubtopicIdByNameAndTopicId("polymorphism", testTopicId);
        assertNull(caseSensitiveId, "Should be case-sensitive");
        
        // Test invalid lookup
        Integer invalidId = controller.getSubtopicIdByNameAndTopicId("Encapsulation", testTopicId);
        assertNull(invalidId, "Should return null for non-existent subtopic");
    }

    @Test
    void testSubtopicsTableCreation() {
        // Verify table structure through successful insert
        boolean result = controller.insertSubtopic("Test Subtopic", testTopicId);
        assertTrue(result, "Table should be properly created");
    }

    private int createTestTopic(String topicName) {
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO topics (topicName, courseID) VALUES ('" + topicName + "', " + testCourseId + ")");
            var rs = stmt.executeQuery("SELECT last_insert_rowid()");
            return rs.getInt(1);
        } catch (Exception e) {
            fail("Test topic creation failed");
            return -1;
        }
    }
}