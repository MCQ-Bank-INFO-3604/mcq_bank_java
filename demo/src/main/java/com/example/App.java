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
        

        //READING FROM A CSV
        CSVImporter csvImporter = new CSVImporter();
        String cFilename = "src\\main\\java\\com\\example\\courses_topics_subtopics.csv";
        System.out.println("Insert course details from CSV: ");
        csvImporter.importCourseTopicSubtopicFromCSV(cFilename);

        String qFilename = "src\\main\\java\\com\\example\\questions.csv";
        System.out.println("Inserts questions from CSV: ");
        csvImporter.importQuestionsFromCSV(qFilename);

        examController.insertExam("Test Exam", 1, null);

        examController.addQuestionToExam(1,1);
        examController.addQuestionToExam(1,2);
        examController.addQuestionToExam(1,3);
        examController.addQuestionToExam(1,4);
        examController.addQuestionToExam(1,5);
        examController.addQuestionToExam(1,6);
        examController.addQuestionToExam(1,7);
        examController.addQuestionToExam(1,8);
        examController.addQuestionToExam(1,9);
        examController.addQuestionToExam(1,10);

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
