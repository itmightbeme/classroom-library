package com.trafny.classroomlibrary.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    @NotBlank(message = "Username is required.")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is required.")
    @Column(nullable = false)
    private String password;

    private String subject;

    private String classGrade;
    private String classroom;

    //Constructor

    public Teacher() {
    }


    //Getters and Setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username != null ? username.trim() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject != null ? subject.trim() : null;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade != null ? classGrade.trim() : null;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom != null ? classroom.trim() : null;
    }
}
