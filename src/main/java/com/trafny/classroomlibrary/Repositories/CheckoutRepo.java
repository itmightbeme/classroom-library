package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepo extends JpaRepository<Checkout, Long> {
    List<Checkout> findByUserAndReturnDateIsNull(User user);
}
