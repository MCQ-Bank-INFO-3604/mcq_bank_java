package com.example;

import com.example.controllers.TopicsController;
import com.example.models.Topics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TopicsTest {
    private TopicsController controller;
    private Topics model;
    private static final String TEST_DB_URL = "jdbc:sqlite:test_mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    private int testCourseId;

    @BeforeEach
    void setUp() throws Exception {
        // Configure test database connections
        controller = new TopicsController() {
            @Override
            protected Connection getConnection() throws java.sql.SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };

        model = new Topics() {
            @Override
            protected Connection getConnection() throws java.sql.SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };

        // Setup courses table and test course
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Create courses table for foreign key constraint
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "courseCode TEXT NOT NULL," +
                    "courseName TEXT NOT NULL);");
            
            // Insert test course
            stmt.executeUpdate("INSERT INTO courses (courseCode, courseName) VALUES ('TEST101', 'Test Course')");
            var rs = stmt.executeQuery("SELECT last_insert_rowid()");
            testCourseId = rs.getInt(1);
        }

        // Initialize topics table
        model.deleteTopicsTable();
        model.createTopicsTable();
    }

    @AfterEach
    void tearDown() {
        // Cleanup test database
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS topics");
            stmt.execute("DROP TABLE IF EXISTS courses");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertTopic() {
        // Test successful insertion
        assertTrue(controller.insertTopic("Algebra", testCourseId), 
            "Should insert new topic successfully");
        
        // Test duplicate prevention
        assertFalse(controller.insertTopic("Algebra", testCourseId), 
            "Should prevent duplicate topic names per course");
    }

    @Test
    void testGetAllTopics() {
        controller.insertTopic("Geometry", testCourseId);
        controller.insertTopic("Trigonometry", testCourseId);
        
        ArrayList<String> topics = controller.getAllTopics();
        assertEquals(2, topics.size(), "Should retrieve all topics");
        assertTrue(topics.contains("Geometry"), "Should contain Geometry");
        assertTrue(topics.contains("Trigonometry"), "Should contain Trigonometry");
    }

    @Test
    void testDeleteTopic() {
        controller.insertTopic("Calculus", testCourseId);
        Integer topicId = controller.getTopicIdByNameAndCourseId("Calculus", testCourseId);
        
        // Test valid deletion
        assertTrue(controller.deleteTopic(topicId), 
            "Should delete existing topic");
        
        // Test invalid deletion
        assertFalse(controller.deleteTopic(999), 
            "Should return false for non-existent topic ID");
    }

    @Test
    void testUpdateTopic() {
        controller.insertTopic("Statistics", testCourseId);
        Integer topicId = controller.getTopicIdByNameAndCourseId("Statistics", testCourseId);
        
        // Test valid update
        assertTrue(controller.updateTopic(topicId, "Advanced Statistics"), 
            "Should update topic name");
        
        // Test invalid update
        assertFalse(controller.updateTopic(999, "Invalid Topic"), 
            "Should return false for non-existent topic ID");
    }

    @Test
    void testGetTopicsByCourseId() {
        // Create second course
        int otherCourseId = createTestCourse("MATH202", "Advanced Math");
        
        // Insert topics for both courses
        controller.insertTopic("Algebra", testCourseId);
        controller.insertTopic("Geometry", otherCourseId);
        
        // Test course filtering
        ArrayList<String[]> topics = controller.getTopicsByCourseId(testCourseId);
        assertEquals(1, topics.size(), "Should only return topics for specified course");
        assertEquals("Algebra", topics.get(0)[0], "Should return correct topic name");
    }

    @Test
    void testGetAllTopicsWithIDs() {
        controller.insertTopic("Physics", testCourseId);
        controller.insertTopic("Chemistry", testCourseId);
        
        ArrayList<String[]> topics = controller.getAllTopicsWithIDs();
        assertEquals(2, topics.size(), "Should return all topics with IDs");
        assertEquals(2, topics.get(0).length, "Each entry should have name and ID");
        assertNotNull(topics.get(0)[1], "Topic ID should not be null");
    }

    @Test
    void testGetTopicNameById() {
        controller.insertTopic("Biology", testCourseId);
        Integer topicId = controller.getTopicIdByNameAndCourseId("Biology", testCourseId);
        
        assertEquals("Biology", controller.getTopicNameById(topicId), 
            "Should return correct topic name");
        assertNull(controller.getTopicNameById(999), 
            "Should return null for invalid topic ID");
    }

    @Test
    void testGetTopicIdByNameAndCourseId() {
        controller.insertTopic("Geology", testCourseId);
        
        // Test valid lookup
        Integer topicId = controller.getTopicIdByNameAndCourseId("Geology", testCourseId);
        assertNotNull(topicId, "Should find topic by name and course ID");
        
        // Test case sensitivity
        Integer caseSensitiveId = controller.getTopicIdByNameAndCourseId("geology", testCourseId);
        assertNull(caseSensitiveId, "Should be case-sensitive");
        
        // Test invalid lookup
        Integer invalidId = controller.getTopicIdByNameAndCourseId("Astronomy", testCourseId);
        assertNull(invalidId, "Should return null for non-existent topic");
    }

    @Test
    void testTopicsTableCreation() {
        // Verify table structure through successful insert
        boolean result = controller.insertTopic("Test Topic", testCourseId);
        assertTrue(result, "Table should be properly created");
    }

    private int createTestCourse(String code, String name) {
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO courses (courseCode, courseName) VALUES ('" + code + "', '" + name + "')");
            var rs = stmt.executeQuery("SELECT last_insert_rowid()");
            return rs.getInt(1);
        } catch (Exception e) {
            fail("Test course creation failed");
            return -1;
        }
    }
}