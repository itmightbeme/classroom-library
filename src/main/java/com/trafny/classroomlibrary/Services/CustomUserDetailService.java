package com.trafny.classroomlibrary.Services;

import com.trafny.classroomlibrary.Entities.Teacher;
import com.trafny.classroomlibrary.Repositories.TeacherRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final TeacherRepo teacherRepo;

    public CustomUserDetailService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🚨 Inside loadUserByUsername with: " + username);

    Teacher teacher = teacherRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found"));

        System.out.println("DB hash: " + teacher.getPassword());



        return User.builder()
                .username(teacher.getUsername())
                .password(teacher.getPassword())
                .roles("TEACHER") // This must match what you use in SecurityConfig
                .build();
    }
}
