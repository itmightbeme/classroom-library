package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepo extends JpaRepository<Genre, Long> {
    Optional<Genre> findByNameIgnoreCase(String cleaned);
}
