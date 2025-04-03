package com.example.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionView {

    public void printMinimalQuestions(ResultSet rs) throws SQLException{
        while (rs != null && rs.next())
        {
            System.out.println(rs.getString("questionID"));
            System.out.println(rs.getString("question"));
            System.out.println(rs.getString("correctAnswer"));
            System.out.println(rs.getString("wrongAnswer1"));
            System.out.println(rs.getString("wrongAnswer2"));
            System.out.println(rs.getString("wrongAnswer3"));
            System.out.println(rs.getString("course"));
            System.out.println(rs.getString("topic"));

        }

    }

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




    public void printQuestions(ResultSet rs) throws SQLException{

        while (rs != null && rs.next())
        {
            System.out.println(rs.getString("questionID"));
            System.out.println(rs.getString("question"));
            System.out.println(rs.getString("correctAnswer"));
            System.out.println(rs.getString("wrongAnswer1"));
            System.out.println(rs.getString("wrongAnswer2"));
            System.out.println(rs.getString("wrongAnswer3"));
            System.out.println(rs.getString("course"));
            System.out.println(rs.getString("topic"));
            System.out.println(rs.getString("subTopic"));
    
            System.out.println(rs.getString("difficulty"));
            System.out.println(rs.getDate("dateCreated"));
    
        }

    }

    public void printTopics(ResultSet rs) throws SQLException{
        while (rs != null && rs.next())
            System.out.println("Topic: " + rs.getString("topic"));

    }
}
