package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Student;
import com.trafny.classroomlibrary.Repositories.CheckoutRepo;
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
    private final CheckoutRepo checkoutRepo;

    public StudentController(StudentRepo studentRepo, CheckoutRepo checkoutRepo) {
        this.studentRepo = studentRepo;
        this.checkoutRepo = checkoutRepo;
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
    @GetMapping("/new")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "teachers/students/detail";
    }

//    @GetMapping("/detail/{id}")
//    public String showEditStudentForm(@PathVariable Long id, Model model) {
//        Student student = studentRepo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
//        model.addAttribute("student", student);
//        return "teachers/students/detail";}


@PostMapping("/add")
public String addStudent(
        @Valid @ModelAttribute("student") Student student,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
) {
    Optional<Student> existing = studentRepo.findByStudentId(student.getStudentId());

    if (existing.isPresent() && !existing.get().getId().equals(student.getId())) {
        bindingResult.rejectValue("studentId", "error.student", "Student ID must be unique.");
    }

    if (bindingResult.hasErrors()) {
        return "teachers/students/detail";
    }

    studentRepo.save(student);
    redirectAttributes.addFlashAttribute("successMessage", "Student added successfully.");
    return "redirect:/teachers/students/list";
}


    @PostMapping("/edit/{id}")
    public String updateStudent(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Student dbStudent = studentRepo.findById(id).orElse(null);
        if (dbStudent == null) {
            bindingResult.reject("student", "Student not found.");
            return "teachers/students/detail";
        }

        // If submitted ID doesn't match original, reject
        if (!dbStudent.getStudentId().equals(student.getStudentId())) {
            bindingResult.rejectValue("studentId", "error.student", "Student ID cannot be changed.");
        }

        if (bindingResult.hasErrors()) {
            return "teachers/students/detail";
        }

        dbStudent.setName(student.getName());
        dbStudent.setPin(student.getPin());
        dbStudent.setReadingLevel(student.getReadingLevel());
        studentRepo.save(dbStudent);

        redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully.");
        return "redirect:/teachers/students/list";
    }


    // Delete student (used on detail page)
    @PostMapping("/delete")
    public String deleteStudent(@RequestParam("id") Long studentId,
                                RedirectAttributes redirectAttributes) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);

        if (studentOpt.isPresent()) {
            List<Checkout> checkouts = checkoutRepo.findByUserId(studentId);
            if (!checkouts.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Cannot delete student. This student has checkout records.");
                return "redirect:/teachers/students/detail/" + studentId;
            }

            studentRepo.deleteById(studentId);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Student not found.");
        }

        return "redirect:/teachers/students/list";
    }

    //Show student reading history
    @GetMapping("/detail/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));

        List<Checkout> checkouts = checkoutRepo.findByUserId(id);

        // Calculate total books read (returned)
        long totalRead = checkouts.stream()
                .filter(c -> c.getReturnDate() != null)
                .count();

        // Calculate average reading level from returned books
        double avgReading = checkouts.stream()
                .filter(c -> c.getReturnDate() != null)
                .mapToDouble(c -> c.getBookCopy().getBook().getReadingLevel())
                .average()
                .orElse(0.0);

        model.addAttribute("student", student);
        model.addAttribute("checkouts", checkouts);
        model.addAttribute("totalRead", totalRead);
        model.addAttribute("avgReadingLevel", avgReading);

        return "teachers/students/detail";
    }

}





