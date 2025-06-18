package com.trafny.classroomlibrary;

import com.trafny.classroomlibrary.Entities.*;
import com.trafny.classroomlibrary.Repositories.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class DataInitializer implements ApplicationRunner {

    private final GenreRepo genreRepo;
    private final TopicRepo topicRepo;
    private final BookRepo bookRepo;
    private final BookCopyRepo bookCopyRepo;
    private final StudentRepo studentRepo;
    private final TeacherRepo teacherRepo;

    private final UserRepo userRepo;
    private final CheckoutRepo checkoutRepo;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(GenreRepo genreRepo, TopicRepo topicRepo, BookRepo bookRepo,
                           BookCopyRepo bookCopyRepo, UserRepo userRepo, CheckoutRepo checkoutRepo, StudentRepo studentRepo, TeacherRepo teacherRepo, PasswordEncoder passwordEncoder) {
        this.genreRepo = genreRepo;
        this.topicRepo = topicRepo;
        this.bookRepo = bookRepo;
        this.bookCopyRepo = bookCopyRepo;
        this.userRepo = userRepo;
        this.checkoutRepo = checkoutRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (genreRepo.count() > 0 || bookRepo.count() > 0 || userRepo.count() > 0) {
            return; // Assume already initialized
        }

        // Sample genres
        Genre adventure = genreRepo.save(new Genre("Adventure"));
        Genre fantasy = genreRepo.save(new Genre("Fantasy"));
        Genre mystery = genreRepo.save(new Genre("Mystery"));
        Genre historicalFiction = genreRepo.save(new Genre("Historical Fiction"));
        Genre realisticFiction = genreRepo.save(new Genre("Realistic Fiction"));
        Genre scienceFiction = genreRepo.save(new Genre("Science Fiction"));
        Genre biography = genreRepo.save(new Genre("Biography"));
        Genre informational = genreRepo.save(new Genre("Informational"));
        Genre graphicNovels = genreRepo.save(new Genre("Graphic Novels"));
        Genre poetry = genreRepo.save(new Genre("Poetry"));

        // Sample topics
        Topic animals = topicRepo.save(new Topic("Animals"));
        Topic dystopia = topicRepo.save(new Topic("Dystopia"));
        Topic friendship = topicRepo.save(new Topic("Friendship"));
        Topic adventureTopic = topicRepo.save(new Topic("Adventure"));
        Topic magic = topicRepo.save(new Topic("Magic"));
        Topic nature = topicRepo.save(new Topic("Nature"));
        Topic family = topicRepo.save(new Topic("Family"));
        Topic courage = topicRepo.save(new Topic("Courage"));
        Topic school = topicRepo.save(new Topic("School"));
        Topic sports = topicRepo.save(new Topic("Sports"));

        //Sample Books
        //Constructor:     public Book(String title, String author, String isbn, double readingLevel, String description) {

        Book book1 = new Book("Charlotte's Web", "E. B. White", "9780064400558", 3.5,
                "A pig named Wilbur and his friendship with a spider named Charlotte.");
        book1.setGenres(Set.of(adventure, realisticFiction));
        book1.setTopics(Set.of(animals, friendship));
        bookRepo.save(book1);

        Book book2 = new Book("The Giver", "Lois Lowry", "9780544336261", 6.2,
                "A boy discovers the dark secrets of his seemingly perfect society.");
        book2.setGenres(Set.of(scienceFiction, historicalFiction));
        book2.setTopics(Set.of(dystopia));
        bookRepo.save(book2);

        Book book3 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "9780439708180", 5.3,
                "A boy discovers he is a wizard and attends a magical school.");
        book3.setGenres(Set.of(fantasy));
        book3.setTopics(Set.of(magic, school));
        bookRepo.save(book3);

        Book book4 = new Book("Wonder", "R.J. Palacio", "9780375869020", 4.8,
                "A boy with a facial difference navigates school and teaches kindness.");
        book4.setGenres(Set.of(realisticFiction));
        book4.setTopics(Set.of(family, school));
        bookRepo.save(book4);

        Book book5 = new Book("Sea Turtles", "Laura Marsh", "9781426308534", 3.2,
                "An introduction to sea turtles with facts and photos.");
        book5.setGenres(Set.of(informational));
        book5.setTopics(Set.of(nature));
        bookRepo.save(book5);

        Book book6 = new Book("Holes", "Louis Sachar", "9780440414803", 4.2,
                "A boy is sent to a detention center where he must dig holes every day.");
        book6.setGenres(Set.of(adventure));
        book6.setTopics(Set.of(courage));
        bookRepo.save(book6);

        Book book7 = new Book("The One and Only Ivan", "Katherine Applegate", "9780061992254", 3.9,
                "A gorilla named Ivan finds hope and friendship.");
        book7.setGenres(Set.of(realisticFiction));
        book7.setTopics(Set.of(animals, friendship));
        bookRepo.save(book7);

        Book book8 = new Book("Diary of a Wimpy Kid", "Jeff Kinney", "9780810993136", 5.2,
                "A humorous diary of middle schooler Greg Heffley.");
        book8.setGenres(Set.of(graphicNovels));
        book8.setTopics(Set.of(school));
        bookRepo.save(book8);

        Book book9 = new Book("Esperanza Rising", "Pam Muñoz Ryan", "9780439120425", 5.5,
                "A girl must adjust to a new life after a family tragedy.");
        book9.setGenres(Set.of(historicalFiction));
        book9.setTopics(Set.of(family));
        bookRepo.save(book9);

        Book book10 = new Book("Scholastic Discover More: Dinosaurs", "Penelope Arlon", "9780545388780", 3.6,
                "A beginner-friendly guide to dinosaurs with rich visuals.");
        book10.setGenres(Set.of(informational));
        book10.setTopics(Set.of(nature));
        bookRepo.save(book10);

        // Additional low reading level books
        Book book11 = new Book("Don't Let the Pigeon Drive the Bus!", "Mo Willems", "9780786819881", 1.2,
                "A pigeon dreams of driving a bus and tries to convince the reader to let him.");
        book11.setGenres(Set.of(realisticFiction));
        book11.setTopics(Set.of(courage, school));
        bookRepo.save(book11);

        Book book12 = new Book("Brown Bear, Brown Bear, What Do You See?", "Bill Martin Jr.", "9780805047905", 0.9,
                "A rhythmic repetition of animals and colors for early readers.");
        book12.setGenres(Set.of(poetry));
        book12.setTopics(Set.of(animals));
        bookRepo.save(book12);

        Book book13 = new Book("Pete the Cat: I Love My White Shoes", "Eric Litwin", "9780061906220", 1.5,
                "Pete the Cat goes walking in his new white shoes and learns to go with the flow.");
        book13.setGenres(Set.of(realisticFiction));
        book13.setTopics(Set.of(friendship));
        bookRepo.save(book13);

        Book book14 = new Book("The Hobbit", "J.R.R. Tolkien", "9780547928227", 6.6,
                "Bilbo Baggins, a hobbit, embarks on a grand adventure to help dwarves reclaim their homeland.");
        book14.setGenres(Set.of(fantasy, adventure));
        book14.setTopics(Set.of(courage, adventureTopic));
        book14 = bookRepo.save(book14);

        //Book copies
        //Constructor:     public BookCopy(Book book, String simpleId, boolean available, String location)
        bookCopyRepo.save(new BookCopy(book1, "CW02", false, "Shelf A"));
        bookCopyRepo.save(new BookCopy(book2, "GV01", false, "Shelf B"));
        bookCopyRepo.save(new BookCopy(book3, "HP01", false, "Shelf C"));
        bookCopyRepo.save(new BookCopy(book3, "HP02", true, "Shelf C"));
        bookCopyRepo.save(new BookCopy(book4, "WD01", true, "Shelf A"));
        bookCopyRepo.save(new BookCopy(book5, "ST01", true, "Shelf B"));
        bookCopyRepo.save(new BookCopy(book6, "HL01", true, "Shelf C"));
        bookCopyRepo.save(new BookCopy(book6, "HL02", true, "Shelf C"));
        bookCopyRepo.save(new BookCopy(book7, "IV01", true, "Shelf A"));
        bookCopyRepo.save(new BookCopy(book8, "WK01", true, "Shelf B"));
        bookCopyRepo.save(new BookCopy(book9, "ER01", true, "Shelf A"));
        bookCopyRepo.save(new BookCopy(book9, "ER02", true, "Shelf A"));
        bookCopyRepo.save(new BookCopy(book9, "ER03", false, "Shelf A"));
        bookCopyRepo.save(new BookCopy(book10, "DN01", false, "Shelf B"));
        bookCopyRepo.save(new BookCopy(book11, "PG01", false, "Shelf D"));
        bookCopyRepo.save(new BookCopy(book12, "BB01", false, "Shelf D"));
        bookCopyRepo.save(new BookCopy(book13, "PC01", true, "Shelf D"));
        bookCopyRepo.save(new BookCopy(book14, "HB01", true, "Shelf D"));

        //Students
        //Constructor: public Student(String name, String email, String studentId, String pin, double readingLevel)
        Student student1 = new Student("Liam Carter", "liam.carter@example.com", "LC01", "1234", 5.0);
        Student student2 = new Student("Ava Johnson", "ava.johnson@example.com", "AJ02", "1234", 4.1);
        Student student3 = new Student("Noah Brown", "noah.brown@example.com", "NB03", "1234", 3.5);
        Student student4 = new Student("Emma Lee", "emma.lee@example.com", "EL04", "1234", 4.3);
        Student student5 = new Student("Oliver Smith", "oliver.smith@example.com", "OS05", "4567", 3.9);
        Student student6 = new Student("Sophia Davis", "sophia.davis@example.com", "SD06", "4567", 4.0);
        Student student7 = new Student("Ethan Wright", "ethan.wright@example.com", "EW07", "1111", 3.6);
        Student student8 = new Student("Mia Thompson", "mia.thompson@example.com", "MT08", "2222", 4.2);

//        System.out.println("Saving: " + student1.getName() + " — Class: " + student1.getClass().getName());

        studentRepo.save(student2);
        studentRepo.save(student3);
        studentRepo.save(student4);
        studentRepo.save(student5);
        studentRepo.save(student6);
        studentRepo.save(student7);
        studentRepo.save(student8);
        studentRepo.save(student1);

        //Teachers
        //Constructor: public Teacher(String name, String email, String username, String password, String subject, String classGrade, String classroom)
        String hashedPassword = passwordEncoder.encode("password123");
        String hashedPassword2 = passwordEncoder.encode("trouble");


        Teacher teacher1 = new Teacher("Ms. Anderson", "anderson@example.com", "anderson", hashedPassword, "Reading", "3rd", "Room 12");
        Teacher teacher2 = new Teacher("Mr. Bennett", "bennett@example.com", "bennett", hashedPassword2, "Science", "4th", "Room 15");

        teacherRepo.save(teacher1);
        teacherRepo.save(teacher2);


        //Checkouts
        //Constructor: public Checkout(User user, BookCopy bookCopy, LocalDate checkoutDate, LocalDate dueDate, LocalDate returnDate)

        BookCopy copy1 = bookCopyRepo.findBySimpleId("CW02").orElseThrow();
        BookCopy copy2 = bookCopyRepo.findBySimpleId("GV01").orElseThrow();
        BookCopy copy3 = bookCopyRepo.findBySimpleId("HP01").orElseThrow();
        BookCopy copy4 = bookCopyRepo.findBySimpleId("HP02").orElseThrow();
        BookCopy copy5 = bookCopyRepo.findBySimpleId("WD01").orElseThrow();
        BookCopy copy6 = bookCopyRepo.findBySimpleId("ST01").orElseThrow();
        BookCopy copy7 = bookCopyRepo.findBySimpleId("HL01").orElseThrow();
        BookCopy copy8 = bookCopyRepo.findBySimpleId("HL02").orElseThrow();
        BookCopy copy9 = bookCopyRepo.findBySimpleId("IV01").orElseThrow();
        BookCopy copy10 = bookCopyRepo.findBySimpleId("WK01").orElseThrow();
        BookCopy copy11 = bookCopyRepo.findBySimpleId("ER01").orElseThrow();
        BookCopy copy12 = bookCopyRepo.findBySimpleId("ER02").orElseThrow();
        BookCopy copy13 = bookCopyRepo.findBySimpleId("ER03").orElseThrow();
        BookCopy copy14 = bookCopyRepo.findBySimpleId("DN01").orElseThrow();
        BookCopy copy15 = bookCopyRepo.findBySimpleId("PG01").orElseThrow();
        BookCopy copy16 = bookCopyRepo.findBySimpleId("BB01").orElseThrow();
        BookCopy copy17 = bookCopyRepo.findBySimpleId("PC01").orElseThrow();
        BookCopy copy18 = bookCopyRepo.findBySimpleId("HB01").orElseThrow();


        checkoutRepo.save(new Checkout(student8, copy18, LocalDate.of(2025,3,15), LocalDate.of(2025,3,29), LocalDate.of(2025,4,1)));
        checkoutRepo.save(new Checkout(student8, copy7, LocalDate.of(2025,4,3), LocalDate.of(2025,4,17), LocalDate.of(2025,4,10)));
        checkoutRepo.save(new Checkout(student6, copy10, LocalDate.of(2025,4,10), LocalDate.of(2025,4,24), LocalDate.of(2025,4,22)));
        checkoutRepo.save(new Checkout(student2, copy4, LocalDate.of(2025,3,26), LocalDate.of(2025,4,10), LocalDate.of(2025,4,26)));
        checkoutRepo.save(new Checkout(student4, copy18, LocalDate.of(2025,4,18), LocalDate.of(2025,5,2), LocalDate.of(2025,4,30)));
        checkoutRepo.save(new Checkout(student4, copy5, LocalDate.of(2025,3,29), LocalDate.of(2025,4,13), LocalDate.of(2025,4,8)));
        checkoutRepo.save(new Checkout(student7, copy10, LocalDate.of(2025,5,5), LocalDate.of(2025,5,19), LocalDate.of(2025,5,10)));
        checkoutRepo.save(new Checkout(student7, copy11, LocalDate.of(2025,5,11), LocalDate.of(2025,5,25), LocalDate.of(2025,5,21)));
        checkoutRepo.save(new Checkout(student4, copy6, LocalDate.of(2025,4,30), LocalDate.of(2025,5,14), LocalDate.of(2025,5,6)));
        checkoutRepo.save(new Checkout(student3, copy15, LocalDate.of(2025,4,14), LocalDate.of(2025,4,28), LocalDate.of(2025,5,8)));
        checkoutRepo.save(new Checkout(student4, copy11, LocalDate.of(2025,6,6), LocalDate.of(2025,6,20), LocalDate.of(2025,6,12)));
        checkoutRepo.save(new Checkout(student6, copy11, LocalDate.of(2025,6,8), LocalDate.of(2025,6,22), LocalDate.of(2025,6,14)));
        checkoutRepo.save(new Checkout(student1, copy12, LocalDate.of(2025,6,12), LocalDate.of(2025,6,26), LocalDate.of(2025,6,19)));
        checkoutRepo.save(new Checkout(student3, copy3, LocalDate.of(2025,5,23), LocalDate.of(2025,6,7), LocalDate.of(2025,6,7)));
        checkoutRepo.save(new Checkout(student1, copy3, LocalDate.of(2025,4,16), LocalDate.of(2025,4,30), null));
        checkoutRepo.save(new Checkout(student2, copy2, LocalDate.of(2025,6,15), LocalDate.of(2025,6,29), null));
        checkoutRepo.save(new Checkout(student1, copy15, LocalDate.of(2025,6,24), LocalDate.of(2025,7,8), null));
        checkoutRepo.save(new Checkout(student2, copy14, LocalDate.of(2025,6,18), LocalDate.of(2025,7,2), null));
        checkoutRepo.save(new Checkout(student3, copy1, LocalDate.of(2025,6,15), LocalDate.of(2025,6,29), null));
        checkoutRepo.save(new Checkout(student3, copy16, LocalDate.of(2025,6,30), LocalDate.of(2025,7,15), null));
        checkoutRepo.save(new Checkout(student6, copy13, LocalDate.of(2025,5,17), LocalDate.of(2025,7,1), null));





        System.out.println("✅ Sample data loaded.");
    }

    }