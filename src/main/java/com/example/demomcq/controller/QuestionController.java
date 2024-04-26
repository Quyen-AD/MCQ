package com.example.demomcq.controller;

import com.example.demomcq.model.Question;
import com.example.demomcq.model.Theme;
import com.example.demomcq.model.User;
import com.example.demomcq.repository.QuestionRepo;
import com.example.demomcq.repository.ThemeRepo;
import com.example.demomcq.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionRepo questionRepo;
    private final ThemeRepo themeRepo;
    private final UserRepo userRepo;

    public QuestionController(QuestionRepo questionRepo, ThemeRepo themeRepo, UserRepo userRepo) {
        this.questionRepo = questionRepo;
        this.themeRepo = themeRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("")
    public String list(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        List<Question> questions = questionRepo.findAllByAuthor_Email(email);
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping("/form-create-question")
    public String getFormCreate(Model model){
        Question question = new Question();
        List<Theme> themes = themeRepo.findAll();
        model.addAttribute("question",question);
        model.addAttribute("themes",themes);
        return "insertQuestion";
    }

    @PostMapping("/save")
    public String save(HttpSession session, @ModelAttribute(name = "question") Question question, @RequestParam("themeId") Integer themeId){
        String email = (String) session.getAttribute("email");
        User user = userRepo.findByEmail(email);
        Theme theme = themeRepo.findById(themeId).get();
        question.setTheme(theme);
        question.setAuthor(user);
        questionRepo.save(question);
        return "redirect:/question";
    }

    @GetMapping("form-update-question/{id}")
    public String getFormUpdate(@PathVariable("id") int id, Model model){
        Question question = questionRepo.findById(id).get();
        List<Theme> themes = themeRepo.findAll();
        model.addAttribute("question",question);
        model.addAttribute("themes",themes);
        return "updateQuestion";
    }

    @PostMapping("/update")
    public String update(HttpSession session, @ModelAttribute(name = "question") Question question, @RequestParam("themeId") Integer themeId){
        Theme theme = themeRepo.findById(themeId).get();
        String email = (String) session.getAttribute("email");
        User user = userRepo.findByEmail(email);
        question.setTheme(theme);
        question.setAuthor(user);
        questionRepo.save(question);
        return "redirect:/question";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        Question question = questionRepo.findById(id).get();
        question.setStatus(0);
        questionRepo.save(question);
        return "redirect:/question";
    }

}
