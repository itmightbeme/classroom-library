package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepo extends JpaRepository<Checkout, Long> {
}
