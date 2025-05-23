/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.example.views;

import javax.swing.*;

import com.example.controllers.CoursesController;
import com.example.controllers.SubtopicsController;
import com.example.controllers.TopicsController;

import java.awt.*;
import java.util.ArrayList;


/**
 *
 * @author Rian Ramdin
 */
public class Courses extends javax.swing.JPanel {

    private CoursesController cController = new CoursesController();
    private TopicsController tController = new TopicsController();
    private SubtopicsController sController = new SubtopicsController();
    private Integer selectedCourseId = null;
    private Integer selectedTopicId = null;
    private Integer selectedSubtopicId = null;

    /**
     * Creates new form Courses
     */
    public Courses() {
        initComponents();
        populateCoursesDropdown();
        setupListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        returnButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();
        coursesLabel = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        controlsPanel = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        courseSelectPanel = new javax.swing.JPanel();
        courseSelectLabel = new javax.swing.JLabel();
        courseSelectComboBox = new javax.swing.JComboBox<>();
        namePanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        codePanel = new javax.swing.JPanel();
        codeLabel = new javax.swing.JLabel();
        codeTF = new javax.swing.JTextField();
        detailsPanel = new javax.swing.JPanel();
        topicPanel = new javax.swing.JPanel();
        topicNameTF = new javax.swing.JTextField();
        topicScrollPane = new javax.swing.JScrollPane();
        topicList = new javax.swing.JList<>();
        topicAddButton = new javax.swing.JButton();
        topicEditButton = new javax.swing.JButton();
        topicDeleteButton = new javax.swing.JButton();
        subtopicPanel = new javax.swing.JPanel();
        subtopicNameTF = new javax.swing.JTextField();
        subtopicScrollPane = new javax.swing.JScrollPane();
        subtopicList = new javax.swing.JList<>();
        subtopicAddButton = new javax.swing.JButton();
        subtopicEditButton = new javax.swing.JButton();
        subtopicDeleteButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        headerPanel.setLayout(new java.awt.BorderLayout());

        returnButton.setText("Return");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });
        headerPanel.add(returnButton, java.awt.BorderLayout.LINE_START);

        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });
        headerPanel.add(helpButton, java.awt.BorderLayout.LINE_END);

        coursesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coursesLabel.setText("Courses");
        headerPanel.add(coursesLabel, java.awt.BorderLayout.CENTER);

        add(headerPanel, java.awt.BorderLayout.PAGE_START);

        controlsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Controls"));

        newButton.setText("New Course");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        controlsPanel.add(newButton);

        saveButton.setText("Save Course");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        controlsPanel.add(saveButton);

        clearButton.setText("Clear Fields");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        controlsPanel.add(clearButton);

        deleteButton.setText("Delete Course");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        controlsPanel.add(deleteButton);

        courseSelectLabel.setText("Select Course:");

        courseSelectComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "course1", "course2", "course3", "course4" }));

        javax.swing.GroupLayout courseSelectPanelLayout = new javax.swing.GroupLayout(courseSelectPanel);
        courseSelectPanel.setLayout(courseSelectPanelLayout);
        courseSelectPanelLayout.setHorizontalGroup(
            courseSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(courseSelectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(courseSelectLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(courseSelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        courseSelectPanelLayout.setVerticalGroup(
            courseSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(courseSelectPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(courseSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseSelectLabel)
                    .addComponent(courseSelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        nameLabel.setText("Course Name:");

        nameTF.setText("Course Name");
        nameTF.setEnabled(false);

        javax.swing.GroupLayout namePanelLayout = new javax.swing.GroupLayout(namePanel);
        namePanel.setLayout(namePanelLayout);
        namePanelLayout.setHorizontalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        namePanelLayout.setVerticalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        codeLabel.setText("Course Code:");

        codeTF.setText("Course Code");
        codeTF.setEnabled(false);

        javax.swing.GroupLayout codePanelLayout = new javax.swing.GroupLayout(codePanel);
        codePanel.setLayout(codePanelLayout);
        codePanelLayout.setHorizontalGroup(
            codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(codePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        codePanelLayout.setVerticalGroup(
            codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(codePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(codePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeLabel)
                    .addComponent(codeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        detailsPanel.setLayout(new java.awt.GridLayout(2, 0));

        topicPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Topic"));

        topicNameTF.setText("Topic Name");
        topicNameTF.setEnabled(false);

        topicList.setEnabled(false);
        topicScrollPane.setViewportView(topicList);

        topicAddButton.setText("Add New Topic");
        topicAddButton.setEnabled(false);
        topicAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topicAddButtonActionPerformed(evt);
            }
        });

        topicEditButton.setText("Save Topic Edits");
        topicEditButton.setEnabled(false);
        topicEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topicEditButtonActionPerformed(evt);
            }
        });

        topicDeleteButton.setText("Delete Selected Topic");
        topicDeleteButton.setEnabled(false);
        topicDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topicDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topicPanelLayout = new javax.swing.GroupLayout(topicPanel);
        topicPanel.setLayout(topicPanelLayout);
        topicPanelLayout.setHorizontalGroup(
            topicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(topicNameTF, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(topicScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topicAddButton)
                    .addComponent(topicEditButton)
                    .addComponent(topicDeleteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        topicPanelLayout.setVerticalGroup(
            topicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topicNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(topicAddButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topicScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(topicPanelLayout.createSequentialGroup()
                        .addComponent(topicEditButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(topicDeleteButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        detailsPanel.add(topicPanel);

        subtopicPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Subtopic"));

        subtopicNameTF.setText("Subtopic Name");
        subtopicNameTF.setEnabled(false);

        subtopicList.setEnabled(false);
        subtopicScrollPane.setViewportView(subtopicList);

        subtopicAddButton.setText("Add New Subtopic");
        subtopicAddButton.setEnabled(false);
        subtopicAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subtopicAddButtonActionPerformed(evt);
            }
        });

        subtopicEditButton.setText("Save Subtopic Edits");
        subtopicEditButton.setEnabled(false);
        subtopicEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subtopicEditButtonActionPerformed(evt);
            }
        });

        subtopicDeleteButton.setText("Delete Selected Subtopic");
        subtopicDeleteButton.setEnabled(false);
        subtopicDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subtopicDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subtopicPanelLayout = new javax.swing.GroupLayout(subtopicPanel);
        subtopicPanel.setLayout(subtopicPanelLayout);
        subtopicPanelLayout.setHorizontalGroup(
            subtopicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subtopicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subtopicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(subtopicNameTF, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(subtopicScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subtopicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subtopicAddButton)
                    .addComponent(subtopicEditButton)
                    .addComponent(subtopicDeleteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        subtopicPanelLayout.setVerticalGroup(
            subtopicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subtopicPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subtopicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subtopicNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subtopicAddButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subtopicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subtopicScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(subtopicPanelLayout.createSequentialGroup()
                        .addComponent(subtopicEditButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subtopicDeleteButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        detailsPanel.add(subtopicPanel);

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addComponent(namePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(courseSelectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(controlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(codePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(courseSelectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(contentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), "Home");
    }//GEN-LAST:event_returnButtonActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        new Help(null, true, 4);
    }//GEN-LAST:event_helpButtonActionPerformed

    private void topicAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topicAddButtonActionPerformed
        topicList.clearSelection();
        selectedTopicId = null;
        topicNameTF.setText("");
        topicNameTF.setEnabled(true);
        topicEditButton.setEnabled(true);
        topicDeleteButton.setEnabled(false);
    }//GEN-LAST:event_topicAddButtonActionPerformed

    private void topicEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topicEditButtonActionPerformed
        String newTopicName = topicNameTF.getText().trim();
        if (selectedTopicId == null) {
            tController.insertTopic(newTopicName, selectedCourseId);
        } else {
            tController.updateTopic(selectedTopicId, newTopicName);
        }
        populateTopicsList();
    }//GEN-LAST:event_topicEditButtonActionPerformed

    private void topicDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topicDeleteButtonActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this topic? This will also delete all related subtopics.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tController.deleteTopic(selectedTopicId);
            populateTopicsList();
            populateSubtopicsList();
        }
    }//GEN-LAST:event_topicDeleteButtonActionPerformed

    private void subtopicAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subtopicAddButtonActionPerformed
        subtopicList.clearSelection();
        selectedSubtopicId = null;
        subtopicNameTF.setText("");
        subtopicNameTF.setEnabled(true);
        subtopicEditButton.setEnabled(true);
        subtopicDeleteButton.setEnabled(false);
    }//GEN-LAST:event_subtopicAddButtonActionPerformed

    private void subtopicEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subtopicEditButtonActionPerformed
        String newSubtopicName = subtopicNameTF.getText().trim();
        if (selectedSubtopicId == null) {
            sController.insertSubtopic(newSubtopicName, selectedTopicId);
        } else {
            sController.updateSubtopic(selectedSubtopicId, newSubtopicName);
        }
        populateSubtopicsList();
    }//GEN-LAST:event_subtopicEditButtonActionPerformed

    private void subtopicDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subtopicDeleteButtonActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this subtopic?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            sController.deleteSubtopic(selectedSubtopicId);
            populateSubtopicsList();
        }
    }//GEN-LAST:event_subtopicDeleteButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        Integer newCourseId = cController.insertCourse("--New--", "--New--");
        System.out.println("newButtonActionPerformed New Course ID: " + newCourseId);
        populateCoursesDropdown();
        courseSelectComboBox.setSelectedItem(cController.getCourseNameById(newCourseId));
        selectedCourseId = newCourseId;
    }//GEN-LAST:event_newButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        System.out.println("saveButtonActionPerformed Selected Course ID: " + selectedCourseId);
        if (selectedCourseId == null || "--None--".equals(courseSelectComboBox.getSelectedItem()) || selectedCourseId == 0) {
            // Check if a course is selected
            JOptionPane.showMessageDialog(this, "Please select a course to save.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nameTF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (codeTF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course code.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Get the user-edited text from the nameComboBox
        String newCourseName = nameTF.getText().trim();
        String newCourseCode = codeTF.getText().trim();

        System.out.println("saveButtonActionPerformed New Course Name: " + newCourseName);
        System.out.println("saveButtonActionPerformed New Course Code: " + newCourseCode);

        cController.updateCourse(selectedCourseId, newCourseCode, newCourseName);
        populateCoursesDropdown();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        resetFields();
        courseSelectComboBox.setSelectedItem("--None--");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (selectedCourseId == null || "--None--".equals(courseSelectComboBox.getSelectedItem()) || selectedCourseId == 0) {
            // Check if a course is selected
            JOptionPane.showMessageDialog(this, "Please select a course to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this course? This will also delete all related topics and subtopics.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cController.deleteCourse(selectedCourseId);
            populateCoursesDropdown();
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void populateCoursesDropdown() {
        courseSelectComboBox.removeAllItems();
        courseSelectComboBox.addItem("--None--");
        ArrayList<String[]> courses = cController.getAllCourses();
        for (String[] course : courses) {
            courseSelectComboBox.addItem(course[0]);
        }
        resetFields();
    }

    private void resetFields() {
        selectedCourseId = null;

        nameTF.setText("Course Name");
        nameTF.setEnabled(false);

        codeTF.setText("Course Code");
        codeTF.setEnabled(false);

        resetTopicFields();
        resetSubtopicFields();
    }

    private void resetTopicFields() {
        selectedTopicId = null;
        topicNameTF.setText("Topic Name");
        topicNameTF.setEnabled(false);
        topicList.setListData(new String[0]);
        topicAddButton.setEnabled(false);
        topicEditButton.setEnabled(false);
        topicDeleteButton.setEnabled(false);
    }

    private void resetSubtopicFields() {
        selectedSubtopicId = null;
        subtopicNameTF.setText("Subtopic Name");
        subtopicNameTF.setEnabled(false);
        subtopicList.setListData(new String[0]);
        subtopicAddButton.setEnabled(false);
        subtopicEditButton.setEnabled(false);
        subtopicDeleteButton.setEnabled(false);
    }

    @SuppressWarnings("unused")
    private void setupListeners() {
        courseSelectComboBox.addActionListener(e -> {
                String selectedCourseCode = (String) courseSelectComboBox.getSelectedItem();
                if ("--None--".equals(selectedCourseCode)) {
                    resetFields();
                } else if (selectedCourseCode != null) {
                    resetSubtopicFields();
                    System.out.println("Listener Selected Course Code: " + selectedCourseCode);
                    selectedCourseId = cController.getCourseIdByCode(selectedCourseCode);
                    System.out.println("Listener Selected Course ID: " + selectedCourseId);
                    nameTF.setText(cController.getCourseNameById(selectedCourseId));
                    nameTF.setEnabled(true);
                    codeTF.setText(selectedCourseCode);
                    codeTF.setEnabled(true);
                    populateTopicsList();
                    topicAddButton.setEnabled(true);
                }
        });

        topicList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedTopic = topicList.getSelectedValue();
                if (selectedTopic != null) {
                    selectedTopicId = tController.getTopicIdByNameAndCourseId(selectedTopic, selectedCourseId);
                    topicNameTF.setText(selectedTopic);
                    topicNameTF.setEnabled(true);
                    topicEditButton.setEnabled(true);
                    topicDeleteButton.setEnabled(true);
                    populateSubtopicsList();
                    subtopicAddButton.setEnabled(true);
                }
            }
        });

        subtopicList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedSubtopic = subtopicList.getSelectedValue();
                if (selectedSubtopic != null) {
                    selectedSubtopicId = sController.getSubtopicIdByNameAndTopicId(selectedSubtopic, selectedTopicId);
                    subtopicNameTF.setText(selectedSubtopic);
                    subtopicNameTF.setEnabled(true);
                    subtopicEditButton.setEnabled(true);
                    subtopicDeleteButton.setEnabled(true);
                }
            }
        });
    }

    private void populateTopicsList() {
        ArrayList<String[]> topics = tController.getTopicsByCourseId(selectedCourseId);
        topicList.setListData(topics.stream().map(topic -> topic[0]).toArray(String[]::new));
        topicList.setEnabled(true);
        selectedTopicId = null;
        topicNameTF.setText("Topic Name");
        topicNameTF.setEnabled(false);
        topicEditButton.setEnabled(false);
        topicDeleteButton.setEnabled(false);
    }

    private void populateSubtopicsList() {
        ArrayList<String[]> subtopics = sController.getSubtopicsByTopicId(selectedTopicId);
        subtopicList.setListData(subtopics.stream().map(subtopic -> subtopic[0]).toArray(String[]::new));
        subtopicList.setEnabled(true);
        subtopicNameTF.setText("Subtopic Name");
        subtopicNameTF.setEnabled(false);
        subtopicEditButton.setEnabled(false);
        subtopicDeleteButton.setEnabled(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JPanel codePanel;
    private javax.swing.JTextField codeTF;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JComboBox<String> courseSelectComboBox;
    private javax.swing.JLabel courseSelectLabel;
    private javax.swing.JPanel courseSelectPanel;
    private javax.swing.JLabel coursesLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JButton newButton;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton subtopicAddButton;
    private javax.swing.JButton subtopicDeleteButton;
    private javax.swing.JButton subtopicEditButton;
    private javax.swing.JList<String> subtopicList;
    private javax.swing.JTextField subtopicNameTF;
    private javax.swing.JPanel subtopicPanel;
    private javax.swing.JScrollPane subtopicScrollPane;
    private javax.swing.JButton topicAddButton;
    private javax.swing.JButton topicDeleteButton;
    private javax.swing.JButton topicEditButton;
    private javax.swing.JList<String> topicList;
    private javax.swing.JTextField topicNameTF;
    private javax.swing.JPanel topicPanel;
    private javax.swing.JScrollPane topicScrollPane;
    // End of variables declaration//GEN-END:variables
}
