package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCopyRepo extends JpaRepository<BookCopy, Long> {

    Optional<BookCopy> findBySimpleId(String simpleId);



}
