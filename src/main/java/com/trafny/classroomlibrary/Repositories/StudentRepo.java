package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
}
