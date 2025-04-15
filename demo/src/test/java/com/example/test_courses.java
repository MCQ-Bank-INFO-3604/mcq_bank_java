package com.example;

import com.example.models.Courses;
import com.example.controllers.CoursesController;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

'Unit Tests'
class test_courses {

    @Test
    void testCreateCoursesTable() {
        Courses courses = new Courses();
        assertDoesNotThrow(() -> courses.createCoursesTable(), "Creating the courses table should not throw an exception.");
    }

    @Test
    void testDeleteCoursesTable() {
        Courses courses = new Courses();
        assertDoesNotThrow(() -> courses.deleteCoursesTable(), "Deleting the courses table should not throw an exception.");
    }
}

'Integration Tests'
class test_courses {

    @Test
    void testInsertCourse() {
        CoursesController controller = new CoursesController();
        assertDoesNotThrow(() -> controller.insertCourse(
            "Computer Science", "Programming", "Basics"
        ), "Inserting a course should not throw an exception.");
    }

    @Test
    void testGetCourse() throws SQLException {
        CoursesController controller = new CoursesController();
        controller.insertCourse("Computer Science", "Programming", "Basics");

        ResultSet resultSet = controller.getCourse(1);
        assertNotNull(resultSet, "ResultSet should not be null.");
        assertTrue(resultSet.next(), "ResultSet should have at least one row.");
        assertEquals("Computer Science", resultSet.getString("course"), "The course name should match.");
    }

    @Test
    void testDeleteCourse() throws SQLException {
        CoursesController controller = new CoursesController();
        controller.insertCourse("Computer Science", "Programming", "Basics");

        boolean result = controller.deleteCourse(1);
        assertTrue(result, "Deleting a course should return true.");
    }
}

