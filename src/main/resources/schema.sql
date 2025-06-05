CREATE TABLE genre (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE book_topic (
                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE book (
                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      isbn VARCHAR(50),
                      reading_level DOUBLE,
                      description TEXT
);

CREATE TABLE book_genres (
                             book_id BIGINT NOT NULL,
                             genre_id BIGINT NOT NULL,
                             FOREIGN KEY (book_id) REFERENCES book(id),
                             FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE TABLE book_topics (
                             book_id BIGINT NOT NULL,
                             book_topic_id BIGINT NOT NULL,
                             FOREIGN KEY (book_id) REFERENCES book(id),
                             FOREIGN KEY (book_topic_id) REFERENCES book_topic(id)
);

CREATE TABLE book_copy (
                           id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           book_id BIGINT NOT NULL,
                           simple_id VARCHAR(255) NOT NULL UNIQUE,
                           available BOOLEAN,
                           location VARCHAR(255),
                           FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE users (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       dtype VARCHAR(31),
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255),
                       username VARCHAR(255),
                       password VARCHAR(255),
                       subject VARCHAR(255),
                       class_grade VARCHAR(255),
                       classroom VARCHAR(255),
                       student_id VARCHAR(255),
                       pin VARCHAR(255),
                       reading_level DOUBLE
);

CREATE TABLE checkout (
                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          book_copy_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          checkout_date DATE NOT NULL,
                          due_date DATE NOT NULL,
                          return_date DATE,
                          FOREIGN KEY (book_copy_id) REFERENCES book_copy(id),
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

