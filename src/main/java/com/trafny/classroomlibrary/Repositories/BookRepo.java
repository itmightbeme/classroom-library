package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
}
