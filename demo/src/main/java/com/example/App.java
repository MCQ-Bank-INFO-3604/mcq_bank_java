package com.example;

import java.sql.ResultSet;

import com.example.controllers.ExamController;
import com.example.controllers.QuestionController;
import com.example.models.Exam;
import com.example.models.ExamQuestions;
import com.example.models.Question;
import com.example.views.Home;
import com.formdev.flatlaf.FlatLightLaf;


public class App {


    public static void main(String[] args) throws Exception {

        //Create Models
        Question question = new Question();
        Exam exam = new Exam();
        ExamQuestions examQuestions = new ExamQuestions();
        
        //Create Contollers
        QuestionController questionController = new QuestionController();
        ExamController examController = new ExamController();
        

        //READING FROM A CSV
        String filename = "demo\\src\\main\\java\\com\\example\\File.csv";
        System.out.println("Inserts Questions from, CSV: ");
        questionController.insertQuestionsFromCSV(filename);

        examController.insertExam("Test Exam",0, "Java", "Topic", "subTopic");

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

        Home gui = new Home();
    }
}
