package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.CheckoutRepo;
import com.trafny.classroomlibrary.Repositories.StudentRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentViewController {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

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

        return "students/dashboard";
    }


    @GetMapping("/sign-out")
    public String logoutStudent(HttpSession session) {
        session.invalidate(); // Clears all session attributes
        return "redirect:/students/login?logout";
    }


}
