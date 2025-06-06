package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Repositories.BookCopyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookCopyRepo bookCopyRepo;

    @GetMapping("/inventory")
    public String showInventory(Model model) {
        List<BookCopy> copies = bookCopyRepo.findAll();
        copies.sort(Comparator.comparing(copy -> copy.getBook().getTitle(), String.CASE_INSENSITIVE_ORDER));
        model.addAttribute("copies", copies);
        return "books/inventory";
    }










}
