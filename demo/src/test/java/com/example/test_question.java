package com.example;

import com.example.models.Question;
import com.example.controllers.QuestionController;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

'Unit Tests'
class test_question {

    @Test
    void testCreateQuestionTable() {
        Question question = new Question();
        assertDoesNotThrow(() -> question.createQuestionTable(), "Creating the question table should not throw an exception.");
    }

    @Test
    void testDeleteQuestionTable() {
        Question question = new Question();
        assertDoesNotThrow(() -> question.deleteQuestionTable(), "Deleting the question table should not throw an exception.");
    }
}

'Integration Tests'
class test_question {

    @Test
    void testInsertQuestion() {
        QuestionController controller = new QuestionController();
        boolean result = controller.insertQuestion(
            "What is Java?", "Programming Language", "Coffee", "Island", "Animal",
            "Computer Science", "Programming", "Basics",
            1.0f, 0.0f, 0.0f
        );
        assertTrue(result, "Inserting a question should return true.");
    }

    @Test
    void testGetQuestion() throws SQLException {
        QuestionController controller = new QuestionController();
        controller.insertQuestion(
            "What is Java?", "Programming Language", "Coffee", "Island", "Animal",
            "Computer Science", "Programming", "Basics",
            1.0f, 0.0f, 0.0f
        );

        ResultSet resultSet = controller.getQuestion(1);
        assertNotNull(resultSet, "ResultSet should not be null.");
        assertTrue(resultSet.next(), "ResultSet should have at least one row.");
        assertEquals("What is Java?", resultSet.getString("question"), "The question text should match.");
    }

    @Test
    void testDeleteQuestion() {
        QuestionController controller = new QuestionController();
        controller.insertQuestion(
            "What is Java?", "Programming Language", "Coffee", "Island", "Animal",
            "Computer Science", "Programming", "Basics",
            1.0f, 0.0f, 0.0f
        );

        boolean result = controller.deleteQuestion(1);
        assertTrue(result, "Deleting a question should return true.");