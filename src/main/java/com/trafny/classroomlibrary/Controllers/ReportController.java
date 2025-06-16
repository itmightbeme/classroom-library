package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/teachers/reports")
public class ReportController {

    private final BookRepo bookRepo;
    private final BookCopyRepo bookCopyRepo;
    private final CheckoutRepo checkoutRepo;
    private final StudentRepo studentRepo;
    private final TeacherRepo teacherRepo;
    private final TopicRepo topicRepo;
    private final GenreRepo genreRepo;

    public ReportController(BookRepo bookRepo,
                            BookCopyRepo bookCopyRepo,
                            CheckoutRepo checkoutRepo,
                            StudentRepo studentRepo,
                            TeacherRepo teacherRepo,
                            TopicRepo topicRepo,
                            GenreRepo genreRepo) {
        this.bookRepo = bookRepo;
        this.bookCopyRepo = bookCopyRepo;
        this.checkoutRepo = checkoutRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.topicRepo = topicRepo;
        this.genreRepo = genreRepo;
    }






    @GetMapping("/index")
    public String viewReportIndex(Model model) {
        return "teachers/reports/index";
    }


    //Overdue Books
    @GetMapping("/overdue-books")
    public String viewOverdueBooks(Model model) {
        List<Checkout> overdueCheckouts = checkoutRepo.findByReturnDateIsNullAndDueDateBefore(LocalDate.now());

        model.addAttribute("title", "Overdue Books");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("reportType", "overdueBooks");
        model.addAttribute("checkouts", overdueCheckouts);

        return "teachers/reports/results";
    }










}
