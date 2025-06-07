package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentIdAndPin(String studentId, String pin);

    Optional<Object> findByStudentId(String studentId);
}
