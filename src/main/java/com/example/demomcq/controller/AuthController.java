package com.example.demomcq.controller;

import com.example.demomcq.model.User;
import com.example.demomcq.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class AuthController {

    private final UserRepo userRepository;
    private int userId = 0;

    public AuthController(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "signin";
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");
        if(!Objects.equals(email, "") && email != null) {
            if (Objects.equals(role, "TEACHER")) {
                return "redirect:/teacher-dashboard";
            } else if (Objects.equals(role, "STUDENT")) {
                model.addAttribute("email", email);
                return "redirect:/student-dashboard";
            }
        }
       return "redirect:/login";
    }

    @GetMapping("/teacher-dashboard")
    public String teacher() {
        return "teacher-index";
    }

    @GetMapping("/student-dashboard")
    public String student() {
        return "student-index";
    }

    @PostMapping("/login/try")
    public String login(
            HttpSession session,
            @RequestParam String email,
            @RequestParam String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            if(user.getStatus() == 1) {
                this.userId = user.getId();
                if (Objects.equals(user.getRole(), "TEACHER")) {
                    session.setAttribute("role", "TEACHER");
                    session.setAttribute("email", email);
                    this.userId = user.getId();
                    return "redirect:/teacher-dashboard";
                } else if (Objects.equals(user.getRole(), "STUDENT")) {
                    session.setAttribute("role", "STUDENT");
                    session.setAttribute("email", email);
                    return "redirect:/student-dashboard";
                }
            }
            else return "redirect:/login";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("role");
        session.removeAttribute("email");
        session.invalidate();
        return "redirect:/login";
    }
}
