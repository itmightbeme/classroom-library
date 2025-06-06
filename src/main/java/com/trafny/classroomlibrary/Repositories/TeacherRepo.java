package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUsername(String username);

}
