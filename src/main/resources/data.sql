INSERT INTO genre (name) VALUES
                             ('Adventure'),
                             ('Fantasy'),
                             ('Mystery'),
                             ('Historical Fiction'),
                             ('Realistic Fiction'),
                             ('Science Fiction'),
                             ('Biography'),
                             ('Informational'),
                             ('Graphic Novels'),
                             ('Poetry');

INSERT INTO topic (name) VALUES
                                  ('Animals'),
                                  ('Dystopia'),
                                  ('Friendship'),
                                  ('Adventure'),
                                  ('Magic'),
                                  ('Nature'),
                                  ('Family'),
                                  ('Courage'),
                                  ('School'),
                                  ('Sports');

INSERT INTO book (title, author, isbn, reading_level, description) VALUES
                                                                       ('Charlotte''s Web', 'E. B. White', '9780064400558', 3.5, 'A pig named Wilbur and his friendship with a spider named Charlotte.'),
                                                                       ('The Giver', 'Lois Lowry', '9780544336261', 6.2, 'A boy discovers the dark secrets of his seemingly perfect society.'),
                                                                       ('Holes', 'Louis Sachar', '9780440414803', 4.2, 'A boy is sent to a detention center where he must dig holes every day.'),
                                                                       ('The One and Only Ivan', 'Katherine Applegate', '9780061992254', 3.9, 'A gorilla named Ivan finds hope and friendship.'),
                                                                       ('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '9780439708180', 5.3, 'A boy discovers he is a wizard and attends a magical school.'),
                                                                       ('Wonder', 'R.J. Palacio', '9780375869020', 4.8, 'A boy with a facial difference navigates school and teaches kindness.'),
                                                                       ('Diary of a Wimpy Kid', 'Jeff Kinney', '9780810993136', 5.2, 'A humorous diary of middle schooler Greg Heffley.'),
                                                                       ('Esperanza Rising', 'Pam Muñoz Ryan', '9780439120425', 5.5, 'A girl must adjust to a new life after a family tragedy.'),
                                                                       ('National Geographic Kids: Sea Turtles', 'Laura Marsh', '9781426308534', 3.2, 'An introduction to sea turtles with facts and photos.'),
                                                                       ('Scholastic Discover More: Dinosaurs', 'Penelope Arlon', '9780545388780', 3.6, 'A beginner-friendly guide to dinosaurs with rich visuals.');

INSERT INTO book_genres (book_id, genre_id) VALUES
                                                (1, 1), (1, 5),
                                                (2, 6), (2, 4),
                                                (3, 1),
                                                (4, 5),
                                                (5, 2),
                                                (6, 5),
                                                (7, 9),
                                                (8, 4),
                                                (9, 8),
                                                (10, 8);

INSERT INTO book_topics (book_id, topic_id) VALUES
                                                     (1, 1), (1, 3),
                                                     (2, 2),
                                                     (3, 4),
                                                     (4, 1),
                                                     (5, 5),
                                                     (6, 7), (6, 9),
                                                     (7, 9),
                                                     (8, 7),
                                                     (9, 6),
                                                     (10, 6);

INSERT INTO book_copy (book_id, simple_id, available, location) VALUES
                                                                    (1, 'CW01', true, 'Shelf A'),
                                                                    (1, 'CW02', true, 'Shelf A'),
                                                                    (2, 'GV01', true, 'Shelf B'),
                                                                    (2, 'GV02', false, 'Shelf B'),
                                                                    (3, 'HL01', true, 'Shelf C'),
                                                                    (4, 'IV01', true, 'Shelf C'),
                                                                    (5, 'HP01', true, 'Shelf D'),
                                                                    (5, 'HP02', true, 'Shelf D'),
                                                                    (6, 'WD01', true, 'Shelf E'),
                                                                    (7, 'WK01', true, 'Shelf E'),
                                                                    (8, 'ER01', true, 'Shelf F'),
                                                                    (9, 'ST01', true, 'Shelf G'),
                                                                    (9, 'ST02', false, 'Shelf G'),
                                                                    (10, 'DN01', true, 'Shelf G');

INSERT INTO users (dtype, name, email, username, password, subject, class_grade, classroom, student_id, pin, reading_level) VALUES
                                                                                                                                    ('STUDENT', 'Liam Carter', 'liam.carter@example.com', NULL, NULL, NULL, NULL, NULL, 'LC01', '1234', 3.8),
                                                                                                                                    ('STUDENT', 'Ava Johnson', 'ava.johnson@example.com', NULL, NULL, NULL, NULL, NULL, 'AJ02', '1234', 4.1),
                                                                                                                                    ('STUDENT', 'Noah Brown', 'noah.brown@example.com', NULL, NULL, NULL, NULL, NULL, 'NB03', '1234', 3.5),
                                                                                                                                    ('STUDENT', 'Emma Lee', 'emma.lee@example.com', NULL, NULL, NULL, NULL, NULL, 'EL04', '1234', 4.3),
                                                                                                                                    ('STUDENT', 'Oliver Smith', 'oliver.smith@example.com', NULL, NULL, NULL, NULL, NULL, 'OS05', '4567', 3.9),
                                                                                                                                    ('STUDENT', 'Sophia Davis', 'sophia.davis@example.com', NULL, NULL, NULL, NULL, NULL, 'SD06', '4567', 4.0),
                                                                                                                                    ('TEACHER', 'Ms. Thompson', 'thompson@example.com', 'thompson', '$2a$10$SNPSWiycSJiyUmgSFfm6M.2g3ZeU3gd2RdumgPDjhHGbKfRdtjIcK', 'Reading', '3rd', 'Room 12', NULL, NULL, NULL),
                                                                                                                                    ('TEACHER', 'Mr. Bennett', 'bennett@example.com', 'bennett', '$2a$10$jEDIyjvbxw/8YrpxKm.XmeBiKp6cgPlUmZ2bi4a7gv7b.JNh9Aev6', 'Science', '4th', 'Room 15', NULL, NULL, NULL),
                                                                                                                                    ('TEACHER', 'Ms. Anderson', 'anderson@example.com', 'anderson', '$2a$10$VtLJRDDRUfsu1k9rFZ6BNu4VHXg5TKAxjWzWmH5N342zOgGMTxDpm', 'Math', '5th', 'Room 18', NULL, NULL, NULL);

INSERT INTO checkout (book_copy_id, user_id, checkout_date, due_date, return_date) VALUES
                                                                                       (14, 1, '2025-04-29', '2025-05-13', NULL),
                                                                                       (12, 1, '2025-05-18', '2025-06-01', '2025-05-24'),
                                                                                       (2, 1, '2025-04-16', '2025-04-30', NULL),
                                                                                       (8, 1, '2025-04-21', '2025-05-05', NULL),
                                                                                       (8, 1, '2025-05-20', '2025-06-03', '2025-06-07'),
                                                                                       (1, 2, '2025-03-21', '2025-04-04', NULL),
                                                                                       (3, 2, '2025-04-06', '2025-04-20', NULL),
                                                                                       (5, 2, '2025-03-22', '2025-04-05', '2025-04-04'),
                                                                                       (2, 2, '2025-05-11', '2025-05-25', NULL),
                                                                                       (14, 2, '2025-04-16', '2025-04-30', NULL),
                                                                                       (8, 3, '2025-05-18', '2025-06-01', NULL),
                                                                                       (7, 3, '2025-04-27', '2025-05-11', '2025-04-28'),
                                                                                       (14, 3, '2025-03-09', '2025-03-23', NULL),
                                                                                       (7, 4, '2025-04-10', '2025-04-24', '2025-04-30'),
                                                                                       (6, 4, '2025-03-22', '2025-04-05', NULL),
                                                                                       (3, 4, '2025-04-24', '2025-05-08', '2025-05-12'),
                                                                                       (2, 5, '2025-04-30', '2025-05-14', '2025-05-17'),
                                                                                       (12, 5, '2025-04-29', '2025-05-13', NULL),
                                                                                       (13, 5, '2025-03-08', '2025-03-22', NULL),
                                                                                       (10, 6, '2025-05-03', '2025-05-17', NULL),
                                                                                       (4, 6, '2025-03-11', '2025-03-25', '2025-03-24'),
                                                                                       (7, 6, '2025-03-10', '2025-03-24', NULL),
                                                                                       (1, 6, '2025-05-04', '2025-05-18', '2025-05-24');

