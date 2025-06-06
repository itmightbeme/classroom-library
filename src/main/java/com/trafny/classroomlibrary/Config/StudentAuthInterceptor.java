package com.trafny.classroomlibrary.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class StudentAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("loggedInStudent") != null;

        if (!isLoggedIn) {
            response.sendRedirect("/students/login");
            return false; // Stop request from going to the controller
        }

        return true; // Allow the request to continue
    }
}
