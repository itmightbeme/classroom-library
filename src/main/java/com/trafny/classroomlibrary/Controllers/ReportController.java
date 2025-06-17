package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.BookCopy;
import com.trafny.classroomlibrary.Entities.Checkout;
import com.trafny.classroomlibrary.Entities.Genre;
import com.trafny.classroomlibrary.Entities.Topic;
import com.trafny.classroomlibrary.Repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/teachers/reports")
public class ReportController {

    private final BookRepo bookRepo;
    private final BookCopyRepo bookCopyRepo;
    private final CheckoutRepo checkoutRepo;
    private final StudentRepo studentRepo;
    private final TeacherRepo teacherRepo;
    private final TopicRepo topicRepo;
    private final GenreRepo genreRepo;

    public ReportController(BookRepo bookRepo,
                            BookCopyRepo bookCopyRepo,
                            CheckoutRepo checkoutRepo,
                            StudentRepo studentRepo,
                            TeacherRepo teacherRepo,
                            TopicRepo topicRepo,
                            GenreRepo genreRepo) {
        this.bookRepo = bookRepo;
        this.bookCopyRepo = bookCopyRepo;
        this.checkoutRepo = checkoutRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.topicRepo = topicRepo;
        this.genreRepo = genreRepo;
    }






    @GetMapping("/index")
    public String viewReportIndex(Model model) {
        return "teachers/reports/index";
    }


    //Overdue Books
    @GetMapping("/overdue-books")
    public String viewOverdueBooks(Model model) {
        List<Checkout> overdueCheckouts = checkoutRepo.findByReturnDateIsNullAndDueDateBefore(LocalDate.now());

        model.addAttribute("title", "Overdue Books");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("reportType", "overdueBooks");
        model.addAttribute("checkouts", overdueCheckouts);

        return "teachers/reports/results";
    }

    @GetMapping("/book-search")
    public String handleBookSearch(
            @RequestParam(value="title", required=false) String titleParam,
            @RequestParam(value="author", required=false) String authorParam,
            @RequestParam(value="topicId", required=false) Long topicId,
            @RequestParam(value="minLevel", required=false) Double minLevelParam,
            @RequestParam(value="maxLevel", required=false) Double maxLevelParam,
            @RequestParam(value="availability", required=false) String availability,
            @RequestParam(value="sortField", required=false) String sortField,
            @RequestParam(value="sortDesc", defaultValue="false") boolean sortDesc,
            Model model) {

        // 1. Trimmed input fields
        String title = titleParam != null ? titleParam.trim().toLowerCase() : "";
        String author = authorParam != null ? authorParam.trim().toLowerCase() : "";

        double minLevel = minLevelParam != null ? minLevelParam : 0;
        double maxLevel = maxLevelParam != null ? maxLevelParam : 12;

        // 2. Preserve form values for redisplay
        model.addAttribute("title", titleParam);
        model.addAttribute("author", authorParam);
        model.addAttribute("topicId", topicId);
        model.addAttribute("minLevel", minLevel);
        model.addAttribute("maxLevel", maxLevel);
        model.addAttribute("availability", availability);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDesc", sortDesc);

        // 3. Dropdown & report metadata
        List<Topic> topics = topicRepo.findAll();
        model.addAttribute("topics", topics);
        model.addAttribute("reportTitle", "Book Search Report");
        model.addAttribute("timestamp", LocalDateTime.now());

        // 4. Determine if any criteria set
        boolean hasCriteria =
                !title.isEmpty() ||
                        !author.isEmpty() ||
                        topicId != null ||
                        minLevelParam != null ||
                        maxLevelParam != null ||
                        (availability != null && !"both".equals(availability));

        // 5. Initial result set
        List<BookCopy> results = hasCriteria
                ? bookCopyRepo.searchCopiesByTitleAuthorOrTopic((title + " " + author).trim().toLowerCase())
                : bookCopyRepo.findAll();

        // 6. Apply Topic filter
        if (topicId != null) {
            results = results.stream()
                    .filter(bc -> bc.getBook().getTopics().stream()
                            .anyMatch(t -> t.getId().equals(topicId)))
                    .toList();
        }

        // 7. Apply Reading Level filter
        results = results.stream()
                .filter(bc -> {
                    double level = bc.getBook().getReadingLevel();
                    return level >= minLevel && level <= maxLevel;
                })
                .toList();

        // 8. Apply Availability filter
        if ("available".equals(availability)) {
            results = results.stream()
                    .filter(BookCopy::isAvailable)
                    .toList();
        } else if ("unavailable".equals(availability)) {
            results = results.stream()
                    .filter(bc -> !bc.isAvailable())
                    .toList();
        }

        // 9. Wrap in mutable list before sorting
        List<BookCopy> sortedResults = new ArrayList<>(results);

        // 10. Sorting based on criteria
        Comparator<BookCopy> comp;
        switch (sortField != null ? sortField : "") {
            case "author":
                comp = Comparator.comparing(bc -> bc.getBook().getAuthor(), String.CASE_INSENSITIVE_ORDER);
                break;
            case "topic":
                comp = Comparator.comparing(
                        bc -> bc.getBook().getTopics().stream()
                                .map(Topic::getName).sorted().findFirst().orElse(""),
                        String.CASE_INSENSITIVE_ORDER
                );
                break;
            case "readingLevel":
                comp = Comparator.comparingDouble(bc -> bc.getBook().getReadingLevel());
                break;
            case "title":
            default:
                comp = Comparator.comparing(bc -> bc.getBook().getTitle(), String.CASE_INSENSITIVE_ORDER);
                break;
        }
        if (sortDesc) {
            comp = comp.reversed();
        }
        sortedResults.sort(comp);

        // 11. Final result
        model.addAttribute("results", sortedResults);

        return "teachers/reports/book-search";
    }












}
