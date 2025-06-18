package com.trafny.classroomlibrary.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{

     @NotBlank
     @Pattern(regexp = "^[A-Z]{2}[0-9]{2}$", message = "Student ID must be 2 uppercase letters followed by 2 digits")
     @Column(unique = true)
     private String studentId;

     @NotBlank(message = "PIN is required.")
     @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
     private String pin;

     @Min(0)
     @Max(12)
     private double readingLevel;

     //Constructor

     public Student() {
          super();
     }

     public Student(String name, String email, String studentId, String pin, double readingLevel) {
          super(name, email);
          this.studentId = studentId;
          this.pin = pin;
          this.readingLevel = readingLevel;
     }

     //Getters and Setters


     public String getStudentId() {
          return studentId;
     }

     public void setStudentId(String studentId) {
          this.studentId = studentId != null ? studentId.trim().toUpperCase() : null;
     }

     public String getPin() {
          return pin;
     }

     public void setPin(String pin) {
          this.pin = pin;
     }

     public double getReadingLevel() {
          return readingLevel;
     }

     public void setReadingLevel(double readingLevel) {
          this.readingLevel = readingLevel;
     }
}
