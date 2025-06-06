package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.StudentRepo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/detail")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "teachers/students/detail";
    }

    @GetMapping("/detail/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        model.addAttribute("student", student);
        return "teachers/students/detail";
    }

    @PostMapping("/detail")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "teachers/students/detail";
        }

        studentRepo.save(student);
        return "redirect:/teachers/students/list";
    }

    // Delete student (used on detail page)
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentRepo.findById(id);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();

            // TODO: check if student has active checkouts (if you haven’t added this yet, just skip this block)
            // if (hasOutstandingBooks(student)) {
            //     redirectAttributes.addFlashAttribute("error", "Cannot delete: student has books checked out.");
            //     return "redirect:/teachers/students/detail/" + id;
            // }

            studentRepo.delete(student);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Student not found.");
        }

        return "redirect:/teachers/students/list";
    }
}

