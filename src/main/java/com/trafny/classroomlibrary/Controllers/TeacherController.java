package com.trafny.classroomlibrary.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers") public class TeacherController {

    @GetMapping("/dashboard")
    public String showTeacherDashboard() {
        return "teachers/dashboard";
    }


}




