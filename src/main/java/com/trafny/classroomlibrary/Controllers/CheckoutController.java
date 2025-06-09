package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.BookCopyRepo;
import com.trafny.classroomlibrary.Repositories.CheckoutRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class CheckoutController {

    @Autowired
    private BookCopyRepo bookCopyRepo;
    @Autowired
    private CheckoutRepo checkoutRepo;

    @GetMapping("/students/simple-checkout")
    public String showCheckoutForm(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("loggedInStudent");
        if (student == null) {
            return "redirect:/students/login?error=session";
        }

        model.addAttribute("username", student.getStudentId());
        return "students/simple-checkout";
    }

    @PostMapping("students/simple-checkout/handle")
    public String processCheckout(@RequestParam("simpleId") String simpleId,
                                  HttpSession session,
                                  Model model) {
        Student student = (Student) session.getAttribute("loggedInStudent");
        if (student == null) {
            return "redirect:/students/login?error=session";
        }

        model.addAttribute("username", student.getStudentId());

        Optional<BookCopy> copyOpt = bookCopyRepo.findBySimpleId(simpleId.trim().toUpperCase());

        if (copyOpt.isEmpty()) {
            model.addAttribute("error", "Book copy not found.");
            return "students/simple-checkout";
        }

        BookCopy bookCopy = copyOpt.get();

        if (!bookCopy.isAvailable()) {
            model.addAttribute("error", "That book is currently unavailable.");
            return "students/simple-checkout";
        }

        if (checkoutRepo.existsByUserAndBookCopyAndReturnDateIsNull(student, bookCopy)) {
            model.addAttribute("error", "You already have this book checked out.");
            return "students/simple-checkout";
        }

        Checkout checkout = new Checkout();
        checkout.setUser(student);
        checkout.setBookCopy(bookCopy);
        checkout.setCheckoutDate(LocalDate.now());
        checkout.setDueDate(LocalDate.now().plusDays(14));

        bookCopy.setAvailable(false);

        checkoutRepo.save(checkout);
        bookCopyRepo.save(bookCopy);

        model.addAttribute("success", "Book successfully checked out!");
        return "students/simple-checkout";
    }



}




