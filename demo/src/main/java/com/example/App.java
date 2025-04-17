package com.example;

import com.example.controllers.ExamController;
import com.example.controllers.QuestionController;
import com.example.models.Courses;
import com.example.models.Exam;
import com.example.models.ExamQuestions;
import com.example.models.Question;
import com.example.models.Subtopics;
import com.example.models.Topics;
import com.example.controllers.CSVImporter;
import com.example.views.Home;
import com.formdev.flatlaf.FlatLightLaf;


public class App {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

        //Create Models
        Question question = new Question();
        Exam exam = new Exam();
        ExamQuestions examQuestions = new ExamQuestions();
        Courses courses = new Courses();
        Topics topics = new Topics();
        Subtopics subtopics = new Subtopics();

        //Create Contollers
        QuestionController questionController = new QuestionController();
        ExamController examController = new ExamController();

        //Create test data from CSV
        CSVImporter csvImporter = new CSVImporter();
        String cResource = "com/example/courses_topics_subtopics.csv";
        String qResource = "com/example/questions.csv";

        System.out.println("Importing courses, topics, and subtopics from CSV...");
        csvImporter.importCourseTopicSubtopicFromCSV(cResource);

        System.out.println("Importing questions from CSV...");
        csvImporter.importQuestionsFromCSV(qResource);

        //Create test exam and add questions to it
        System.out.println("Insert test exam and add questions to it");
        csvImporter.createTestExamWithQuestions();

        //Create Views
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
            ex.printStackTrace();
        }

        new Home();
    }
}
