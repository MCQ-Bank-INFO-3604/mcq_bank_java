package com.example;

import com.example.controllers.CoursesController;
import com.example.models.Courses;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private CoursesController controller;
    private Courses model;
    private static final String TEST_DB_URL = "jdbc:sqlite:test_mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    @BeforeEach
    void setUp() {
        // Initialize with test database
        controller = new CoursesController() {
            @Override
            protected Connection getConnection() throws SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };
        
        model = new Courses() {
            @Override
            protected Connection getConnection() throws SQLException {
                return DriverManager.getConnection(TEST_DB_URL);
            }
        };
        
        // Create fresh tables for testing
        model.createCoursesTable();
    }

    @AfterEach
    void tearDown() {
        // Clean up the test database after each test
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS courses;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertCourse() {
        // Test inserting a new course
        Integer courseId = controller.insertCourse("Mathematics", "MATH101");
        assertNotNull(courseId, "Course ID should not be null after insertion");
        
        // Test inserting duplicate course (same name)
        Integer duplicateId = controller.insertCourse("Mathematics", "MATH102");
        assertEquals(courseId, duplicateId, "Should return existing ID for duplicate course name");
        
        // Test inserting duplicate course (same code)
        duplicateId = controller.insertCourse("Math", "MATH101");
        assertEquals(courseId, duplicateId, "Should return existing ID for duplicate course code");
    }

    @Test
    void testGetAllCourseCodes() {
        // Insert test data
        controller.insertCourse("Mathematics", "MATH101");
        controller.insertCourse("Physics", "PHYS201");
        
        // Test retrieving all course codes
        ArrayList<String> codes = controller.getAllCourseCodes();
        assertEquals(2, codes.size(), "Should return 2 course codes");
        assertTrue(codes.contains("MATH101"), "Should contain MATH101");
        assertTrue(codes.contains("PHYS201"), "Should contain PHYS201");
    }

    @Test
    void testGetAllCourses() {
        // Insert test data
        controller.insertCourse("Mathematics", "MATH101");
        controller.insertCourse("Physics", "PHYS201");
        
        // Test retrieving all courses
        ArrayList<String[]> courses = controller.getAllCourses();
        assertEquals(2, courses.size(), "Should return 2 courses");
        
        // Verify structure of returned data
        for (String[] course : courses) {
            assertEquals(3, course.length, "Each course should have 3 elements");
            assertNotNull(course[0], "Course code should not be null");
            assertNotNull(course[1], "Course ID should not be null");
            assertNotNull(course[2], "Course name should not be null");
        }
    }

    @Test
    void testDeleteCourse() {
        // Insert test data
        Integer courseId = controller.insertCourse("Chemistry", "CHEM301");
        
        // Test deleting existing course
        boolean result = controller.deleteCourse(courseId);
        assertTrue(result, "Should return true for successful deletion");
        
        // Verify deletion
        ArrayList<String[]> courses = controller.getAllCourses();
        assertEquals(0, courses.size(), "Courses table should be empty after deletion");
        
        // Test deleting non-existent course
        result = controller.deleteCourse(999);
        assertFalse(result, "Should return false for non-existent course");
    }

    @Test
    void testUpdateCourse() {
        // Insert test data
        Integer courseId = controller.insertCourse("Biology", "BIO101");
        
        // Test updating course
        boolean result = controller.updateCourse(courseId, "BIO102", "Advanced Biology");
        assertTrue(result, "Should return true for successful update");
        
        // Verify update
        String[] course = controller.getAllCourses().get(0);
        assertEquals("BIO102", course[0], "Course code should be updated");
        assertEquals("Advanced Biology", course[2], "Course name should be updated");
        
        // Test updating non-existent course
        result = controller.updateCourse(999, "BIO999", "Non-existent");
        assertFalse(result, "Should return false for non-existent course");
    }

    @Test
    void testGetCourseCodeById() {
        // Insert test data
        Integer courseId = controller.insertCourse("Computer Science", "CS101");
        
        // Test getting course code
        String code = controller.getCourseCodeById(courseId);
        assertEquals("CS101", code, "Should return correct course code");
        
        // Test with invalid ID
        code = controller.getCourseCodeById(999);
        assertNull(code, "Should return null for invalid course ID");
        
        // Test with null ID
        code = controller.getCourseCodeById(null);
        assertNull(code, "Should return null for null course ID");
    }

    @Test
    void testGetCourseIdByName() {
        // Insert test data
        controller.insertCourse("History", "HIST101");
        
        // Test getting course ID
        Integer id = controller.getCourseIdByName("History");
        assertNotNull(id, "Should return non-null ID for existing course");
        
        // Test case insensitivity
        id = controller.getCourseIdByName("history");
        assertNotNull(id, "Should be case insensitive");
        
        // Test with non-existent course
        id = controller.getCourseIdByName("Non-existent");
        assertNull(id, "Should return null for non-existent course");
        
        // Test with null/empty name
        id = controller.getCourseIdByName(null);
        assertNull(id, "Should return null for null name");
        id = controller.getCourseIdByName("");
        assertNull(id, "Should return null for empty name");
    }

    @Test
    void testGetCourseNameById() {
        // Insert test data
        Integer courseId = controller.insertCourse("Economics", "ECON201");
        
        // Test getting course name
        String name = controller.getCourseNameById(courseId);
        assertEquals("Economics", name, "Should return correct course name");
        
        // Test with invalid ID
        name = controller.getCourseNameById(999);
        assertNull(name, "Should return null for invalid course ID");
    }

    @Test
    void testGetCourseIdByCode() {
        // Insert test data
        Integer expectedId = controller.insertCourse("Literature", "LIT301");
        
        // Test getting course ID
        Integer actualId = controller.getCourseIdByCode("LIT301");
        assertEquals(expectedId, actualId, "Should return correct course ID");
        
        // Test with invalid code
        actualId = controller.getCourseIdByCode("INVALID");
        assertNull(actualId, "Should return null for invalid course code");
    }

    @Test
    void testCoursesTableCreation() {
        // Verify table was created by trying to insert data
        Integer courseId = controller.insertCourse("Test Course", "TEST001");
        assertNotNull(courseId, "Should be able to insert into newly created table");
    }
}