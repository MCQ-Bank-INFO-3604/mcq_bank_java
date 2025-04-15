package com.example;

import java.sql.ResultSet;

import com.example.controllers.ExamController;
import com.example.controllers.QuestionController;
import com.example.models.Exam;
import com.example.models.ExamQuestions;
import com.example.models.Question;
import com.example.views.ExamView;
import com.example.views.Home;
import com.example.views.QuestionView;
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
        
        //Create Views
        QuestionView questionView = new QuestionView();
        ExamView examView = new ExamView();


        //READING FROM A CSV
        String filename = "src\\main\\java\\com\\example\\File.csv";
        System.out.println("Inserts Questions from, CSV: ");
        questionController.insertQuestionsFromCSV(filename);

        ResultSet rs = questionController.getQuestionsWithFilter();
        questionView.printQuestions(rs);


        //SHOWCASE DATABASE
        // System.out.println("Inserts 3 Questions: ");
        // questionController.insertQuestion("What is the capital of France?","Paris", "Berlin","Madrid","Rome", "Geography", "Geography", "Countries", "Easy");

        // questionController.insertQuestion("What is the chemical symbol for gold?","Au","Ag", "Pb", "Fe", "Chemistry", "Periodic Table", "Elements", "Medium");
        

        // questionController.insertQuestion("Which planet is known as the Red Planet?", "Mars", "Venus", "Jupiter", "Saturn", "Astronomy", "Solar System", "Planets", "Easy");

        // System.out.println("\n\nPrints All Questions: \n");
        // ResultSet rs = questionController.getAllQuestions();
        // questionView.printQuestions(rs);

        // System.out.println("\n\nEdits question 2 and Prints All Questions: \n");
        // questionController.editQuestion(2,"In what year did World War II end?", "1945", "1939",    "1918", "1950", "History", "World War II", "Major Events", "Hard");

        // rs = questionController.getAllQuestions();
        // questionView.printQuestions(rs);

        // System.out.println("\n\nDeletes question 3 and Prints All Questions: \n");
        // questionController.deleteQuestion(3);

        // rs = questionController.getAllQuestions();
        // questionView.printQuestions(rs);
        
        // System.out.println("\n\nPrints Questions 1: \n");
        // rs = questionController.getQuestion(1);
        // questionView.printQuestions(rs);

        
        // System.out.println("\n\nPrints All Unique Topics in Database: \n");
        // rs = questionController.getTopics();
        // questionView.printTopics(rs);

        // System.out.println("\n\nInsert exam, adds question 1 and prints questions\n");

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

//        examController.generateExamPDF(1);
        // rs = examController.getQuestionsFromExam(1);
        // examView.printExamQuestions(rs);

        // System.out.println("\n\n Adds question 2 and prints exam questions again \n");
        // examController.addQuestionToExam(1,2);
        // rs = examController.getQuestionsFromExam(1);
        // examView.printExamQuestions(rs);

        // System.out.println("\n\nRemove Question 1 and prints exam questions again \n");
        // examController.removeQuestionFromExam(1, 1);
        // rs = examController.getQuestionsFromExam(1);
        // examView.printExamQuestions(rs); 

        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
            ex.printStackTrace();
        }

        Home gui = new Home();
        gui.setSize(800, 600);
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);


    }
}
