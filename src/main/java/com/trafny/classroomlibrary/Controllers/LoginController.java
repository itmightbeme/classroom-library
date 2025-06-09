package com.trafny.classroomlibrary.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.StudentRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // This assumes your login.html is in src/main/resources/templates/
    }


    private final StudentRepo studentRepo;

    public LoginController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping("/students/login")
    public String showStudentLogin(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "students/login";
    }

    @PostMapping("/students/login")
    public String processStudentLogin(@RequestParam("studentId") String studentId,
                                      @RequestParam("pin") String pin,
                                      HttpSession session) {

        Optional<Student> studentOpt = studentRepo.findByStudentIdAndPin(studentId, pin);
// for debugging only
        System.out.println("Login attempt: " + studentId + " / " + pin);
        studentRepo.findAll().forEach(s -> {
            System.out.println(s.getStudentId() + " / " + s.getPin());
        });

        if (studentOpt.isPresent()) {
            session.setAttribute("loggedInStudent", studentOpt.get());
            return "redirect:/students/dashboard"; // or student dashboard
        } else {
            return "redirect:/students/login?error";
        }
    }
}

