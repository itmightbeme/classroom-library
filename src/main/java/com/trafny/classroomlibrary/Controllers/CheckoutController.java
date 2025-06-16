package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.BookCopyRepo;
import com.trafny.classroomlibrary.Repositories.CheckoutRepo;
import com.trafny.classroomlibrary.Repositories.StudentRepo;
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
    @Autowired
    private StudentRepo studentRepo;

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

    @PostMapping("/checkouts/return-by-id")
    public String returnBook(@RequestParam("checkoutId") Long checkoutId,
                             RedirectAttributes redirectAttributes) {

        Optional<Checkout> checkoutOpt = checkoutRepo.findById(checkoutId);
        if (checkoutOpt.isPresent()) {
            Checkout checkout = checkoutOpt.get();

            // Mark the return date
            checkout.setReturnDate(LocalDate.now());

            // Mark book copy available again
            BookCopy copy = checkout.getBookCopy();
            copy.setAvailable(true);

            checkoutRepo.save(checkout);
            bookCopyRepo.save(copy);

            redirectAttributes.addFlashAttribute("success", "Book returned successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Checkout record not found.");
        }

        return "redirect:/students/dashboard"; // or wherever you want to redirect
    }

    @PostMapping("/checkouts/return-by-id/teacher")
    public String returnBookFromTeacher(@RequestParam("checkoutId") Long checkoutId,
                                        RedirectAttributes redirectAttributes) {

        Optional<Checkout> checkoutOpt = checkoutRepo.findById(checkoutId);
        if (checkoutOpt.isPresent()) {
            Checkout checkout = checkoutOpt.get();
            checkout.setReturnDate(LocalDate.now());

            BookCopy copy = checkout.getBookCopy();
            copy.setAvailable(true);

            checkoutRepo.save(checkout);
            bookCopyRepo.save(copy);

            redirectAttributes.addFlashAttribute("success", "Book returned successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Checkout record not found.");
        }

        return "redirect:/teachers/dashboard";
    }

    @GetMapping("teachers/students/checkout")
    public String showTeacherCheckoutForm(Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "teachers/students/checkout";
    }

    @PostMapping("/teachers/students/checkout")
    public String processTeacherCheckout(@RequestParam("studentId") Long studentId,
                                         @RequestParam("simpleId") String simpleId,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Student not found.");
            return "redirect:/teachers/students/checkout";
        }

        Student student = studentOpt.get();

        Optional<BookCopy> copyOpt = bookCopyRepo.findBySimpleId(simpleId.trim().toUpperCase());
        if (copyOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Book copy not found.");
            return "redirect:/teachers/students/checkout";
        }

        BookCopy bookCopy = copyOpt.get();

        if (!bookCopy.isAvailable()) {
            redirectAttributes.addFlashAttribute("error", "That book is currently unavailable.");
            return "redirect:/teachers/students/checkout";
        }

        if (checkoutRepo.existsByUserAndBookCopyAndReturnDateIsNull(student, bookCopy)) {
            redirectAttributes.addFlashAttribute("error", "This student already has that book checked out.");
            return "redirect:/teachers/students/checkout";
        }

        Checkout checkout = new Checkout();
        checkout.setUser(student);
        checkout.setBookCopy(bookCopy);
        checkout.setCheckoutDate(LocalDate.now());
        checkout.setDueDate(LocalDate.now().plusDays(14));

        bookCopy.setAvailable(false);

        checkoutRepo.save(checkout);
        bookCopyRepo.save(bookCopy);

        redirectAttributes.addFlashAttribute("success", "Book successfully checked out to " + student.getName() + "!");
        return "redirect:/teachers/students/checkout";
    }









}




