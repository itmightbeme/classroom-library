package com.trafny.classroomlibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ClassroomLibraryApplication {


    public static void main(String[] args) {
        SpringApplication.run(ClassroomLibraryApplication.class, args);

        System.out.println("hello");

    }
}
