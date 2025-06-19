package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Teacher;
import com.trafny.classroomlibrary.Repositories.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private BookCopyRepo bookCopyRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private StudentRepo studentRepo;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();







    @GetMapping("/dashboard")
    public String showTeacherDashboard(Model model) {
        model.addAttribute("bookCount", bookRepo.count());
        model.addAttribute("studentCount", studentRepo.count());
        model.addAttribute("booksCheckedOutCount", checkoutRepo.countByReturnDateIsNull());
        model.addAttribute("overdueCount", checkoutRepo.countByDueDateBeforeAndReturnDateIsNull(LocalDate.now()));
        model.addAttribute("students", studentRepo.findAll());
        model.addAttribute("checkouts", checkoutRepo.findByReturnDateIsNull());

        return "teachers/dashboard";
    }


    @GetMapping("/account")
    public String viewAccount(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Teacher> teacher = teacherRepo.findByUsername(username);

        if (teacher.isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("teacher", teacher.get());
        model.addAttribute("teachers", teacherRepo.findAll());  // Supports fragment
        return "teachers/account";
    }



    @PostMapping("/account/edit/{id}")
    public String updateAccount(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("teacher") Teacher teacher,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        Teacher dbTeacher = teacherRepo.findById(id).orElse(null);
        if (dbTeacher == null) {
            bindingResult.reject("teacher", "Teacher not found.");
            return "teachers/account";
        }

        // Check if username is changed and unique
        teacherRepo.findByUsername(teacher.getUsername()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                bindingResult.rejectValue("username", "error.teacher", "Username must be unique.");
            }
        });

        if (bindingResult.hasErrors()) {
            return "teachers/account";
        }

        // Update allowed fields
        dbTeacher.setName(teacher.getName());
        dbTeacher.setUsername(teacher.getUsername());
        dbTeacher.setEmail(teacher.getEmail());
        dbTeacher.setSubject(teacher.getSubject());
        dbTeacher.setClassGrade(teacher.getClassGrade());
        dbTeacher.setClassroom(teacher.getClassroom());

        teacherRepo.save(dbTeacher);
        redirectAttributes.addFlashAttribute("successMessage", "Account updated successfully.");
        return "redirect:/teachers/account";
    }

    @GetMapping("/new-teacher-form")
    public String showNewTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());  // Bind to 'teacher' to match the form
        return "teachers/new-teacher-form";  // Your new template
    }



    @PostMapping("/account/new")
    public String addNewTeacher(
            @Valid @ModelAttribute("teacher") Teacher teacher,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        // Check for unique username
        teacherRepo.findByUsername(teacher.getUsername()).ifPresent(existing -> {
            bindingResult.rejectValue("username", "error.teacher", "Username must be unique.");
        });

        if (bindingResult.hasErrors()) {
            return "teachers/new-teacher-form";
        }

        //Hash the password before saving.
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));


        teacherRepo.save(teacher);
        redirectAttributes.addFlashAttribute("successMessage", "New teacher added.");
        return "redirect:/teachers/account";
    }

    @PostMapping("/account/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        Optional<Teacher> teacherOpt = teacherRepo.findByUsername(principal.getName());

        if (teacherOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Teacher not found.");
            return "redirect:/teachers/account";
        }

        Teacher teacher = teacherOpt.get();

        if (!passwordEncoder.matches(currentPassword, teacher.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Current password is incorrect.");
            return "redirect:/teachers/account";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "New passwords do not match.");
            return "redirect:/teachers/account";
        }

        // Update and encode new password
        teacher.setPassword(passwordEncoder.encode(newPassword));
        teacherRepo.save(teacher);

        redirectAttributes.addFlashAttribute("success", "Password updated successfully.");
        return "redirect:/teachers/account";
    }

    @GetMapping("/reports/find-by-simpleid")
    public String showFindBySimpleIdForm() {
        return "teachers/reports/find-by-simpleid";  // You can include the fragment here
    }

    @GetMapping("/reports/process-simpleid")
    public String processSimpleIdLookup(@RequestParam String simpleId,
                                        RedirectAttributes redirectAttributes) {
        Optional<BookCopy> copyOpt = bookCopyRepo.findBySimpleIdIgnoreCase(simpleId.trim());

        if (copyOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No book found for Simple ID: " + simpleId);
            return "redirect:/teachers/reports/find-by-simpleid";
        }

        Long bookId = copyOpt.get().getBook().getId();
        return "redirect:/books/detail/" + bookId;
    }











}





