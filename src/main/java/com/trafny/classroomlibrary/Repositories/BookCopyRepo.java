package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepo extends JpaRepository<BookCopy, Long> {

    Optional<BookCopy> findBySimpleId(String simpleId);


    @Query("""
    SELECT bc FROM BookCopy bc
    JOIN bc.book b
    LEFT JOIN b.topics t
    WHERE bc.available = true
      AND (LOWER(b.title) LIKE %:query%
           OR LOWER(b.author) LIKE %:query%
           OR LOWER(t.name) LIKE %:query%)
      AND (bc.location IS NULL OR LOWER(bc.location) <> 'out of circulation')
""")
    List<BookCopy> searchAvailableCopiesByTitleAuthorOrTopic(@Param("query") String query);

    @Query("""
SELECT bc FROM BookCopy bc
JOIN bc.book b
LEFT JOIN b.topics t
WHERE (LOWER(b.title) LIKE %:query%
   OR LOWER(b.author) LIKE %:query%
   OR LOWER(t.name) LIKE %:query%)
  AND (bc.location IS NULL OR LOWER(bc.location) <> 'out of circulation')
""")
    List<BookCopy> searchCopiesByTitleAuthorOrTopic(@Param("query") String query);


    Optional<BookCopy> findBySimpleIdIgnoreCase(String trim);
}
