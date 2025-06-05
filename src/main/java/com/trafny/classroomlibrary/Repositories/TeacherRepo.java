package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
