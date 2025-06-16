package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CheckoutRepo extends JpaRepository<Checkout, Long> {
    List<Checkout> findByUserAndReturnDateIsNull(User user);

    List<Checkout> findByUserAndReturnDateIsNotNull(User user);

    boolean existsByUserAndBookCopyAndReturnDateIsNull(User user, BookCopy bookCopy);


    int countByReturnDateIsNull();

    int countByDueDateBeforeAndReturnDateIsNull(LocalDate date);

    List<Checkout> findByReturnDateIsNull();


    List<Checkout> findByReturnDateIsNullAndDueDateBefore(LocalDate date);


    List<Checkout> findByUserId(Long studentId);
}
