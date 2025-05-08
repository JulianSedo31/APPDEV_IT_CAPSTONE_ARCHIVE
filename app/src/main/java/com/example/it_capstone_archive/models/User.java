package com.example.it_capstone_archive.models;

public class User {
    private String userId;
    private String email;
    private String fullName;
    private String userType; // "STUDENT" or "TEACHER"
    private String studentId; // Optional, for students only
    private String department;

    // Empty constructor for Firestore
    public User() {}

    public User(String userId, String email, String fullName, String userType, String department) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.userType = userType;
        this.department = department;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
} 