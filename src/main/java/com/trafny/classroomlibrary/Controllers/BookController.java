package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Book;
import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Genre;
import com.trafny.classroomlibrary.Repositories.BookCopyRepo;
import com.trafny.classroomlibrary.Repositories.BookRepo;
import com.trafny.classroomlibrary.Repositories.GenreRepo;
import com.trafny.classroomlibrary.Repositories.TopicRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private BookCopyRepo bookCopyRepo;

    @Autowired
    private GenreRepo genreRepo;

    @Autowired
    private TopicRepo topicRepo;

    @GetMapping("/inventory")
    public String showInventory(Model model) {
        List<BookCopy> copies = bookCopyRepo.findAll();
        copies.sort(Comparator.comparing(copy -> copy.getBook().getTitle(), String.CASE_INSENSITIVE_ORDER));
        model.addAttribute("copies", copies);
        return "books/inventory";
    }

    @GetMapping("/detail/{id}")
    public String showBookDetail(@PathVariable Long id, Model model) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        model.addAttribute("book", book);
        return "books/detail";
    }


    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genreRepo.findAll());
        model.addAttribute("topics", topicRepo.findAll());
        return "books/detail";
    }
}