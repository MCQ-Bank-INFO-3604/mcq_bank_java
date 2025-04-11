/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.example.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.example.controllers.CoursesController;
import com.example.controllers.ExamController;
import com.example.controllers.QuestionController;
import com.example.controllers.SubtopicsController;
import com.example.controllers.TopicsController;

/**
 *
 * @author Michelle Khadan
 */
public class GenerateQuestionForm extends javax.swing.JDialog {
    private HashMap<String, Integer> courseCodeToIDMap = new HashMap<>();
    private CoursesController cController = new CoursesController();
    
    private HashMap<String, Integer> topicNameToIDMap = new HashMap<>();
    private TopicsController tController = new TopicsController();
    
    private HashMap<String, Integer> subtopicNameToIDMap = new HashMap<>();
    private SubtopicsController sController = new SubtopicsController();
    
    private static final String DEFAULT_ALL_OPTION = "--All--";

    private Integer currentExamId;
    /**
     * Creates new form GenerateQuestionForm
     */
    public GenerateQuestionForm(java.awt.Frame parent, boolean modal, Integer currentExamId) {
        super(parent, modal);
        this.currentExamId = currentExamId;
        initComponents();

        populateDropdowns();
        setupDropdownListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        contentPane = new javax.swing.JPanel();
        numLabel = new javax.swing.JLabel();
        numSpinner = new javax.swing.JSpinner();
        courseLabel = new javax.swing.JLabel();
        courseComboBox = new javax.swing.JComboBox<>();
        topicLabel = new javax.swing.JLabel();
        topicComboBox = new javax.swing.JComboBox<>();
        subtopicLabel = new javax.swing.JLabel();
        subtopicComboBox = new javax.swing.JComboBox<>();
        difficultyLabel = new javax.swing.JLabel();
        diffEasyRB = new javax.swing.JRadioButton();
        diffMedRB = new javax.swing.JRadioButton();
        diffHardRB = new javax.swing.JRadioButton();
        diffAnyRB = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        genButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generate Questions");
        setResizable(false);

        numLabel.setText("Number of Questions:");

        numSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        courseLabel.setText("Course:");

        courseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Course 1", "Course 2", "Course 3", "Course 4" }));

        topicLabel.setText("Topic:");

        topicComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Topic 1", "Topic 2", "Topic 3", "Topic 4" }));

        subtopicLabel.setText("Sub-Topic:");

        subtopicComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sub-Topic 1", "Sub-Topic 2", "Sub-Topic 3", "Sub-Topic 4" }));

        difficultyLabel.setText("Difficulty:");

        buttonGroup1.add(diffEasyRB);
        diffEasyRB.setText("Easy");

        buttonGroup1.add(diffMedRB);
        diffMedRB.setText("Medium");

        buttonGroup1.add(diffHardRB);
        diffHardRB.setText("Hard");

        buttonGroup1.add(diffAnyRB);
        diffAnyRB.setSelected(true);
        diffAnyRB.setText("Any");

        genButton.setText("Generate");
        genButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genButtonActionPerformed(evt);
            }
        });
        jPanel1.add(genButton);

        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });
        jPanel1.add(helpButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(cancelButton);

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createSequentialGroup()
                        .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(courseLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(numLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(topicLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(topicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(subtopicLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(subtopicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(difficultyLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diffEasyRB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diffMedRB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diffHardRB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diffAnyRB)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numLabel)
                    .addComponent(numSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel)
                    .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topicLabel)
                    .addComponent(topicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subtopicLabel)
                    .addComponent(subtopicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(difficultyLabel)
                    .addComponent(diffEasyRB)
                    .addComponent(diffMedRB)
                    .addComponent(diffHardRB)
                    .addComponent(diffAnyRB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(contentPane, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        // TODO add your handling code here:
        new Help(null, true, 5);
    }//GEN-LAST:event_helpButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void genButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genButtonActionPerformed
        // TODO add your handling code here:
        // Get the selected criteria
        int numQuestions = (Integer) numSpinner.getValue();
        String course = (String) courseComboBox.getSelectedItem();
        String topic = (String) topicComboBox.getSelectedItem();
        String subtopic = (String) subtopicComboBox.getSelectedItem();
        
        // Get selected difficulty
        String difficulty = null;
        if (diffEasyRB.isSelected()) difficulty = "Easy";
        else if (diffMedRB.isSelected()) difficulty = "Medium";
        else if (diffHardRB.isSelected()) difficulty = "Hard";
        // If "Any" is selected, difficulty remains null
        
        try {
            // Get questions that match the criteria
            QuestionController qController = new QuestionController();
            ResultSet matchingQuestions = qController.getQuestionsWithFilter(buildQuery(course, topic, subtopic, difficulty));
            
            // Add the specified number of questions to the exam
            ExamController eController = new ExamController();
            int addedCount = 0;
            
            while (matchingQuestions != null && matchingQuestions.next() && addedCount < numQuestions) {
                int questionId = matchingQuestions.getInt("questionID");
                eController.addQuestionToExam(currentExamId, questionId);
                addedCount++;
            }
            
            if (addedCount > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Successfully added " + addedCount + " questions to the exam", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No questions matched the specified criteria", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            }
            
            this.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error generating questions: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_genButtonActionPerformed

    private String buildQuery(String course, String topic, String subtopic, String difficulty) {
        StringBuilder query = new StringBuilder("SELECT * FROM questions WHERE 1=1");
        
        if (course != null && !course.equals("--All--")) {
            query.append(" AND course = '").append(course).append("'");
        }
        
        if (topic != null && !topic.equals("--All--")) {
            query.append(" AND topic = '").append(topic).append("'");
        }
        
        if (subtopic != null && !subtopic.equals("--All--")) {
            query.append(" AND subTopic = '").append(subtopic).append("'");
        }
        
        if (difficulty != null) {
            query.append(" AND difficulty = '").append(difficulty).append("'");
        }
        
        // Add random ordering to get different questions each time
        query.append(" ORDER BY RANDOM()");
        
        return query.toString();
    }

    private void populateDropdowns() {
        // Clear the existing items in the combo boxes
        courseComboBox.removeAllItems();
        topicComboBox.removeAllItems();
        subtopicComboBox.removeAllItems();
        courseCodeToIDMap.clear();
        topicNameToIDMap.clear();
        subtopicNameToIDMap.clear();

        // Add All option
        courseComboBox.addItem(DEFAULT_ALL_OPTION);
        topicComboBox.addItem(DEFAULT_ALL_OPTION);
        subtopicComboBox.addItem(DEFAULT_ALL_OPTION);

        ArrayList<String[]> courses = cController.getAllCourses();
        
        // Add items to the combo boxes
        for (String[] course : courses) {
            String courseCode = course[0];
            int courseID = Integer.parseInt(course[1]);
            courseComboBox.addItem(courseCode);
            courseCodeToIDMap.put(courseCode, courseID);
        }
        
        // Set the default selected item to the first one
        topicComboBox.setEnabled(false);
        subtopicComboBox.setEnabled(false);
    }

    private void setupDropdownListeners() {
        courseComboBox.addActionListener(e -> {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            if (DEFAULT_ALL_OPTION.equals(selectedCourse)) {
                topicComboBox.setEnabled(false);
                subtopicComboBox.setEnabled(false);
                topicComboBox.setSelectedItem(DEFAULT_ALL_OPTION);
                subtopicComboBox.setSelectedItem(DEFAULT_ALL_OPTION);
            } else {
                Integer courseID = courseCodeToIDMap.get(selectedCourse);
                topicComboBox.setEnabled(true);
                populateTopicsDropdown(topicComboBox, courseID);
            }
        });

        topicComboBox.addActionListener(e -> {
            String selectedTopic = (String) topicComboBox.getSelectedItem();
            if (DEFAULT_ALL_OPTION.equals(selectedTopic)) {
                subtopicComboBox.setEnabled(false);
                subtopicComboBox.setSelectedItem(DEFAULT_ALL_OPTION);
            } else {
                Integer topicID = topicNameToIDMap.get(selectedTopic);
                subtopicComboBox.setEnabled(true);
                populateSubtopicsDropdown(subtopicComboBox, topicID);
            }
        });
    }

    private void populateTopicsDropdown(JComboBox<String> topicDropdown, Integer course) {
        topicDropdown.removeAllItems();
        topicDropdown.addItem(DEFAULT_ALL_OPTION);

        ArrayList<String[]> topics = tController.getTopicsByCourse(course);
        for (String[] topic : topics) {
            String topicName = topic[0];
            topicDropdown.addItem(topicName);
        }
    }

    private void populateSubtopicsDropdown(JComboBox<String> subtopicDropdown, Integer topic) {
        subtopicDropdown.removeAllItems();
        subtopicDropdown.addItem(DEFAULT_ALL_OPTION);

        ArrayList<String[]> subtopics = sController.getSubtopicsByTopicID(topic);
        for (String[] subtopic : subtopics) {
            String subtopicName = subtopic[0];
            subtopicDropdown.addItem(subtopicName);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GenerateQuestionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateQuestionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateQuestionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateQuestionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GenerateQuestionForm dialog = new GenerateQuestionForm(new javax.swing.JFrame(), true, 1);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel contentPane;
    private javax.swing.JComboBox<String> courseComboBox;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JRadioButton diffAnyRB;
    private javax.swing.JRadioButton diffEasyRB;
    private javax.swing.JRadioButton diffHardRB;
    private javax.swing.JRadioButton diffMedRB;
    private javax.swing.JLabel difficultyLabel;
    private javax.swing.JButton genButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel numLabel;
    private javax.swing.JSpinner numSpinner;
    private javax.swing.JComboBox<String> subtopicComboBox;
    private javax.swing.JLabel subtopicLabel;
    private javax.swing.JComboBox<String> topicComboBox;
    private javax.swing.JLabel topicLabel;
    // End of variables declaration//GEN-END:variables
}
