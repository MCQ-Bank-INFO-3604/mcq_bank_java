package com.example.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.controllers.ExamController;
import com.example.controllers.QuestionController;


public class ExamView{
    ExamController examController = new ExamController();
    QuestionController questionController = new QuestionController();



    public void printShuffledQuestion(ResultSet rs) throws SQLException{


        while (rs != null && rs.next())
        {
            List<String> li = new ArrayList<>();
            li.add(rs.getString("correctAnswer"));
            li.add(rs.getString("wrongAnswer1"));
            li.add(rs.getString("wrongAnswer2"));
            li.add(rs.getString("wrongAnswer3"));
            Collections.shuffle(li);

            System.out.println("Question: " + rs.getString("question"));
            System.out.println("A: " + li.get(0));
            System.out.println("B: " + li.get(1));
            System.out.println("C: " + li.get(2));
            System.out.println("D: " + li.get(3));
    
        }
    }


    public void printExamQuestions(ResultSet rs) throws SQLException{

        ResultSet exam = examController.getExam(rs.getInt("examID"));
        System.out.println("Exam ID :"  + exam.getInt("examID"));
        System.out.println("Number of Questions :"  + exam.getInt("numQuestions"));
        System.out.println("Course :"  + exam.getString("course"));
        System.out.println("Topic :"  + exam.getString("topic"));
        System.out.println("Sub-Topic :"  + exam.getString("subTopic"));
        System.out.println("Difficulty :"  + exam.getString("difficulty"));
        System.out.println("Date Created :"  + exam.getString("dateCreated"));
        System.out.println("Last Used :"  + exam.getString("lastUsed"));
        System.out.println("Last Edited :"  + exam.getString("lastEdited"));
        System.out.println("Questions: \n");
        //String sql = "SELECT * FROM ExamQuestions WHERE examID = '" + examID + "' ;";


        while (rs != null && rs.next())
        {
            ResultSet question = questionController.getQuestion(rs.getInt("questionID"));

    

            System.out.println("Question ID: " + rs.getInt("questionID"));

            System.out.println("Question: " + question.getString("question"));
            System.out.println("Correct Answer: " + question.getString("correctAnswer"));
            System.out.println("Wrong Answer1: "+question.getString("wrongAnswer1"));
            System.out.println("Wrong Answer2: "+question.getString("wrongAnswer2"));
            System.out.println("Wrong Answer3: "+question.getString("wrongAnswer3"));
            // System.out.println("Course: " +  question.getString("course"));
            // System.out.println("Topic: " + question.getString("topic"));
            // System.out.println("subTopic: " +question.getString("subTopic"));
    
            // System.out.println("Difficulty: " + question.getString("difficulty"));
            // System.out.println("DateCreated: " + question.getString("dateCreated"));
            // System.out.println("Last Used: "+ question.getString("lastUsed"));
            // System.out.println("Last Edited: "+ question.getString("lastEdited"));
            // System.out.println("Times Used: "+ question.getInt("timesUsed"));
            // System.out.println("Performance Metric: "+ question.getFloat("performanceMetric"));
    
        }

    }


}