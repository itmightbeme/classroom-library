package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Genre;
import com.trafny.classroomlibrary.Repositories.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookGenreController {

    @Autowired
    private GenreRepo genreRepo;

    @GetMapping("/books/genres/list")
    public String listGenres(Model model) {
        List<Genre> genres = genreRepo.findAll();
        model.addAttribute("genres", genres);
        model.addAttribute("newGenre", new Genre());
        return "books/genres/list";
    }

    @PostMapping("/books/genres")
    public String createGenre(@ModelAttribute Genre newGenre, RedirectAttributes ra) {
        genreRepo.save(newGenre);
        ra.addFlashAttribute("message", "Genre added successfully.");
        return "redirect:/books/genres/list";
    }

    @PostMapping("/books/genres/delete/{id}")
    public String deleteGenre(@PathVariable Long id, RedirectAttributes ra) {
        genreRepo.deleteById(id);
        ra.addFlashAttribute("message", "Genre deleted.");
        return "redirect:/books/genres/list";
    }


}


