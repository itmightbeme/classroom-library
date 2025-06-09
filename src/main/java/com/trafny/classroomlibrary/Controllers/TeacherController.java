package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Teacher;
import com.trafny.classroomlibrary.Repositories.TeacherRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherRepo teacherRepo;

    @GetMapping("/dashboard")
    public String showTeacherDashboard() {
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

        // ⚠️ Hash the password before saving (add PasswordEncoder if using Spring Security)
        // Example: teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

        teacherRepo.save(teacher);
        redirectAttributes.addFlashAttribute("successMessage", "New teacher added.");
        return "redirect:/teachers/account";
    }


}





