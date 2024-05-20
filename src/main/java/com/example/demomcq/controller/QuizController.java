package com.example.demomcq.controller;

import com.example.demomcq.model.Question;
import com.example.demomcq.model.Quiz;
import com.example.demomcq.model.Theme;
import com.example.demomcq.model.User;
import com.example.demomcq.repository.QuestionRepo;
import com.example.demomcq.repository.QuizRepo;
import com.example.demomcq.repository.ThemeRepo;
import com.example.demomcq.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quizzes")
public class QuizController {
    private final QuestionRepo questionRepo;
    private final QuizRepo quizRepo;
    private final ThemeRepo themeRepo;
    private final UserRepo userRepo;

    public QuizController(QuestionRepo questionRepo, QuizRepo quizRepo, ThemeRepo themeRepo, UserRepo userRepo) {
        this.questionRepo = questionRepo;
        this.quizRepo = quizRepo;
        this.themeRepo = themeRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("")
    public String list(HttpSession session, Model model) {
        List<Quiz> quizzes = quizRepo.findAll();
        model.addAttribute("quizzes", quizzes);
        return "quizzes";
    }

    @GetMapping("/form-create-quiz")
    public String getFormCreate(Model model){
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("questions", questionRepo.findAll());
        return "insertQuiz";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "quiz") Quiz quiz, @RequestParam List<Integer> questionIds){
        List<Question> selectedQuestions = questionRepo.findAllById(questionIds);
        quiz.setQuestions(selectedQuestions);
        quiz.setDate(LocalDateTime.now());
        quizRepo.save(quiz);
        return "redirect:/quizzes";
    }

    @GetMapping("form-update-quiz/{id}")
    public String getFormUpdate(@PathVariable("id") int id, Model model){
        Quiz quiz = quizRepo.findById(id).get();
        List<Question> questions = questionRepo.findAll();
        quiz.setQuestionIds(quiz.getQuestions().stream().map(Question::getQuesId).collect(Collectors.toList())); // Populate questionIds
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        return "updateQuiz";
    }

    @PostMapping("/update")
    public String update(HttpSession session,  @ModelAttribute(name = "quiz") Quiz quiz, @RequestParam("questionIds") List<Integer> questionIds){
        List<Question> selectedQuestions = new ArrayList<>(questionRepo.findAllById(questionIds));
        quiz.setQuestions(selectedQuestions);
        quiz.setDate(LocalDateTime.now());
        quizRepo.save(quiz);
        return "redirect:/quizzes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        Quiz quiz = quizRepo.findById(id).get();
        quiz.setStatus(0);
        quizRepo.save(quiz);
        return "redirect:/quizzes";
    }

}
