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
    public ArrayList<String[]> getAllSubtopicsWithIDs() {
        ArrayList<String[]> subtopics = new ArrayList<>();
        String sql = "SELECT subtopicName, subtopicID FROM subtopics;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                subtopics.add(new String[]{rs.getString("subtopicName"), rs.getString("subtopicID")});
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
    
    public ArrayList<String[]> getSubtopicsByTopicID(Integer topicID) {
        ArrayList<String[]> subtopics = new ArrayList<>();
        String sql = "SELECT subtopicName, subtopicID FROM subtopics WHERE topicID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                subtopics.add(new String[]{rs.getString("subtopicName"), rs.getString("subtopicID")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subtopics;
    }
    public String getSubtopicName(Integer subtopicID) {
        String sql = "SELECT subtopicName FROM subtopics WHERE subtopicID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subtopicID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("subtopicName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no subtopic found with the given ID
    }
}
 