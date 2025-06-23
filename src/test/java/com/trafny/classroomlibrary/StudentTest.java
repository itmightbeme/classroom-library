package com.trafny.classroomlibrary;

import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.StudentRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudentTest {

    @Autowired
    private StudentRepo studentRepo;

    @Test
    void testAddAndDeleteStudent() {
        Student student = new Student();
        student.setName("Test Student");
        student.setEmail("student@test.com");
        student.setPin("1234");
        student.setStudentId("TS01");
        student.setReadingLevel(2.4);

        Student saved = studentRepo.save(student);
        assertNotNull(saved.getId());
        assertEquals("TS01", saved.getStudentId());

        //Confirm add
        Optional<Student> fetched = studentRepo.findById(saved.getId());
        assertTrue(fetched.isPresent());

        //Delete
        studentRepo.delete(saved);

        //Confirm delete
        Optional<Student> deleted = studentRepo.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void testInvalidIDStudentAdd() {
        Student student = new Student();
        student.setName("Student Testing");
        student.setEmail("studentb@test.com");
        student.setPin("1234");
        student.setStudentId("HBTRK"); //Invalid should be AA00
        student.setReadingLevel(2.4);

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertFalse(violations.isEmpty());
        boolean hasStudentIdViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString().equals("studentId"));
        assertTrue(hasStudentIdViolation, "Validation should catch incorrect studentId");
    }

    @Test
    void testBlankNameFailsValidation() {
        Student student = new Student();
        student.setName("");//Invalid
        student.setEmail("studentb@test.com");
        student.setPin("1234");
        student.setStudentId("ST99");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Student with blank name should fail");
    }

    @Test
    void testInvalidAuthorThrowsConstraintViolationError() {
        Student student = new Student();
        student.setName("");//Invalid
        student.setEmail("studentb@test.com");
        student.setPin("1234");
        student.setStudentId("ST99");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty());
    }







}
