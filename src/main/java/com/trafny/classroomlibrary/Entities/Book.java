package com.trafny.classroomlibrary.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max=100)
    private String title;

    @NotBlank(message = "Author is required.")
    @Size(max=100)
    private String author;

    @ManyToMany
    @JoinTable(name="book_genres", joinColumns = @JoinColumn(name="book_id"),inverseJoinColumns = @JoinColumn(name="genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(name="book_topics", joinColumns = @JoinColumn(name="book_id"), inverseJoinColumns = @JoinColumn(name="topic_id"))
    private Set<Topic> topics = new HashSet<>();
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
    private String isbn;

    @Min(0)
    @Max(12)
    private double readingLevel;

    private String description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookCopy> bookCopies = new ArrayList<>();


    //Constructor

    public Book() {
    }


    //Getters & Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title != null ? title.trim() : null;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author != null ? author.trim() : null;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getReadingLevel() {
        return readingLevel;
    }

    public void setReadingLevel(double readingLevel) {
        this.readingLevel = readingLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;

    }
}
