/*
package com.example;

import com.example.models.Exam;
import com.example.controllers.ExamController;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

'Unit Tests'
class test_exam {

    @Test
    void testCreateExamTable() {
        Exam exam = new Exam();
        assertDoesNotThrow(() -> exam.createExamTable(), "Creating the exam table should not throw an exception.");
    }

    @Test
    void testDeleteExamTable() {
        Exam exam = new Exam();
        assertDoesNotThrow(() -> exam.deleteExamTable(), "Deleting the exam table should not throw an exception.");
    }
}

'Integration Tests'
class test_exam {

    @Test
    void testInsertExam() {
        ExamController controller = new ExamController();
        assertDoesNotThrow(() -> controller.insertExam(
            "Sample Exam", 10, "Computer Science", "Programming", "Basics"
        ), "Inserting an exam should not throw an exception.");
    }

    @Test
    void testGetExam() throws SQLException {
        ExamController controller = new ExamController();
        controller.insertExam("Sample Exam", 10, "Computer Science", "Programming", "Basics");

        ResultSet resultSet = controller.getExam(1);
        assertNotNull(resultSet, "ResultSet should not be null.");
        assertTrue(resultSet.next(), "ResultSet should have at least one row.");
        assertEquals("Sample Exam", resultSet.getString("examTitle"), "The exam title should match.");
    }

    @Test
    void testDeleteExam() throws SQLException {
        ExamController controller = new ExamController();
        controller.insertExam("Sample Exam", 10, "Computer Science", "Programming", "Basics");

        boolean result = controller.deleteExam(1);
        assertTrue(result, "Deleting an exam should return true.");
    }
}
*/