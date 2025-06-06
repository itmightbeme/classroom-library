package com.trafny.classroomlibrary.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentViewController {

    @GetMapping("/dashboard")
    public String showStudentDashboard() {
        return "students/dashboard";
    }

    @GetMapping("/sign-out")
    public String logoutStudent(HttpSession session) {
        session.invalidate(); // Clears all session attributes
        return "redirect:/students/login?logout";
    }








}
