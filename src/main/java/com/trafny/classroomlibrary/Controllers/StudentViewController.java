package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Book;
import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.BookCopyRepo;
import com.trafny.classroomlibrary.Repositories.BookRepo;
import com.trafny.classroomlibrary.Repositories.CheckoutRepo;
import com.trafny.classroomlibrary.Repositories.StudentRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentViewController {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private BookCopyRepo bookCopyRepo;

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/dashboard")
    public String showStudentDashboard(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("loggedInStudent");

        if (student == null) {
            return "redirect:/students/login?error=session";
        }

        List<Checkout> checkouts = checkoutRepo.findByUserAndReturnDateIsNull(student);
        List<Checkout> completedCheckouts = checkoutRepo.findByUserAndReturnDateIsNotNull(student);

        long overdueBooks = checkouts.stream()
                .filter(c -> c.getDueDate().isBefore(LocalDate.now()))
                .count();

        long booksReadThisYear = completedCheckouts.stream()
                .filter(c -> c.getReturnDate() != null && c.getReturnDate().getYear() == LocalDate.now().getYear())
                .count();

        model.addAttribute("booksCheckedOut", checkouts.size());
        model.addAttribute("overdueBooks", overdueBooks);
        model.addAttribute("booksReadThisYear", booksReadThisYear);
        model.addAttribute("student", student);
        model.addAttribute("checkouts", checkouts);
        model.addAttribute("completedCheckouts", completedCheckouts);
        model.addAttribute("showDays", true);
        model.addAttribute("formAction", "/checkouts/return-by-id");


        return "students/dashboard";
    }


    @GetMapping("/sign-out")
    public String logoutStudent(HttpSession session) {
        session.invalidate(); // Clears all session attributes
        return "redirect:/students/login?logout";
    }

    //Student Search methods

    @GetMapping("/search")
    public String showSearchForm(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isBlank()) {
            List<BookCopy> results = bookCopyRepo.searchCopiesByTitleAuthorOrTopic(query.trim().toLowerCase());
            model.addAttribute("results", results);
        }
        model.addAttribute("query", query);
        return "students/search";
    }

    @GetMapping("/book-details/{id}")
    public String showStudentBookDetails(@PathVariable Long id, Model model) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        model.addAttribute("book", book);
        model.addAttribute("showActionColumn", false);
        model.addAttribute("readonlyMode", true);
        return "students/book-details";
    }
















}
