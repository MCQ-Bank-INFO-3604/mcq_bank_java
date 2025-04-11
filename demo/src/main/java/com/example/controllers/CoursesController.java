package com.example.controllers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CoursesController {
    private static final String DB_URL = "jdbc:sqlite:mcq_bank.db?journal_mode=WAL&busy_timeout=3000";

    public boolean insertCourse(String courseCode, String courseName) {
        String sql = "INSERT INTO courses (courseCode, courseName) VALUES (?, ?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseCode);
            pstmt.setString(2, courseName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public ArrayList<String> getAllCourses() {
        ArrayList<String> courses = new ArrayList<>();
        String sql = "SELECT courseCode FROM courses;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(rs.getString("courseCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    public boolean deleteCourse(String courseCode) {
        String sql = "DELETE FROM courses WHERE courseCode = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseCode);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public boolean updateCourse(String oldCourseCode, String newCourseCode, String oldCourseName, String newCourseName) {
        String sql = "UPDATE courses SET courseCode = ?, courseName = ? WHERE courseCode = ? AND courseName = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newCourseCode);
            pstmt.setString(2, newCourseName);
            pstmt.setString(3, oldCourseCode);
            pstmt.setString(4, oldCourseName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
