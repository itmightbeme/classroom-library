package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepo extends JpaRepository<BookCopy, Long> {
}
