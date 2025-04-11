package com.example.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SubtopicsController {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";  

    public boolean insertSubtopic(String subtopicName) {
        String sql = "INSERT INTO subtopics (subtopicName) VALUES (?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subtopicName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public ArrayList<String> getAllSubtopics() {
        ArrayList<String> subtopics = new ArrayList<>();
        String sql = "SELECT subtopicName FROM subtopics;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                subtopics.add(rs.getString("subtopicName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subtopics;
    }
    public boolean deleteSubtopic(String subtopicName) {
        String sql = "DELETE FROM subtopics WHERE subtopicName = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subtopicName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public boolean updateSubtopic(String oldSubtopicName, String newSubtopicName) {
        String sql = "UPDATE subtopics SET subtopicName = ? WHERE subtopicName = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newSubtopicName);
            pstmt.setString(2, oldSubtopicName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public ArrayList<String> getSubtopicsByTopic(String topicName) {
        ArrayList<String> subtopics = new ArrayList<>();
        String sql = "SELECT subtopicName FROM subtopics WHERE topicName = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, topicName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                subtopics.add(rs.getString("subtopicName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subtopics;
    }
}
