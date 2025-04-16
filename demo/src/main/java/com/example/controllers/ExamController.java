package com.example.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.*;

public class ExamController {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";
    private QuestionController questionController = new QuestionController();

    public Boolean insertExam(String examTitle, Integer course, String dateAdministered) {
        String sql = "INSERT INTO exams (examTitle, numQuestions, course, dateCreated, lastEdited, lastUsed) VALUES (?, ?, ?, ?, ?, ?);";
        
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, examTitle);
            pstmt.setInt(2, 0);
            pstmt.setInt(3, course);
            pstmt.setString(4, date);
            pstmt.setString(5, date);
            pstmt.setString(6, dateAdministered);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExam(int examId) throws SQLException {
        // First delete all exam-question relationships
        String deleteRelationsSql = "DELETE FROM ExamQuestions WHERE examID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteRelationsSql)) {
            pstmt.setInt(1, examId);
            pstmt.executeUpdate();
        }
        
        // Then delete the exam itself
        String deleteExamSql = "DELETE FROM exams WHERE examID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteExamSql)) {
            pstmt.setInt(1, examId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public ResultSet getExam(int examID) {
        String sql = "SELECT * FROM exams WHERE examID = '" + examID + "';";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        
    }

    public ResultSet getQuestionsFromExam(int examID){

        String sql = "SELECT * FROM ExamQuestions WHERE examID = '" + examID + "' ;";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public void addQuestionToExam(int examID, int questionID) throws SQLException {
        // Fetch the exam details
        int numQuestions = 0;
        String sql = "SELECT numQuestions FROM exams WHERE examID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    numQuestions = rs.getInt("numQuestions");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        // Update exam with new lastEdited and numQuestions
        sql = "UPDATE exams SET lastEdited = ?, numQuestions = ? WHERE examID = ?";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setInt(2, numQuestions + 1);
            pstmt.setInt(3, examID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        // Add the question to the exam
        sql = "INSERT INTO ExamQuestions (examID, questionID) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examID);
            pstmt.setInt(2, questionID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void removeQuestionFromExam(int examID, int questionID) throws SQLException {
        ResultSet rs2 = getExam(examID);

        // Update exam with new lastEdited and numQuestions
        String sql = "UPDATE exams SET lastEdited = ?, numQuestions = ? WHERE examID = ?";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try (Connection conn1 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt1 = conn1.prepareStatement(sql)) {
            pstmt1.setString(1, date);
            pstmt1.setInt(2, rs2.getInt("numQuestions") - 1);
            pstmt1.setInt(3, examID);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        // Remove the question from the exam
        sql = "DELETE FROM ExamQuestions WHERE questionID = ? AND examID = ?";
        try (Connection conn2 = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt2 = conn2.prepareStatement(sql)) {
            pstmt2.setInt(1, questionID);
            pstmt2.setInt(2, examID);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean editExam(int examID, String newTitle, Integer newCourse, String newLastUsed) throws SQLException {
        String sql = "UPDATE exams SET examTitle = ?, course = ?, lastUsed = ?, lastEdited = ? WHERE examID = ?";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, newCourse);
            pstmt.setString(3, newLastUsed);
            pstmt.setString(4, date);
            pstmt.setInt(5, examID);
            pstmt.executeUpdate();
        }

        // Update the lastUsed for questions in the exam
        updateQuestionsLastUsed(examID, newLastUsed);

        // Call updateTimesUsed for each question in the exam
        String getQuestionsSql = "SELECT questionID FROM ExamQuestions WHERE examID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(getQuestionsSql)) {
            pstmt.setInt(1, examID);
            ResultSet rs = pstmt.executeQuery();

            QuestionController questionController = new QuestionController();
            while (rs.next()) {
                int questionID = rs.getInt("questionID");
                questionController.updateTimesUsed(questionID);
            }
        }

        return true;
    }

    private void updateQuestionsLastUsed(int examID, String examLastUsed) throws SQLException {
        String sql = "SELECT questionID, lastUsed FROM questions WHERE questionID IN (SELECT questionID FROM ExamQuestions WHERE examID = ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int questionID = rs.getInt("questionID");
                String questionLastUsed = rs.getString("lastUsed");

                String newerLastUsed;
                if (examLastUsed == null || examLastUsed.isEmpty()) {
                    // If the exam's lastUsed is null or empty, find the most recent lastUsed from all related exams
                    newerLastUsed = getMostRecentLastUsedFromExams(questionID);
                } else {
                    // Compare the exam's lastUsed with the question's lastUsed
                    newerLastUsed = getNewerDate(examLastUsed, questionLastUsed);
                }

                // Update the question's lastUsed if it has changed
                if (newerLastUsed != null && !newerLastUsed.isEmpty()) {
                    // If the newerLastUsed is not null or empty, update the question's lastUsed
                    updateQuestionLastUsed(questionID, newerLastUsed);
                } else {
                    // If both are null or empty, set it to null
                    newerLastUsed = null;
                }
            }
        }
    }

    private String getMostRecentLastUsedFromExams(int questionID) throws SQLException {
        String sql = "SELECT MAX(lastUsed) AS mostRecentLastUsed " +
                     "FROM exams " +
                     "WHERE examID IN (SELECT examID FROM ExamQuestions WHERE questionID = ?) AND lastUsed IS NOT NULL AND lastUsed != ''";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("mostRecentLastUsed");
            }
        }
        return null; // Return null if no valid lastUsed dates are found
    }

    private String getNewerDate(String date1, String date2) {
        if (date1 == null || date1.isEmpty()) return date2;
        if (date2 == null || date2.isEmpty()) return date1;

        return date1.compareTo(date2) > 0 ? date1 : date2;
    }

    private void updateQuestionLastUsed(int questionID, String newLastUsed) throws SQLException {
        String sql = "UPDATE questions SET lastUsed = ? WHERE questionID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newLastUsed);
            pstmt.setInt(2, questionID);
            pstmt.executeUpdate();
        }
    }

    public ResultSet getExamsWithFilter(String sqlQuery) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getExamsWithFilter() {
        return getExamsWithFilter("SELECT * FROM exams ORDER BY examID");
    }


    public void generateExamPDF(int examID, String filePath) throws SQLException, DocumentException, IOException {
        ResultSet rs = getQuestionsFromExam(examID);
        ResultSet rs2 = getExam(examID);
        int autoNum = 1;

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
            // Add exam header
            document.add(new Paragraph("Exam ID: " + examID, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Exam Title: " + rs2.getString("examTitle"), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Course: " + rs2.getString("course")));
            document.add(new Paragraph("Number of Questions: " + rs2.getInt("numQuestions")));
            document.add(new Paragraph("\n"));
    
            // Add questions
            while (rs != null && rs.next()) {
                ResultSet question = questionController.getQuestion(rs.getInt("questionID"));   
                List<String> li = new ArrayList<>();
                li.add(question.getString("correctAnswer"));
                li.add(question.getString("wrongAnswer1"));
                li.add(question.getString("wrongAnswer2"));
                li.add(question.getString("wrongAnswer3"));
                Collections.shuffle(li);              
    
                // Format question
                Paragraph questionPara = new Paragraph();
                questionPara.add(new Chunk(autoNum + ". ", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                questionPara.add(question.getString("question"));
                document.add(questionPara);
                
                // Format answers
                document.add(new Paragraph("A. " + li.get(0)));
                document.add(new Paragraph("B. " + li.get(1)));
                document.add(new Paragraph("C. " + li.get(2)));
                document.add(new Paragraph("D. " + li.get(3)));
                document.add(new Paragraph("\n"));
                
                autoNum++;
            }
            
            document.add(new Paragraph("End of Exam", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    public void generateExamDocx(int examID, String filePath) throws SQLException, IOException {
        ResultSet rs = getQuestionsFromExam(examID);
        ResultSet rs2 = getExam(examID);
        int autoNum = 1;

        try (// Create a new Word document
        XWPFDocument document = new XWPFDocument()) {
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                // Add exam header
                XWPFParagraph header = document.createParagraph();
                header.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun headerRun = header.createRun();
                headerRun.setBold(true);
                headerRun.setFontSize(16);
                headerRun.setText("Exam ID: " + examID);

                XWPFParagraph title = document.createParagraph();
                title.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = title.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(14);
                titleRun.setText("Exam Title: " + rs2.getString("examTitle"));

                XWPFParagraph course = document.createParagraph();
                XWPFRun courseRun = course.createRun();
                courseRun.setText("Course: " + rs2.getString("course"));

                XWPFParagraph numQuestions = document.createParagraph();
                XWPFRun numQuestionsRun = numQuestions.createRun();
                numQuestionsRun.setText("Number of Questions: " + rs2.getInt("numQuestions"));

                document.createParagraph(); // Add a blank line

                // Add questions
                while (rs != null && rs.next()) {
                    ResultSet question = questionController.getQuestion(rs.getInt("questionID"));
                    List<String> li = new ArrayList<>();
                    li.add(question.getString("correctAnswer"));
                    li.add(question.getString("wrongAnswer1"));
                    li.add(question.getString("wrongAnswer2"));
                    li.add(question.getString("wrongAnswer3"));
                    Collections.shuffle(li);

                    List<String> li2 = new ArrayList<>();
                    li2.add(question.getString("correctAnswerImagePath"));
                    li2.add(question.getString("wrongAnswer1ImagePath"));
                    li2.add(question.getString("wrongAnswer2ImagePath"));
                    li2.add(question.getString("wrongAnswer3ImagePath"));
                    Collections.shuffle(li2);

                    // Format question
                    XWPFParagraph questionPara = document.createParagraph();
                    XWPFRun questionRun = questionPara.createRun();
                    questionRun.setBold(true);
                    questionRun.setText(autoNum + ". " + question.getString("question"));

                    String imagePath = question.getString("questionImagePath"); // Make sure this field exists
                    if (imagePath != null && !imagePath.isEmpty()) {
                        try (FileInputStream is = new FileInputStream(imagePath)) {
                            XWPFRun imageRun = document.createParagraph().createRun();
                            imageRun.addPicture(
                                is,
                                XWPFDocument.PICTURE_TYPE_PNG,  // or PICTURE_TYPE_JPEG, etc.
                                imagePath,
                                Units.toEMU(200),   // Width
                                Units.toEMU(150)    // Height
                            );
                        } catch (Exception e) {
                            System.out.println("Could not add image: " + e.getMessage());
                        }
                    }                    

                    // Format answers
                    XWPFParagraph answerA = document.createParagraph();
                    XWPFRun answerARun = answerA.createRun();
                    answerARun.setText("A. " + li.get(0));

                    imagePath = li2.get(0); // Make sure this field exists
                    if (imagePath != null && !imagePath.isEmpty()) {
                        try (FileInputStream is = new FileInputStream(imagePath)) {
                            XWPFRun imageRun = document.createParagraph().createRun();
                            imageRun.addPicture(
                                is,
                                XWPFDocument.PICTURE_TYPE_PNG,  // or PICTURE_TYPE_JPEG, etc.
                                imagePath,
                                Units.toEMU(200),   // Width
                                Units.toEMU(150)    // Height
                            );
                        } catch (Exception e) {
                            System.out.println("Could not add image: " + e.getMessage());
                        }
                    }                    

                    
                    XWPFParagraph answerB = document.createParagraph();
                    XWPFRun answerBRun = answerB.createRun();
                    answerBRun.setText("B. " + li.get(1));

                    imagePath = li2.get(1); // Make sure this field exists
                    if (imagePath != null && !imagePath.isEmpty()) {
                        try (FileInputStream is = new FileInputStream(imagePath)) {
                            XWPFRun imageRun = document.createParagraph().createRun();
                            imageRun.addPicture(
                                is,
                                XWPFDocument.PICTURE_TYPE_PNG,  // or PICTURE_TYPE_JPEG, etc.
                                imagePath,
                                Units.toEMU(200),   // Width
                                Units.toEMU(150)    // Height
                            );
                        } catch (Exception e) {
                            System.out.println("Could not add image: " + e.getMessage());
                        }
                    }                    

                    XWPFParagraph answerC = document.createParagraph();
                    XWPFRun answerCRun = answerC.createRun();
                    answerCRun.setText("C. " + li.get(2));

                    imagePath = li2.get(2); // Make sure this field exists
                    if (imagePath != null && !imagePath.isEmpty()) {
                        try (FileInputStream is = new FileInputStream(imagePath)) {
                            XWPFRun imageRun = document.createParagraph().createRun();
                            imageRun.addPicture(
                                is,
                                XWPFDocument.PICTURE_TYPE_PNG,  // or PICTURE_TYPE_JPEG, etc.
                                imagePath,
                                Units.toEMU(200),   // Width
                                Units.toEMU(150)    // Height
                            );
                        } catch (Exception e) {
                            System.out.println("Could not add image: " + e.getMessage());
                        }
                    }                    

                    XWPFParagraph answerD = document.createParagraph();
                    XWPFRun answerDRun = answerD.createRun();
                    answerDRun.setText("D. " + li.get(3));

                    imagePath = li2.get(3); // Make sure this field exists
                    if (imagePath != null && !imagePath.isEmpty()) {
                        try (FileInputStream is = new FileInputStream(imagePath)) {
                            XWPFRun imageRun = document.createParagraph().createRun();
                            imageRun.addPicture(
                                is,
                                XWPFDocument.PICTURE_TYPE_PNG,  // or PICTURE_TYPE_JPEG, etc.
                                imagePath,
                                Units.toEMU(200),   // Width
                                Units.toEMU(150)    // Height
                            );
                        } catch (Exception e) {
                            System.out.println("Could not add image: " + e.getMessage());
                        }
                    }                    

                    document.createParagraph(); // Add a blank line between questions
                    autoNum++;
                }

                // Add footer
                XWPFParagraph footer = document.createParagraph();
                footer.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun footerRun = footer.createRun();
                footerRun.setBold(true);
                footerRun.setText("End of Exam");

                // Write the document to the file
                document.write(out);
            }
        }
    }
}