package com.example.demomcq.controller;

import com.example.demomcq.model.QuestionForm;
import com.example.demomcq.model.Result;
import com.example.demomcq.model.User;
import com.example.demomcq.repository.UserRepo;
import com.example.demomcq.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class StudentController {
	
	@Autowired
	Result result;

	@Autowired
	UserRepo userRepo;

	@Autowired
	QuizService qService;
	
	Boolean submitted = false;
	
	@ModelAttribute("result")
	public Result getResult() {
		return result;
	}

	@PostMapping("/quiz")
	public String quiz(Model m, RedirectAttributes ra) {
		submitted = false;
		QuestionForm qForm = qService.getQuestions();
		m.addAttribute("qForm", qForm);
		return "quiz";
	}

	@PostMapping("/submit")
	public String submit(HttpSession session, @ModelAttribute QuestionForm qForm, Model m) {
		if(!submitted) {
			String email = (String)session.getAttribute("email");
			User user = userRepo.findByEmail(email);
			result.setTotalCorrect(qService.getResult(qForm));
			result.setStatus(1);
			result.setUser(user);
			result.setSubmittedDate(LocalDateTime.now());
			qService.saveScore(result);
			submitted = true;
		}
		result = new Result();
		submitted = false;

		return "result";
	}

	@GetMapping("/scoreboard")
	public String score(Model m) {
		List<Result> sList = qService.getTopScore();
		m.addAttribute("sList", sList);
		return "scoreboard";
	}
}
