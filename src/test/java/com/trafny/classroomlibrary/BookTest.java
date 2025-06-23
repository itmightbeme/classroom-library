package com.trafny.classroomlibrary;

import com.trafny.classroomlibrary.Entities.Book;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidBookPasses() {
        Book book = new Book();
        book.setTitle("Holes");
        book.setAuthor("Louis Sachar");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty(), "Book should pass validation");
    }

    @Test
    void testBlankTitleFailsValidation() {
        Book book = new Book();
        book.setTitle(""); // Invalid
        book.setAuthor("Louis Sachar");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty(), "Book with blank title should fail");
    }

    @Test
    void testInvalidAuthorThrowsConstraintViolationError() {
        Book book = new Book();
        book.setTitle("The Rainbow Fish");
        book.setAuthor("");// Invalid

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankAuthorFailsValidation() {
        Book book = new Book();
        book.setTitle("The Rainbow Fish");
        book.setAuthor("");// Invalid

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty(), "Book with blank author should fail");
    }







}
