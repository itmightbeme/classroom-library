package com.trafny.classroomlibrary.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (optional = false)
    private Book book;

    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}$", message = "Simple ID must be 2 uppercase letters followed by 2 digits")
    @Column(unique = true, nullable = false)
    private String simpleId;

    private boolean available = true;

    private String location;


    //Constructor

    public BookCopy() {
    }


    //Getters & Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(String simpleId) {
        this.simpleId = simpleId != null ? simpleId.trim().toUpperCase() : null;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location != null ? location.trim() : null;

    }
}
