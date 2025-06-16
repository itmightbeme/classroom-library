package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Book;
import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Genre;
import com.trafny.classroomlibrary.Entities.Topic;
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
import java.util.Optional;

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

    //Method for updating books/detail
    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute @Valid Book updatedBook, BindingResult result, RedirectAttributes ra) {
        // Manually trim strings
        updatedBook.setTitle(updatedBook.getTitle().trim());
        updatedBook.setAuthor(updatedBook.getAuthor().trim());
        if (updatedBook.getIsbn() != null) {
            updatedBook.setIsbn(updatedBook.getIsbn().trim());
        }
        if (updatedBook.getDescription() != null) {
            updatedBook.setDescription(updatedBook.getDescription().trim());
        }

        if (result.hasErrors()) {
            ra.addFlashAttribute("error", "Please fix validation errors.");
            return "books/detail"; // Note: if you want errors to persist, consider using RedirectAttributes or Model
        }

        Book existingBook = bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setReadingLevel(updatedBook.getReadingLevel());
        existingBook.setDescription(updatedBook.getDescription());

        bookRepo.save(existingBook);
        ra.addFlashAttribute("success", "Book updated successfully.");
        return "redirect:/books/detail/" + id;
    }

    @PostMapping("/{id}/genres/add")
    public String addGenreToBook(@PathVariable Long id,
                                 @RequestParam String genreName,
                                 RedirectAttributes ra) {
        String trimmedName = genreName.trim();
        if (trimmedName.isEmpty()) {
            ra.addFlashAttribute("error", "Genre name cannot be empty.");
            return "redirect:/books/detail/" + id;
        }

        // Title-case the name
        String formattedName = Character.toUpperCase(trimmedName.charAt(0)) + trimmedName.substring(1).toLowerCase();

        Book book = bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));

        // Case-insensitive match for existing genre
        Genre genre = genreRepo.findAll().stream()
                .filter(g -> g.getName().equalsIgnoreCase(trimmedName))
                .findFirst()
                .orElseGet(() -> {
                    Genre newGenre = new Genre();
                    newGenre.setName(formattedName);
                    return genreRepo.save(newGenre);
                });

        if (!book.getGenres().contains(genre)) {
            book.getGenres().add(genre);
            bookRepo.save(book);
            ra.addFlashAttribute("success", "Genre added.");
        } else {
            ra.addFlashAttribute("info", "Book already has this genre.");
        }

        return "redirect:/books/detail/" + id;
    }

    @PostMapping("/{id}/topics/add")
    public String addTopicToBook(@PathVariable Long id,
                                 @RequestParam String topicName,
                                 RedirectAttributes ra) {
        String trimmedName = topicName.trim();
        if (trimmedName.isEmpty()) {
            ra.addFlashAttribute("error", "Topic name cannot be empty.");
            return "redirect:/books/detail/" + id;
        }

        // Title-case the name
        String formattedName = Character.toUpperCase(trimmedName.charAt(0)) + trimmedName.substring(1).toLowerCase();

        Book book = bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));

        Topic topic = topicRepo.findAll().stream()
                .filter(t -> t.getName().equalsIgnoreCase(trimmedName))
                .findFirst()
                .orElseGet(() -> {
                    Topic newTopic = new Topic();
                    newTopic.setName(formattedName);
                    return topicRepo.save(newTopic);
                });

        if (!book.getTopics().contains(topic)) {
            book.getTopics().add(topic);
            bookRepo.save(book);
            ra.addFlashAttribute("success", "Topic added.");
        } else {
            ra.addFlashAttribute("info", "Book already has this topic.");
        }

        return "redirect:/books/detail/" + id;
    }

    @PostMapping("/{bookId}/genres/remove/{genreId}")
    public String removeGenreFromBook(@PathVariable Long bookId,
                                      @PathVariable Long genreId,
                                      RedirectAttributes ra) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + bookId));
        Genre genre = genreRepo.findById(genreId).orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));

        if (book.getGenres().remove(genre)) {
            bookRepo.save(book);
            ra.addFlashAttribute("success", "Genre removed from book.");
        } else {
            ra.addFlashAttribute("info", "Genre was not associated with this book.");
        }

        return "redirect:/books/detail/" + bookId;
    }

    @PostMapping("/{bookId}/topics/remove/{topicId}")
    public String removeTopicFromBook(@PathVariable Long bookId,
                                      @PathVariable Long topicId,
                                      RedirectAttributes ra) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + bookId));
        Topic topic = topicRepo.findById(topicId).orElseThrow(() -> new IllegalArgumentException("Invalid topic ID: " + topicId));

        if (book.getTopics().remove(topic)) {
            bookRepo.save(book);
            ra.addFlashAttribute("success", "Topic removed from book.");
        } else {
            ra.addFlashAttribute("info", "Topic was not associated with this book.");
        }

        return "redirect:/books/detail/" + bookId;
    }

    //save updated book copy inline

    @PostMapping("/copy/save/{copyId}")
    public String updateBookCopy(@PathVariable Long copyId,
                                 @RequestParam String simpleId,
                                 @RequestParam(required = false) String location,
                                 RedirectAttributes ra) {

        BookCopy copy = bookCopyRepo.findById(copyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid copy ID: " + copyId));

        String trimmedId = simpleId.trim().toUpperCase();

        // Check if another copy already uses this simpleId
        Optional<BookCopy> existing = bookCopyRepo.findBySimpleId(trimmedId);

        if (existing.isPresent() && !existing.get().getId().equals(copyId)) {
            ra.addFlashAttribute("error", "That Simple ID is already in use.");
            return "redirect:/books/detail/" + copy.getBook().getId();
        }

        copy.setSimpleId(trimmedId);
        copy.setLocation(location != null ? location.trim() : null);

        bookCopyRepo.save(copy);
        ra.addFlashAttribute("success", "Copy updated.");
        return "redirect:/books/detail/" + copy.getBook().getId();
    }

    @PostMapping("/copy/delete/{copyId}")
    public String deleteBookCopy(@PathVariable Long copyId,
                                 @RequestParam(required = false) boolean confirmFallback,
                                 RedirectAttributes ra) {
        BookCopy copy = bookCopyRepo.findById(copyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid copy ID: " + copyId));

        Long bookId = copy.getBook().getId();

        if (!copy.getCheckouts().isEmpty()) {
            if (confirmFallback) {
                copy.setAvailable(false);
                copy.setLocation("Out of circulation");
                bookCopyRepo.save(copy);
                ra.addFlashAttribute("info", "Copy not deleted. Marked as unavailable and out of circulation.");
            } else {
                ra.addFlashAttribute("error", "Cannot delete: This copy has checkout history.");
            }
            return "redirect:/books/detail/" + bookId;
        }

        bookCopyRepo.delete(copy);
        ra.addFlashAttribute("success", "Book copy deleted.");
        return "redirect:/books/detail/" + bookId;
    }

    @PostMapping("/add")
    public String saveNewBook(@ModelAttribute @Valid Book book,
                              BindingResult result,
                              @RequestParam String simpleId,
                              @RequestParam(required = false) String location,
                              RedirectAttributes ra,
                              Model model) {

        // Trim and uppercase the simpleId
        String trimmedId = simpleId.trim().toUpperCase();

        // Check if simpleId already exists
        if (bookCopyRepo.findBySimpleId(trimmedId).isPresent()) {
            ra.addFlashAttribute("error", "That Simple ID is already in use.");
            model.addAttribute("book", book);
            model.addAttribute("genres", genreRepo.findAll());
            model.addAttribute("topics", topicRepo.findAll());
            return "books/detail";
        }

        if (result.hasErrors()) {
            ra.addFlashAttribute("error", "Please fix validation errors.");
            model.addAttribute("genres", genreRepo.findAll());
            model.addAttribute("topics", topicRepo.findAll());
            return "books/detail";
        }

        // Save the book
        Book savedBook = bookRepo.save(book);

        // Create and save first copy
        BookCopy copy = new BookCopy();
        copy.setBook(savedBook);
        copy.setSimpleId(trimmedId);
        copy.setAvailable(true);
        copy.setLocation(location != null ? location.trim() : "");

        bookCopyRepo.save(copy);

        ra.addFlashAttribute("success", "Book and first copy saved.");
        return "redirect:/books/detail/" + savedBook.getId();
    }


    @PostMapping("/{id}/copy/add")
    public String addCopyToExistingBook(@PathVariable Long id,
                                        @RequestParam String simpleId,
                                        @RequestParam(required = false) String location,
                                        RedirectAttributes ra) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));

        String trimmedId = simpleId.trim().toUpperCase();

        if (bookCopyRepo.findBySimpleId(trimmedId).isPresent()) {
            ra.addFlashAttribute("error", "That Simple ID is already in use.");
            return "redirect:/books/detail/" + id;
        }

        BookCopy newCopy = new BookCopy();
        newCopy.setBook(book);
        newCopy.setSimpleId(trimmedId);
        newCopy.setAvailable(true);
        newCopy.setLocation(location != null ? location.trim() : "");

        bookCopyRepo.save(newCopy);
        ra.addFlashAttribute("success", "New copy added.");
        return "redirect:/books/detail/" + id;
    }
















}