package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Long> {
}
