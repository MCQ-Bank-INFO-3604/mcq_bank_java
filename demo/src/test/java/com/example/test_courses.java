/*
package com.example;

import com.example.models.Courses;
import com.example.controllers.CoursesController;
import java.sql.ResultSet;
import java.sql.SQLException;

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

class CoursesControllerUnitTests {

    @Test
    void testInsertCourse() {
        CoursesController controller = new CoursesController();
        Integer courseId = controller.insertCourse("Test Course", "TEST101");
        assertNotNull(courseId, "Inserting a course should return a valid course ID.");
    }

    @Test
    void testGetCourseById() throws SQLException {
        CoursesController controller = new CoursesController();
        Integer courseId = controller.insertCourse("Test Course", "TEST101");

        String courseName = controller.getCourseNameById(courseId);
        assertEquals("Test Course", courseName, "The course name should match the inserted value.");
    }

    @Test
    void testUpdateCourse() {
        CoursesController controller = new CoursesController();
        Integer courseId = controller.insertCourse("Old Course", "OLD101");

        boolean updated = controller.updateCourse(courseId, "NEW101", "New Course");
        assertTrue(updated, "Updating the course should return true.");

        String updatedCourseName = controller.getCourseNameById(courseId);
        assertEquals("New Course", updatedCourseName, "The course name should be updated.");
    }

    @Test
    void testDeleteCourse() {
        CoursesController controller = new CoursesController();
        Integer courseId = controller.insertCourse("Test Course", "TEST101");

        boolean deleted = controller.deleteCourse(courseId);
        assertTrue(deleted, "Deleting the course should return true.");

        String courseName = controller.getCourseNameById(courseId);
        assertNull(courseName, "The course should no longer exist.");
    }
}

class CoursesControllerIntegrationTests {

    @Test
    void testInsertAndRetrieveCourse() throws SQLException {
        CoursesController controller = new CoursesController();
        Integer courseId = controller.insertCourse("Integration Course", "INT101");

        ResultSet resultSet = controller.getCourse(courseId);
        assertNotNull(resultSet, "ResultSet should not be null.");
        assertTrue(resultSet.next(), "ResultSet should have at least one row.");
        assertEquals("Integration Course", resultSet.getString("courseName"), "The course name should match.");
        assertEquals("INT101", resultSet.getString("courseCode"), "The course code should match.");
    }

    @Test
    void testInsertAndDeleteCourse() throws SQLException {
        CoursesController controller = new CoursesController();
        Integer courseId = controller.insertCourse("Integration Course", "INT101");

        boolean deleted = controller.deleteCourse(courseId);
        assertTrue(deleted, "Deleting the course should return true.");

        ResultSet resultSet = controller.getCourse(courseId);
        assertFalse(resultSet.next(), "The course should no longer exist.");
    }

    @Test
    void testGetAllCourses() {
        CoursesController controller = new CoursesController();
        controller.insertCourse("Course 1", "C101");
        controller.insertCourse("Course 2", "C102");

        var courses = controller.getAllCourses();
        assertTrue(courses.size() >= 2, "There should be at least two courses in the database.");
    }
}
*/
