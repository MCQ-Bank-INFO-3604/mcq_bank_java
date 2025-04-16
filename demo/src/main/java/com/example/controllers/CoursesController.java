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

    public Integer insertCourse(String courseName, String courseCode) {
        // Check if the course already exists
        String select = "SELECT courseID FROM courses WHERE courseName = ? OR courseCode = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, courseName);
            stmt.setString(2, courseCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("courseID"); // Course already exists
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO courses (courseName, courseCode) VALUES (?, ?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, courseName);
            pstmt.setString(2, courseCode);
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Return the new course ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the insert failed
    }

    public ArrayList<String> getAllCourseCodes() {
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

    public ArrayList<String[]> getAllCourses() {
        ArrayList<String[]> courses = new ArrayList<>();
        String sql = "SELECT courseCode, courseID, courseName FROM courses;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new String[]{rs.getString("courseCode"), rs.getString("courseID"), rs.getString("courseName")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean deleteCourse(Integer courseId) {
        String sql = "DELETE FROM courses WHERE courseID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public boolean updateCourse(Integer courseId, String newCourseCode, String newCourseName) {
        String sql = "UPDATE courses SET courseCode = ?, courseName = ? WHERE courseID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newCourseCode);
            pstmt.setString(2, newCourseName);
            pstmt.setInt(3, courseId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public String getCourseCodeById(Integer courseID) {
        if (courseID == null) {
            System.out.println("Course ID is null.");
            return null; // Return null if the course ID is invalid
        }
        String sql = "SELECT courseCode FROM courses WHERE courseID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("courseCode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no course found with the given ID
    }

    public Integer getCourseIdByName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            System.out.println("getCourseIdByName Course name is null or empty.");
            return null; // Return null if the course name is invalid
        }
        String sql = "SELECT courseID FROM courses WHERE LOWER(courseName) = LOWER(?);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseName.trim());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("getCourseIdByName Course ID found: " + rs.getInt("courseID"));
                return rs.getInt("courseID");
            } else {
                System.out.println("getCourseIdByName No course found with name: " + courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no course found with the given name
    }

    public String getCourseNameById(Integer courseID) {
        String sql = "SELECT courseName FROM courses WHERE courseID = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("courseName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no course found with the given ID
    }
}
