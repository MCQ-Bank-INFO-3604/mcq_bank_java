package com.example.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TopicsController {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    public boolean insertTopic(String topicName, Integer courseID) {
        // Check if the topic already exists
        String select = "SELECT topicID FROM topics WHERE topicName = ? AND courseID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, topicName);
            stmt.setInt(2, courseID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return false; // Topic already exists
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO topics (topicName, courseID) VALUES (?, ?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, topicName);
            pstmt.setInt(2, courseID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public ArrayList<String> getAllTopics() {
        ArrayList<String> topics = new ArrayList<>();
        String sql = "SELECT topicName FROM topics;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                topics.add(rs.getString("topicName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }
    public boolean deleteTopic(Integer topicId) {
        String sql = "DELETE FROM topics WHERE topicID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public boolean updateTopic(Integer topicId, String newTopicName) {
        String sql = "UPDATE topics SET topicName = ? WHERE topicID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTopicName);
            pstmt.setInt(2, topicId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public ArrayList<String[]> getTopicsByCourseId(Integer courseID) {
        if (courseID == null) {
            return new ArrayList<>(); // Return an empty list if courseID is null
        }
        // Get all topics for a specific course ID
        ArrayList<String[]> topics = new ArrayList<>();
        String sql = "SELECT topicName, topicID FROM topics WHERE courseID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                topics.add(new String[]{rs.getString("topicName"), rs.getString("topicID")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    public ArrayList<String[]> getAllTopicsWithIDs() {
        ArrayList<String[]> topics = new ArrayList<>();
        String sql = "SELECT topicName, topicID FROM topics;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                topics.add(new String[]{rs.getString("topicName"), rs.getString("topicID")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }
    public String getTopicNameById(Integer topicID) {
        String sql = "SELECT topicName FROM topics WHERE topicID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, topicID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("topicName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no topic found with the given ID
    }

    public Integer getTopicIdByNameAndCourseId(String topicName, Integer courseID) {
        String sql = "SELECT topicID FROM topics WHERE topicName = ? AND courseID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, topicName);
            pstmt.setInt(2, courseID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("topicID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no topic found with the given name and course ID
    }

}