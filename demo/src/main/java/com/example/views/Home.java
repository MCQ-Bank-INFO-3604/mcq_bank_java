/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.views;

import java.awt.CardLayout;

/**
 *
 * @author Rian Ramdin
 */
public class Home extends javax.swing.JFrame {
    
    private CardLayout cardLayout;

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        cardLayout = (CardLayout) getContentPane().getLayout();
        getContentPane().add(new Questions(), "questionsPanel");
        getContentPane().add(new Exams(), "examsPanel");
        getContentPane().add(new Courses(), "coursesPanel");
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        homePanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        settingsButton = new javax.swing.JButton();
        homeLabel = new javax.swing.JLabel();
        helpButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        questionsButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        examsButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        coursesButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MCQ Bank V2 App");
        getContentPane().setLayout(new java.awt.CardLayout());

        homePanel.setLayout(new java.awt.BorderLayout());

        headerPanel.setLayout(new java.awt.BorderLayout());

        settingsButton.setText("Settings");
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });
        headerPanel.add(settingsButton, java.awt.BorderLayout.LINE_START);

        homeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homeLabel.setText("Home");
        headerPanel.add(homeLabel, java.awt.BorderLayout.CENTER);

        helpButton.setText("Help");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });
        headerPanel.add(helpButton, java.awt.BorderLayout.LINE_END);

        homePanel.add(headerPanel, java.awt.BorderLayout.PAGE_START);

        contentPanel.setLayout(new java.awt.GridBagLayout());
        contentPanel.add(filler1, new java.awt.GridBagConstraints());

        questionsButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        questionsButton.setText("Questions");
        questionsButton.setAlignmentX(0.5F);
        questionsButton.setMaximumSize(new java.awt.Dimension(175, 35));
        questionsButton.setMinimumSize(new java.awt.Dimension(175, 35));
        questionsButton.setPreferredSize(new java.awt.Dimension(175, 35));
        questionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                questionsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 40, 10, 40);
        contentPanel.add(questionsButton, gridBagConstraints);
        contentPanel.add(filler2, new java.awt.GridBagConstraints());

        examsButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        examsButton.setText("Examinations");
        examsButton.setAlignmentX(0.5F);
        examsButton.setMaximumSize(new java.awt.Dimension(175, 35));
        examsButton.setMinimumSize(new java.awt.Dimension(175, 35));
        examsButton.setPreferredSize(new java.awt.Dimension(175, 35));
        examsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 40, 10, 40);
        contentPanel.add(examsButton, gridBagConstraints);
        contentPanel.add(filler3, new java.awt.GridBagConstraints());

        coursesButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        coursesButton.setText("Configure Courses");
        coursesButton.setAlignmentX(0.5F);
        coursesButton.setMaximumSize(new java.awt.Dimension(175, 35));
        coursesButton.setMinimumSize(new java.awt.Dimension(175, 35));
        coursesButton.setPreferredSize(new java.awt.Dimension(175, 35));
        coursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 40, 10, 40);
        contentPanel.add(coursesButton, gridBagConstraints);
        contentPanel.add(filler4, new java.awt.GridBagConstraints());

        homePanel.add(contentPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(homePanel, "Home");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        // TODO add your handling code here:
        new Help(null, true, 1);
    }//GEN-LAST:event_helpButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void questionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_questionsButtonActionPerformed
        // TODO add your handling code here:
        cardLayout.show(getContentPane(), "questionsPanel");
    }//GEN-LAST:event_questionsButtonActionPerformed

    private void examsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examsButtonActionPerformed
        // TODO add your handling code here:
        cardLayout.show(getContentPane(), "examsPanel");
    }//GEN-LAST:event_examsButtonActionPerformed

    private void coursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesButtonActionPerformed
        // TODO add your handling code here:
        cardLayout.show(getContentPane(), "coursesPanel");
    }//GEN-LAST:event_coursesButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton coursesButton;
    private javax.swing.JButton examsButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel homeLabel;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton questionsButton;
    private javax.swing.JButton settingsButton;
    // End of variables declaration//GEN-END:variables
}
