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

    public boolean insertTopic(String topicName) {
        String sql = "INSERT INTO topics (topicName) VALUES (?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, topicName);
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
    public boolean deleteTopic(String topicName) {
        String sql = "DELETE FROM topics WHERE topicName = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, topicName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public boolean updateTopic(String oldTopicName, String newTopicName) {
        String sql = "UPDATE topics SET topicName = ? WHERE topicName = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTopicName);
            pstmt.setString(2, oldTopicName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public ArrayList<String[]> getTopicsByCourse(Integer courseID) {
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
    public String getTopicName(Integer topicID) {
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
}