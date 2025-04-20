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

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    public boolean insertSubtopic(String subtopicName, Integer topicId) {
        // Check if the subtopic already exists
        String select = "SELECT subtopicID FROM subtopics WHERE subtopicName = ? AND topicID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, subtopicName);
            stmt.setInt(2, topicId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return false; // Subtopic already exists
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO subtopics (subtopicName, topicID) VALUES (?, ?);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subtopicName);
            pstmt.setInt(2, topicId);
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
        try (Connection conn = getConnection();
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
        try (Connection conn = getConnection();
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

    public boolean deleteSubtopic(Integer subtopicId) {
        String sql = "DELETE FROM subtopics WHERE subtopicID = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subtopicId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public boolean updateSubtopic(Integer SubtopicId, String newSubtopicName) {
        String sql = "UPDATE subtopics SET subtopicName = ? WHERE subtopicID = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newSubtopicName);
            pstmt.setInt(2, SubtopicId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    
    public ArrayList<String[]> getSubtopicsByTopicId(Integer topicID) {
        ArrayList<String[]> subtopics = new ArrayList<>();
        String sql = "SELECT subtopicName, subtopicID FROM subtopics WHERE topicID = ?;";
        try (Connection conn = getConnection();
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
        try (Connection conn = getConnection();
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

    public Integer getSubtopicIdByNameAndTopicId(String subtopicName, Integer topicID) {
        String sql = "SELECT subtopicID FROM subtopics WHERE subtopicName = ? AND topicID = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subtopicName);
            pstmt.setInt(2, topicID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("subtopicID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no subtopic found with the given name and topic ID
    }
}
 