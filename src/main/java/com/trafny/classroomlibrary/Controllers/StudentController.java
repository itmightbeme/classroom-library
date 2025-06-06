package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.StudentRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/teachers/students")
public class StudentController {

    private final StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    // List all students
    @GetMapping("/list")
    public String listStudents(Model model) {
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        model.addAttribute("timestamp", "Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")));
        return "teachers/students/list";
    }

    // Show form to add new student
    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-add-form";
    }

    // Process new student submission
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentRepo.save(student);
        return "redirect:/teachers/students/list";
    }

    // Delete student (used on detail page)
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
        return "redirect:/teachers/students/list";
    }
}

